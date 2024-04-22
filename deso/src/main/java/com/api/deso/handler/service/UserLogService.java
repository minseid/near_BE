package com.api.deso.handler.service;

import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.User;
import com.api.deso.model.entity.UserLog;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.UserLogApiRequest;
import com.api.deso.model.network.response.UserLogApiResponse;
import com.api.deso.repository.UserLogRepository;
import com.api.deso.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserLogService implements CrudInterface<UserLogApiRequest, UserLogApiResponse> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserLogRepository userLogRepository;



    @Override
    public Header<UserLogApiResponse> create(Header<UserLogApiRequest> request) {

        UserLogApiRequest userLogApiRequest=request.getData();

        // 사용자 존재 여부 확인
        User user = userRepository.findById(userLogApiRequest.getUserId()).orElse(null);
        if(user==null)
            return Header.ERROR("Wrong user");

        // 사용자 로그 생성
        UserLog userLog = UserLog.builder()
                .title(userLogApiRequest.getTitle())
                .link(userLogApiRequest.getLink())
                .type(userLogApiRequest.getType())
                .createAt(userLogApiRequest.getCreateAt())
                .user(user)
                .build();
        UserLog newuserlog = userLogRepository.save(userLog);
        return Header.OK(reponse(newuserlog));
    }



    @Override
    public Header<UserLogApiResponse> read(Long id) {
//      2. 유저 아이디를 통해 로그 확인
        UserLog userLog = userLogRepository.findById(id).orElse(null);
        if(userLog==null)
            return Header.ERROR("userlog dosen't exist");
        else
            return Header.OK(reponse(userLog));
    }

    @Override
    public Header<UserLogApiResponse> update(Header<UserLogApiRequest> request) {
//      3.사용자가 접속할때마다 로그 업데이트
        UserLogApiRequest userLogApiRequest=request.getData();

        // 사용자 존재 여부 확인
        UserLog userLog = userLogRepository.findById(userLogApiRequest.getUserId()).orElse(null);
        if(userLog==null)
            return Header.ERROR("Wrong user");

        UserLog userLog1 = UserLog.builder()
                .id(userLogApiRequest.getId())
                .title(userLogApiRequest.getTitle())
                .link(userLogApiRequest.getLink())
                .type(userLogApiRequest.getType())
                .createAt(userLogApiRequest.getCreateAt())
                .user(userLog.getUser())
                .build();
        userLogRepository.save(userLog1);
        return Header.OK(reponse(userLog1));
    }

    @Override
    public Header<UserLogApiResponse> delete(Long id) {
//      4. 유저가 계정 삭제시 로그 삭제
        userLogRepository.deleteById(id);
        return Header.OK();
    }

    private UserLogApiResponse reponse(UserLog userLog) {
        UserLogApiResponse userLogApiResponse = UserLogApiResponse.builder()
                .title(userLog.getTitle())
                .link(userLog.getLink())
                .type(userLog.getType())
                .createAt(userLog.getCreateAt())
                .userId(userLog.getUser().getId())
                .build();
        return userLogApiResponse;
    }
}