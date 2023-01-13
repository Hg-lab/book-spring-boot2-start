package com.web.controller;

import com.web.annotation.SocialUser;
import com.web.domain.User;
import com.web.oauth.CustomOAuth2Provider;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
public class LoginController {

    @Value("${custom.oauth2.kakao.client-id}")
    private String kakaoClientId;

    private static final String DEFAULT_LOGIN_REDIRECT_URL
            = "{baseUrl}/oauth2/{registrationId}";

    @GetMapping({"", "/"})
    public String home() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/loginSuccess")
    public String loginComplete(@SocialUser User user) {
        return "redirect:/board/list";
    }

    // kakao oauth 로그인 동작방식 확인
    @GetMapping("/oauth2/kakao")
    public String kakaoCallback(@RequestParam String code) {
        User user = User.builder().build();
        System.out.println(getKakaoAccessToken(user, code));
        return "redirect:/board/list";
    }

    private String getKakaoAccessToken(
            @SocialUser User user,
            String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        LinkedMultiValueMap<Object, Object> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_url", DEFAULT_LOGIN_REDIRECT_URL);
        params.add("code", code);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoTokenReq = new HttpEntity(params, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenReq,
                Map.class
        );

        HashMap<String, String> map = (HashMap<String, String>) response.getBody();
        String accessToken = map.get("access_token");

        return accessToken;
    }
}
