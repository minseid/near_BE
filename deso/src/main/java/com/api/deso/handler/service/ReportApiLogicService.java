package com.api.deso.handler.service;

import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.*;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.ReportApiRequest;
import com.api.deso.model.network.response.NoticeApiResponse;
import com.api.deso.model.network.response.ReportApiResponse;
import com.api.deso.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReportApiLogicService implements CrudInterface<ReportApiRequest, ReportApiResponse> {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    EventReviewRepository eventReviewRepository;

    @Override
    public Header<ReportApiResponse> create(Header<ReportApiRequest> request) {

        ReportApiRequest reportApiRequest = request.getData();

        Report report = reportRepository.findByUserIdAndTargetIdAndType(
                reportApiRequest.getUserId(),
                reportApiRequest.getTargetId(),
                reportApiRequest.getType()
        );

        if(report == null) {
            Report newReport = Report.builder()
                            .userId(reportApiRequest.getUserId())
                            .targetId(reportApiRequest.getTargetId())
                            .content(reportApiRequest.getContent())
                            .type(reportApiRequest.getType())
                            .createdAt(LocalDateTime.now())
                    .build();
            Report resultReport = reportRepository.save(newReport);

        if (reportApiRequest.getType().equals( "user")) {
                Optional<User> user = userRepository.findById(reportApiRequest.getTargetId());
                user.map(user1 -> {
                    user1.setWarning(user1.getWarning() + 1);
                    userRepository.save(user1);
                    return null;
                });
            }
            else if (reportApiRequest.getType().equals("eventReview")) {
                Optional<EventReview> eventReview = eventReviewRepository.findById(reportApiRequest.getTargetId());
                eventReview.map(eventReview1 -> {
                    eventReview1.setWarning(eventReview1.getWarning() + 1);
                    eventReviewRepository.save(eventReview1);
                    return null;
                });
            }


            return response(resultReport);
        } else { return Header.ERROR("EXIST"); }

    }

    @Override
    public Header<ReportApiResponse> read(Long id) {
        return reportRepository.findById(id)
                .map(report -> response(report))
                .orElseGet(() -> Header.ERROR("NOT EXIST"));
    }

    @Override
    public Header<ReportApiResponse> update(Header<ReportApiRequest> request) {
        return null;
    }

    @Override
    public Header<ReportApiResponse> delete(Long id) {
        reportRepository.deleteById(id);
        return Header.OK();
    }

    public List<Report> all() {
        List<Report> reportList = reportRepository.OrderByCreatedAt();
        return reportList;
    }

    private Header<ReportApiResponse> response(Report report) {

        ReportApiResponse reportApiResponse = ReportApiResponse.builder()
                .id(report.getId())
                .userId(report.getUserId())
                .targetId(report.getTargetId())
                .content(report.getContent())
                .type(report.getType())
                .createdAt(report.getCreatedAt())
                .build();
        return Header.OK(reportApiResponse);
    }
}
