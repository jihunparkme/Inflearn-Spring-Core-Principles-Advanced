package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV1Test {

    @Autowired
    CallServiceV1 callServiceV1;

    /**
     * aop=void hello.aop.internalcall.CallServiceV1.external()
     * call external
     * aop=void hello.aop.internalcall.CallServiceV1.internal()
     * call internal
     */
    @Test
    void external() {
        callServiceV1.external();
    }
}
