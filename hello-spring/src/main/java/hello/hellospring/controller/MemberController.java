package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// @Controller 어노테이션을 사용하면 스프링컨테이너에서 컨트롤러 객체를 생성해서 컨테이너에 넣어
// 정확하게 말하면 스프링컨테이너에서 스프링빈을 관리한다라고 말함
@Controller
public class MemberController {

    //    private final MemberService memberService = new MemberService(); // 매번 컨트롤러마다 객체를 생성하는거보다 스프링 컨테이너에 등록해서
    private final MemberService memberService; // 이렇게 가져다 쓰는 방식으로 사용하는 것을 권장

    // Autowired는 스프링컨테이너에 있는 MemberService를 가져다가 연결을 시켜줌
    // MemberController가 생성될 때 스프링 빈에 등록되어 있는 memberService 객체를 가져다가 넣어줌
    // 이것을 의존관계 주입 DI(Dependency Infjection)이라고 한다
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);
        return "redirect:/";
    }
}

/**
* DI의 방법 3가지
 1. 생성자 주입 (가장 권장)
    @Autowired
    public MemberController(MemberService memberService) {
         this.memberService = memberService;
    }
 2. 필드 주입 (권장하는 방법은 아님)
   @Autowired private MemberService memberService;

 3. Set 주입 (누군가가 컨트롤러를 호출할 때 항상 public으로 열려 있어야한다.)
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
 */