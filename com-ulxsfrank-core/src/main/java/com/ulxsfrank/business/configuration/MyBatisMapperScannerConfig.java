package com.ulxsfrank.business.configuration;

import com.ulxsfrank.business.entity.ConfigProperties;
import com.ulxsfrank.business.utils.ViewMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * <p>Title:@ClassName MyBatisMapperScannerConfig.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/8/2 4:42
 * @Version: 1.0
 */

@Configuration
@AutoConfigureAfter(MybatisConfig.class)
public class MyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.frankdevhub.business.mapper");

        ConfigProperties props = new ConfigProperties();
        props.setProperty("mappers", ViewMapper.class.getName()).setProperty("notEmpty", "false")
                .setProperty("IDENTITY", "MYSQL");

        mapperScannerConfigurer.setProperties(props);
        return mapperScannerConfigurer;
    }
}
