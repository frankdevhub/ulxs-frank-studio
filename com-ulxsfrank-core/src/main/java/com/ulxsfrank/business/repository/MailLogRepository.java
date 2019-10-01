package com.ulxsfrank.business.repository;

import com.ulxsfrank.business.data.Constants;
import com.ulxsfrank.business.data.logging.Logger;
import com.ulxsfrank.business.data.logging.LoggerFactory;
import com.ulxsfrank.business.entity.MailActionLogEntity;
import com.ulxsfrank.business.mapper.MailLogMapper;
import com.ulxsfrank.business.message.MessageMethod;
import com.ulxsfrank.business.utils.SpringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

/**
 * <p>Title:@ClassName MailLogRepository.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/8/4 21:58
 * @Version: 1.0
 */

@Repository
public class MailLogRepository extends MyBatisRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailLogRepository.class);

    private static final String MAIL_TYPE = "type";
    private static final String DATETIME = "date";
    private static final String ID = "id";
    private static final String IP = "ip";

    private MailLogMapper getMapper() {
        return SpringUtils.getBean(MailLogMapper.class);
    }

    public Boolean insertMailLog(MailActionLogEntity mailActionLogEntity) throws Exception {
        LOGGER.begin().headerAction(MessageMethod.EVENT).info("do insertMailLog.");
        Assert.notNull(mailActionLogEntity.getType());
        if (!(mailActionLogEntity.getType().equals(Constants.MAIL_TYPE_CONTACT)
                || mailActionLogEntity.equals(Constants.MAIL_TYPE_SUBSCRIBE)))
            throw new Exception("invalid mail type.");
        return Boolean.TRUE;
    }

    public Integer getContactMailsBetweenTimestamps(Long[] timestamps) {
        LOGGER.begin().headerAction(MessageMethod.EVENT).info("do getContactMailsBetweenTimestamps.");
        Integer count;
        Example example = new Example(MailActionLogEntity.class);
        example.createCriteria().andEqualTo(MAIL_TYPE, Constants.MAIL_TYPE_CONTACT)
                .andBetween(DATETIME, timestamps[0], timestamps[1]);

        count = getMapper().selectCountByExample(example);
        return count;
    }

    public Integer getContactMailsBetweenTimestampsByIp(Long[] timestamps, String ip) {
        LOGGER.begin().headerAction(MessageMethod.EVENT).info("do getContactMailsBetweenTimestampsByIp.");
        Integer count;
        Example example = new Example(MailActionLogEntity.class);
        example.createCriteria().andEqualTo(MAIL_TYPE, Constants.MAIL_TYPE_CONTACT)
                .andEqualTo(IP, ip);

        count = getMapper().selectCountByExample(example);
        return count;
    }

}
