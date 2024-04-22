package com.api.deso.handler.service;

import com.api.deso.dto.EventDtoClass;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Alim;
import com.api.deso.model.entity.BookMark;
import com.api.deso.model.entity.Event;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.AlimApiRequest;
import com.api.deso.model.network.request.BookMarkApiRequest;
import com.api.deso.model.network.response.AlimApiResponse;
import com.api.deso.model.network.response.BookMarkApiResponse;
import com.api.deso.model.network.response.EventApiResponse;
import com.api.deso.repository.BookMarkRepository;
import com.api.deso.repository.EventRepository;
import com.api.deso.repository.UserRepository;
import edu.emory.mathcs.backport.java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookMarkApiLogicService implements CrudInterface<BookMarkApiRequest, BookMarkApiResponse> {

    private final BookMarkRepository bookMarkRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

   private final ModelMapper modelMapper;

    @Override
    public Header<BookMarkApiResponse> create(Header<BookMarkApiRequest> request) {

        BookMarkApiRequest bookMarkApiRequest = request.getData();
        Optional<User> user = userRepository.findById(bookMarkApiRequest.getUserId());
        Optional<Event> event = eventRepository.findById(bookMarkApiRequest.getEventId());
        User newUser;
        Event newEvent;
        if(user.isPresent())
            newUser = user.get();
        else
            return Header.ERROR("Wrong User");

        if(event.isPresent())
            newEvent = event.get();
        else
            return Header.ERROR("Wrong user");

        BookMark bookMark = BookMark.builder()
                .user(newUser)
                .event(newEvent)
                .build();

        BookMark newBookMark = bookMarkRepository.save(bookMark);

        return response(newBookMark);
    }

    @Override
    public Header<BookMarkApiResponse> read(Long id) {
        return bookMarkRepository.findById(id)
                .map(this::response)
                .orElseGet(
                        () -> Header.ERROR("NULL")
                );
    }

    @Override
    public Header<BookMarkApiResponse> update(Header<BookMarkApiRequest> request) {
        return null;
    }

    @Override
    public Header<BookMarkApiResponse> delete(Long id) {
        bookMarkRepository.deleteById(id);
        return null;
    }

    public void deleteAll(Long userId) {
        bookMarkRepository.deleteAllByUserId(userId);
    }

    private Header<BookMarkApiResponse> response(BookMark bookMark) {
        BookMarkApiResponse bookMarkApiResponse = BookMarkApiResponse .builder()
                .id(bookMark.getId())
                .userId(bookMark.getUser().getId())
                .eventId(bookMark.getEvent().getId())
                .build();

        return Header.OK(bookMarkApiResponse);
    }
}
