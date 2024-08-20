package hello.core.domain.member.repository;

import hello.core.domain.member.entity.Member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
}
