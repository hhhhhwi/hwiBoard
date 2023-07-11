package com.hwi.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
//@EntityListeners(AuditingEntityListener.class) // AuditingFields 에 구현
@Entity
public class Article extends AuditingFields{
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

    //@Embedded // 클래스 하나를 만들어서 해당 클래스를 필드로 추가하는 방식
    //@MappedSuperclass 로 구현

    protected Article() {

    }

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
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
