package web.mvc.controller.reservation;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.mvc.dto.reservation.BankDTO;
import web.mvc.dto.reservation.BankerAllResponseDTO;
import web.mvc.dto.reservation.BankerInfoResponseDTO;
import web.mvc.dto.reservation.ReservationDTO;
import web.mvc.service.reservation.ReservationService;

import java.util.List;
import java.util.Map;

@Api(tags = "예약관리 API")
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @ApiOperation(value = "지점 전체보기", notes = "전체 지점의 상세정보를 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 500, message = "에러")
    })
    @PostMapping("/")
    public List<BankDTO> findBankAll(){
        return reservationService.findBankAll();
    }

    @ApiOperation(value = "예약가능한 행원보기", notes = "해당 지점내 선택한 업무를 수행하는 모든 행원의 정보를 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 500, message = "에러")
    })
    @PostMapping("/bankerAll")
    public List<BankerAllResponseDTO> findBankerAll(Long bankId, Long taskId){
        return reservationService.findBankerAll(bankId, taskId);
    }

    @ApiOperation(value = "선택한 행원 정보조회", notes = "선택한 행원의 상세정보를 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 500, message = "에러")
    })
    @PostMapping("/bankerInfo")
    public BankerInfoResponseDTO findBankerInfo(Long bankerId){
        return reservationService.findBankerInfo(bankerId);
    }

    @ApiOperation(value = "예약", notes = "예약을 등록하거나 예약을 변경한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "예약 성공"),
            @ApiResponse(code = 500, message = "에러")
    })
    @ApiParam(value = "예약 정보 객체", required = true)
    @PostMapping("/book")
    public String reservation(@RequestBody ReservationDTO reservationDTO){

        Long reservationId = reservationDTO.getReservationId();

        if(reservationId == null){
            return reservationService.doReservation(reservationDTO, reservationId);
        }

        return reservationService.changeReservation(reservationDTO, reservationId);
    }

    @ApiOperation(value = "예약 취소", notes = "예약을 취소한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "예약취소 성공"),
            @ApiResponse(code = 500, message = "에러")
    })
    @ApiParam(value = "취소할 예약 아이디", required = true)
    @PostMapping("/cancle")
    public String cancelReservation(@RequestBody Map<String, Long> data){
        Long reservationId = data.get("reservationId");
        return reservationService.cancelReservation(reservationId);
    }

}
