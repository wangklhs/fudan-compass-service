package fudan.pm.fudanCompass.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String major;

    //标识符进行区分，天数之间为*，课程之间为#
    private String courseTable=" & & & & & & ";
    public User(String username, String password,String major) {
        this.username = username;
        this.password = password;
        this.major = major;
    }

    public User() {

    }

}
