package com.api.deso.handler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class PushService {
    public static final String apiKey = "AAAAPJvwj2U:APA91bGwCLMazhCNxczyY-lUwburqcMVYPn5aKwmi3z74M55nwPucMSuUmYCdWTyWlwcwHxnSUHLAyWR6wm1MHUfAlTwU8OWiI9-FfjED1xRoP4-T8X114jLjeX2Zji1gRVZK2NSv5Bj";
    public static final String senderid = "260314271589";


    /*
     *  푸시알림 보내기
     *
     *	memberToken: 회원 토큰
     * 	title: 보낼 제목
     *  message: 보낼 메세지
     *  link: 푸시알림 클릭 시 보내지는 링크
     *  device: device 정보
     *
     */

    public void push(String memberToken, String title, String message, String link, String device) {
        String token = memberToken != null ? memberToken : "";

        System.out.println("device : "+device);

        if(token != null && !token.equals("")) {

            try {
                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "key=" + apiKey);


                JSONObject json = new JSONObject();
                JSONObject info = new JSONObject();


                if(device.equals("ios")) {

                    info.put("title", title);
                    info.put("body", message);
                    info.put("sound", "default");
                    info.put("badge", "1");
                    json.put("notification", info);


                    info.put("link", "https://desoadmin.com"+link);
                    json.put("data", info);

                }

                else if(device.equals("android")) {

                    info.put("title", title);
                    info.put("body", message);
                    info.put("icon", "notification");
                    info.put("link", "https://desoadmin.com"+link);
                    info.put("sound", "default");
                    json.put("data", info);

                }


                json.put("to", token);



                String msg = json.toJSONString();

                System.out.println("msg 정보 : "+msg);

                OutputStream os = conn.getOutputStream();

                os.write(msg.getBytes("UTF-8"));
                os.flush();
                os.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();


                System.out.println(response.toString());

            } catch (Exception e) {
                System.out.println(e);
                System.out.println("푸시실패");
                e.printStackTrace();
            }


        }


    }
}
