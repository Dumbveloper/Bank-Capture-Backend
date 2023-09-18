package web.mvc.service.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.mvc.domain.Bank;
import web.mvc.domain.Banker;
import web.mvc.domain.Reservation;
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
    private ReservationRepository reservationRepository;
    @Autowired
    private BankerRepository bankerRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<BankerScheduleResponseDTO> bankerSchedule(Long bankerId) {
        List<BankerScheduleResponseDTO> list = reservationRepository.findReservationDetailsByBankerId(bankerId);
        return list;
    }



    @Override
    public Page<BankerRankingResponseDTO> bankerRanking(Long bankId,int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return bankerRepository.findBankerRankingByBankId(bankId, pageable);
    }

    @Override
    public String checkTime(ScheduleDTO scheduleDTO) {
        Banker insertBanker = bankerRepository.findById(scheduleDTO.getBankerId()).orElse(null);
        Bank insertBank = bankRepository.findById(scheduleDTO.getBankId()).orElse(null);
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
        scheduleRepository.save(insertSchedule);

        return "success";
    }

    @Override
    public String updateFlag(Long reservationId) {
        Reservation reviewReservation = reservationRepository.findById(reservationId).orElse(null);
        reviewReservation.setReservationFinishFlag("T");
        reservationRepository.save(reviewReservation);

        return "success";
    }
}
