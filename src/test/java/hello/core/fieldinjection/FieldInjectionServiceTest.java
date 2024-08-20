package hello.core.fieldinjection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("필드 주입 테스트")
class FieldInjectionServiceTest {

    @Test
    @DisplayName("필드 주입을 사용해 의존 관계 주입을 하면 순수한 자바 테스트로 테스트 할 때 NullPointerException 이 발생한다.")
    void fieldInjectionCall() {
        //given
        FieldInjectionService fieldInjectionService = new FieldInjectionService();

        //when, then
        Assertions.assertThrows(NullPointerException.class, () -> fieldInjectionService.fieldInjectionCall());
    }
}