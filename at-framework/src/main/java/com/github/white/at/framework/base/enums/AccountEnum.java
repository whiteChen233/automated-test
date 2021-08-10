package com.github.white.at.framework.base.enums;

/**
 * AccountEnum: The enum Account enum.
 *
 * @author White
 * @version 1.0
 * @date 2021 /08/10 下午 10:26
 */
public enum AccountEnum {

    /**
     * Available account enum.
     */
    AVAILABLE(1, "可用"),

    /**
     * Disable account enum.
     */
    DISABLE(2, "禁用"),

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

    /**
     * Gets status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }
}
