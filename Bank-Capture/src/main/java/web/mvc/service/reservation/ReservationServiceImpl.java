package web.mvc.service.reservation;

import com.querydsl.core.types.Path;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.mvc.domain.*;
import web.mvc.dto.reservation.BankerAllResponseDTO;
import web.mvc.dto.reservation.BankerInfoResponseDTO;
import web.mvc.dto.reservation.ReservationDTO;
import web.mvc.repository.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankerRepository bankerRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<BankerAllResponseDTO> findBankerAll(String bankId) {
        return null;
    }

    @Override
    public BankerInfoResponseDTO findBankerInfo(String bankerId) {
        return null;
    }

    @Override
    @Transactional
    public String doReservation(ReservationDTO reservationDTO, Long reservationId) {

        Customer customer = customerRepository.findById(reservationDTO.getCustomerId()).orElse(null);
        Banker banker = bankerRepository.findById(reservationDTO.getBankerId()).orElse(null);
        Task task = taskRepository.findById(reservationDTO.getTaskId()).orElse(null);

        Reservation reservation = Reservation.builder().
                customer(customer).
                banker(banker).
                task(task).
                reservationDate(reservationDTO.getReservationDate()).
                reservationTime(reservationDTO.getReservationTime()).
                reservationFinishFlag(reservationDTO.getReservationFinishFlag()).
                build();

        //예약하기
        reservationRepository.save(reservation);

        QSchedule qSchedule = QSchedule.schedule;

        //예약 시간번호 앞에 "time"을 붙임
        String time = "time" + reservationDTO.getReservationTime();

        //예약시간번호를 스케줄테이블의 칼럼이름과 매핑
        Map<String, Path<?>> columnMap = new HashMap<>();
        columnMap.put("time1", qSchedule.time1);
        columnMap.put("time2", qSchedule.time2);
        columnMap.put("time3", qSchedule.time3);
        columnMap.put("time4", qSchedule.time4);
        columnMap.put("time5", qSchedule.time5);
        columnMap.put("time6", qSchedule.time6);
        columnMap.put("time7", qSchedule.time7);

        Path<?> columnPath = columnMap.get(time);

        //해당 예약시간에 해당하는 행원의 스케줄을 0으로 변경
        long update = jpaQueryFactory
                .update(qSchedule)
                .set((Path<Long>)columnPath, 0L)
                .where(qSchedule.banker.eq(banker), qSchedule.scheduleDate.eq(reservationDTO.getReservationDate()))
                .execute();

        //업데이트가 성공이면 "success"리턴
        if(update == 1)
            return "success";

        return "fail";

    }

    @Override
    @Transactional
    public String cancelReservation(Long reservationId) {

        QSchedule qSchedule = QSchedule.schedule;
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        Banker banker = bankerRepository.findById(reservation.getBanker().getBankerId()).orElse(null);

        //예약 시간번호 앞에 "time"을 붙임
        String time = "time" + reservation.getReservationTime();

        //예약시간번호를 스케줄테이블의 칼럼이름과 매핑
        Map<String, Path<?>> columnMap = new HashMap<>();
        columnMap.put("time1", qSchedule.time1);
        columnMap.put("time2", qSchedule.time2);
        columnMap.put("time3", qSchedule.time3);
        columnMap.put("time4", qSchedule.time4);
        columnMap.put("time5", qSchedule.time5);
        columnMap.put("time6", qSchedule.time6);
        columnMap.put("time7", qSchedule.time7);

        Path<?> columnPath = columnMap.get(time);

        long update = jpaQueryFactory
                .update(qSchedule)
                .set((Path<Long>)columnPath, 1L)
                .where(qSchedule.banker.eq(banker), qSchedule.scheduleDate.eq(reservation.getReservationDate()))
                .execute();

        //업데이트가 성공이면 예약을 삭제하고 "success"리턴
        if(update == 1) {
            reservationRepository.deleteById(reservationId);
            return "success";
        }

        return "fail";
    }

    @Override
    @Transactional
    public String changeReservation(ReservationDTO reservationDTO, Long reservationId) {

          QSchedule qSchedule = QSchedule.schedule;
          Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
          Task task = taskRepository.findById(reservationDTO.getTaskId()).orElse(null);
          Banker banker = bankerRepository.findById(reservationDTO.getBankerId()).orElse(null);

          //기존예약 시간번호 앞에 "time"을 붙임
          String openTime = "time" + reservation.getReservationTime();

          reservation.setReservationDate(reservationDTO.getReservationDate());
          reservation.setReservationFinishFlag(reservationDTO.getReservationFinishFlag());
          reservation.setReservationTime(reservationDTO.getReservationTime());
          reservation.setTask(task);
          reservation.setBanker(banker);


        //신규예약 시간번호 앞에 "time"을 붙임
        String closeTime = "time" + reservationDTO.getReservationTime();

        //예약시간번호를 스케줄테이블의 칼럼이름과 매핑
        Map<String, Path<?>> columnMap = new HashMap<>();
        columnMap.put("time1", qSchedule.time1);
        columnMap.put("time2", qSchedule.time2);
        columnMap.put("time3", qSchedule.time3);
        columnMap.put("time4", qSchedule.time4);
        columnMap.put("time5", qSchedule.time5);
        columnMap.put("time6", qSchedule.time6);
        columnMap.put("time7", qSchedule.time7);

        Path<?> openColumnPath = columnMap.get(openTime);
        Path<?> closeColumnPath = columnMap.get(closeTime);

        Long updateOpen = jpaQueryFactory
                .update(qSchedule)
                .set((Path<Long>)openColumnPath, 1L)
                .where(qSchedule.banker.eq(banker), qSchedule.scheduleDate.eq(reservation.getReservationDate()))
                .execute();

        Long updateClose = jpaQueryFactory
                .update(qSchedule)
                .set((Path<Long>)closeColumnPath, 0L)
                .where(qSchedule.banker.eq(banker), qSchedule.scheduleDate.eq(reservationDTO.getReservationDate()))
                .execute();

        if(updateOpen == 1 && updateClose == 1){
            return "success";
        }

        return "fail";

    }
}
