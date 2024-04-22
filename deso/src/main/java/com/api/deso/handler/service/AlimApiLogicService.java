package com.api.deso.handler.service;

import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Alim;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.AlimApiRequest;
import com.api.deso.model.network.response.AlimApiResponse;
import com.api.deso.repository.AlimRepository;
import com.api.deso.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlimApiLogicService implements CrudInterface<AlimApiRequest, AlimApiResponse> {

    private final AlimRepository alimRepository;

    private final UserRepository userRepository;

    @Override
    public Header<AlimApiResponse> create(Header<AlimApiRequest> request) {

        AlimApiRequest alimApiRequest = request.getData();

        Optional<User> from = userRepository.findById(alimApiRequest.getFrom());
        Optional<User> to = userRepository.findById(alimApiRequest.getTo());
        User newFrom;
        User newTo;

        if(from.isPresent())
            newFrom = from.get();
        else
            return Header.ERROR("Wrong User");

        if(to.isPresent())
            newTo = to.get();
        else
            return Header.ERROR("Wrong User");

        Alim alim = Alim.builder()
                .title(alimApiRequest.getTitle())
                .content(alimApiRequest.getContent())
                .from(newFrom)
                .to(newTo)
                .type(alimApiRequest.getType())
                .link(alimApiRequest.getLink())
                .showFrom(true)
                .showTo(true)
                .createdAt(LocalDateTime.now())
                .build();

        Alim newAlim = alimRepository.save(alim);
        return response(newAlim);
    }

    @Override
    public Header<AlimApiResponse> read(Long id) {
        return alimRepository.findById(id)
                .map(this::response)
                .orElseGet(
                        () -> Header.ERROR("NULL")
                );
    }

    public List<AlimApiResponse> readLog(Long userId,String type) {
        List<Alim> alimList;
        if(type.equals("from")) {
            alimList = alimRepository.findByFromId(userId).orElse(null);
        }
        else if(type.equals("to")) {
            alimList =  alimRepository.findByToId(userId).orElse(null);
        }
        else
            alimList=null;

        List<AlimApiResponse> alimApiResponseList = new ArrayList<>();
        for (int i = 0;i<alimList.size();i++){

            AlimApiResponse alimApiResponse = AlimApiResponse.builder()
                    .title(alimList.get(i).getTitle())
                    .content(alimList.get(i).getContent())
                    .to(alimList.get(i).getTo().getId())
                    .from(alimList.get(i).getFrom().getId())
                    .type(alimList.get(i).getType())
                    .link(alimList.get(i).getLink())
                    .showFrom(alimList.get(i).getShowFrom())
                    .showTo(alimList.get(i).getShowTo())
                    .createdAt(alimList.get(i).getCreatedAt())
                    .build();
            alimApiResponseList.add(alimApiResponse);
        }
        return alimApiResponseList;
    }
    @Override
    public Header<AlimApiResponse> update(Header<AlimApiRequest> request){
        return null;
    }

    public Header<AlimApiResponse> updateShow(Header<AlimApiRequest> request) {

        AlimApiRequest alimApiRequest = request.getData();
        Alim alim = alimRepository.findById(alimApiRequest.getId()).orElse(null);

        if(alim==null)
            return Header.ERROR("null");

        if(alim.getFrom().getId()==alimApiRequest.getFrom()){
            alim.setShowFrom(!alim.getShowFrom());
        }
        else if((alim.getTo().getId()==alimApiRequest.getTo())){
            alim.setShowTo(!alim.getShowTo());
        }
        else
            return Header.ERROR("Wrong user");

        Alim newAlim =  alimRepository.save(alim);
        return response(newAlim);
    }

    @Override
    public Header<AlimApiResponse> delete(Long id) {
        alimRepository.deleteById(id);
        return null;
    }

    public void deleteAll(Long userId) {
        alimRepository.deleteAllByToId(userId);
    }

    private Header<AlimApiResponse> response(Alim alim) {
        AlimApiResponse alimApiResponse = AlimApiResponse.builder()
                .title(alim.getTitle())
                .content(alim.getContent())
                .to(alim.getTo().getId())
                .from(alim.getFrom().getId())
                .type(alim.getType())
                .link(alim.getLink())
                .showFrom(alim.getShowFrom())
                .showTo(alim.getShowTo())
                .createdAt(alim.getCreatedAt())
                .build();

        return Header.OK(alimApiResponse);
    }
}
