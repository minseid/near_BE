package com.api.deso.handler.service;

import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Banner;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.BannerApiRequest;
import com.api.deso.model.network.request.BlacklistApiRequest;
import com.api.deso.model.network.response.BannerApiResponse;
import com.api.deso.model.network.response.BlacklistApiResponse;
import com.api.deso.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BannerApiLogicService implements CrudInterface<BannerApiRequest, BannerApiResponse> {

    @Autowired
    BannerRepository bannerRepository;

    @Override
    public Header<BannerApiResponse> create(Header<BannerApiRequest> request) {

        BannerApiRequest bannerApiRequest = request.getData();

        Banner banner = Banner.builder()
                .src(bannerApiRequest.getSrc())
                .link(bannerApiRequest.getLink())
                .createdAt(LocalDateTime.now())
                .build();

        Banner newBanner = bannerRepository.save(banner);

        return response(newBanner);
    }

    @Override
    public Header<BannerApiResponse> read(Long id) {
        return bannerRepository.findById(id)
                .map(banner -> response(banner))
                .orElseGet(
                        () -> Header.ERROR("NULL")
                );
    }

    @Override
    public Header<BannerApiResponse> update(Header<BannerApiRequest> request) {

        BannerApiRequest bannerApiRequest = request.getData();
        Banner banner = bannerRepository.findById(bannerApiRequest.getId()).orElse(null);

        if(banner ==null)
            return Header.ERROR("Wrong Banner");

        banner.setSrc(bannerApiRequest.getSrc())
                .setLink(bannerApiRequest.getLink());

        Banner newBanner = bannerRepository.save(banner);

        return response(newBanner);
    }

    @Override
    public Header<BannerApiResponse> delete(Long id) {
        bannerRepository.deleteById(id);
        return Header.OK();
    }

    public List<Banner> all() {
        List<Banner> bannerList = bannerRepository.OrderByCreatedAt();
        return bannerList;
    }

    private Header<BannerApiResponse> response(Banner banner) {
        BannerApiResponse bannerApiResponse = BannerApiResponse.builder()
                .id(banner.getId())
                .src(banner.getSrc())
                .link(banner.getLink())
                .createdAt(banner.getCreatedAt())
                .build();

        return Header.OK(bannerApiResponse);
    }
}
