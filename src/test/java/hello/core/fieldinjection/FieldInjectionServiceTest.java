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

    @Test
    @DisplayName("수정자 주입 테스트")
    void setterInjection() {

        //given
        FieldInjectionService fieldInjectionService = new FieldInjectionService();
        FieldInjectionRepository fieldInjectionRepository = new FieldInjectionRepository();

        //when
        fieldInjectionService.setFieldInjectionRepository(fieldInjectionRepository);

        //then
        //예외 발생하지 않고 정상적으로 출력됨
        fieldInjectionService.fieldInjectionCall();
    }
}