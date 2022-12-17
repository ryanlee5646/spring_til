package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// cmd+shift+T 단축기를 사용하게되면 Test를 쉽게 할 수 있음

@Service // 이렇게 해야 스프링컨테이너에 등록해서 쓸 수 있게 함
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired // 이것 역시 MemberService가 호출될 때 mmberRepository를 주입시켜줌
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     * */
    public Long join(Member member) {
        //같은 이름이 있는 중복 회원X
        // (Cmd+Option+V 하면 변수만들어주는 자동완성)

        // #1. 값을 꺼낼 때 그냥 Optional로 감싸져 있는 result를 .get()메서드로 꺼내도 되지만 권장하지 않음
        // #2. Optional<Member>를 씌우는거 보다 그냥 바로 메서드를 써서 호출하는 것을 권장
        // Optional<Member> result = memberRepository.findByName(member.getName());
        // #3. 하나의 로직은 메서드로 뽑는 것을 권장(드래그 후 ctrl + T를 누르면 extract 메서드 기능 개꿀~!)
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
