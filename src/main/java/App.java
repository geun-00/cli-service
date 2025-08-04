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
        }

    }

    private void writeArticle() {
        System.out.println("게시글이 등록되었습니다.");
    }
}
