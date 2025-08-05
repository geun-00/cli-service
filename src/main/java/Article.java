import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Article {
    private int id;
    private String regDate;
    private String title;
    private String content;
    private int viewCount;

    public Article(int id, String title, String content, String regDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.regDate = regDate;
    }

    public void addViewCount() {
        this.viewCount++;
    }
}
