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

        PageRequest pageRequest = rq.getPageRequest();
        assertEquals(1, pageRequest.getPageNum());
        assertEquals(10, pageRequest.getPageSize());
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

    @Test
    @DisplayName("search [keyword] 명령어")
    void keyword() {
        //given
        String input = "search 자바 공부";

        //when
        Rq rq = new Rq(input);

        //then
        String[] split = input.split(" ");

        String expectedMainCommand = split[0];
        String expectedKeyword = input.substring(expectedMainCommand.length());

        assertEquals(expectedMainCommand, rq.getMainCommand());
        assertEquals(expectedKeyword, rq.getKeyword());
    }

    @Test
    @DisplayName("orderBy [option] 명령어")
    void orderBy() {
        //given
        String input = "orderBy date";

        //when
        Rq rq = new Rq(input);

        //then
        String[] split = input.split(" ");

        String expectedMainCommand = split[0];
        String expectedOrderBy = split[1];

        assertEquals(expectedMainCommand, rq.getMainCommand());
        assertEquals(expectedOrderBy, rq.getOrderBy());
    }

    @Test
    @DisplayName("list [페이징] 명령어")
    void test() {
        //given
        String input = "list pageNum=3 pageSize=5";

        //when
        Rq rq = new Rq(input);

        //then
        assertEquals("list", rq.getMainCommand());

        PageRequest pageRequest = rq.getPageRequest();
        assertEquals(3, pageRequest.getPageNum());
        assertEquals(5, pageRequest.getPageSize());
    }
}
