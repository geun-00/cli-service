import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponse<E> {

    private List<E> contents;   //데이터 목록
    private int totalPage;      //총 페이지
    private int currentPage;    //현재 페이지
    private int totalCount;     //총 데이터 개수
}
