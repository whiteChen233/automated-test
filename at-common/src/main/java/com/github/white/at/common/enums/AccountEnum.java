package com.github.white.at.common.enums;

import lombok.Getter;

/**
 * AccountEnum: The enum Account enum.
 *
 * @author White
 * @version 1.0
 * @date 2021 /08/10 下午 10:26
 */
@Getter
public enum AccountEnum {

    /**
     * Available account enum.
     */
    AVAILABLE(1, "可用"),
    /**
     * Disable account enum.
     */
    DISABLE(2, "禁用"),
    /**
     * Locked account enum.
     */
    LOCKED(3, "锁定"),

    ;

    /**
     * The Status.
     */
    private final int status;
    /**
     * The Text.
     */
    private final String text;

    /**
     * Instantiates a new Account enum.
     *
     * @param status the status
     * @param text   the text
     */
    AccountEnum(int status, String text) {
        this.status = status;
        this.text = text;
    }
}
