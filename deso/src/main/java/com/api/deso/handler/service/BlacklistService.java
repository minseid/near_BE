package com.api.deso.handler.service;

import com.api.deso.dto.UserDtoClass;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Blacklist;
import com.api.deso.model.entity.Friend;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.BlacklistApiRequest;
import com.api.deso.model.network.request.FriendApiRequest;
import com.api.deso.model.network.response.BlacklistApiResponse;
import com.api.deso.model.network.response.FriendApiResponse;
import com.api.deso.repository.BlacklistRepository;
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
public class BlacklistService implements CrudInterface<BlacklistApiRequest, BlacklistApiResponse> {

    @Autowired
    private BlacklistRepository blacklistRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<BlacklistApiResponse> create(Header<BlacklistApiRequest> request) {

        BlacklistApiRequest blacklistApiRequest = request.getData();

        if(blacklistApiRequest == null)
            return Header.ERROR("데이터가 없습니다.");

        if(!blacklistRepository.findByUserIdAndUserId2(blacklistApiRequest.getUserId(), blacklistApiRequest.getUserId2()).isEmpty())
            return Header.ERROR("이미 블랙리스트에 추가되어 있습니다.");

        if(Objects.equals(blacklistApiRequest.getUserId(), blacklistApiRequest.getUserId2()))
            return Header.ERROR("자기 자신을 블랙리스트에 추가할 수 없습니다.");

        Optional<User> optionalUser = userRepository.findById(blacklistApiRequest.getUserId());

        Blacklist blacklist;
        if(optionalUser.isPresent()) {
            blacklist = Blacklist.builder()
                    .user(optionalUser.get())
                    .userId2(blacklistApiRequest.getUserId2())
                    .createdAt(LocalDateTime.now())
                    .build();
        } else {
            return Header.ERROR("CAN'T FIND USER");
        }

        Blacklist newBlacklist = blacklistRepository.save(blacklist);

        return response(newBlacklist);
    }

    @Override
    public Header<BlacklistApiResponse> read(Long id) {
        return null;
    }

    public List<UserDtoClass.blacklistDto> user_read(Long id) {
        List<Blacklist> blacklistList = blacklistRepository.findByUserId(id);
        ModelMapper modelMapper = new ModelMapper();
        List<UserDtoClass.blacklistDto> userDtoList = new ArrayList<>();
        blacklistList.stream().forEach(blacklist -> userDtoList.add(modelMapper.map(blacklist, UserDtoClass.blacklistDto.class)));
        return userDtoList;
    }

    @Override
    public Header<BlacklistApiResponse> update(Header<BlacklistApiRequest> request) {

        BlacklistApiRequest blacklistApiRequest = request.getData();


        return null;
    }

    @Override
    public Header<BlacklistApiResponse> delete(Long id) {

        Optional<Blacklist> optional = blacklistRepository.findById(id);

        return optional.map(blacklist -> {
            blacklistRepository.deleteById(id);
            return blacklist;
        })
                .map(blacklist -> response(blacklist))
                .orElseGet(() -> Header.ERROR("NULL"));
    }

    public Header<BlacklistApiResponse> body_delete(Header<BlacklistApiRequest> request) {
        BlacklistApiRequest blacklistApiRequest = request.getData();

        List<Blacklist> blacklist = blacklistRepository.findByUserIdAndUserId2(blacklistApiRequest.getUserId(), blacklistApiRequest.getUserId2());
        blacklistRepository.delete(blacklist.get(0));

        return Header.OK();
    }


    private Header<BlacklistApiResponse> response(Blacklist blacklist) {
        User user = blacklist.getUser();
        UserDtoClass.SimpleInfo userDto = new UserDtoClass.SimpleInfo(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );
        BlacklistApiResponse blacklistApiResponse = BlacklistApiResponse.builder()
                .user(userDto)
                .userId2(blacklist.getUserId2())
                .createdAt(blacklist.getCreatedAt())
                .build();

        return Header.OK(blacklistApiResponse);
    }
}
