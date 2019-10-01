package com.ulxsfrank.business.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * <p>Title:@ClassName FeedBackEntity.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/7/19 11:35
 * @Version: 1.0
 */

@Table(name = "mxtech_mail_feedback")
public class FeedBackEntity {

    @Id
    private Long id;

    @Column(name = "date")
    private Long date;

    @Length(min = 7, max = 15, message = "ip地址格式超出范围(7,15)")
    @Column(name = "remote_host")
    private String remoteHost;

    @NotNull
    @Length(min = 1, max = 20, message = "用户姓名长度超出范围(1,20)")
    @Column(name = "username")
    private String userName;

    @NotNull
    @Length(min = 5, max = 25, message = "邮件地址长度超出范围(5,25)")
    @Column(name = "email")
    private String email;

    @Length(min = 0, max = 50, message = "企业网址长度超出范围(10,50)")
    @Column(name = "website")
    private String website;

    @NotNull
    @Length(min = 5, max = 300, message = "留言信息超出范围(5,300)")
    @Column(name = "message")
    private String message;

    @Column(name = "protocool")
    private Boolean agreeProtocol;

    @NotNull
    @Column(name = "type")
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getAgreeProtocol() {
        return agreeProtocol;
    }

    public void setAgreeProtocol(Boolean agreeProtocol) {
        this.agreeProtocol = agreeProtocol;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
