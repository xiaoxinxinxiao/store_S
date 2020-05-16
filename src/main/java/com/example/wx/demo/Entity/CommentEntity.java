package com.example.wx.demo.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "even_info")
@Entity
public class CommentEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "per_id")
    private String personId;

    @Column(name = "article_id")
    private String articleId;

    @Column(name = "detail_info")
    private String detailInfo;

    @Column(name = "c_time")
    private long cTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public long getcTime() {
        return cTime;
    }

    public void setcTime(long cTime) {
        this.cTime = cTime;
    }
}
