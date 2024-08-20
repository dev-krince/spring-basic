package hello.core;

import hello.core.config.AppConfig;
import hello.core.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

@DisplayName("싱글톤 테스트")
public class singletonTest {

    @Test
    @DisplayName("스프링 없이 순수한 객체 생성")
    void pureContainer() {

        //given
        AppConfig appConfig = new AppConfig();

        //when
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        //then
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonConstructTest() {

        //given, when
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        //then
        assertThat(singletonService1).isSameAs(singletonService2);
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {

        //given
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        //when
        MemberService memberService1 = applicationContext.getBean("memberService", MemberService.class);
        MemberService memberService2 = applicationContext.getBean("memberService", MemberService.class);

        //then
        assertThat(memberService1).isSameAs(memberService2);
    }
}
