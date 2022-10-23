package com.api.parking.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name="member")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE member SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class MemberModel extends BaseDao implements Serializable {

    private static final long serialVersionUID = 5997353193872930935L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(name = "ktm", nullable = false)
    private String ktm;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "job", nullable = false)
    private String job;

    @Column(name = "access_code", nullable = false)
    private String accessCode;

}
