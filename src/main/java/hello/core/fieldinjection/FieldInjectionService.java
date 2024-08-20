package hello.core.fieldinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldInjectionService {

    @Autowired
    FieldInjectionRepository fieldInjectionRepository;

    public void fieldInjectionCall() {
        fieldInjectionRepository.fieldInjectionCall();
    }
}
