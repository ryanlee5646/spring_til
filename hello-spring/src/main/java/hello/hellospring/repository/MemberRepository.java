package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;


public interface MemberRepository {
    Member save(Member member);
    // Optional은 null 값이 존재하지 않을 경우를 포장한 객체
    // null을 바로 반환하는거 보다 Optional로 감싸서 반환하는 거 선호
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
