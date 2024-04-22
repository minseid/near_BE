package com.api.deso.handler.service;

import com.api.deso.model.entity.Auth;
import com.api.deso.repository.AuthRepository;
import com.api.deso.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.Address;
import javax.mail.Header;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final AuthRepository authRepository;

    private final UserRepository userRepository;

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;


    public void sendEmailMessage(String email) throws Exception {
        String code = createCode(); // 인증코드 생성

        Auth auth = Auth.builder()
                .type("SCHOOL")
                .checkStr(email)
                .createdAt(LocalDateTime.now())
                .checkCharacter(code)
                .build();

        authRepository.save(auth);

        code = "https://deso-us.com/api/v1/school/check/" + code;
        MimeMessage message = emailSender.createMimeMessage();

        message.setFrom("deso@oddhem.com");
        message.addRecipients(MimeMessage.RecipientType.TO, email); // 보낼 이메일 설정
        message.setSubject("DESO 학교인증 메일입니다."); // 이메일 제목
        message.setText(setContext(code), "utf-8", "html"); // 내용 설정(Template Process)

        // 보낼 때 이름 설정하고 싶은 경우
        // message.setFrom(new InternetAddress([이메일 계정], [설정할 이름]));



        emailSender.send(message); // 이메일 전송
    }

    public String verifyEmailMessage(String path) {
        Auth auth = authRepository.findTop1ByCheckCharacterOrderByCreatedAtDesc(path);

        if(auth == null) return "ERROR";

        return "Success";
    }

    private String setContext(String code) { // 타임리프 설정하는 코드
        Context context = new Context();
        context.setVariable("code", code); // Template에 전달할 데이터 설정
        return templateEngine.process("email", context); // email.html
    }


    private String createCode() throws NoSuchAlgorithmException {
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 7; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    code.append((char) (rnd.nextInt(26) + 97));
                    break;
                case 1:
                    code.append((char) (rnd.nextInt(26) + 65));
                    break;
                case 2:
                    code.append((rnd.nextInt(10)));
                    break;
            }
        }

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(code.toString().getBytes());

        return bytesToHex(md.digest());

//        return code.toString();
    }

    public static String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}