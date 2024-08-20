package hello.core.config;

import hello.core.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AutoAppConfig 테스트")
class AutoAppConfigTest {

    @Test
    @DisplayName("@Component 어노테이션 활용 컴포넌트 스캔 테스트")
    void basicScan() {
        //given
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        //when
        MemberService memberService = applicationContext.getBean(MemberService.class);

        //then
        assertThat(memberService).isInstanceOf(MemberService.class);
    }
}