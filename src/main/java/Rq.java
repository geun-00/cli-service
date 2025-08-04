import java.util.Map;

public class Rq {

    private final String mainCommand;
    private Map<String, String> params;

    public Rq(String input) {
        String[] split = input.split(" ");
        this.mainCommand = split[0];
    }

    public String getMainCommand() {
        return mainCommand;
    }
}
