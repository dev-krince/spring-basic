package hello.core;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("싱글톤과 프로토타입 스코프 함께 사용 테스트1")
public class SingletonWithPrototypeTest1 {


    @Test
    @DisplayName("프로토타입 빈 카운트 테스트")
    void prototypeFind() {

        //given
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = applicationContext.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = applicationContext.getBean(PrototypeBean.class);

        //when
        prototypeBean1.addCount();
        prototypeBean2.addCount();

        //then
        assertThat(prototypeBean1.getCount()).isEqualTo(1);
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("프로토타입 빈을 참조하는 싱글톤 빈이 있다면 기존에 생성한 프로토타입 빈을 그대로 사용한다.")
    void singletonClientUsePrototype() {

        //given
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = applicationContext.getBean(ClientBean.class);
        ClientBean clientBean2 = applicationContext.getBean(ClientBean.class);

        //when
        int count1 = clientBean1.logic();
        int count2 = clientBean2.logic();

        //then
        assertThat(count1).isEqualTo(1);
        assertThat(count2).isEqualTo(2);
    }

    @Test
    @DisplayName("프로토타입 빈을 참조하는 싱글톤 빈을 사용하려면 프로토타입 빈을 사용하는 시점마다 새롭게 생성해서 반환해주면 된다. 하지만 좋은 방법은 아니다")
    void singletonClientUseNewPrototypeBean() {

        //given
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = applicationContext.getBean(ClientBean.class);
        ClientBean clientBean2 = applicationContext.getBean(ClientBean.class);

        //when
        int count1 = clientBean1.newPrototypeBeanLogic();
        int count2 = clientBean2.newPrototypeBeanLogic();

        //then
        assertThat(count1).isEqualTo(1);
        assertThat(count2).isEqualTo(1);
    }

    @Getter
    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }

    @Scope("singleton")
    static class ClientBean {

        private PrototypeBean prototypeBean;

        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();

            return prototypeBean.getCount();
        }

        public int newPrototypeBeanLogic() {

            ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PrototypeBean.class);
            this.prototypeBean = applicationContext.getBean(PrototypeBean.class);
            prototypeBean.addCount();

            return prototypeBean.getCount();
        }
    }
}