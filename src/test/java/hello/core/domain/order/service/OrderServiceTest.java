package hello.core.domain.order.service;

import hello.core.config.AppConfig;
import hello.core.domain.member.entity.Grade;
import hello.core.domain.member.entity.Member;
import hello.core.domain.member.service.MemberService;
import hello.core.domain.order.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

@DisplayName("주문 서비스 테스트")
class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    void before() {
//        AppConfig appConfig = new AppConfig();
//        this.memberService = appConfig.memberService();
//        this.orderService = appConfig.orderService();

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        this.memberService = applicationContext.getBean("memberService", MemberService.class);
        this.orderService = applicationContext.getBean("orderService", OrderService.class);
    }

    @Test
    @DisplayName("주문 생성 테스트 - 성공")
    void createOrderSuccess() {
        //given
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);


        //when
        Order order = orderService.createOrder(memberId, "itemA", 10000);

        //then
        assertThat(order.getMemberId()).isEqualTo(memberId);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}