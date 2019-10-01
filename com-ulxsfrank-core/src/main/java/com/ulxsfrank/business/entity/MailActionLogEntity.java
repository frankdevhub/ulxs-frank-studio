package com.ulxsfrank.business.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * <p>Title:@ClassName MailActionLogEntity.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/8/2 22:14
 * @Version: 1.0
 */

@Table(name = "mxtech_mail_log")
public class MailActionLogEntity {

    @Id
    private Long id;

    @NotNull
    @Column(name = "ip")
    private String ip;

    @NotNull
    @Column(name = "type")
    private Integer type;

    @Column(name = "date")
    private Long date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
