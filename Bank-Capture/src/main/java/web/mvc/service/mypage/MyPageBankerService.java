package web.mvc.service.mypage;

import org.springframework.data.domain.Page;
import web.mvc.domain.Schedule;
import web.mvc.dto.mypage.BankerRankingResponseDTO;
import web.mvc.dto.mypage.BankerScheduleResponseDTO;
import web.mvc.dto.reservation.ScheduleDTO;

import java.util.List;

public interface MyPageBankerService {

    /**
     * /myPage/banker/schedule
     * 마이페이지(행원) - 예약관리
     * Reservation , Customer , Task , BankerReview
     *
     * @param bankerId
     * @return List<BankerScheduleResponseDTO>
     */
    List<BankerScheduleResponseDTO> bankerSchedule(Long bankerId);

    /**
     * /myPage/banker/ranking
     * 마이페이지(행원) 지점내 순위
     * Banker , 뷰
     *
     * @param bankId
     * @return List<BankerRankingResponseDTO>
     */
    Page<BankerRankingResponseDTO> bankerRanking(Long bankId,int page, int pageSize);

    /**
     * /myPage/banker/checktime
     * 마이페이지(행원) - 예약가능한 시간 체크
     * Schedule
     *
     * @return Object
     */
    String checkTime(ScheduleDTO scheduleDTO);

    /**
     * /myPage/banker/schedule/done
     * 마이페이지(행원) - 방문완료 체크
     * reservation_id
     */
    String updateFlag(Long reservationId);

}
