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
    // Food 엔티티와 Order 엔티티 사이의 일대다 관계를 설정
    // @OneToMany : 한 엔티티가 여러 개의 다른 엔티티와 일대다 관계를 가질 때 사용
    // 즉, 하나의 엔티티 인스턴스가 여러 개의 다른 엔티티 인스턴스를 가질 수 있음을 의미
    @OneToMany(mappedBy = "food")

    // orderList는 Food 엔티티가 Order 엔티티들과 관계를 맺기 위해 사용되는 컬렉션
    // 이 필드를 통해서 Food 엔티티는 여러 개의 Order 엔티티와 관계를 맺을 수 있음
    private List<Order> orderList = new ArrayList<>();
}