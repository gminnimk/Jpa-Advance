package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/*
✅ Food 클래스는 DB의 food 테이블과 매핑되는 JPA 엔티티

    ➡️ 각각의 인스턴스는 DB에서 하나의 음식 레코드를 나타냄

    ➡️ 해당 클래스는 음식에 관한 정보를 담고 있고, DB와의 매핑을 통해 데이터를 영구적으로 저장하고 관리가 가능
 */

@Entity // JPA 엔티티 클래스임을 나타내는 어노테이션
@Getter
@Setter
@Table(name = "food") // DB에서 매핑할 테이블의 이름을 지정
public class Food {
    @Id //음식 객체의 고유 식별자로, 자동으로 생성되며 기본 키 역할을 함

    // @GeneratedValue(strategy = GenerationType.IDENTITY): 데이터베이스에서 자동으로 생성되는 기본 키 값을 사용함을 지정하는 전략
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 음식의 고유 식별자

    private String name;  // 음식의 이름
    private double price;  // 음식의 가격

    // JPA에서 사용되는 관계 매핑 어노테이션, 일대다 관계를 정의.
    // 일대다 관계에서는 외래 키(Foreign Key)가 자식 엔티티에 존재
    // 양방향 관계에서는 반대 방향 엔티티에 ManyToOne 애노테이션을 사용하여 매핑을 추가
    // 한 엔티티가 다른 엔티티와 일대다 관계를 가질 때 사용, 예를 들어, 하나의 Post 엔티티가 여러 개의 Comment 엔티티를 가지는 경우가 이에 해당
    @OneToMany
    @JoinColumn(name = "user_id") // user 필드와 매핑될 외래 키 칼럼을 지정
//    private User user;  // 해당 음식을 주문한 사용자와의 관계를 나타내는 필드
    private List<User> userList = new ArrayList<>();
}