package hello.advanced.trace.enumtest.code;

public class ThreadUtils {
    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
