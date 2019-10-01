package com.ulxsfrank.business.configuration;

import org.springframework.util.Assert;

/**
 * <p>Title:@ClassName SnowFlakeGenerator.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/8/2 4:45
 * @Version: 1.0
 */
public class SnowFlakeGenerator implements KeyGenerator<Long> {
    private SnowFlakeIdWorker snowflakeIdWorker = new SnowFlakeIdWorker();

    @Override
    public Long generateKey() {
        Assert.notNull(this.snowflakeIdWorker);
        long nextId = this.snowflakeIdWorker.nextId();
        return nextId / 10000;
    }

    public SnowFlakeIdWorker getSnowflakeIdWorker() {
        return snowflakeIdWorker;
    }

    public void setSnowflakeIdWorker(SnowFlakeIdWorker snowflakeIdWorker) {
        this.snowflakeIdWorker = snowflakeIdWorker;
    }

}
