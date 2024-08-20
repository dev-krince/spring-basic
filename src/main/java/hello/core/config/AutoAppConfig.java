package hello.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class),
        basePackages = "hello.core"
)//@ComponentScan을 사용하면 @Configuration이 붙은 설정 정보도 자동으로 등록되기 때문에 앞서 만들었던 AppConfig도 설정 정보로 등록된다. 이를 막기 위해 사용
public class AutoAppConfig {

}
