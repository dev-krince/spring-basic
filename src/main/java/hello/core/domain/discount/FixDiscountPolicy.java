package hello.core.domain.discount;

import hello.core.domain.member.entity.Grade;
import hello.core.domain.member.entity.Member;

public class FixDiscountPolicy implements DiscountPolicy {

    private final int discountFixAmount = 1000;
    private final int notDiscountAmount = 0;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        }

        return notDiscountAmount;
    }
}
