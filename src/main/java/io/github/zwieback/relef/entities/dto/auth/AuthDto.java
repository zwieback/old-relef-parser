package io.github.zwieback.relef.entities.dto.auth;

public class AuthDto {

    private String action;

    private String sid;

    private String userId;

    private MarkDto mark;

    public AuthDto() {
    }

    public String getAction() {
        return action;
    }

    public AuthDto setAction(String action) {
        this.action = action;
        return this;
    }

    public String getSid() {
        return sid;
    }

    public AuthDto setSid(String sid) {
        this.sid = sid;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public AuthDto setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public MarkDto getMark() {
        return mark;
    }

    public AuthDto setMark(MarkDto mark) {
        this.mark = mark;
        return this;
    }

    @Override
    public String toString() {
        return "AuthDto{" +
                "action='" + action + '\'' +
                ", sid='" + sid + '\'' +
                ", userId='" + userId + '\'' +
                ", mark=" + mark +
                '}';
    }
}
