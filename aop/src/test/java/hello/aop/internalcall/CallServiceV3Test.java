package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV3Test {

    @Autowired
    CallServiceV3 callServiceV3;

    /**
     * aop=void hello.aop.internalcall.CallServiceV2.external()
     * call external
     * aop=void hello.aop.internalcall.CallServiceV2.internal()
     * call internal
     */
    @Test
    void external() {
        callServiceV3.external();
    }
}