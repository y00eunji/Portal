package kr.ac.jejunu;


import lombok.*;
import lombok.Getter;

@Getter
@Setter
@Builder
//기본생성자만듦
@NoArgsConstructor
@AllArgsConstructor
//getter,setter,hashcode
//@Data

public class User {
    private Integer id;
    private String name;
    private String password;



}
