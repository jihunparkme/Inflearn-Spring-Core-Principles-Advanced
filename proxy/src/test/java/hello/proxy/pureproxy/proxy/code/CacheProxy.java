package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject {

    private Subject target; // 프록시가 호출하는 대상
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    /**
     * 프록시도 실제 객체와 모양이 같아야 하므로 인터페이스 구현
     */
    @Override
    public String operation() {
        log.info("프록시 호출");
        if (cacheValue == null) {
            // 클라이언트가 프록시를 호출하면 프록시가 최종적으로 실제 객체 호출
            cacheValue = target.operation();
        }
        return cacheValue;
    }
}
