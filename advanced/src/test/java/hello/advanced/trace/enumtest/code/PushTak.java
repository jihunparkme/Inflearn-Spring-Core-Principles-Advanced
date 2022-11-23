package hello.advanced.trace.enumtest.code;

import lombok.Getter;

@Getter
public enum PushTak {

    IOS("", "this is IOS"),
    ANDROID("", "this is ANDROID");

    private String name;
    private String message;

    PushTak(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
