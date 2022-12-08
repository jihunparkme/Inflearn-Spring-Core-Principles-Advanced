package hello.proxy.pureproxy.proxy.code;

public class ProxyPatternClient {

    /**
     * 클라이언트는 Subject 인터페이스를 의존하고 있으므로
     * 프록시 객체가 주입되었는지, 실제 객체가 주입되었는지 알 수 없음.
     */
    private Subject subject;

    public ProxyPatternClient(Subject subject) {
        this.subject = subject;
    }

    public void execute() {
        subject.operation();
    }
}
