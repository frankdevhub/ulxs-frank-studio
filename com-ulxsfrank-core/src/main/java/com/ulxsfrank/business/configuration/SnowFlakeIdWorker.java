package com.ulxsfrank.business.configuration;

/**
 * <p>Title:@ClassName SnowFlakeIdWorker.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/8/2 4:44
 * @Version: 1.0
 */
public class SnowFlakeIdWorker {
    private final long twepoch = 1420041600000L;
    private final long workerIdBits = 7L;
    private final long datacenterIdBits = 3L;
    private final long maxWorkerId = 127L;
    private final long maxDatacenterId = 7L;
    private final long sequenceBits = 12L;
    private final long workerIdShift = 12L;
    private final long datacenterIdShift = 19L;
    private final long timestampLeftShift = 22L;
    private final long sequenceMask = 4095L;
    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowFlakeIdWorker() {

    }

    public SnowFlakeIdWorker(long workerId, long datacenterId) {
        if ((workerId > 127L) || (workerId < 0L)) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",
                    new Object[]{Long.valueOf(127L)}));
        }
        if ((datacenterId > 7L) || (datacenterId < 0L)) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0",
                    new Object[]{Long.valueOf(7L)}));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                            new Object[]{Long.valueOf(this.lastTimestamp - timestamp)}));
        }
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1L & 0xFFF);
            if (this.sequence == 0L) {
                timestamp = tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0L;
        }
        this.lastTimestamp = timestamp;

        return timestamp - 1420041600000L << 22 | this.datacenterId << 19 | this.workerId << 12 | this.sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
