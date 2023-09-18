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
import java.util.Random;

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
        String [] info={"감사합니다. 최선을 다하겠습니다.","고객에게 언제나 만족스러운 서비스를 제공하겠습니다.","최고의 서비스로 보답하겠습니다.",
        "모두가 안심할 수 있는 서비스를 제공하겠습니다.","고객 우선 가치를 실천하겠습니다.","금융 전문가로서 최선을 다하겠습니다."};
        //경력 랜덤 생성
        Random random = new Random();
        // 랜덤한 숫자 생성 (예: 1부터 30까지의 범위에서)
        int minCareer = 1;
        int maxCareer = 30;
        int randomCareer = random.nextInt(maxCareer - minCareer + 1) + minCareer;
        for(int i=1;i<150;i++) {
            long bankerBankId = (i / 10L) + 1L;
            BankerDTO bankerDTO = BankerDTO.builder().
                    bankerBankId(bankerBankId).
                    bankerName("Banker"+i).
                    bankerEmail("Banker"+i+"@bankers.com").
                    bankerPassword("123"+i).
                    bankerCareer(String.valueOf(randomCareer)).
                    bankerImgPath("www.img.com").
                    bankerInfo(info[i%6]).
                    bankerReviewFlag("Y").
                    build();
            userService.registerBanker(bankerDTO);
        }

    }
}
