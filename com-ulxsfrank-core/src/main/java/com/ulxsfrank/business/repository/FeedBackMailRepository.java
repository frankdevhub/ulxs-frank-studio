package com.ulxsfrank.business.repository;

import com.ulxsfrank.business.data.logging.Logger;
import com.ulxsfrank.business.data.logging.LoggerFactory;
import com.ulxsfrank.business.entity.FeedBackEntity;
import com.ulxsfrank.business.mapper.FeedBackMailMapper;
import com.ulxsfrank.business.message.MessageMethod;
import com.ulxsfrank.business.utils.SpringUtils;
import org.springframework.stereotype.Repository;

/**
 * <p>Title:@ClassName FeedBackMailRepository.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/8/8 22:34
 * @Version: 1.0
 */

@Repository
public class FeedBackMailRepository extends MyBatisRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedBackMailRepository.class);

    private FeedBackMailMapper getMapper() {
        return SpringUtils.getBean(FeedBackMailMapper.class);
    }

    public Boolean insertFeedbackMail(FeedBackEntity entity) {
        LOGGER.begin().headerMethod(MessageMethod.EVENT).info("do insertFeedbackMail.");
        getMapper().insertSelective(entity);
        return Boolean.TRUE;
    }
}
