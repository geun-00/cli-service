import java.util.Set;

public class Rq {

    private static final Set<String> ID_REQUIRED_COMMANDS = Set.of("detail", "update", "delete");
    private static final Set<String> KEYWORD_REQUIRED_COMMANDS = Set.of("search");
    private static final Set<String> ORDER_BY_REQUIRED_COMMANDS = Set.of("orderBy");

    private final String mainCommand;
    private int id;
    private String keyword;
    private String orderBy;

    public Rq(String input) {
        String[] split = input.split(" ");
        this.mainCommand = split[0];

        if (ID_REQUIRED_COMMANDS.contains(mainCommand)) {
            this.id = Integer.parseInt(split[1]);
        } else if (KEYWORD_REQUIRED_COMMANDS.contains(mainCommand)) {
            this.keyword = input.substring(mainCommand.length());
        } else if (ORDER_BY_REQUIRED_COMMANDS.contains(mainCommand)) {
            this.orderBy = split[1];
        }
    }

    public String getMainCommand() {
        return mainCommand;
    }

    public int getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getOrderBy() {
        return orderBy;
    }
}
