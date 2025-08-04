import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RqTest {

    @Test
    @DisplayName("write 명령어")
    void test() {
        //given
        String input = "write";

        //when
        Rq rq = new Rq(input);

        //then
        assertEquals(input, rq.getMainCommand());
    }
}
