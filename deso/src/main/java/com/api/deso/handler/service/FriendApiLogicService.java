package com.api.deso.handler.service;

import com.api.deso.config.mapper.UserDto;
import com.api.deso.dto.UserDtoClass;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Friend;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.FriendApiRequest;
import com.api.deso.model.network.request.UserApiRequest;
import com.api.deso.model.network.response.FriendApiResponse;
import com.api.deso.model.network.response.UserApiResponse;
import com.api.deso.repository.FriendRepository;
import com.api.deso.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FriendApiLogicService implements CrudInterface<FriendApiRequest, FriendApiResponse>{

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<FriendApiResponse> create(Header<FriendApiRequest> request) {

        FriendApiRequest friendApiRequest = request.getData();

        if(friendRepository.findByUserIdAndUserId2(friendApiRequest.getUserId(), friendApiRequest.getUserId2()).isEmpty()
        && friendRepository.findByUserIdAndUserId2(friendApiRequest.getUserId2(), friendApiRequest.getUserId()).isEmpty()) {
            ;
        } else{
            return Header.ERROR("이미 친구요청을 보냈거나 받은 친구입니다.");
        }

        if(Objects.equals(friendApiRequest.getUserId(), friendApiRequest.getUserId2()))
            return Header.ERROR("자기 자신을 친구로 추가할 수 없습니다.");

        Optional<User> optionalUser = userRepository.findById(friendApiRequest.getUserId());

        Friend friend;
        if (optionalUser.isPresent()) {
            friend = Friend.builder()
                    .user(optionalUser.get())
                    .userId2(friendApiRequest.getUserId2())
                    .createdAt(LocalDateTime.now())
                    .status("WAIT").build();
        } else {
            return Header.ERROR("CAN'T FIND USER");
        }

        Friend newFriend = friendRepository.save(friend);

        return response(newFriend);
    }

    @Override
    public Header<FriendApiResponse> read(Long id) {
        return null;
    }

    public List<UserDtoClass.friendDto> user_read(Long id) {
        List<Friend> friends = friendRepository.findByUserId(id);
        ModelMapper modelMapper = new ModelMapper();
        List<UserDtoClass.friendDto> userDtoList = new ArrayList<>();
        friends.stream().forEach(friend -> userDtoList.add(modelMapper.map(friend, UserDtoClass.friendDto.class)));

        return userDtoList;
    }

    @Override
    public Header<FriendApiResponse> update(Header<FriendApiRequest> request) {
        return null;

    }

    public Header<FriendApiResponse> permit(Header<FriendApiRequest> request) {
        FriendApiRequest friendApiRequest = request.getData();

        List<Friend> optionalFriend = friendRepository.findByUserIdAndUserId2(friendApiRequest.getUserId(), friendApiRequest.getUserId2());

        Optional<User> optionalUser2 = userRepository.findById(friendApiRequest.getUserId2());

        Friend friend2;
        if (optionalFriend != null) {
            friend2 = Friend.builder()
                    .user(optionalUser2.get())
                    .userId2(friendApiRequest.getUserId())
                    .createdAt(LocalDateTime.now())
                    .status("ACTIVATE").build();
            friendRepository.save(friend2);
        } else {
            return Header.ERROR("CAN'T FIND USER");
        }

        optionalFriend.forEach(friend -> {
            System.out.println("friend : ");
                    friend.setStatus("ACTIVATE");
                    friendRepository.save(friend);
                    System.out.println(friend);
                });


        return Header.OK();

    }

    @Override
    public Header<FriendApiResponse> delete(Long id) {

        Optional<Friend> optional = friendRepository.findById(id);

        return optional.map(friend -> {
            friendRepository.deleteById(id);
            return friend;
        })
                .map(friend -> response(friend))
                .orElseGet(() -> Header.ERROR("NULL"));
    }

    public Header<FriendApiResponse> body_delete(Header<FriendApiRequest> request) {
        FriendApiRequest friendApiRequest = request.getData();

        List<Friend> friendList = friendRepository.findByUserIdAndUserId2(friendApiRequest.getUserId(), friendApiRequest.getUserId2());
        friendRepository.deleteAll(friendList);

        return Header.OK();
    }

    private Header<FriendApiResponse> response(Friend friend) {
        User user = friend.getUser();
        UserDtoClass.SimpleInfo userDto = new UserDtoClass.SimpleInfo(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );
        FriendApiResponse friendApiResponse = FriendApiResponse.builder()
                .id(friend.getId())
                .user(userDto)
                .userId2(friend.getUserId2())
                .createdAt(friend.getCreatedAt())
                .status(friend.getStatus())
                .build();

        return Header.OK(friendApiResponse);
    }
}
