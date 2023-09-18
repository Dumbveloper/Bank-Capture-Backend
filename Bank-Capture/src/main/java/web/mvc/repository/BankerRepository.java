package web.mvc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.mvc.domain.Bank;
import web.mvc.domain.Banker;
import web.mvc.domain.Customer;
import web.mvc.dto.mypage.BankerRankingResponseDTO;

import java.util.List;

public interface BankerRepository extends JpaRepository<Banker,Long> {


    /**
     * 행원 조회 - 로그인, 회원가입시 해당 이메일을 가진 행원이 있는지 조회
     * @param bankerEmail
     * @return Banker
     */
    Banker findByBankerEmail(String bankerEmail);

    /**
     * 행원 조회 - 로그인시 해당 이메일, 비밀번호를 가진 행원이 있는지 조회
     * @param bankerEmail
     * @param bankerPassword
     * @return Banker
     */
    Banker findByBankerEmailAndBankerPassword(String bankerEmail, String bankerPassword);

    /**
     * /reservation/bankerAll
     * 예약하기 - 예약가능한 행원 조회 / 해당 지점 내의 행원 전부 찾기
     *
     * @param bankId
     * @return List<Banker>
     */
    //List<Banker> findBanker(String bankId);


    /**
     * /myPage/banker/ranking
     * 마이페이지(행원) - 지점내 순위
     *
     * @param bankId
     * @return List <BankerRankingResponseDTO>
     */
    @Query("SELECT NEW web.mvc.dto.mypage.BankerRankingResponseDTO(b.bankerName, b.bankerImgPath) FROM BankerRating br JOIN br.banker b WHERE br.bankId = :bankId ORDER BY br.avgStar desc")
    Page<BankerRankingResponseDTO> findBankerRankingByBankId(@Param("bankId") Long bankId, Pageable pageable);


}