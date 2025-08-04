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

    @Test
    @DisplayName("detail [id] 명령어")
    void detail() {
        //given
        String input = "detail 1";

        //when
        Rq rq = new Rq(input);

        //then
        String[] split = input.split(" ");

        String expectedMainCommand = split[0];
        int expectedId = Integer.parseInt(split[1]);

        assertEquals(expectedMainCommand, rq.getMainCommand());
        assertEquals(expectedId, rq.getId());
    }

    @Test
    @DisplayName("update [id] 명령어")
    void update() {
        //given
        String input = "update 1";

        //when
        Rq rq = new Rq(input);

        //then
        String[] split = input.split(" ");

        String expectedMainCommand = split[0];
        int expectedId = Integer.parseInt(split[1]);

        assertEquals(expectedMainCommand, rq.getMainCommand());
        assertEquals(expectedId, rq.getId());
    }

    @Test
    @DisplayName("delete [id] 명령어")
    void delete() {
        //given
        String input = "delete 1";

        //when
        Rq rq = new Rq(input);

        //then
        String[] split = input.split(" ");

        String expectedMainCommand = split[0];
        int expectedId = Integer.parseInt(split[1]);

        assertEquals(expectedMainCommand, rq.getMainCommand());
        assertEquals(expectedId, rq.getId());
    }

    @Test
    @DisplayName("exit 명령어")
    void exit() {
        //given
        String input = "exit";

        //when
        Rq rq = new Rq(input);

        //then
        assertEquals(input, rq.getMainCommand());
    }
}
