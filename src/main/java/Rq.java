public class Rq {

    private final String mainCommand;
    private int id;

    public Rq(String input) {
        String[] split = input.split(" ");
        this.mainCommand = split[0];
    }

    public String getMainCommand() {
        return mainCommand;
    }

    public int getId() {
        return id;
    }
}
