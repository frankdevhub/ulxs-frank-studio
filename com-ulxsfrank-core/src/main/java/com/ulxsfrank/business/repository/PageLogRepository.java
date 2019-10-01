package com.ulxsfrank.business.repository;

import com.ulxsfrank.business.data.logging.Logger;
import com.ulxsfrank.business.data.logging.LoggerFactory;
import com.ulxsfrank.business.entity.PageLogEntity;
import com.ulxsfrank.business.mapper.PageLogMapper;
import com.ulxsfrank.business.message.MessageMethod;
import com.ulxsfrank.business.utils.SpringUtils;
import org.springframework.stereotype.Repository;

/**
 * <p>Title:@ClassName PageLogRepository.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/8/2 4:51
 * @Version: 1.0
 */

@Repository
public class PageLogRepository extends MyBatisRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageLogRepository.class);

    private PageLogMapper getMapper() {
        return SpringUtils.getBean(PageLogMapper.class);
    }

    public Boolean insertPageLog(PageLogEntity entity) {
        LOGGER.begin().headerMethod(MessageMethod.EVENT).info("do insertPageLog.");
        getMapper().insertSelective(entity);
        return Boolean.TRUE;
    }
}
