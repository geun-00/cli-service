import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    @Test
    @DisplayName("write 명령어는 \"게시글이 등록되었습니다.\"를 출력한다. ")
    void write() {
        //given
        String input = """
                write
                자바 공부
                자바 텍스트 게시판 만들기
                exit
                """;

        //when
        String result = AppTestRunner.run(input);

        //then
        String expectedOutput = "게시글이 등록되었습니다.";
        assertThat(result).contains(expectedOutput);
    }

    @Test
    @DisplayName("list 명령어는 \"번호 | 제목 | 등록일 | 조회수\"를 출력한다.")
    void list() {
        //given
        String input = """
                list
                exit
                """;

        //when
        String result = AppTestRunner.run(input);

        //then
        String expectedOutput = "번호   | 제목                   | 등록일          | 조회수   ";
        assertThat(result).contains(expectedOutput);
    }

    @Test
    @DisplayName("detail [id] 명령어는 \"번호:, 제목:, 내용:, 등록일:, 조회수: \"를 출력한다.")
    void detail() {
        //given
        String input = """
                write
                자바 공부
                자바 텍스트 게시판 만들기
                detail 1
                exit
                """;

        //when
        String result = AppTestRunner.run(input);

        //then
        assertThat(result).contains("번호: ");
        assertThat(result).contains("제목: ");
        assertThat(result).contains("내용: ");
        assertThat(result).contains("등록일: ");
        assertThat(result).contains("조회수: ");
    }

    @Test
    @DisplayName("update [id] 명령어는 \"게시글이 수정되었습니다.\"를 출력한다.")
    void update() {
        //given
        String input = """
                write
                자바 공부
                자바 텍스트 게시판 만들기
                update 1
                Java 게시판
                콘솔 기반으로 구현
                exit
                """;

        //when
        String result = AppTestRunner.run(input);

        //then
        assertThat(result).contains("게시글이 수정되었습니다.");
    }

    @Test
    @DisplayName("delete [id] 명령어는 \"게시글이 삭제되었습니다.\"를 출력한다.")
    void delete() {
        //given
        String input = """
                delete 1
                exit
                """;

        //when
        String result = AppTestRunner.run(input);

        //then
        assertThat(result).contains("게시글이 삭제되었습니다.");
    }

    @Test
    @DisplayName("exit [id] 명령어는 \"프로그램을 종료합니다.\"를 출력한다.")
    void exit() {
        //given
        String input = "exit";

        //when
        String result = AppTestRunner.run(input);

        //then
        assertThat(result).contains("프로그램을 종료합니다.");
    }
}
