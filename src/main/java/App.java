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
        }

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
