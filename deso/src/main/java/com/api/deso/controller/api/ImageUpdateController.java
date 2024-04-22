package com.api.deso.controller.api;

import com.api.deso.handler.service.ImageUpdateService;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.ImageUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/image-update")
public class ImageUpdateController {
    private final ImageUpdateService imageUpdateService;

    @PostMapping("/create")
    public Header Create(@RequestBody Header<ImageUpdateRequest> request) {
        return imageUpdateService.create(request);
    }

    @PostMapping("/delete")
    public Header Delete(@RequestBody Header<ImageUpdateRequest> request) {
        return imageUpdateService.delete(request);
    }

}
