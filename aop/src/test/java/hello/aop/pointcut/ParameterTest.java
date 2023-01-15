package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {
        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember() {
        }

        /**
         * joinPoint.getArgs()[0] 와 같이 매개변수 전달
         */
        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1]{}, arg={}", joinPoint.getSignature(), arg1);
            // [logArgs1]String hello.aop.member.MemberServiceImpl.hello(String), arg=helloA
            return joinPoint.proceed();
        }

        /**
         * args(arg,..) 와 같이 매개변수 전달
         */
        @Around("allMember() && args(arg,..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2]{}, arg={}", joinPoint.getSignature(), arg);
            // [logArgs2]String hello.aop.member.MemberServiceImpl.hello(String), arg=helloA
            return joinPoint.proceed();
        }

        /**
         * @Before 사용 축약 버전. 추가로 타입을 String 으로 제한
         */
        @Before("allMember() && args(arg,..)")
        public void logArgs3(String arg) {
            log.info("[logArgs3] arg={}", arg);
            // [logArgs3] arg=helloA
        }

        /**
         * this: 프록시 객체 전달 (Spring Container 에 들어있는 객체)
         */
        @Before("allMember() && this(obj)")
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[this]{}, obj={}", joinPoint.getSignature(), obj.getClass());
            // [this]String hello.aop.member.MemberServiceImpl.hello(String), obj=class hello.aop.member.MemberServiceImpl$$SpringCGLIB$$0
        }

        /**
         * target: 실제 대상 객체 전달 (프록시가 호출하는 실제 타겟 객체)
         */
        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[target]{}, obj={}", joinPoint.getSignature(), obj.getClass());
            // [target]String hello.aop.member.MemberServiceImpl.hello(String), obj=class hello.aop.member.MemberServiceImpl
        }

        /**
         * @target: 타입의 애노테이션 전달
         */
        @Before("allMember() && @target(annotation)")
        public void atTarget(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@target]{}, obj={}", joinPoint.getSignature(), annotation);
            // [@target]String hello.aop.member.MemberServiceImpl.hello(String), obj=@hello.aop.member.annotation.ClassAop()
        }

        /**
         * @within: 타입의 애노테이션 전달
         */
        @Before("allMember() && @within(annotation)")
        public void atWithin(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@within]{}, obj={}", joinPoint.getSignature(), annotation);
            // [@within]String hello.aop.member.MemberServiceImpl.hello(String), obj=@hello.aop.member.annotation.ClassAop()
        }

        /**
         * @annotation: 메서드의 애노테이션 전달. annotation.value() 로 해당 애노테이션의 값 출력
         */
        @Before("allMember() && @annotation(annotation)")
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@annotation]{}, annotationValue={}", joinPoint.getSignature(), annotation.value());
            // [@annotation]String hello.aop.member.MemberServiceImpl.hello(String), annotationValue=test value
        }
    }
}
