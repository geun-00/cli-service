import java.util.List;
import java.util.Scanner;

public class App {

    private final Scanner sc;
    private final ArticleService articleService;

    public App(Scanner sc) {
        this.sc = sc;
//        this.articleService = new ListArticleService();
        this.articleService = new FileArticleService();
    }

    public void run() {
        while (true) {

            System.out.print("명령어: ");
            String input = sc.nextLine();
            Rq rq = new Rq(input);

            switch (rq.getMainCommand()) {
                case "write" -> writeArticle();
                case "list" -> listArticles(rq.getPageRequest());
                case "detail" -> showDetail(rq.getId());
                case "update" -> updateArticle(rq.getId());
                case "delete" -> deleteArticle(rq.getId());
                case "search" -> searchArticles(rq.getKeyword(), rq.getPageRequest());
                case "orderby" -> searchOrderByArticles(rq.getOrderBy(), rq.getPageRequest());
                case "exit" -> {
                    exit();
                    return;
                }
            }
        }
    }

    private void writeArticle() {
        System.out.print("제목: ");
        String title = sc.nextLine();

        System.out.print("내용: ");
        String content = sc.nextLine();

        if (title.isEmpty()) {
            System.out.println("=> !!!제목을 입력해 주세요!!!\n");
            return;
        }

        if (content.isEmpty()) {
            System.out.println("=> !!!내용을 입력해 주세요!!!\n");
            return;
        }

        articleService.writeArticle(title, content);
        System.out.println("=> 게시글이 등록되었습니다.\n");
    }

    private void listArticles(PageRequest pageRequest) {
        printArticles(articleService.listArticles(pageRequest, "default"));
    }

    private void showDetail(int id) {
        Article article = articleService.findById(id);
        if (article == null) {
            System.out.println(id + "번 게시글이 존재하지 않습니다.\n");
            return;
        }

        System.out.println("번호: " + article.getId());
        System.out.println("제목: " + article.getTitle());
        System.out.println("내용: " + article.getContent());
        System.out.println("등록일: " + article.getRegDate());
        System.out.println("조회수: " + article.getViewCount());
        System.out.println();
    }

    private void updateArticle(int id) {
        Article article = articleService.findById(id);

        if (article == null) {
            System.out.println(id + "번 게시글이 존재하지 않습니다.\n");
            return;
        }

        String oldTitle = article.getTitle();
        String oldContent = article.getContent();

        System.out.printf("제목 (현재: %s): ", oldTitle);
        String newTitle = sc.nextLine();

        System.out.printf("내용 (현재: %s): ", oldContent);
        String newContent = sc.nextLine();

        if (newTitle.isEmpty()) {
            System.out.println("=> !!!새로운 제목을 입력해 주세요!!!\n");
            return;
        }

        if (newContent.isEmpty()) {
            System.out.println("=> !!!새로운 내용을 입력해 주세요!!!\n");
            return;
        }

        articleService.updateArticle(id, newTitle, newContent);
        System.out.println("=> 게시글이 수정되었습니다.\n");
    }

    private void deleteArticle(int id) {
        if (!articleService.deleteArticle(id)) {
            System.out.println(id + "번 게시글이 존재하지 않습니다.\n");
            return;
        }

        System.out.println("=> 게시글이 삭제되었습니다.\n");
    }

    private void searchArticles(String keyword, PageRequest pageRequest) {
        if (keyword.isEmpty()) {
            System.out.println("=> !!!검색 키워드를 입력해 주세요!!!\n");
            return;
        }

        printArticles(articleService.findByKeyword(
                keyword.toLowerCase(), pageRequest, "default"
        ));
    }

    private void searchOrderByArticles(String orderBy, PageRequest pageRequest) {
        PageResponse<Article> pageResponse = articleService.listArticles(pageRequest, orderBy);
        printArticles(pageResponse);
    }

    private void exit() {
        System.out.println("프로그램을 종료합니다.");
    }

    private void printArticles(PageResponse<Article> pagedArticles) {
        String format = "%-5s | %-15s | %-12s | %-7s\n";

        int columnLength = 60;

        System.out.println();
        System.out.println("=".repeat(columnLength));
        System.out.printf(format, "번호", "제목", "등록일", "조회수");
        System.out.println("-".repeat(columnLength));

        List<Article> articles = pagedArticles.getContents();
        int currentPage = pagedArticles.getCurrentPage();
        int totalPage = pagedArticles.getTotalPage();
        int totalCount = pagedArticles.getTotalCount();

        if (articles.isEmpty()) {
            System.out.println("           !!!아직 등록된 게시글이 없습니다.!!!     \n");
            return;
        }

        articles.forEach(article -> System.out.printf(
                format,
                article.getId(),
                article.getTitle(),
                article.getRegDate(),
                article.getViewCount()
        ));

        System.out.println("-".repeat(columnLength));
        System.out.printf("        [현재 페이지] %d / %d [마지막 페이지]  [total: %d]\n", currentPage, totalPage, totalCount);
        System.out.println("=".repeat(columnLength));
        System.out.println();
    }
}
