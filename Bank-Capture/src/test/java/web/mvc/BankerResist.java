package web.mvc;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.Bank;
import web.mvc.domain.Banker;
import web.mvc.dto.users.BankerDTO;
import web.mvc.repository.BankRepository;
import web.mvc.repository.BankerRepository;
import web.mvc.service.user.UserService;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BankerResist {

    @Autowired
    private UserService userService;
    @Autowired
    private BankRepository bankRepository;

    @Test
    @Rollback(value = false)
    public void 행원생성() throws Exception{

        BankerDTO bankerDTO = BankerDTO.builder().
                bankerBankId(1L).
                bankerName("송봉섭").
                bankerEmail("qhdtjq4799@naver.com").
                bankerPassword("1234").
                bankerCareer("22년02월22일").
                bankerImgPath("www.img.com").
                bankerInfo("카오짱~! 요로시꾸 오네가이시마스").
                bankerReviewFlag("Y").
                build();

        userService.registerBanker(bankerDTO);
    }
}
