package fudan.pm.fudanCompass.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long commenterId;

    @Column(columnDefinition = "text")
    private String content;



}
