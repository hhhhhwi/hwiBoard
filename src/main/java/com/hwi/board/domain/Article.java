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
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql은 IDENTITY 방식으로 생성
    private Long id;

    @Setter // 전체 클래스에 Setter를 거는 것이 아니라 필드마다 Setter를 거는 이유 : 임의로 값을 변경할 수 없고 JPA가 직접 설정할 수 있도록 하기 위해
    @Column(nullable = false)
    private String title;   // 제목

    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 본문

    @Setter
    private String hashtag; // 해시태그

    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude
    private final Set<ArticleComment> articleComents = new LinkedHashSet<>();

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

    protected Article() {

    }

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public Article of(String title, String content, String hashtag) {
        // factory method를 이용하여 Article을 사용하기 위해 필요한 값을 가이드
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Article article)) return false;
        return id != null && Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
