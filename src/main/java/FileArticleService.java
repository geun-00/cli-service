import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FileArticleService implements ArticleService {

    private static final String DB_PATH_PREFIX = "src/main/java/db/";
    private static final String LAST_ID_PATH = DB_PATH_PREFIX + "lastId.txt";
    private static final String ARTICLE_LIST_PATH = DB_PATH_PREFIX + "articles";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void writeArticle(String title, String content) {
        int id = getLastId();

        attemptWrite(id, new Article(id, title, content, getCurrentDate()));
        updateLastId(id + 1);
    }

    @Override
    public PageResponse<Article> listArticles(PageRequest pageRequest, String orderBy) {
        Path path = Path.of(ARTICLE_LIST_PATH);

        try (Stream<Path> files = Files.list(path)) {
            List<Article> articleList = files.map(this::parseToArticle)
                                             .filter(Objects::nonNull)
                                             .sorted(getComparator(orderBy))
                                             .toList();

            return getArticlePageResponse(pageRequest, articleList);

        } catch (IOException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Article findById(int id) {
        Path path = Path.of(ARTICLE_LIST_PATH);

        try {
            File[] files = path.toFile().listFiles();

            if (files == null) {
                return null;
            }

            for (File file : files) {
                Article article = attemptFindArticleAndAddViewCount(file, id);
                if (article != null) {
                    return article;
                }
            }

        } catch (IOException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void updateArticle(int id, String newTitle, String newContent) {
        Path path = Path.of(ARTICLE_LIST_PATH);

        try {
            File[] files = path.toFile().listFiles();

            if (files == null) {
                return;
            }

            for (File file : files) {
                if (attemptUpdateFile(file, id, newTitle, newContent)) {
                    return;
                }
            }

        } catch (IOException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteArticle(int id) {
        Path path = Path.of(ARTICLE_LIST_PATH);

        try {
            File[] files = path.toFile().listFiles();

            if (files == null) {
                return false;
            }

            for (File file : files) {
                if (attemptDeleteFile(file, id)) {
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public PageResponse<Article> findByKeyword(String keyword, PageRequest pageRequest, String orderBy) {
        Path path = Path.of(ARTICLE_LIST_PATH);

        try (Stream<Path> files = Files.list(path)) {

            List<Article> articleList = files.map(this::parseToArticle)
                                             .filter(Objects::nonNull)
                                             .filter(article ->
                                                     article.getTitle().contains(keyword) || article.getContent().contains(keyword)
                                             )
                                             .sorted(getComparator(orderBy))
                                             .toList();

            return getArticlePageResponse(pageRequest, articleList);

        } catch (IOException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
        }
    }

    private PageResponse<Article> getArticlePageResponse(PageRequest pageRequest, List<Article> articleList) {
        int totalPage = (int) Math.ceil(((double) articleList.size() / pageRequest.getPageSize()));

        List<Article> contents = articleList.stream()
                                            .skip(pageRequest.getOffSet())
                                            .limit(pageRequest.getPageSize())
                                            .toList();
        return new PageResponse<>(
                contents,
                totalPage,
                pageRequest.getPageNum(),
                articleList.size()
        );
    }

    private void attemptWrite(int id, Article article) {
        Path path = Path.of(ARTICLE_LIST_PATH, "article-" + id + ".json");

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.write(objectMapper.writeValueAsString(article));
        } catch (IOException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
        }
    }

    private Article attemptFindArticleAndAddViewCount(File file, int id) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        Article article = objectMapper.readValue(br, Article.class);
        if (article.getId() == id) {
            article.addViewCount();
            attemptWrite(id, article);
            return article;
        }

        return null;
    }

    private boolean attemptUpdateFile(File file, int id, String newTitle, String newContent) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        Article article = objectMapper.readValue(br, Article.class);
        if (article.getId() == id) {
            article.setTitle(newTitle);
            article.setContent(newContent);

            attemptWrite(id, article);
            return true;
        }

        return false;
    }

    private boolean attemptDeleteFile(File file, int id) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        Article article = objectMapper.readValue(br, Article.class);
        if (article.getId() == id) {
            return file.delete();
        }

        return false;
    }

    private Article parseToArticle(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            return objectMapper.readValue(br.readLine(), Article.class);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

        return null;
    }

    private int getLastId() {
        Path path = Path.of(LAST_ID_PATH);

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String id = br.readLine();
            return Integer.parseInt(id);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
        }
    }

    private void updateLastId(int id) {
        Path path = Path.of(LAST_ID_PATH);

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.write(String.valueOf(id));
        } catch (IOException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
        }
    }
}
