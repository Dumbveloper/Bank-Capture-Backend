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
//test
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankId;  //지점 ID
    private String bankName; // 지점이름
    private double locationX; // 지점X좌표
    private double locationY; // 지점Y좌표
    private String bankPhone; // 지점전화번호
    private String bankAddr; // 지점주소
}

