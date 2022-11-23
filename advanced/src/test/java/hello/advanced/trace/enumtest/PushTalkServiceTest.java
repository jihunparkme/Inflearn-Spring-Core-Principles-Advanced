package hello.advanced.trace.enumtest;

import hello.advanced.trace.enumtest.code.PushTalkService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;

@Slf4j
public class PushTalkServiceTest {

    private PushTalkService pushTalkService = new PushTalkService();

    @Test
    void field() throws InterruptedException {
        log.info("main start");

        Runnable userA = () -> {
            pushTalkService.logic("불고기버거 세트 기프트콘");
        };
        Runnable userB = () -> {
            pushTalkService.logic("무소음 키보드");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();

        sleep(100);

        threadB.start();

        sleep(3000);
        log.info("main exit");
    }
}
