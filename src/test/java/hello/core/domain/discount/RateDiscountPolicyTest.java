package hello.core.domain.discount;

import hello.core.config.AppConfig;
import hello.core.domain.member.entity.Grade;
import hello.core.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

@DisplayName("정률 할인 정책 테스트")
class RateDiscountPolicyTest {

    DiscountPolicy discountPolicy;

    @BeforeEach
    void before() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        this.discountPolicy = applicationContext.getBean("discountPolicy", DiscountPolicy.class);
    }

    @Test
    @DisplayName("정률 할인 테스트 - 성공(VIP는 10% 할인이 적용되어야 한다)")
    void discountSuccess() {

        //given
        Member member = new Member(1L, "memberVIP", Grade.VIP);

        //when
        int discount = discountPolicy.discount(member, 20000);

        //then
        assertThat(discount).isEqualTo(2000);
    }

    @Test
    @DisplayName("정률 할인 테스트 - 성공(VIP가 아니면 할인이 적용되지 않아야 한다)")
    void nonVIPDiscountSuccess() {

        //given
        Member member = new Member(1L, "memberVIP", Grade.BASIC);

        //when
        int discount = discountPolicy.discount(member, 20000);

        //then
        assertThat(discount).isEqualTo(0);
    }
}