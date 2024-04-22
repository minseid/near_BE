package com.api.deso.handler.service;

import com.api.deso.model.entity.Auth;
import com.api.deso.repository.AuthRepository;
import com.api.deso.repository.UserRepository;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

@Service
public class PhoneAuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserRepository userRepository;

    public String PhoneNumberInput(String type, String phone) throws CoolsmsException {

        if(Objects.equals(type, "JOIN")) {
            if(userRepository.findByPhoneNumber(phone) != null) {
                if (Objects.equals(userRepository.findByPhoneNumber(phone).getStatus(), "REGISTERED")) return "EXIST NUMBER!";
            }
        } else {
            if(userRepository.findByPhoneNumber(phone) == null || Objects.equals(userRepository.findByPhoneNumber(phone).getStatus(), "UNREGISTERED")) {
                 return "UNREGISTERED USER";
            }
        }

        String api_key = "NCS1WLG6XY6MV1D8";
        String api_secret = "CYE6HZRLDSK326HOYJDFE0QIYKW620XJ";
        Message coolsms = new Message(api_key, api_secret);

        Random rand = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }

        Auth auth = Auth.builder()
                .type(type)
                .phone(phone)
                .checkStr(numStr)
                .createdAt(LocalDateTime.now())
                .build();

        Auth newAuth = authRepository.save(auth);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phone);    // 수신전화번호 (ajax로 view 화면에서 받아온 값으로 넘김)
        params.put("from", "07081213677 ");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "sms");
        params.put("text", "nearby 인증번호는 [" + numStr + "] 입니다.");

        coolsms.send(params); // 메시지 전송

        return numStr;

    }

    public String PhoneCheck(String type, String phone, String check) {

        Auth auth = authRepository.findTop1ByPhoneOrderByCreatedAtDesc(phone);

        if(Objects.equals(type, "JOIN")) {
            if(Objects.equals(auth.getCheckStr(), check)) {
                return "OK";
            } else {
                return "ERROR";
            }
        } else if(Objects.equals(type, "EMAIL")) {
            if(Objects.equals(auth.getCheckStr(), check)) {
                return userRepository.findByPhoneNumber(phone).getEmail();
            } else {
                return "ERROR";
            }
        } else if(Objects.equals(type, "PASSWORD")) {
            if(Objects.equals(auth.getCheckStr(), check)) {
                return Long.toString(userRepository.findByPhoneNumber(phone).getId());
            } else {
                return "ERROR";
            }
        } else {
            return "ERROR";
        }
    }

}
