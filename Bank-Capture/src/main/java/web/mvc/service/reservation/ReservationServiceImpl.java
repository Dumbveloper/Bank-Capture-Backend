package web.mvc.service.reservation;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.mvc.domain.*;
import web.mvc.dto.reservation.*;
import web.mvc.repository.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private BankRepository bankRep;

    //@Override
    public List<BankDTO> findBankAll() {
        List<BankDTO> bankdtolist = new ArrayList<>();
        List<Map<String, Object>> bankmaplist = bankRep.findDistinctAvgStar();

        for (Map<String, Object> b : bankmaplist) {
            Long bankId = ((BigInteger) b.get("bank_id")).longValue();

            Double avgstar=((BigDecimal) b.get("avg_star")).doubleValue();
            String bankname = (String) b.get("bank_name");
            Double locationX = (Double) b.get("locationx");
            Double locationY = (Double) b.get("locationy");
            String bankphone = (String) b.get("bank_phone");
            String bankaddr = (String) b.get("bank_addr");



            if(avgstar!=null) {
                bankdtolist.add(new BankDTO(bankId, bankname, locationX, locationY, bankphone, bankaddr, avgstar));
            }else{
                bankdtolist.add(new BankDTO(bankId, bankname, locationX, locationY, bankphone, bankaddr));
            }

        }
        return bankdtolist;
    }

    @Override
    public List<BankerAllResponseDTO> findBankerAll(Long bankId, Long taskId) {
        QBanker banker = QBanker.banker;                        //banker Q클래스 생성
        QSchedule schedule = QSchedule.schedule;                //schedule Q클래스
        QMainTask mainTask = QMainTask.mainTask;                //mainTask Q클래스
        QTask task = QTask.task;                                //task Q클래스
        QBankerRating bankerRating = QBankerRating.bankerRating;    //뷰에 매핑 될 bankerRating Q클래스 생성

        /**
         * QueryDSL로 banker, mainTask, task, 별점/리뷰수 뷰 조인하고
         *  Projectrions 사용하여 BankerAllResponseDTO 생성자로 직접 받기
         */
        List<BankerAllResponseDTO> bankerList = jpaQueryFactory.select(Projections.constructor(
                        BankerAllResponseDTO.class,banker.bankerId,banker.bankerName
                        ,banker.bankerCareer,banker.bankerImgPath,banker.bankerInfo
                        ,bankerRating.avgStar,bankerRating.cntComment,banker.bankerReviewFlag))
                .from(banker)
                .join(mainTask).on(banker.bankerId.eq(mainTask.banker.bankerId))
                .join(task).on(task.taskId.eq(mainTask.task.taskId))
                .join(bankerRating).on(bankerRating.bankerId.eq(banker.bankerId))
                .where(banker.bank.bankId.eq(bankId).and(task.taskId.eq(taskId)))
                .fetch();

        /**
         * 행원별 스케줄리스트, 주업무 조회하여 BankerAllResponseDTO에 추가
         */
        for(int i = 0; i<bankerList.size();i++){
            /**
             * 스케줄리스트 조회
             * bankerList의 bankerid에 해당하는 스케줄리스트 조회 Query
             * */
            List<ScheduleDTO> schDto = jpaQueryFactory.select(Projections.constructor(
                            ScheduleDTO.class,schedule.banker.bankerId,schedule.bank.bankId,schedule.scheduleDate
                            ,schedule.time1,schedule.time2,schedule.time3,schedule.time4,schedule.time5,
                            schedule.time6,schedule.time7))
                    .from(schedule)
                    .where(schedule.banker.bankerId.eq(bankerList.get(i).getBankerId()))
                    .fetch();

            /**
             * 주업무 조회
             * bankerList의 bankerid에 해당하는 주업무 조회 Query
             * banker,mainTask,task 조인
             */
            List<TaskDTO> taskdto = jpaQueryFactory.select(Projections.constructor(TaskDTO.class, task.taskName))
                    .from(task)
                    .join(mainTask).on(task.taskId.eq(mainTask.task.taskId))
                    .join(banker).on(banker.bankerId.eq(mainTask.banker.bankerId))
                    .where(banker.bankerId.eq(bankerList.get(i).getBankerId()))
                    .fetch();

            //스케줄 리스트를 BankerAllResponseDTO에 삽입
            bankerList.get(i).setScheduleList(schDto);
            //주업무 리스트를 BankerAllResponseDTO에 삽입
            bankerList.get(i).setTaskList(taskdto);
        }
        return bankerList;
    }
    @Override
    public BankerInfoResponseDTO findBankerInfo(Long bankerId) {
        QBanker banker = QBanker.banker;
        QCertification certification = QCertification.certification;
        QBankerCertification bankerCertification = QBankerCertification.bankerCertification;
        QReservation reservation = QReservation.reservation;

        /**
         * Parameter bankerId가 가진 자격증 리스트 조회
         * banker, banker_certification, certification 조인
         */
        List<Certification> result = jpaQueryFactory.select(certification)
                .from(banker)
                .join(bankerCertification).on(banker.bankerId.eq(bankerCertification.banker.bankerId))
                .join(certification).on(bankerCertification.certification.certificationId.eq(certification.certificationId))
                .where(banker.bankerId.eq(bankerId))
                .fetch();

        /**
         * 조회한 자격증 리스트 domain -> dto로 생성
         */
        List<CertificationDTO> certificationDTOS = result.stream().map(
                certification1 -> {
                    return new CertificationDTO(certification1.getCertificationId(),certification1.getCertificationName());
                }
        ).collect(Collectors.toList());

        /**
         * bankerId에 해당하는 리뷰 리스트 조회
         */

        List<BankerReviewDTO> reDto = jpaQueryFactory.select(Projections.constructor(
                        BankerReviewDTO.class,reservation.comment))
                .from(reservation)
                .where(reservation.banker.bankerId.eq(bankerId))
                .fetch();


        //자격증리스트, 리뷰리스트 BankerInfoResponseDTO로 생성
        BankerInfoResponseDTO bankerInfo = new BankerInfoResponseDTO(certificationDTOS,reDto);

        return bankerInfo;
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
