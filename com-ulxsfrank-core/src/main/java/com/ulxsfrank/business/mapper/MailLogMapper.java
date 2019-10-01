package com.ulxsfrank.business.mapper;

import com.ulxsfrank.business.entity.MailActionLogEntity;
import com.ulxsfrank.business.utils.ViewMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailLogMapper extends ViewMapper<MailActionLogEntity> {

}
