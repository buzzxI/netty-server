package icu.buzz.example;

import java.util.Date;

/**
 * wrapper POJO of time
 */
public class UnixTime {
    private final long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L);
    }

    public UnixTime(long time) {
        this.value = time;
    }

    public long value() {
        return this.value;
    }
    @Override
    public String toString() {
        return new Date(value() * 1000L).toString();
    }
}
