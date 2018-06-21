package com.platform.redis;

public class ApiBuyKey extends BasePrefix {
    public ApiBuyKey(String prefix) {
        super(prefix);
    }

    public ApiBuyKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static ApiBuyKey goods() {
        return new ApiBuyKey("goods");
    }
}
