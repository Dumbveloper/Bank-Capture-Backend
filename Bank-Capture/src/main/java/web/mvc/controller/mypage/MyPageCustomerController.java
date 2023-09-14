package web.mvc.controller.mypage;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.mypage.CustomerReviewRequestDTO;
import web.mvc.dto.mypage.CustomerScheduleResponseDTO;
import web.mvc.service.mypage.MyPageCustomerService;

import java.util.List;
import java.util.Map;

@Api(tags = "고객 마이페이지 API")
@RestController
@RequestMapping("/myPage/customer")
public class MyPageCustomerController {

    @Autowired
    private MyPageCustomerService myPageCustomerService;

    @ApiOperation(value = "고객 스케줄 조회", notes = "해당 고객의 모든 예약을 가져오고 Front에서 finishFlag = F일 경우 왼쪽, T일 경우 오른쪽에 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "고객 스케줄 조회 성공"),
            @ApiResponse(code = 500, message = "에러")
    })
    @ApiParam(value = "고객의 아이디", required = true)
    @GetMapping("/schedule")
    public List<CustomerScheduleResponseDTO> customerSchedule(@RequestParam(name = "customerId") Long customerId){
        return myPageCustomerService.customerSchedule(customerId);
    }

    @ApiOperation(value = "리뷰 등록", notes = "해당 예약의 별점과 코멘트를 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "고객 리뷰 등록 완료"),
            @ApiResponse(code = 500, message = "에러")
    })
    @ApiParam(value = "리뷰를 등록할 예약의 아이디", required = true)
    @PostMapping("/review")
    public String insertReview(@RequestBody CustomerReviewRequestDTO customerReviewRequestDTO){
        return myPageCustomerService.insertReview(customerReviewRequestDTO);
    }

    @ApiOperation(value = "리뷰 삭제", notes = "해당 예약의 별점과 코멘트를 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "고객 리뷰 삭제 완료"),
            @ApiResponse(code = 500, message = "에러")
    })
    @ApiParam(value = "리뷰를 삭제할 예약의 아이디", required = true)
    @PostMapping("/reviewdelete")
    public String deleteReview(@RequestBody Map<String, Long> data){
        Long reservationId = data.get("reservationId");
        return myPageCustomerService.deleteReview(reservationId);
    }


}
