import java.util.List;
import java.util.Scanner;

public class App {

    private final Scanner sc;
    private final ArticleService articleService;

    public App(Scanner sc) {
        this.sc = sc;
        this.articleService = new ListArticleService();
    }

    public void run() {
        while (true) {

            System.out.print("명령어: ");
            String input = sc.nextLine();
            Rq rq = new Rq(input);

            switch (rq.getMainCommand()) {
                case "write" -> writeArticle();
                case "list" -> listArticles();
                case "detail" -> showDetail(rq.getId());
                case "update" -> updateArticle(rq.getId());
                case "delete" -> deleteArticle(rq.getId());
                case "search" -> searchArticles(rq.getKeyword());
                case "exit" -> {
                    exit();
                    return;
                }
            }
        }
    }

    private void searchArticles(String keyword) {
        printArticles(articleService.findByKeyword(keyword.toLowerCase()));
    }

    private void listArticles() {
        printArticles(articleService.listArticles());
    }

    private void printArticles(List<Article> articles) {
        System.out.printf("%-4s | %-20s | %-12s | %-6s\n", "번호", "제목", "등록일", "조회수");
        System.out.println("----------------------------------------------------------");

        for (Article article : articles) {
            System.out.printf(
                    "%-4d | %-20s | %-12s | %-6d\n",
                    article.getId(),
                    article.getTitle(),
                    article.getRegDate(),
                    article.getViewCount()
            );
        }
        System.out.println();
    }

    private void writeArticle() {
        System.out.print("제목: ");
        String title = sc.nextLine();

        System.out.print("내용: ");
        String content = sc.nextLine();

        articleService.writeArticle(title, content);
        System.out.println("=> 게시글이 등록되었습니다.\n");
    }

    private void showDetail(int id) {
        Article article = articleService.findById(id);
        if (article == null) {
            return;
        }

        article.addViewCount();

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

        articleService.updateArticle(id, newTitle, newContent);
        System.out.println("=> 게시글이 수정되었습니다.\n");
    }

    private void deleteArticle(int id) {
        articleService.deleteArticle(id);
        System.out.println("=> 게시글이 삭제되었습니다.\n");
    }

    private void exit() {
        System.out.println("프로그램을 종료합니다.");
    }
}
