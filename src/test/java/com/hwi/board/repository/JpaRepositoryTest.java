package com.hwi.board.repository;

import com.hwi.board.config.JpaConfig;
import com.hwi.board.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class) // JpaAuditing 기능을 사용하기 위해 import
@DataJpaTest
class JpaRepositoryTest {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("조회 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When
        List<Article> articles = articleRepository.findAll();

        // Then
        assertThat(articles)
                .isNotNull()
                .hasSize(199);
    }

    @DisplayName("등록 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // Given
        long previousCount = articleRepository.count();

        // When
        articleRepository.save(Article.of("new article", "new content", "spring"));

        // Then
        assertThat(articleRepository.count())
                .isEqualTo(previousCount+1);
    }

    @DisplayName("수정 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        //DataJpaTest 의 기본 트랜젝션은 롤백 -> update 같은 동작을 생략

        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updateHashtag = "#springboot";
        article.setHashtag(updateHashtag);

        // When
        Article savedArticle = articleRepository.saveAndFlush(article); // 그래서 flush를 해야 update 쿼리를 생성 -> 다만 이 내용은 롤백되기 때문에 반영 되지는 않는다.

        // Then
        assertThat(savedArticle)
                .hasFieldOrPropertyWithValue("hashtag", updateHashtag);
    }

    @DisplayName("삭제 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previoutArticleCommentCount = articleCommentRepository.count();
        long deletedCommentsSize = article.getArticleComents().size();

        // When
        articleRepository.delete(article);

        // Then
        assertThat(articleRepository.count())
                .isEqualTo(previousArticleCount-1);
        assertThat(articleCommentRepository.count()).isEqualTo(previoutArticleCommentCount - deletedCommentsSize);
    }
}