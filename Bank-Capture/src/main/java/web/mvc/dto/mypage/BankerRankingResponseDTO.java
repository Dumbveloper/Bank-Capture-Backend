package web.mvc.dto.mypage;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /myPage/banker/ranking
 * 마이페이지(행원) 지점내 순위
 */
public class BankerRankingResponseDTO {
    private String bankerName;  //행원 이름
    private double bankerAvgStar; // 행원 평균 별점
    private int bankerCommentCnt; // 행원 별점 수
    private String bankerImgPath;  //행원 사진
}
