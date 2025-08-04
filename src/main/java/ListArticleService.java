import java.util.ArrayList;
import java.util.List;

public class ListArticleService implements ArticleService {

    private int articleId = 1;
    private final List<Article> articleList = new ArrayList<>();


    @Override
    public void writeArticle(String title, String content) {
        Article article = new Article(articleId, title, content, getCurrentDate());
        articleList.add(article);

        articleId++;
    }

    @Override
    public List<Article> listArticles() {
        return new ArrayList<>(articleList);
    }

    @Override
    public Article findById(int id) {
        return articleList.stream()
                          .filter(article -> article.getId() == id)
                          .findFirst()
                          .orElse(null);
    }

    @Override
    public void updateArticle(int id, String newTitle, String newContent) {
        articleList.stream()
                   .filter(article -> article.getId() == id)
                   .findFirst()
                   .ifPresent(article -> {
                       article.setTitle(newTitle);
                       article.setContent(newContent);
                   });
    }

    @Override
    public void deleteArticle(int id) {
        articleList.removeIf(article -> article.getId() == id);
    }
}
