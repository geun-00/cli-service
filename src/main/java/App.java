import java.util.Scanner;

public class App {

    private final Scanner sc;

    public App(Scanner sc) {
        this.sc = sc;
    }

    public void run() {
        String input = sc.nextLine();
        Rq rq = new Rq(input);

        switch (rq.getMainCommand()) {
            case "write" -> writeArticle();
            case "list" -> listArticles();
            case "detail" -> showDetail(rq.getId());
            case "update" -> updateArticle(rq.getId());
            case "delete" -> deleteArticle(rq.getId());
            case "exit" -> exit();
        }

    }

    private void exit() {
        System.out.println("프로그램을 종료합니다.");
    }

    private void deleteArticle(int id) {
        System.out.println("게시글이 삭제되었습니다.");
    }

    private void updateArticle(int id) {
        System.out.println("게시글이 수정되었습니다.");
    }

    private void showDetail(int id) {
        System.out.println("번호: ");
        System.out.println("제목: ");
        System.out.println("내용: ");
        System.out.println("등록일: ");
    }

    private void writeArticle() {
        System.out.println("게시글이 등록되었습니다.");
    }

    private void listArticles() {
        System.out.println("번호 | 제목     | 등록일      ");
    }
}
