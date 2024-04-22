package com.api.deso.controller.api;

import com.api.deso.dto.EventDtoClass;
import com.api.deso.dto.UserDtoClass;
import com.api.deso.handler.service.SelectService;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.EventApiRequest;
import com.api.deso.model.network.response.BookMarkApiResponse;
import com.api.deso.model.network.response.EventApiResponse;
import com.api.deso.model.network.response.ThemeApiResponse;
import com.api.deso.model.network.response.ThemeEventApiResponse;
import com.api.deso.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SelectController {


    @Autowired
    private SelectService selectService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/event")
    public Header<List<EventDtoClass.SimpleEventDto>> searchAllEvent(Pageable pageable) {

        return selectService.searchAllEvent(pageable);
    }

    @GetMapping(path = "/event/end")
    public Header<List<EventDtoClass.SimpleEventDto>> searchEndEvent() {
        return selectService.searchEventBeforeAWeekAgo();
    }

    @GetMapping(path = "/event/{num}")
    public Header<List<EventDtoClass.SimpleEventDto>> searchAllEvent(@PathVariable(name="num") Long num) {

        return selectService.eventRandomSearch(num);
    }

    @GetMapping(path = "/event/name/{name}")
    public Header<List<EventDtoClass.SimpleEventDto>> searchNameEvent(@PathVariable(name = "name") String name) {

        return selectService.searchNameEvent(name);
    }

    @GetMapping(path = "/event/category/{category}")
    public Header<List<EventDtoClass.SimpleEventDto>> searchCategoryEvent(@PathVariable(name = "category") String category) {
        return selectService.searchCategoryEvent(category);
    }
    @GetMapping(path = "/event/hashtag/{hashtag}")
    public Header<List<EventDtoClass.SimpleEventDto>> searchHashTagEvent(@PathVariable(name = "hashtag") String hashtag) {
        return selectService.searchHashTagEvent(hashtag);
    }

    @GetMapping(path = "/event/location/{location}")
    public Header<List<EventDtoClass.SimpleEventDto>> searchLocationEvent(@PathVariable(name = "location") String location) {
        return selectService.searchLocationEvent(location);
    }

    @GetMapping(path = "/bookmark/{userId}")
    public Header<List<BookMarkApiResponse>> searchBookMark (@PathVariable(name = "userId") Long userId){
            return selectService.searchUserBookMark(userId);
    }

    @GetMapping(path = "/theme")
    public Header<List<ThemeApiResponse>> searchAllTheme (){
        return selectService.searchAllTheme();

    }

    @GetMapping(path = "/theme/name/{content}")
    public Header<List<ThemeApiResponse>> searchContentTheme (@PathVariable(name = "content") String content){
        return selectService.searchContentTheme(content);
    }

    @GetMapping(path = "/theme/{num}")
    public Header<List<ThemeApiResponse>> themeRandomSearch (@PathVariable(name = "num") Long num){
        return selectService.themeRandomSearch(num);
    }

    @GetMapping(path = "/themeevent/{id}")
    public Header<List<ThemeEventApiResponse>> searchThemeEventByEventId(@PathVariable(name = "id") Long id){
        return selectService.searchThemeEventByEventId(id);
    }

    @GetMapping(path = "/themeevent/all/{id}")
    public Header<List<ThemeEventApiResponse>> searchThemeEventByThemeId(@PathVariable(name = "id") Long id){
        return selectService.searchThemeEventByThemeId(id);
    }

    @GetMapping(path = "/user" , headers = "Authorization")
    public Header<List<UserDtoClass.SimpleInfo>> searchAllUser(Pageable pageable, @RequestHeader("Authorization") String jwtHeader){

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            return Header.ERROR("Can't find token");
        }

        String jwtToken = jwtHeader.replace("Bearer ", "");
        String email = JWT.require(Algorithm.HMAC512("desooddhem")).build().verify(jwtToken).getClaim("username").asString();

        if (email == null) {
            return Header.ERROR("Wrong User Info");
        } else {
            User user = userRepository.findByEmail(email);
            if(user.getLevel()==3)
                return selectService.searchAllUser(pageable);
        }
        return Header.ERROR("Permission dined");
    }
}
