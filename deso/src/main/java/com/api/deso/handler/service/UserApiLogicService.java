package com.api.deso.handler.service;

import com.api.deso.dto.UserDtoClass;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Blacklist;
import com.api.deso.model.entity.BookMark;
import com.api.deso.model.entity.Friend;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.UserApiRequest;
import com.api.deso.model.network.response.UserApiResponse;
import com.api.deso.repository.BlacklistRepository;
import com.api.deso.repository.BookMarkRepository;
import com.api.deso.repository.FriendRepository;
import com.api.deso.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.api.deso.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  BookMarkRepository bookMarkRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private BlacklistRepository blacklistRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    // 1. request data
    // 2. user 생성
    // 3. 생성된 데이터 -> UserApiResponse return

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        // 1. request data
        UserApiRequest userApiRequest = request.getData();

        // 2. User 검증
        if (userRepository.findByEmail(userApiRequest.getEmail()) != null) return Header.ERROR("EXIST");

        // 3. User 생성
        User user = User.builder()
                .email(userApiRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(userApiRequest.getPassword()))
                .phoneNumber(userApiRequest.getPhoneNumber())
                .nickname(userApiRequest.getNickname())
                .profileImage(userApiRequest.getProfileImage())
                .description(userApiRequest.getDescription())
                .profileOpen(userApiRequest.getProfileOpen())
                .role("ROLE_USER")
                .level(1L)
                .status("REGISTERED")
                .authKakao(userApiRequest.getAuthKakao())
                .authApple(userApiRequest.getAuthApple())
                .adKakao(userApiRequest.getAdKakao())
                .adPush(userApiRequest.getAdPush())
                .createdAt(LocalDateTime.now())
                .warning(0L)
                .build();

        User newUser = userRepository.save(user);

        return response(newUser);
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
        return userRepository.findById(id)
                .map(user ->response(user))
                .orElseGet(
                        ()->Header.ERROR("NULL")
                );
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {

        // 1.data
        UserApiRequest userApiRequest = request.getData();

        // 2. id->user
        Optional<User> optionalUser = userRepository.findById(userApiRequest.getId());

        return optionalUser.map(user -> {
            // 3. update
            user.setEmail(userApiRequest.getEmail())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setNickname(userApiRequest.getNickname())
                    .setProfileImage(userApiRequest.getProfileImage())
                    .setDescription(userApiRequest.getDescription())
                    .setProfileOpen(userApiRequest.getProfileOpen())
                    .setRole(user.getRole())
                    .setLevel(user.getLevel())
                    .setStatus(user.getStatus())
                    .setAuthKakao(user.getAuthKakao())
                    .setAuthApple(user.getAuthApple())
                    .setAdPush(userApiRequest.getAdPush())
                    .setAdKakao(userApiRequest.getAdKakao())
                    .setUpdatedAt(LocalDateTime.now());
            return user;
        })
                .map(user -> userRepository.save(user))
                .map(user -> response(user))
                .orElseGet(() -> Header.ERROR("NULL"));
    }


    public Header<UserApiResponse> passwordUpdate(Header<UserApiRequest> request) {

        UserApiRequest userApiRequest = request.getData();

        User user = userRepository.findById(userApiRequest.getId()).orElse(null);
        if(user == null)
            return Header.ERROR("Wrong user");

        user.setPassword(bCryptPasswordEncoder.encode(userApiRequest.getPassword()));

        User newUser =  userRepository.save(user);

        return response(newUser);
    }


    public Header<UserApiResponse> unregister(Long id) {

        // 1. id->repository->user
        Optional<User> optional = userRepository.findById(id);

        // 2. repository -> delete
        return optional.map(user-> {


                    user.setStatus("UNREGISTERED")
                            .setUnregisteredAt(LocalDateTime.now());
                    return user;
                })
                .map(user -> userRepository.save(user))
                .map(user -> response(user))
                .orElseGet(()->Header.ERROR("NULL"));

    }

//    public List<UserDtoClass.SimpleInfo> all(Pageable pageable){
//
//        Page<User> user = userRepository.findAll(pageable);
//
//
//    }

    @Override
    public Header<UserApiResponse> delete(Long id) {

        // 1. id->repository->user
        Optional<User> optional = userRepository.findById(id);

       if(optional.isPresent()){
           bookMarkRepository.deleteAllByUserId(id);
           friendRepository.deleteAllByUserId(id);
           blacklistRepository.deleteAllByUserId(id);
           userRepository.deleteById(id);
           return null;
       }

       else
          return  Header.ERROR("Wrong User");
    }

    private Header<UserApiResponse> response(User user) {

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .description(user.getDescription())
                .profileOpen(user.getProfileOpen())
                .role(user.getRole())
                .status(user.getStatus())
                .adPush(user.getAdPush())
                .adKakao(user.getAdKakao())
                .createdAt(user.getCreatedAt())
                .unregisteredAt(user.getUnregisteredAt())
                .updatedAt(user.getUpdatedAt())
                .friendList(user.getFriendList())
                .blacklistList(user.getBlacklistList())
                .warning(user.getWarning())
                .build();

        return Header.OK(userApiResponse);
    }
}
