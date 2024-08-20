package hello.core.domain.member.service;

import hello.core.domain.member.entity.Member;

public interface MemberService {

    void join(Member member);

    Member findMember(Long memberId);
}
