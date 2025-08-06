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
    public PageResponse<Article> listArticles(PageRequest pageRequest, String orderBy) {
        List<Article> contents = articleList.stream()
                                            .sorted(getComparator(orderBy))
                                            .skip(pageRequest.getOffSet())
                                            .limit(pageRequest.getPageSize())
                                            .toList();
        int totalPage = (int) Math.ceil(((double) articleList.size() / pageRequest.getPageSize()));
        return new PageResponse<>(
                contents,
                totalPage,
                pageRequest.getPageNum(),
                articleList.size()
        );
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
    public PageResponse<Article> findByKeyword(String keyword, PageRequest pageRequest, String orderBy) {
        List<Article> contents = articleList.stream()
                                            .filter(article ->
                                                    article.getTitle().contains(keyword) || article.getContent().contains(keyword)
                                            )
                                            .sorted(getComparator(orderBy))
                                            .skip(pageRequest.getOffSet())
                                            .limit(pageRequest.getPageSize())
                                            .toList();

        int totalPage = (int) Math.ceil(((double) articleList.size() / pageRequest.getPageSize()));
        return new PageResponse<>(
                contents,
                totalPage,
                pageRequest.getPageNum(),
                articleList.size()
        );
    }
}
