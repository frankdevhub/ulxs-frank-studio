package com.ulxsfrank.business.repository;

import com.github.pagehelper.PageHelper;

/**
 * <p>Title:@ClassName MyBatisRepository.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/8/2 4:50
 * @Version: 1.0
 */

public class MyBatisRepository {
    public void setPage(Integer pageNum, Integer pageSize) {
        if (pageSize != null && pageSize != null)
            PageHelper.startPage(pageNum, pageSize, true);
    }
}
