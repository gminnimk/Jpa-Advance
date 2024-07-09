package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/*
✅ User 클래스는 DB의 users 테이블과 매핑되는 JPA 엔터티

    ➡️ 각각의 인스턴스는 DB에서 하나의 사용자 레코드를 나타냄

    ➡️ 사용자에 대한 정보를 담고 있으며, DB와의 매핑을 통해 사용자 데이터를 영구적으로 저장 & 관리 가능
*/

@Entity // JPA 엔티티 클래스임을 나타내는 어노테이션
@Getter
@Setter
@Table(name = "users") // DB에서 매핑할 테이블의 이름을 지정
public class User {
    @Id // 사용자 객체의 고유 식별자로, 자동으로 생성되며 기본 키 역할을 함
    // DB에서 자동으로 생성되는 기본 키 값을 사용함을 지정하는 전략
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 사용자의 고유 식별자

    private String name;  // 사용자의 이름

    /*
    @ManyToMany(mappedBy = "userList"): Food 엔티티 클래스에서 userList 필드를 사용하여 매핑된 양방향 다대다 관계를 정의
    mappedBy 속성은 User 엔티티가 Food 엔티티에서 어떤 필드에 의해 매핑되었는지를 지정
    이 경우 userList 필드가 Food 엔티티에서 User 엔티티와의 관계를 매핑
     */
    @ManyToMany(mappedBy = "userList")

    // private List<Food> foodList = new ArrayList<>();: User 엔티티가 가지는 Food 엔티티의 컬렉션입니다.
    // 이 필드를 통해 한 User 엔티티가 여러 Food 엔티티와 관계를 맺을 수 있음
    private List<Food> foodList = new ArrayList<>();

    // addFoodList() 메서드: 이 메서드는 User 엔티티에 속한 foodList에 새로운 Food 객체를 추가하고,
    // 이 Food 객체의 userList에 자신(User 엔티티)을 추가하여 양방향 관계를 설정
    public void addFoodList(Food food) {
        // this.foodList.add(food);: 현재 User 엔티티의 foodList에 파라미터로 전달된 Food 객체를 추가
        this.foodList.add(food); // 자신의 foodList에 해당 음식을 추가
        // food.getUserList().add(this);: 전달된 Food 객체의 getUserList() 메서드를 통해 Food 엔티티의 userList에 현재 User 엔티티를 추가
        // 이렇게 함으로써 양방향 매핑이 완성
        food.getUserList().add(this); // 해당 음식의 userList에 자신을 추가(양방향 관계 설정), 외래 키(연관 관계) 설정
    }
}