package hello.core.fieldinjection;

import org.springframework.stereotype.Component;

@Component
public class FieldInjectionRepository {

    public void fieldInjectionCall() {
        System.out.println("success");
    }
}
