package hello.core.fieldinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldInjectionService {

    @Autowired
    private FieldInjectionRepository fieldInjectionRepository;

    public void fieldInjectionCall() {
        fieldInjectionRepository.fieldInjectionCall();
    }

    @Autowired
    public void setFieldInjectionRepository(FieldInjectionRepository fieldInjectionRepository) {
        this.fieldInjectionRepository = fieldInjectionRepository;
    }
}
