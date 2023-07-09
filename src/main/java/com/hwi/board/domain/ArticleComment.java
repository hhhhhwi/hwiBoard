package com.hwi.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@ToString
@Entity
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql은 IDENTITY 방식으로 생성
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    private Article article; // cascade none

    @Setter
    @Column(nullable = false, length = 500)
    private String content; // 본문

    @CreatedDate // JPA가 자동으로 세팅
    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성일자

    @CreatedBy // '누구'에 대한 설정 -> JpaConfig
    @Column(nullable = false, length = 100)
    private String createdBy; // 생성자

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일자

    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy; // 수정자

    protected ArticleComment() {

    }

    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (! (o instanceof ArticleComment that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
