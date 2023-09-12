package web.mvc.service.mypage;

import org.springframework.stereotype.Service;
import web.mvc.domain.Schedule;
import web.mvc.dto.mypage.BankerRankingResponseDTO;
import web.mvc.dto.mypage.BankerScheduleResponseDTO;
import web.mvc.dto.reservation.ScheduleDTO;

import java.util.List;

@Service
public class MyPageBankerServiceImpl implements MyPageBankerService{

    @Override
    public List<BankerScheduleResponseDTO> bankerSchedule(Long bankerId) {
        return null;
    }

    @Override
    public List<BankerRankingResponseDTO> bankerRanking(Long bankId) {
        return null;
    }

    @Override
    public Schedule checkTime(ScheduleDTO scheduleDTO) {
        return null;
    }
}
