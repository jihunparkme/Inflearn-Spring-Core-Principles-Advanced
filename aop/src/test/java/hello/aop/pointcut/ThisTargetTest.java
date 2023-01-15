package hello.aop.pointcut;

import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * this : 스프링 빈으로 등록되어 있는 **프록시 객체**를 대상으로 포인트컷 매칭
 * target : 스프링 AOP 프록시 객체가 가르키는 **실제 target 객체**를 대상으로 포인트컷 매칭
 *
 * application.properties
 * spring.aop.proxy-target-class=true CGLIB
 * spring.aop.proxy-target-class=false JDK 동적 프록시
 */
@Slf4j
@Import(ThisTargetTest.ThisTargetAspect.class)
@SpringBootTest(properties = "spring.aop.proxy-target-class=true") // CGLIB(default)
//@SpringBootTest(properties = "spring.aop.proxy-target-class=false") // JDK 동적 프록시
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        // JDK 동적 프록시 : memberService Proxy=class jdk.proxy3.$Proxy50
        // CGLIB : memberService Proxy=class hello.aop.member.MemberServiceImpl$$SpringCGLIB$$0
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {

        /**
         * 인터페이스(부모 타입 허용)
         **/
        @Around("this(hello.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            // JDK 동적 프록시 : [this-interface] String hello.aop.member.MemberService.hello(String)
            // CGLIB : [this-interface] String hello.aop.member.MemberServiceImpl.hello(String)
            return joinPoint.proceed();
        }

        @Around("target(hello.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            // JDK 동적 프록시 : [target-interface] String hello.aop.member.MemberService.hello(String)
            // CGLIB : [target-interface] String hello.aop.member.MemberServiceImpl.hello(String)
            return joinPoint.proceed();
        }

        /**
         * 구체 클래스
         **/
        // this: 스프링 AOP 프록시 객체 대상
        @Around("this(hello.aop.member.MemberServiceImpl)")
        public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-impl] {}", joinPoint.getSignature());
            // JDK 동적 프록시 : 인터페이스를 기반으로 생성되므로 구현 클래스를 알 수 없음 -> AOP 적용 대상 X
            // CGLIB : [this-impl] String hello.aop.member.MemberServiceImpl.hello(String) -> CGLIB 프록시는 구현 클래스를 기반으로 생성되므로 구현 클래스를 알 수 있음
            return joinPoint.proceed();
        }

        // target: 실제 target 객체 대상
        @Around("target(hello.aop.member.MemberServiceImpl)")
        public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-impl] {}", joinPoint.getSignature());
            // JDK 동적 프록시 : [target-impl] String hello.aop.member.MemberService.hello(String)
            // CGLIB : [target-impl] String hello.aop.member.MemberServiceImpl.hello(String)
            return joinPoint.proceed();
        }
    }
}
