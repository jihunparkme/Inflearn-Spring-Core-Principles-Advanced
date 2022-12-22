package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {
    @Test
    void cglib() {
        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer(); // CGLIB 는 Enhancer 를 사용해서 프록시 생성
        enhancer.setSuperclass(ConcreteService.class); // 구체 클래스를 상속받아서 프록시 생성
        enhancer.setCallback(new TimeMethodInterceptor(target)); // 프록시에 적용할 실행 로직 할당
        ConcreteService proxy = (ConcreteService) enhancer.create(); // 프록시 생성
        log.info("targetClass={}", target.getClass()); // hello...ConcreteService
        log.info("proxyClass={}", proxy.getClass()); // hello...ConcreteService$$EnhancerByCGLIB$$25d6b0e3

        proxy.call();
    }
}
