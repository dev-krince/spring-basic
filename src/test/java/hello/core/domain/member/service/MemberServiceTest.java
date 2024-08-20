package hello.core.domain.member.service;

import hello.core.config.AppConfig;
import hello.core.domain.member.entity.Grade;
import hello.core.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

@DisplayName("회원 서비스 테스트")
class MemberServiceTest {

    MemberService memberService;

    @BeforeEach
    void before() {
//        AppConfig appConfig = new AppConfig();
//        this.memberService = appConfig.memberService();

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        this.memberService = applicationContext.getBean("memberService", MemberService.class);
    }

    @Test
    @DisplayName("회원가입 테스트 - 성공")
    void joinSuccess() {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @DisplayName("회원 조회 테스트 - 성공")
    void findMemberSuccess() {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        //when
        Member findMember = memberService.findMember(1L);

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @DisplayName("회원 조회 테스트 - 실페(없는 계정은 null을 반환한다)")
    void findMemberFail() {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        //when
        Member findMember = memberService.findMember(2L);

        //then
        assertThat(findMember).isEqualTo(null);
    }
}