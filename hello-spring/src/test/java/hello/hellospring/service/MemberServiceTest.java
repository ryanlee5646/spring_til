package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    /**
     * 기존 코드에서 MemoryMemberRepository new로 선언하게되면 테스트하고자하는 인스턴스가 서로 다른걸 참조하게됨
     * 그래서 MemoryMemberRepository를 직접 주입시켜주므로써 같은 인스턴스를 사용하도록 방식으로 변경
     * 그것이 Dependency Injection(DI)
     */
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    // 메서드 실행 전 매번 동작하는 Annotation
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    // 메서드 실행 후에 매번 동작하는 Annotation
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given (어떤게 주어지면) -> 멤버 객체에 세팅을하고
        Member member = new Member();
        member.setName("spring");

        //when (어떤 행위를 했을 때) -> 회원가입을 시키면
        Long saveId = memberService.join(member);

        //then (결과값이 뭐냐) -> 정상적으로 가입이 되었는가?
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);

        /*
        에러가 발생이 의도한대로 됐는지 체크
        try {
            memberService.join(member2);
            fail("fail!");
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        }

        */
        // 위에 코드를 아래 코드로 리팩토링 가능
        // assertThrows로 join에 member2를 기입시 에러가 발생하는 에러가 IllegalStateException 인지 체크
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}