import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class ArticleServiceTest {

    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        this.articleService = new ListArticleService();
    }

    @Test
    @DisplayName("getCurrentDate()는 오늘 날짜를 yyyy-MM-dd 형식의 String을 반환한다.")
    void getCurrentDate() {
        //given
        //when
        String currentDate = articleService.getCurrentDate();

        //then
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonth().getValue();
        int day = now.getDayOfMonth();

        String expectedDate = String.format("%d-%02d-%02d", year, month, day);
        assertThat(currentDate).isEqualTo(expectedDate);
    }

    @Test
    @DisplayName("writeArticle()은 Article 등록에 성공한다.")
    void writeArticle() {
        //given
        String title = "자바 공부";
        String content = "자바 텍스트 게시판 만들기";

        //when
        articleService.writeArticle(title, content);

        //then
        Article article = articleService.findById(1);
        assertThat(article.getTitle()).isEqualTo(title);
        assertThat(article.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("listArticles()는 등록된 Article을 정확히 반환한다.")
    void listArticles() {
        //given
        String title1 = "자바 공부";
        String content1 = "자바 텍스트 게시판 만들기";
        String title2 = "파이썬 공부";
        String content2 = "파이썬 텍스트 게시판 만들기";
        String title3 = "C 공부";
        String content3 = "C 텍스트 게시판 만들기";

        articleService.writeArticle(title1, content1);
        articleService.writeArticle(title2, content2);
        articleService.writeArticle(title3, content3);

        //when
        PageResponse<Article> pageResponse = articleService.listArticles(
                new PageRequest(1, 10), "default");
        List<Article> articles = pageResponse.getContents();

        //then
        assertThat(articles).hasSize(3);
        assertThat(articles).extracting("title", "content")
                .containsExactlyInAnyOrder(
                        tuple(title1, content1),
                        tuple(title2, content2),
                        tuple(title3, content3)
                );
    }

    @Test
    @DisplayName("findById()는 id에 해당하는 Article을 정확히 조회한다.")
    void findById() {
        //given
        int targetId = 1;
        String title = "자바 공부";
        String content = "자바 텍스트 게시판 만들기";
        articleService.writeArticle(title, content);

        //when
        Article article = articleService.findById(targetId);

        //then
        assertThat(article.getId()).isEqualTo(targetId);
        assertThat(article.getTitle()).isEqualTo(title);
        assertThat(article.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("updateArticle()은 id에 해당하는 Article을 수정한다.")
    void updateArticle() {
        //given
        int targetId = 1;
        String oldTitle = "자바 공부";
        String oldContent = "자바 텍스트 게시판 만들기";
        articleService.writeArticle(oldTitle, oldContent);

        String newTitle = "파이썬 공부";
        String newContent = "파이썬 텍스트 게시판 만들기";

        //when
        articleService.updateArticle(targetId, newTitle, newContent);
        Article updatedArticle = articleService.findById(targetId);

        //then
        assertThat(updatedArticle.getTitle()).isNotEqualTo(oldTitle);
        assertThat(updatedArticle.getContent()).isNotEqualTo(oldContent);

        assertThat(updatedArticle.getTitle()).isEqualTo(newTitle);
        assertThat(updatedArticle.getContent()).isEqualTo(newContent);
    }

    @Test
    @DisplayName("deleteArticle()은 id에 해당하는 Article을 삭제한다.")
    void deleteArticle() {
        //given
        String title1 = "자바 공부";
        String content1 = "자바 텍스트 게시판 만들기";
        String title2 = "파이썬 공부";
        String content2 = "파이썬 텍스트 게시판 만들기";
        String title3 = "C 공부";
        String content3 = "C 텍스트 게시판 만들기";

        articleService.writeArticle(title1, content1);
        articleService.writeArticle(title2, content2);
        articleService.writeArticle(title3, content3);

        int targetId = 1;

        //when
        boolean result = articleService.deleteArticle(targetId);

        //then
        Article article = articleService.findById(targetId);
        PageResponse<Article> pageResponse = articleService.listArticles(
                new PageRequest(1, 10), "default");
        List<Article> articles = pageResponse.getContents();

        assertThat(result).isTrue();
        assertThat(article).isNull();
        assertThat(articles).hasSize(2);
    }

    @Test
    @DisplayName("findByKeyword()는 keyword를 포함하는 Article을 반환한다.")
    void test() {
        //given
        String title1 = "자바 공부";
        String content1 = "자바 텍스트 게시판 만들기";
        String title2 = "파이썬 공부";
        String content2 = "파이썬 텍스트 게시판 만들기";
        String title3 = "C 공부";
        String content3 = "C 텍스트 게시판 만들기";

        articleService.writeArticle(title1, content1);
        articleService.writeArticle(title2, content2);
        articleService.writeArticle(title3, content3);

        String keyword = "텍스트 게시판 만들기";

        //when
        PageResponse<Article> pageResponse = articleService.findByKeyword(
                keyword, new PageRequest(1, 10), "default");
        List<Article> articles = pageResponse.getContents();

        //then
        assertThat(articles).hasSize(3);
    }
}
