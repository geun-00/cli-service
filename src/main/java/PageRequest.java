import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageRequest {
    private int pageNum;
    private int pageSize;

    public int getOffSet() {
        return (pageNum - 1) * pageSize;
    }
}
