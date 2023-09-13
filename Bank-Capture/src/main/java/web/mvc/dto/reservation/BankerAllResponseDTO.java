package web.mvc.dto.reservation;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /reservation/bankerAll
 * 예약하기 - 예약가능한 행원 조회
 */
public class BankerAllResponseDTO {
    private Long bankerId;  //행원 ID
    private String bankerName;  //행원 이름
    private String bankerCareer; // 행원 경력
    private String bankerImgPath;  //행원 사진
    private String bankerInfo;  //행원 한줄 소개
    private double bankerAvgStar; // 평균 별점
    private Long bankerCommentCnt; // 리뷰수
    private String bankerReviewFlag; //행원 리뷰 공개 여부 플래그
    private List<ScheduleDTO> scheduleList = new ArrayList<>(); // 스케줄 리스트
    private List<TaskDTO> taskList = new ArrayList<>(); // 행원 주업무 리스트

    public BankerAllResponseDTO (Long bankerId, String bankerName, String bankerCareer
            , String bankerImgPath, String bankerInfo, double bankerAvgStar, Long bankerCommentCnt, String bankerReviewFlag){
        this.bankerId = bankerId;
        this.bankerName = bankerName;
        this.bankerCareer = bankerCareer;
        this.bankerImgPath = bankerImgPath;
        this.bankerInfo = bankerInfo;
        this.bankerAvgStar = bankerAvgStar;
        this.bankerCommentCnt = bankerCommentCnt;
        this.bankerReviewFlag = bankerReviewFlag;
    }

}
