package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect // 애노테이션 기반 프록시 적용 시 필요
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    /**
     * Pointcut + Advice = Advisor
     *
     * Pointcut : @Around 값에 포인트컷 표현식 삽입 (표현식은 AspectJ 표현식 사용)
     * Advice : @Around 메서드 = Advice
     * ProceedingJoinPoint : 실제 호출 대상, 전달 인자, 어떤 객체와 메서드가 호출되었는지 정보 포함(MethodInvocation invocation 과 유사)
     */
    @Around("execution(* hello.proxy.app..*(..))") //=> Pointcut path
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable { //=> Advice Logic

        TraceStatus status = null;

        // log.info("target={}", joinPoint.getTarget()); //실제 호출 대상
        // log.info("getArgs={}", joinPoint.getArgs()); //전달인자
        // log.info("getSignature={}", joinPoint.getSignature()); //join point시그니처

        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            // 실제 호출 대상(target) 호출
            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}