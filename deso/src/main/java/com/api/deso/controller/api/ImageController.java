package com.api.deso.controller.api;

import com.api.deso.handler.service.AwsS3Service;
import com.api.deso.model.network.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class  ImageController {

    private final AwsS3Service awsS3Service;

    @PostMapping("/upload")
    public Header<List<String>> uploadImage(@RequestPart List<MultipartFile> multipartFile, @RequestParam("dir") String dirName){
        return Header.OK(awsS3Service.uploadImage(multipartFile, dirName));
    }

    /**
     * Amazon S3에 이미지 업로드 된 파일을 삭제
     * @return 성공 시 200 Success
     */
    @DeleteMapping("/delete")
    public Header<Void> deleteImage (@RequestParam String fileName) {
        awsS3Service.deleteImage(fileName);
        return Header.OK();
    }

}
