package com.api.deso.handler.service;

import com.api.deso.model.entity.*;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.ImageUpdateRequest;
import com.api.deso.model.network.request.NoticeApiRequest;
import com.api.deso.model.network.response.NoticeApiResponse;
import com.api.deso.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUpdateService {

    private final EventImageRepository eventImageRepository;
    private final EventRepository eventRepository;

    private final  AwsS3Service awsS3Service;
    private final EventReviewImageRepository eventReviewImageRepository;
    private final EventReviewRepository eventReviewRepository;

    private final  NoticeRepository noticeRepository;

    private final NoticeImageRepository noticeImageRepository;

    private final PromotionImageRepository promotionImageRepository;
    public Header create(Header<ImageUpdateRequest> request) {
        ImageUpdateRequest imageUpdateRequest = request.getData();
         if (Objects.equals(imageUpdateRequest.getType(), "event")) {
            if (eventRepository.findById(imageUpdateRequest.getId()).isPresent()) {
                EventImage eventImage = EventImage.builder()
                        .event(eventRepository.findById(imageUpdateRequest.getId()).get())
                        .src(imageUpdateRequest.getSrc())
                        .build();
                eventImageRepository.save(eventImage);
                return Header.OK();
            } else {
                return Header.ERROR("NULL EVENT");
            }
        }
        else if (Objects.equals(imageUpdateRequest.getType(), "promotion")) {
            if (eventRepository.findById(imageUpdateRequest.getId()).isPresent()) {
                PromotionImage promotionImage = PromotionImage.builder()
                        .event(eventRepository.findById(imageUpdateRequest.getId()).get())
                        .src(imageUpdateRequest.getSrc())
                        .build();
                promotionImageRepository.save(promotionImage);
                return Header.OK();
            } else {
                return Header.ERROR("NULL EVENT");
            }
        }
        else if (Objects.equals(imageUpdateRequest.getType(), "eventReview")) {
            if (eventReviewRepository.findById(imageUpdateRequest.getId()).isPresent()) {
                EventReviewImage eventReviewImage = EventReviewImage.builder()
                        .eventReview(eventReviewRepository.findById(imageUpdateRequest.getId()).get())
                        .src(imageUpdateRequest.getSrc())
                        .build();
                eventReviewImageRepository.save(eventReviewImage);
                return Header.OK();
            } else {
                return Header.ERROR("NULL EVENT REVIEW");
            }
        }

        else if (Objects.equals(imageUpdateRequest.getType(), "notice")) {
            if (noticeRepository.findById(imageUpdateRequest.getId()).isPresent()) {
                NoticeImage noticeImage = NoticeImage.builder()
                        .notice(noticeRepository.findById(imageUpdateRequest.getId()).get())
                        .src(imageUpdateRequest.getSrc())
                        .build();
                noticeImageRepository.save(noticeImage);
                return Header.OK();
            } else {
                return Header.ERROR("NULL NOTICE");
            }
        }

        return Header.ERROR("NULL TYPE");

    }

    public Header delete(Header<ImageUpdateRequest> request) {
        ImageUpdateRequest imageUpdateRequest = request.getData();
        String origin = "https://deso-spring.s3.ap-northeast-2.amazonaws.com/";
        if (Objects.equals(imageUpdateRequest.getType(), "event")) {
            if (eventRepository.findById(imageUpdateRequest.getId()).isPresent()) {
               EventImage eventImage =  eventImageRepository.findById(imageUpdateRequest.getImgId()).orElse(null);
               if(eventImage==null)
                   return Header.ERROR("NULL image");
                awsS3Service.deleteImage(eventImage.getSrc().split(origin)[1]);
                eventImageRepository.deleteById(imageUpdateRequest.getImgId());
                return Header.OK();
            } else {
                return Header.ERROR("NULL EVENT");
            }
        }
        else if (Objects.equals(imageUpdateRequest.getType(), "promotion")) {
            if (eventRepository.findById(imageUpdateRequest.getId()).isPresent()) {
                PromotionImage promotionImage = promotionImageRepository.findById(imageUpdateRequest.getImgId()).orElse(null);
                if(promotionImage==null)
                    return Header.ERROR("NULL image");
                awsS3Service.deleteImage(promotionImage.getSrc().split(origin)[1]);
                promotionImageRepository.deleteById(imageUpdateRequest.getImgId());
                return Header.OK();
            } else {
                return Header.ERROR("NULL EVENT");
            }
        }
        else if (Objects.equals(imageUpdateRequest.getType(), "eventReview")) {
            if (eventReviewRepository.findById(imageUpdateRequest.getId()).isPresent()) {
                EventReviewImage eventReviewImage= eventReviewImageRepository.findById(imageUpdateRequest.getImgId()).orElse(null);
                if(eventReviewImage==null)
                    return Header.ERROR("NULL image");
                awsS3Service.deleteImage(eventReviewImage.getSrc().split(origin)[1]);
                eventReviewImageRepository.deleteById(imageUpdateRequest.getImgId());
                return Header.OK();
            } else {
                return Header.ERROR("NULL EVENT Review");
            }
        }

        else if (Objects.equals(imageUpdateRequest.getType(), "notice")) {
            if (noticeRepository.findById(imageUpdateRequest.getId()).isPresent()) {
                NoticeImage noticeImage = noticeImageRepository.findById(imageUpdateRequest.getImgId()).orElse(null);
                if(noticeImage==null)
                    return Header.ERROR("NULL image");
                awsS3Service.deleteImage(noticeImage.getSrc().split(origin)[1]);
                noticeImageRepository.deleteById(imageUpdateRequest.getImgId());
                return Header.OK();
            } else {
                return Header.ERROR("NULL EVENT Review");
            }
        }


        return Header.ERROR("NULL TYPE");
    }


}
