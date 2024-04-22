package com.api.deso.handler.service;

import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.PurchaseLog;
import com.api.deso.model.entity.Ticket;
import com.api.deso.model.entity.User;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.PurchaseLogApiRequest;
import com.api.deso.model.network.response.PurchaseLogApiReaponse;
import com.api.deso.repository.PurchaseLogRepository;
import com.api.deso.repository.TicketRepository;
import com.api.deso.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseLogService implements CrudInterface<PurchaseLogApiRequest, PurchaseLogApiReaponse> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private PurchaseLogRepository purchaseLogRepository;

    @Override
    public Header<PurchaseLogApiReaponse> create(Header<PurchaseLogApiRequest> request) {
        PurchaseLogApiRequest purchaseLogApiRequest = request.getData();
        User user=userRepository.findById(purchaseLogApiRequest.getUserId()).orElse(null);
        if(user==null)
            return Header.ERROR("Wrong user");
        Ticket ticket=ticketRepository.findById(purchaseLogApiRequest.getTicketId()).orElse(null);
        if(ticket==null)
            return Header.ERROR("Wrong ticket");
        PurchaseLog purchaseLog = PurchaseLog.builder()
                .name(purchaseLogApiRequest.getName())
                .phoneNumber(purchaseLogApiRequest.getPhoneNumber())
                .email(purchaseLogApiRequest.getEmail())
                .payment(purchaseLogApiRequest.getPayment())
                .user(user)
                .ticket(ticket)
                .build();
        PurchaseLog newpurchaseLog= purchaseLogRepository.save(purchaseLog);
        return Header.OK(reponse(newpurchaseLog));


    }



    @Override
    public Header<PurchaseLogApiReaponse> read(Long id) {
        PurchaseLog purchaseLog = purchaseLogRepository.findById(id).orElse(null);
        if(purchaseLog==null)
            return Header.ERROR("Wrong user");
        else
            return Header.OK(reponse(purchaseLog));
    }

    @Override
    public Header<PurchaseLogApiReaponse> update(Header<PurchaseLogApiRequest> request) {

        PurchaseLogApiRequest purchaseLogApiRequest = request.getData();

        PurchaseLog purchaseLog=purchaseLogRepository.findById(purchaseLogApiRequest.getUserId()).orElse(null);
        if(purchaseLog==null)
            return Header.ERROR("Wrong user");
        PurchaseLog purchaseLog1 = PurchaseLog.builder()
                .id(purchaseLogApiRequest.getId())
                .name(purchaseLogApiRequest.getName())
                .phoneNumber(purchaseLogApiRequest.getPhoneNumber())
                .email(purchaseLogApiRequest.getEmail())
                .payment(purchaseLogApiRequest.getPayment())
                .user(purchaseLog.getUser())
                .ticket(purchaseLog.getTicket())
                .build();
        purchaseLogRepository.save(purchaseLog1);
        return Header.OK(reponse(purchaseLog1));
    }

    @Override
    public Header<PurchaseLogApiReaponse> delete(Long id) {
        purchaseLogRepository.deleteById(id);
        return Header.OK();
    }
    private PurchaseLogApiReaponse reponse(PurchaseLog purchaseLog) {
        PurchaseLogApiReaponse purchaseLogApiReaponse = PurchaseLogApiReaponse.builder()
                .name(purchaseLog.getName())
                .phoneNumber(purchaseLog.getPhoneNumber())
                .email(purchaseLog.getEmail())
                .payment(purchaseLog.getPayment())
                .userId(purchaseLog.getUser().getId())
                .ticketId(purchaseLog.getTicket().getId())
                .build();
        return purchaseLogApiReaponse;

    }
}
