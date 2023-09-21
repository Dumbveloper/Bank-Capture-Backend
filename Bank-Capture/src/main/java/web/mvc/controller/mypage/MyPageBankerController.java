package web.mvc.controller.mypage;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import web.mvc.domain.Schedule;
import web.mvc.dto.mypage.BankerRankingResponseDTO;
import web.mvc.dto.mypage.BankerScheduleResponseDTO;
import web.mvc.dto.reservation.ScheduleDTO;
import web.mvc.service.mypage.MyPageBankerService;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@Api(tags = "행원 마이페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/myPage/banker")
public class MyPageBankerController {
    private final MyPageBankerService myPageBankerService;

    @ApiOperation(value = "행원 스케줄 조회", notes = "해당 행원의 모든 예약을 가져오고 Front에서 finishFlag = F일 경우 왼쪽, T일 경우 오른쪽에 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "행원 스케줄 조회 성공"),
            @ApiResponse(code = 500, message = "에러")
    })
    @ApiParam(value = "행원의 아이디", required = true)
    @GetMapping("/schedule")
    public List<BankerScheduleResponseDTO> bankerSchedule(@RequestParam(name = "bankerId") Long bankerId) {
        return myPageBankerService.bankerSchedule(bankerId);
    }

    @ApiOperation(value = "지점내 행원의 순위 TOP3", notes = "해당 은행내 평균별점이 높은 상위 3명을 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "행원 스케줄 조회 성공"),
            @ApiResponse(code = 500, message = "에러")
    })
    @ApiParam(value = "순위 조회할 은행 아이디", required = true)
    @GetMapping("/ranking")
    public Page<BankerRankingResponseDTO> bankerRanking(@RequestParam(name = "bankId") Long bankId) {
        return myPageBankerService.bankerRanking(bankId, 0, 3);
    }

    @ApiOperation(value = "행원 스케줄 설정", notes = "해당 행원이 예약받을 수 있는 시간을 설정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "행원 스케줄 설정 성공"),
            @ApiResponse(code = 500, message = "에러")
    })
    @ApiParam(value = "스케줄 설정할 행원의 아이디", required = true)
    @PostMapping("/checkTime")
    public String checkTime(@RequestBody ScheduleDTO scheduleDTO) {
        return myPageBankerService.checkTime(scheduleDTO);
    }

    @ApiOperation(value = "예약 방문완료 설정", notes = "해당 예약을 방문완료 설정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "예약 방문완료 설정 성공"),
            @ApiResponse(code = 500, message = "에러")
    })
    @ApiParam(value = "방문완료처리할 예약의 아이디", required = true)
    @PostMapping("/schedule/done")
    public String updateFlag(@RequestBody Map<String, Long> data) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        Long reservationId = data.get("reservationId");
        return myPageBankerService.updateFlag(reservationId);
    }
}
