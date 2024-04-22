package com.api.deso.handler.service;

import com.api.deso.dto.EventDtoClass;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Banner;
import com.api.deso.model.entity.EventImage;
import com.api.deso.model.entity.Notice;
import com.api.deso.model.entity.NoticeImage;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.BannerApiRequest;
import com.api.deso.model.network.request.NoticeApiRequest;
import com.api.deso.model.network.response.NoticeApiResponse;
import com.api.deso.repository.NoticeImageRepository;
import com.api.deso.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeApiLogicService implements CrudInterface<NoticeApiRequest, NoticeApiResponse> {

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    NoticeImageRepository noticeImageRepository;
    @Override
    public Header<NoticeApiResponse> create(Header<NoticeApiRequest> request) {
        NoticeApiRequest noticeApiRequest = request.getData();
        Notice notice = Notice.builder()
                .title(noticeApiRequest.getTitle())
                .content(noticeApiRequest.getContent())
                .userId(noticeApiRequest.getUserId())
                .link(noticeApiRequest.getLink())
                .type(noticeApiRequest.getType())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        List<NoticeImage> noticeImageList = new ArrayList<>();
        Notice newNotice = noticeRepository.save(notice);

        if(noticeApiRequest.getImages() != null) {
            noticeApiRequest.getImages().stream()
                    .forEach(image -> {
                        NoticeImage noticeImage = NoticeImage.builder()
                                .src(image)
                                .notice(newNotice)
                                .build();
                        noticeImageList .add(noticeImage);
                        noticeImageRepository.save(noticeImage);
                    });
        }

        return response(newNotice);
    }


    @Override
    public Header<NoticeApiResponse> read(Long id) {
        return noticeRepository.findById(id)
                .map(notice -> response(notice))
                .orElseGet(
                        () -> Header.ERROR("NULL")
                );
    }

    @Override
    public Header<NoticeApiResponse> update(Header<NoticeApiRequest> request) {
        NoticeApiRequest noticeApiRequest = request.getData();
        Notice notice = noticeRepository.findById(noticeApiRequest.getId()).get();
        Notice notice1 = Notice.builder()
                .id(noticeApiRequest.getId())
                .title(noticeApiRequest.getTitle())
                .content(noticeApiRequest.getContent())
                .userId(noticeApiRequest.getUserId())
                .link(noticeApiRequest.getLink())
                .type(noticeApiRequest.getType())
                .createdAt(notice.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .noticeImageList(notice.getNoticeImageList())
                .build();

        Notice newNotice = noticeRepository.save(notice1);

        return response(newNotice);
    }

    @Override
    public Header<NoticeApiResponse> delete(Long id) {
        noticeRepository.deleteById(id);
        return Header.OK();
    }

    public List<Notice> all() {
        List<Notice> noticeList = noticeRepository.OrderByCreatedAt();
        return noticeList;
    }

    private Header<NoticeApiResponse> response(Notice notice) {

        List<NoticeImage> noticeImageList = notice.getNoticeImageList();
        List<EventDtoClass.NoticeImageDto> noticeImageDtoList = new ArrayList<>();

        for(int i=0;i<noticeImageDtoList.size();i++){

            EventDtoClass.NoticeImageDto noticeImageDto = new EventDtoClass.NoticeImageDto();
            noticeImageDto.setId(noticeImageList.get(i).getId());
            noticeImageDto.setSrc(noticeImageList.get(i).getSrc());
            noticeImageDtoList.add(noticeImageDto);
        }

        NoticeApiResponse noticeApiResponse = NoticeApiResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .userId(notice.getUserId())
                .link(notice.getLink())
                .type(notice.getType())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .noticeImageDtoList(noticeImageDtoList)
                .build();
        return Header.OK(noticeApiResponse);
    }
}
