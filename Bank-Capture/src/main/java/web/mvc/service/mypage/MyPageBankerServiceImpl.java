package web.mvc.service.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.mvc.domain.Bank;
import web.mvc.domain.Banker;
import web.mvc.domain.Schedule;
import web.mvc.dto.mypage.BankerRankingResponseDTO;
import web.mvc.dto.mypage.BankerScheduleResponseDTO;
import web.mvc.dto.reservation.ScheduleDTO;
import web.mvc.repository.BankRepository;
import web.mvc.repository.BankerRepository;
import web.mvc.repository.ReservationRepository;
import web.mvc.repository.ScheduleRepository;

import java.util.List;

@Service
public class MyPageBankerServiceImpl implements MyPageBankerService{
    @Autowired
    private ReservationRepository reservationRep;
    @Autowired
    private BankerRepository bankerRep;
    @Autowired
    private BankRepository bankRep;
    @Autowired
    private ScheduleRepository scheduleRep;

    @Override
    public List<BankerScheduleResponseDTO> bankerSchedule(Long bankerId) {
        List<BankerScheduleResponseDTO> list = reservationRep.findReservationDetailsByBankerId(bankerId);
        return list;
    }

    @Override
    public List<BankerRankingResponseDTO> bankerRanking(Long bankId) {
        return null;
    }

    @Override
    public Schedule checkTime(ScheduleDTO scheduleDTO) {
        Banker insertBanker = bankerRep.findById(scheduleDTO.getBankerId()).orElse(null);
        Bank insertBank = bankRep.findById(scheduleDTO.getBankId()).orElse(null);
        Schedule insertSchedule = Schedule.builder()
                .banker(insertBanker)
                .bank(insertBank)
                .scheduleDate(scheduleDTO.getScheduleDate())
                .time1(scheduleDTO.getTime1())
                .time2(scheduleDTO.getTime2())
                .time3(scheduleDTO.getTime3())
                .time4(scheduleDTO.getTime4())
                .time5(scheduleDTO.getTime5())
                .time6(scheduleDTO.getTime6())
                .time7(scheduleDTO.getTime7()).build();
        Schedule result = scheduleRep.save(insertSchedule);

        return result;
    }
}
