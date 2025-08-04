import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    @Test
    @DisplayName("write 명령어는 \"게시글이 등록되었습니다.\"를 출력한다. ")
    void test() {
        //given
        String input = "write";

        //when
        String result = AppTestRunner.run(input);

        //then
        String expectedOutput = "게시글이 등록되었습니다.";
        assertThat(result).contains(expectedOutput);
    }
}
