import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    @Test
    @DisplayName("write 명령어는 \"게시글이 등록되었습니다.\"를 출력한다. ")
    void write() {
        //given
        String input = "write";

        //when
        String result = AppTestRunner.run(input);

        //then
        String expectedOutput = "게시글이 등록되었습니다.";
        assertThat(result).contains(expectedOutput);
    }

    @Test
    @DisplayName("list 명령어는 \"번호 | 제목 | 등록일\"을 출력한다.")
    void list() {
        //given
        String input = "list";

        //when
        String result = AppTestRunner.run(input);

        //then
        String expectedOutput = "번호 | 제목     | 등록일      ";
        assertThat(result).contains(expectedOutput);
    }

    @Test
    @DisplayName("detail [id] 명령어는 \"번호:, 제목:, 내용:, 등록일:\"을 출력한다.")
    void detail() {
        //given
        String input = "detail 1";

        //when
        String result = AppTestRunner.run(input);

        //then
        assertThat(result).contains("번호: ");
        assertThat(result).contains("제목: ");
        assertThat(result).contains("내용: ");
        assertThat(result).contains("등록일: ");
    }

    @Test
    @DisplayName("update [id] 명령어는 \"게시글이 수정되었습니다.\"를 출력한다.")
    void update() {
        //given
        String input = "update 1";

        //when
        String result = AppTestRunner.run(input);

        //then
        assertThat(result).contains("게시글이 수정되었습니다.");
    }

    @Test
    @DisplayName("delete [id] 명령어는 \"게시글이 삭제되었습니다.\"를 출력한다.")
    void delete() {
        //given
        String input = "delete 1";

        //when
        String result = AppTestRunner.run(input);

        //then
        assertThat(result).contains("게시글이 삭제되었습니다.");
    }
}
