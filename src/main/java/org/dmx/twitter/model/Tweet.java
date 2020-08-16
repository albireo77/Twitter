package org.dmx.twitter.model;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

public class Tweet {

    private String text;
    private String userId;

    @ApiModelProperty(hidden = true)
    private LocalDateTime createdAt;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
