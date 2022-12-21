package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 테스트 어노테이션
@Transactional // 이 어노테이션을 사용하게되면 테스트코트를 실행하고 DB에 커밋하지않고 데이터를 다시 롤백시킴
class MemberServiceIntegrationTest {
    /**
     * 기존 코드에서 MemoryMemberRepository new로 선언하게되면 테스트하고자하는 인스턴스가 서로 다른걸 참조하게됨
     * 그래서 MemoryMemberRepository를 직접 주입시켜주므로써 같은 인스턴스를 사용하도록 방식으로 변경
     * 그것이 Dependency Injection(DI)
     */
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

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

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


        //then

    }


}