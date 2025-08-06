import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public interface ArticleService {

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    void writeArticle(String title, String content);
    PageResponse<Article> listArticles(PageRequest pageRequest, String orderBy);
    Article findById(int id);
    void updateArticle(int id, String newTitle, String newContent);
    void deleteArticle(int id);
    PageResponse<Article> findByKeyword(String keyword, PageRequest pageRequest, String orderBy);

    default String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DATE_TIME_FORMATTER);
    }

    default Comparator<Article> getComparator(String orderBy) {
        if ("id".equalsIgnoreCase(orderBy)) {
            return Comparator.comparingInt(Article::getId);
        }
        if ("view".equalsIgnoreCase(orderBy)) {
            return Comparator.comparingInt(Article::getViewCount).reversed()
                             .thenComparing(Comparator.comparingInt(Article::getId).reversed());
        }

        //default - 최신순 정렬
        return Comparator.comparing(Article::getRegDate).reversed()
                         .thenComparing(Comparator.comparingInt(Article::getId).reversed());
    }
}
