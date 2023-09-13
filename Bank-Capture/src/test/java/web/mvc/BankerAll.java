package web.mvc;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import web.mvc.domain.*;
import web.mvc.dto.reservation.BankerAllResponseDTO;
import web.mvc.dto.reservation.ScheduleDTO;
import web.mvc.dto.reservation.TaskDTO;
import web.mvc.repository.review.BankerReviewRepository;

import java.util.List;

@SpringBootTest
class BankerAll {

    @Autowired
    private JPAQueryFactory queryFactory;
    @Autowired
    BankerReviewRepository bankerReviewRep;

    @Test
    public void 뱅커(){
        QBanker banker = QBanker.banker;
        QSchedule schedule = QSchedule.schedule;
        QMainTask mainTask = QMainTask.mainTask;
        QTask task = QTask.task;
        QBankerRating bankerRating = QBankerRating.bankerRating;

        List<BankerAllResponseDTO> bankerList = queryFactory.select(Projections.constructor
                        (BankerAllResponseDTO.class,banker.bankerId,banker.bankerName
                                ,banker.bankerCareer,banker.bankerImgPath,banker.bankerInfo
                                ,bankerRating.avgStar,bankerRating.cntComment,banker.bankerReviewFlag))
                .from(banker)
                .join(mainTask).on(banker.bankerId.eq(mainTask.banker.bankerId))
                .join(task).on(task.taskId.eq(mainTask.task.taskId))
                .leftJoin(bankerRating).on(bankerRating.bankerId.eq(banker.bankerId))
                .where(banker.bank.bankId.eq(1L).and(task.taskId.eq(2L)))
                .fetch();

        System.out.println("================================="+bankerList.size());

        System.out.println(bankerList.get(1));

        for(int i = 0; i<bankerList.size();i++){
            List<ScheduleDTO> schDto = queryFactory.select(Projections.constructor(
                            ScheduleDTO.class,schedule.banker.bankerId,schedule.bank.bankId,schedule.scheduleDate
                            ,schedule.time1,schedule.time2,schedule.time3,schedule.time4,schedule.time5,
                            schedule.time6,schedule.time7))
                    .from(schedule)
                    .where(schedule.banker.bankerId.eq(bankerList.get(i).getBankerId()))
                    .fetch();



            List<TaskDTO> taskdto = queryFactory.select(Projections.constructor(TaskDTO.class, task.taskName))
                    .from(task)
                    .join(mainTask).on(task.taskId.eq(mainTask.task.taskId))
                    .join(banker).on(banker.bankerId.eq(mainTask.banker.bankerId))
                    .where(banker.bankerId.eq(bankerList.get(i).getBankerId()))
                    .fetch();

            bankerList.get(i).setScheduleList(schDto);
            bankerList.get(i).setTaskList(taskdto);

            System.out.println("========================================="+schDto);
            System.out.println("========================================="+taskdto);
        }
        System.out.println("****************************"+bankerList);
    }





}
