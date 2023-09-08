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
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_seq")
    @SequenceGenerator(name = "bank_seq", allocationSize = 1, sequenceName = "bank_seq")
    private Long bankId;  //지점 ID
    private String name;  //행원 이름
    private String email;  // 행원 이메일
    private String password;  // 행원 비밀번호
    private String task;  // 행원 주업무
    private String imgPath;  //행원 사진
    private String info;  //행원 한줄 소개
    private String reviewFlag; //행원 리뷰 공개 여부 플래그


}
