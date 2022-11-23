package hello.advanced.trace.enumtest.code;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

@Slf4j
public class PushTalkService {

    private PushTak pushTalk;

    public void logic(String name) {
        pushTalk = PushTak.IOS;

        StringBuffer message = new StringBuffer();
        message.append("[주문 완료] ");
        message.append(name);
        message.append(" 주문의 완료되었습니다.");


        log.info("수정 message={} -> enumMessage={}", message, pushTalk.getMessage());
        pushTalk.setMessage(message.toString());
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("조회 enumMessage={}", pushTalk.getMessage());
    }
}
