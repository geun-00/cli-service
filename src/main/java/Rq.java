import java.util.Map;

public class Rq {

    private final String mainCommand;
    private Map<String, String> params;

    public Rq(String input) {
        this.mainCommand = input;
    }

    public String getMainCommand() {
        return mainCommand;
    }
}
