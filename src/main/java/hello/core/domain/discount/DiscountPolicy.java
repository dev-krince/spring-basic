package hello.core.domain.discount;

import hello.core.domain.member.entity.Member;

public interface DiscountPolicy {

    int discount(Member member, int price);
}
