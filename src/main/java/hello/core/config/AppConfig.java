package hello.core.config;

import hello.core.domain.discount.DiscountPolicy;
import hello.core.domain.discount.RateDiscountPolicy;
import hello.core.domain.member.repository.MemberRepository;
import hello.core.domain.member.repository.MemoryMemberRepository;
import hello.core.domain.member.service.MemberService;
import hello.core.domain.member.service.MemberServiceImpl;
import hello.core.domain.order.service.OrderService;
import hello.core.domain.order.service.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
}