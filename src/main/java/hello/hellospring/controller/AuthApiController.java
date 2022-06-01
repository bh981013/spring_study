package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@CrossOrigin
@RequestMapping(produces="application/json;charset=UTF-8")
public class AuthApiController {

    private final MemberService memberService;

    public AuthApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/loginSuccess") //login api 성공시 리턴
    String onAuthSuccess(@AuthenticationPrincipal User user){
        return "Success";
    }

    @PostMapping("/loginFail") //login api 실패시 리턴
    String onAuthSuccess(HttpServletRequest req){
        return "Failed";
    }

    @GetMapping("/userName") //유저 이름 api
    String onAuthUserName(@AuthenticationPrincipal User user){
        return user.getUsername();
    }

    @GetMapping("/userInfo") //유저 정보 api
    String onAuthUserInfo(@AuthenticationPrincipal User user){
        return memberService.findOne(user.getUsername()).get().toString();
    }

    @PostMapping("/autoLogin")
    String tempAutoLogin(String username, String password){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8080")
                .path("/login")
                .build()
                .toUri();

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("username", username);
        parameters.add("password", password);
        HttpEntity request = new HttpEntity<>(parameters, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, request, String.class);
        return responseEntity.getBody();
    }
}
