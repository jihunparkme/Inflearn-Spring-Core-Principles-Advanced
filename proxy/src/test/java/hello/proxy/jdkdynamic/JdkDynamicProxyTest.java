package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA() {
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        /**
         * Proxy.newProxyInstance (동적 프록시 생성)
         *
         * ClassLoader loader, Class<?>[] interfaces, InvocationHandler h
         * 클래스 로더 정보, 인터페이스, 핸들러 로직
         *
         * 해당 인터페이스 기반으로 동적 프록시 생성 및 핸들러 로직의 결과 반환
         */
        AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);

        proxy.call();
        log.info("targetClass={}", target.getClass()); // targetClass=class hello.proxy.jdkdynamic.code.AImpl
        log.info("proxyClass={}", proxy.getClass()); // proxyClass=class com.sun.proxy.$Proxy12
    }

    @Test
    void dynamicB() {
        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);
        BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);
        proxy.call();
        log.info("targetClass={}", target.getClass()); // targetClass=class hello.proxy.jdkdynamic.code.BImpl
        log.info("proxyClass={}", proxy.getClass()); // proxyClass=class com.sun.proxy.$Proxy12
    }
}
