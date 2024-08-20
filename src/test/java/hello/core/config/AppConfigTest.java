package hello.core.config;

import hello.core.domain.discount.DiscountPolicy;
import hello.core.domain.discount.FixDiscountPolicy;
import hello.core.domain.discount.RateDiscountPolicy;
import hello.core.domain.member.repository.MemberRepository;
import hello.core.domain.member.repository.MemoryMemberRepository;
import hello.core.domain.member.service.MemberService;
import hello.core.domain.member.service.MemberServiceImpl;
import hello.core.domain.order.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("설정 정보 테스트")
class AppConfigTest {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    AnnotationConfigApplicationContext sameBeanConfig = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("모든 빈 조회 테스트")
    void findAllBeanSuccess() {

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            System.out.println("name = " + beanDefinitionName + " object = " + bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 조회 테스트")
    void findApplicationBeanSuccess() {

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = applicationContext.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = applicationContext.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + " object = " + bean);
            }
        }
    }

    @Test
    @DisplayName("스프링 내부 생성 빈 조회 테스트")
    void findInfrastructureBeanSuccess() {

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = applicationContext.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_INFRASTRUCTURE) {
                Object bean = applicationContext.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + " object = " + bean);
            }
        }
    }

    @Test
    @DisplayName("빈 이름으로 조회 - 성공")
    void findBeanByNameSuccess() {

        //given
        String beanName = "memberService";

        //when
        MemberService memberService = applicationContext.getBean(beanName, MemberService.class);

        //then
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름 없이 타입으로 조회 - 성공")
    void findBeanByTypeSuccess() {

        //given, when
        MemberService memberService = applicationContext.getBean(MemberService.class);

        //then
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구현체 타입으로 조회 - 성공")
    void findBeanByName2Success() {

        //given
        String beanName = "memberService";

        //when
        MemberService memberService = applicationContext.getBean(beanName, MemberServiceImpl.class);

        //then
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름으로 조회 - 실패(없는 빈 이름)")
    void findBeanByNameFail() {

        //given
        String beanName = "wrongBeanName";

        //when, then
        assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(beanName, MemberServiceImpl.class));
    }

    @Configuration
    static class SameBeanConfig {

        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }

        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }

    @Test
    @DisplayName("타입으로 조회 - 실패(중복된 타입이 둘 이상 있음)")
    void findBeanByTypeDuplicateFail() {

        //given, when, then
        assertThrows(NoUniqueBeanDefinitionException.class, () -> sameBeanConfig.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByType() {

        //given, when
        Map<String, MemberRepository> beansOfType = sameBeanConfig.getBeansOfType(MemberRepository.class);

        //then
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }

        System.out.println("beansOfType = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면 중복 오류가 발생한다")
    void findBeanByParentTypeDuplicate() {

        //given, when, then
        assertThrows(NoUniqueBeanDefinitionException.class, () -> sameBeanConfig.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 빈 이름을 지정하면 된다")
    void findBeanByParentBeanName() {
        //given
        String rateDiscountPolicyName = "rateDiscountPolicy";
        String fixDiscountPolicyName = "fixDiscountPolicy";

        //when
        DiscountPolicy rateDiscountPolicy = sameBeanConfig.getBean(rateDiscountPolicyName, DiscountPolicy.class);
        DiscountPolicy fixDiscountPolicy = sameBeanConfig.getBean(fixDiscountPolicyName, DiscountPolicy.class);

        //then
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
        assertThat(fixDiscountPolicy).isInstanceOf(FixDiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회")
    void findBeanBySubType() {
        //given, when
        DiscountPolicy rateDiscountPolicy = sameBeanConfig.getBean(RateDiscountPolicy.class);
        DiscountPolicy fixDiscountPolicy = sameBeanConfig.getBean(FixDiscountPolicy.class);

        //then
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
        assertThat(fixDiscountPolicy).isInstanceOf(FixDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllBeanByParentType() {
        //given, when
        Map<String, DiscountPolicy> beansOfType = sameBeanConfig.getBeansOfType(DiscountPolicy.class);

        //then
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
        assertThat(beansOfType.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기 - object")
    void findAllBeanByObjectType() {
        //given, when
        Map<String, Object> beansOfType = sameBeanConfig.getBeansOfType(Object.class);

        //then
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
    }

    @Test
    void xmlAppContext() {
        ApplicationContext applicationContext = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        assertThat(memberService).isInstanceOf(MemberService.class);
        assertThat(orderService).isInstanceOf(OrderService.class);
    }

    @Test
    @DisplayName("싱글톤으로 관리되는 이유(AppConfig를 등록하는게 아니라 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 AppConfig를 상속 받은 다른 클래스를 등록하는 것)")
    void configurationDeep() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = applicationContext.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
    }
}