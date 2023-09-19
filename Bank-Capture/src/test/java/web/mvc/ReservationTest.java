package web.mvc;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.mvc.domain.*;
import web.mvc.dto.reservation.BankerAllResponseDTO;
import web.mvc.dto.reservation.BankerInfoResponseDTO;
import web.mvc.dto.reservation.ReservationDTO;
import web.mvc.repository.*;
import web.mvc.service.reservation.ReservationService;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReservationTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankerRepository bankerRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BankRepository bankRepository;




    /**
     * ReservationDTO 를  Reservation Entity로 변환하는 함수
     * @return Reservation
     */
    private Reservation dtoToEntity(ReservationDTO reservationDTO) {

        Customer customer = customerRepository.findById(reservationDTO.getCustomerId()).orElse(null);
        Banker banker = bankerRepository.findById(reservationDTO.getBankerId()).orElse(null);
        Task task = taskRepository.findById(reservationDTO.getTaskId()).orElse(null);
        Bank bank = bankRepository.findById(reservationDTO.getBankId()).orElse(null);

        Reservation reservation = Reservation.builder().
                customer(customer).
                banker(banker).
                task(task).
                bank(bank).
                reservationDate(reservationDTO.getReservationDate()).
                reservationTime(reservationDTO.getReservationTime()).
                reservationFinishFlag(reservationDTO.getReservationFinishFlag()).
                build();
        return reservation;
    }



    @Test
    public void 예약하기() throws Exception{
        //given
        ReservationDTO reservationDTO =
                new ReservationDTO(1L, 1L, 1L, 1L,"20230928", "7", "F");
        //when
        String result = reservationService.doReservation(reservationDTO);
        //then
        assertEquals("success",result); // 예약하기
    }
    @Test
    public void 예약변경() throws Exception{ // reservationId 필요
        //given
        ReservationDTO reservationDTO =
                new ReservationDTO(1L, 1L, 1L, 1L,"20230928", "7", "F");
        Reservation reservation = dtoToEntity(reservationDTO); // dto 를 entity로 변환
        Long reservationId = reservationRepository.save(reservation).getReservationId(); // 예약하면서 예약완료된 예약의 reservationId
        //when
        // 1번 행원 -> 2번 행원
        ReservationDTO changeReservationDTO =
                new ReservationDTO(1L, 2L, 1L, 1L,"20230928", "7", "F");
        String result = reservationService.changeReservation(reservationDTO, reservationId);
        //then
        assertEquals("success",result); // 예약하기
    }

    @Test
    public void 예약취소() throws Exception{
        //given
        ReservationDTO reservationDTO =
                new ReservationDTO(1L, 1L, 1L, 1L,"20230928", "7", "F");
        Reservation reservation = dtoToEntity(reservationDTO); // dto 를 entity로 변환
        Long reservationId = reservationRepository.save(reservation).getReservationId(); // 예약하면서 예약완료된 예약의 reservationId
        //when
        reservationService.cancelReservation(reservationId);
    }

    @Test
    public void 해당지점_해당업무_은행원찾기() throws Exception{
        List<BankerAllResponseDTO> bankerAll = reservationService.findBankerAll(1L, 1L);

        //ToString 붙여서 Test
        for (BankerAllResponseDTO dto : bankerAll) {
            System.out.println(dto);
        }
    }
    @Test
    public void 행원정보_검색() throws Exception{
        //given
        BankerInfoResponseDTO bankerInfoResponseDTO = reservationService.findBankerInfo(1L);
        //when
        System.out.println(bankerInfoResponseDTO);
        //then
    }
}
