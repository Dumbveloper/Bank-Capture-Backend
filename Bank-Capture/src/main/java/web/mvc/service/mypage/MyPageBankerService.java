package web.mvc.service.mypage;

import web.mvc.dto.mypage.BankerRankingResponseDTO;
import web.mvc.dto.mypage.BankerScheduleAfterResponseDTO;
import web.mvc.dto.mypage.BankerScheduleBeforeResponseDTO;
import web.mvc.dto.reservation.ScheduleDTO;

import java.util.List;

public interface MyPageBankerService {
    /**
     * /myPage/banker/schedule/before
     * 마이페이지(행원) - 예약관리-오늘의 방문
     * Reservation , Customer , Task
     *
     * @param bankerId
     * @param finishFlag
     * @return List<BankerScheduleBeforeResponseDTO>
     */
    List<BankerScheduleBeforeResponseDTO> bankerScheduleBefore(Long bankerId, String finishFlag);

    /**
     * /myPage/banker/schedule/after
     * 마이페이지(행원) - 예약관리-방문후 예약
     * Reservation , Customer , Task , BankerReview
     *
     * @param bankerId
     * @param finishFlag
     * @return List<BankerScheduleAfterResponseDTO>
     */
    List<BankerScheduleAfterResponseDTO> bankerScheduleAfter(Long bankerId, String finishFlag);

    /**
     * /myPage/banker/ranking
     * 마이페이지(행원) 지점내 순위
     * Banker , 뷰
     *
     * @param bankId
     * @return List<BankerRankingResponseDTO>
     */
    List<BankerRankingResponseDTO> bankerRanking(Long bankId);

    /**
     * /myPage/banker/checktime
     * 마이페이지(행원) - 예약가능한 시간 체크
     * Schedule
     *
     * @return Object
     */
    Object checkTime(ScheduleDTO scheduleDTO);


}
