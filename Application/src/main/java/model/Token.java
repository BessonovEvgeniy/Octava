package model;

import lombok.Data;

@Data
public class Token {
    private Long currentTs;

    private Long expiryTs;

    private String username;

    private String password;

    public Token withCurrentTs(Long currentTs) {
        this.currentTs = currentTs;
        return this;
    }

    public Token withExpiryTs(Long expiryTs) {
        this.expiryTs = expiryTs;
        return this;

    }

    public Token withUsername(String username) {
        this.username = username;
        return this;
    }
}