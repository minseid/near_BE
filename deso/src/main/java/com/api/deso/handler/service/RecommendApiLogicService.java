package com.api.deso.handler.service;

import com.api.deso.dto.EventDtoClass;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Banner;
import com.api.deso.model.entity.EventPostReview;
import com.api.deso.model.entity.Recommend;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.BannerApiRequest;
import com.api.deso.model.network.request.EventPostReviewApiRequest;
import com.api.deso.model.network.request.RecommendApiRequest;
import com.api.deso.model.network.response.BannerApiResponse;
import com.api.deso.model.network.response.RecommendApiResponse;
import com.api.deso.repository.BannerRepository;
import com.api.deso.repository.EventRepository;
import com.api.deso.repository.RecommendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecommendApiLogicService implements CrudInterface<RecommendApiRequest, RecommendApiResponse> {

    @Autowired
    RecommendRepository recommendRepository;

    @Autowired
    EventRepository eventRepository;

    @Override
    public Header<RecommendApiResponse> create(Header<RecommendApiRequest> request) {

        RecommendApiRequest RecommendApiRequest = request.getData();

        Recommend recommend = Recommend.builder()
                .content(RecommendApiRequest .getContent())
                .event(eventRepository.findById(RecommendApiRequest.getEventId()).get())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Recommend newRecommend =recommendRepository.save(recommend);

        return response(newRecommend);
    }

    @Override
    public Header<RecommendApiResponse> read(Long id) {

        return recommendRepository.findById(id)
                .map(recommend -> response(recommend))
                .orElseGet(
                        () -> Header.ERROR("NULL")
                );
    }

    @Override
    public Header<RecommendApiResponse> update(Header<RecommendApiRequest> request) {
        RecommendApiRequest recommendApiRequest = request.getData();

        Recommend recommend = recommendRepository.findById(recommendApiRequest.getId()).get();
        Recommend recommend1 = Recommend.builder()
                .id(recommend.getId())
                .content(recommendApiRequest.getContent())
                .createdAt(recommend.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .event(recommend.getEvent())
                .build();

        Recommend newRecommend = recommendRepository.save(recommend1);
        return response(newRecommend);
    }

    @Override
    public Header<RecommendApiResponse> delete(Long id) {
        recommendRepository.deleteById(id);
        return Header.OK();
    }

    public List<RecommendApiResponse> all() {
        List<Recommend> recommendList = recommendRepository.findAll();
        List<RecommendApiResponse> recommendApiResponseList = new ArrayList<>();
        for(int i=0;i<recommendList.size();i++){

            EventDtoClass.RecommendDto recommendDto = EventDtoClass.RecommendDto.builder()
                    .id(recommendList.get(i).getEvent().getId())
                    .title(recommendList.get(i).getEvent().getTitle())
                    .category(recommendList.get(i).getEvent().getCategory())
                    .startDate(recommendList.get(i).getEvent().getStartDate())
                    .endDate(recommendList.get(i).getEvent().getEndDate())
                    .location(recommendList.get(i).getEvent().getEventPlace().getLocation())
                    .description(recommendList.get(i).getEvent().getDescription())
                    .src(recommendList.get(i).getEvent().getEventImageList().get(0).getSrc())
                    .build();

            RecommendApiResponse recommendApiResponse = RecommendApiResponse.builder()
                    .id(recommendList.get(i).getId())
                    .content(recommendList.get(i).getContent())
                    .eventId(recommendList.get(i).getEvent().getId())
                    .createdAt(recommendList.get(i).getCreatedAt())
                    .updatedAt(recommendList.get(i).getUpdatedAt())
                    .eventDto(recommendDto)
                    .build();

            recommendApiResponseList.add(recommendApiResponse);
        }
        return recommendApiResponseList;

    }

    private Header<RecommendApiResponse> response(Recommend recommend) {

        EventDtoClass.RecommendDto recommendDto = EventDtoClass.RecommendDto.builder()
                .id(recommend.getEvent().getId())
                .title(recommend.getEvent().getTitle())
                .category(recommend.getEvent().getCategory())
                .startDate(recommend.getEvent().getStartDate())
                .endDate(recommend.getEvent().getEndDate())
                .location(recommend.getEvent().getEventPlace().getLocation())
                .description(recommend.getEvent().getDescription())
                .src(recommend.getEvent().getEventImageList().get(0).getSrc())
                .build();
        RecommendApiResponse recommendApiResponse = RecommendApiResponse.builder()
                .id(recommend.getId())
                .content(recommend.getContent())
                .eventId(recommend.getEvent().getId())
                .eventDto(recommendDto)
                .createdAt(recommend.getCreatedAt())
                .updatedAt(recommend.getUpdatedAt())
                .build();

        return Header.OK(recommendApiResponse);
    }
}
