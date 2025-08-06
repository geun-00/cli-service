import lombok.Getter;

import java.util.Set;

@Getter
public class Rq {

    private static final Set<String> ID_REQUIRED_COMMANDS = Set.of("detail", "update", "delete");
    private static final Set<String> KEYWORD_REQUIRED_COMMANDS = Set.of("search");
    private static final Set<String> ORDER_BY_REQUIRED_COMMANDS = Set.of("orderby");
    private static final Set<String> PAGE_INFO_OPTIONAL_COMMANDS = Set.of("list");

    private final String mainCommand;
    private int id;
    private String keyword;
    private String orderBy;

    private final PageRequest pageRequest = new PageRequest(1, 10);

    public Rq(String input) {
        String[] arr = input.split(" ");
        this.mainCommand = arr[0].toLowerCase();

        if (ID_REQUIRED_COMMANDS.contains(mainCommand)) {
            this.id = Integer.parseInt(arr[1]);
        }
        else if (KEYWORD_REQUIRED_COMMANDS.contains(mainCommand)) {
            this.keyword = input.substring(mainCommand.length());
        }
        else if (ORDER_BY_REQUIRED_COMMANDS.contains(mainCommand)) {
            this.orderBy = arr[1];
        }
        else if (PAGE_INFO_OPTIONAL_COMMANDS.contains(mainCommand)) {
            if (arr.length > 1) {
                setPageValue(arr[1]);

                if (arr.length > 2) {
                    setPageValue(arr[2]);
                }
            }
        }
    }

    private void setPageValue(String query) {
        String[] temp = query.split("=");

        String key = temp[0];
        String value = temp[1];

        if ("pageNum".equalsIgnoreCase(key)) {
            pageRequest.setPageNum(Integer.parseInt(value));
        }
        else if ("pageSize".equalsIgnoreCase(key)) {
            pageRequest.setPageSize(Integer.parseInt(value));
        }
    }
}
