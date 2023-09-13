package web.mvc.controller.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.mvc.dto.reservation.ReservationDTO;
import web.mvc.service.reservation.ReservationService;

import java.util.Map;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/")
    public String reservation(@RequestBody ReservationDTO reservationDTO){

        Long reservationId = reservationDTO.getReservationId();

        if(reservationId == null){
            return reservationService.doReservation(reservationDTO, reservationId);
        }

        return reservationService.changeReservation(reservationDTO, reservationId);
    }

    @PostMapping("/cancle")
    public String cancelReservation(@RequestBody Map<String, Long> data){
        Long reservationId = data.get("reservationId");
        return reservationService.cancelReservation(reservationId);
    }

}
