package hello.proxy.proxyfactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {
    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();
        /** new ProxyFactory(target)
         * 프록시 호출 대상을 함께 전달
         * target 인스턴스에 인터페이스가 있다면, JDK 동적 프록시를 기본으로 사용
         * 인터페이스가 없고 구체 클래스만 있다면, CGLIB를 통해서 동적 프록시를 생성
         */
        ProxyFactory proxyFactory = new ProxyFactory(target);
        /** .addAdvice(new TimeAdvice())
         * 프록시 팩토리를 통해서 만든 프록시가 사용할 부가 기능 로직을 설정
         * JDK 동적 프록시가 제공하는 InvocationHandler 와 CGLIB가 제공하는 MethodInterceptor 의 개념과 유사
         */
        proxyFactory.addAdvice(new TimeAdvice());
        /** proxyFactory.getProxy()
         *  프록시 객체를 생성하고 그 결과 반환
         */
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass()); // class hello.proxy.common.service.ServiceImpl
        log.info("proxyClass={}", proxy.getClass()); // class com.sun.proxy.$Proxy13 (JDK 동적 프록시 적용)

        proxy.save();

        // 프록시 팩토리를 통해 만들어진 경우에만 사용 가능
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    void concreteProxy() {
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target); // 구체 클래스만 있으면 CGLIB 사용
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass()); // class hello.proxy.common.service.ConcreteService
        log.info("proxyClass={}", proxy.getClass()); // class hello.proxy.common.service.ConcreteService$$EnhancerBySpringCGLIB$$8328efc5

        proxy.call();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용")
    void proxyTargetClass() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // target class 기반 프록시 생성
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass()); // class hello.proxy.common.service.ServiceImpl
        log.info("proxyClass={}", proxy.getClass()); // class hello.proxy.common.service.ServiceImpl$$EnhancerBySpringCGLIB$$bf3a4aee
        proxy.save();
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }
}
