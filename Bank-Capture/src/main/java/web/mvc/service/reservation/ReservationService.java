package web.mvc.service.reservation;

import web.mvc.dto.reservation.BankDTO;
import web.mvc.dto.reservation.BankerAllResponseDTO;
import web.mvc.dto.reservation.BankerInfoResponseDTO;
import web.mvc.dto.reservation.ReservationDTO;

import java.util.List;

public interface ReservationService {

    /**
     * /reservation/marker
     * 지점 마커 표시
     * @return List<BankDTO>
     */
    public List<BankDTO> findBankAll();


    /**
     * /reservation/search
     * 지점 검색시 검색명 포함하는 지점 조회
     * @param name
     * @return
     */
    public List<BankDTO> findBankSearchByName(String name);


    /**
     * /reservation/bankerAll
     * 예약하기 - 예약가능한 행원 조회
     * Banker , Schedule , Task , 계산뷰
     *
     * @param bankId
     * @param taskId
     * @return List<BankerAllResponseDTO>
     */
//    public List<BankerAllResponseDTO> findBankerAll(Long bankId, Long taskId);

    List<BankerAllResponseDTO> findBankerAll(Long bankId, Long taskId);

    /**
     * /reservation/bankerinfo/
     * 예약하기 - 행원선택(행원정보)
     * Certification , BankerReview
     *
     * @param bankerId
     * @return BankerInfoResponseDTO
     */
    public BankerInfoResponseDTO findBankerInfo(Long bankerId);


    /**
     * /reservation/done
     * 예약하기
     * Reservation , Schedule
     *
     * @param reservationDTO
     * @return String 성공여부
     */
    public String doReservation(ReservationDTO reservationDTO);

    /**
     * 예약취소
     * Reservation , Schedule
     *
     * @param reservationId
     * @return String 성공여부
     */
    public String cancelReservation(Long reservationId);

    /**
     * 예약변경
     * Reservation , Schedule
     *
     * @param reservationDTO
     * @param reservationId
     * @return String 성공여부
     */
    public String changeReservation(ReservationDTO reservationDTO, Long reservationId);

}
