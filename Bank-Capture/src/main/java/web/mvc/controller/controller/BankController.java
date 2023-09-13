package web.mvc.controller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.mvc.dto.reservation.BankDTO;
import web.mvc.service.reservation.ReservationService;

import java.util.List;

@RestController
public class BankController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping("/reservation")
    public List<BankDTO> reservationMap(){
        return reservationService.findBankAll();
    }

}
