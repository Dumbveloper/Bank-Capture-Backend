package web.mvc.service.reservation;

import web.mvc.dto.reservation.BankerAllResponseDTO;
import web.mvc.dto.reservation.BankerInfoResponseDTO;
import web.mvc.dto.reservation.ReservationDTO;

import java.util.List;

public interface ReservationService {

    /**
     * /reservation/bankerAll
     * 예약하기 - 예약가능한 행원 조회
     * Banker , Schedule , Task , 계산뷰
     *
     * @param bankId
     * @return List<BankerAllResponseDTO>
     */
    public List<BankerAllResponseDTO> findBankerAll(String bankId);

    /**
     * /reservation/bankerinfo/
     * 예약하기 - 행원선택(행원정보)
     * Certification , BankerReview
     *
     * @param bankerId
     * @return BankerInfoResponseDTO
     */
    public BankerInfoResponseDTO findBankerInfo(String bankerId);


    /**
     * /reservation/done
     * 예약하기
     * Reservation , Schedule
     *
     * @param reservationDTO
     * @return String 성공여부
     */
    public String doReservation(ReservationDTO reservationDTO, Long reservationId);

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
