import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface ArticleService {

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    void writeArticle(String title, String content);
    List<Article> listArticles();
    Article findById(int id);
    void updateArticle(int id, String newTitle, String newContent);
    void deleteArticle(int id);

    default String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DATE_TIME_FORMATTER);
    }
}
