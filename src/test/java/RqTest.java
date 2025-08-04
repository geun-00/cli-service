import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RqTest {

    @Test
    @DisplayName("write 명령어")
    void write() {
        //given
        String input = "write";

        //when
        Rq rq = new Rq(input);

        //then
        assertEquals(input, rq.getMainCommand());
    }

    @Test
    @DisplayName("list 명령어")
    void list() {
        //given
        String input = "list";

        //when
        Rq rq = new Rq(input);

        //then
        assertEquals(input, rq.getMainCommand());
    }
}
