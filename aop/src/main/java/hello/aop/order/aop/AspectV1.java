package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV1 {

    /**
     * @Around 애노테이션의 값은 Pointcut
     * @Around 애노테이션의 메서드는 Advice
     * execution(* hello.aop.order..*(..)) -> hello.aop.order 패키지와 그 하위 패키지( .. )를 지정하는 AspectJ 포인트컷 표현식
     */
    @Around("execution(* hello.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
        return joinPoint.proceed();
    }
}
