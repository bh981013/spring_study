package hello.hellospring.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
public class Member {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Id
    private String name;

    @Column
    private String password;

    @Column
    private char enable = '1';

    @Column
    private String role = "USER";
}
