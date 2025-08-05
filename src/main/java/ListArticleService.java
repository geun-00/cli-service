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
        return articleList.stream()
                          .sorted((origin, other) -> other.getRegDate().compareTo(origin.getRegDate()))
                          .toList();
    }

    @Override
    public Article findById(int id) {
        Article foundArticle = articleList.stream()
                                      .filter(article -> article.getId() == id)
                                      .findFirst()
                                      .orElse(null);

        if (foundArticle != null) {
            foundArticle.addViewCount();
            return foundArticle;
        }

        return null;
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

    @Override
    public List<Article> findByKeyword(String keyword) {
        return articleList.stream()
                          .filter(article ->
                                  article.getTitle().contains(keyword) || article.getContent().contains(keyword)
                          )
                          .sorted((origin, other) -> other.getRegDate().compareTo(origin.getRegDate()))
                          .toList();
    }
}
