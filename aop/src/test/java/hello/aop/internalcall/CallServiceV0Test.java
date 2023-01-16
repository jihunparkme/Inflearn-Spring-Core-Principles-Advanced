package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV0Test {

    @Autowired
    CallServiceV0 callServiceV0;

    /**
     * aop=void hello.aop.internalcall.CallServiceV0.external() -> call CallLogAspect Advice
     * call external
     * call internal (this.internal) -> 자신의 인스턴스 내부 메서드 호출 -> not call CallLogAspect Advice
     */
    @Test
    void external() {
        callServiceV0.external();
    }

    /**
     * aop=void hello.aop.internalcall.CallServiceV0.internal()
     * call internal
     */
    @Test
    void internal() {
        callServiceV0.internal();
    }
}