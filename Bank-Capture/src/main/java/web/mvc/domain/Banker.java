package web.mvc.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Banker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankerId;  //행원 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;  //지점 ID
    private String bankerName;  //행원 이름
    private String email;  // 행원 이메일
    private String password;  // 행원 비밀번호
    private String career; // 행원 경력
    private String imgPath;  //행원 사진
    private String info;  //행원 한줄 소개
    private String reviewFlag; //행원 리뷰 공개 여부 플래그


}
