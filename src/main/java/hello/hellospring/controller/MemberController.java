package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@Controller가 있으면 처음 Spring을 실행할 때 해당 객체를 생성하고 관리한다.
public class MemberController {
    private final MemberService memberService; //여러개 생성할 필요x -> container에 등록한다
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    //생성자에 @Autowired를 쓰면 controller와 service를 연결해줌 -> 이것이 dependency injection
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@RequestBody MemberForm form){
        System.out.println(form.getName() + " "+ form.getPassword());
        Member member =new Member();
        member.setName(form.getName());
        member.setPassword(passwordEncoder.encode(form.getPassword()));
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return  "members/memberList";
    }

    @PostMapping("/members/login")
    @ResponseBody
    public boolean tryLogin(@RequestBody MemberForm form){
        System.out.println(form.getName() + " "+ form.getPassword());
        Member member =new Member();
        member.setName(form.getName());
        member.setPassword(passwordEncoder.encode(form.getPassword()));
        return memberService.login(member);
    }
}
