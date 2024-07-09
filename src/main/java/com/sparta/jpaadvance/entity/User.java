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

    // @OneToMany : 한 User가 여러 Food 객체와 관계를 맺을 수 있음을 나타냄
    // mappedBy = user 는 Food 엔티티에서 user 필드가 이 관계의 주인임을 의미.
    @OneToMany(mappedBy = "user")

    // foodList는 User가 가지고 있는 여러 Food 객체를 저장하는 리스트.
    // new ArrayList<>()로 초기화하여 User 객체가 생성될 때 foodList가 빈 리스트로 초기화되도록 함.
    private List<Food> foodList = new ArrayList<>();

    public void addFoodList(Food food) {
        this.foodList.add(food);
        food.setUser(this); // 외래 키(연관 관계) 설정
    }


    // @OneToOne을 명시함으로써 User와 Food 간의 1대1 양방향 관계 설정

    // 📢 mappedBy : JPA에서 양방향 관계를 설정할 때 사용, 외래키의 주인을 설정하기 위한 옵션, 설정하지 않으면 외래키의 주인이 누군지 모르기 때문에 설정하는것이 좋음.
    // 그때 옵션은 외래키 주인 즉, 상대 엔티티의 필드 명(@JoinColumn으로 외래 키를 가지는 필드의 명으로 설정)
    // 주의할 점은 절대 내 클래스 이름이라고 오해하면 안 되고 외래 키의 주인 즉 상대 엔티티의 조인컬럼으로 설정되고 있는 필드 명이다 라고 기억!

    // @OneToOne(mappedBy = "user")는 Food 엔티티의 user 필드가 이 관계의 주인임을 지정,
    // 따라서 User 엔티티는 외래 키 컬럼을 가지지 않고, Food 엔티티가 user_id 외래 키 컬럼을 가지게 됨.
    // mappedBy 를 사용하지 않는 쪽이 관계의 주인이 되고 외래 키 컬럼을 직접 관리, mappedBy를 사용한 쪽은 관계를 읽기 전용으로 가짐.

    // ➡️ 이를 통해서 JPA는 어떤 엔티티가 외래 키를 관리하는지, 즉 DB에서 실제로 외래 키 컬럼을 가지고 있는지 알 수 있음.
    // 관계의 주인은 외래 키를 직접 관리하고, mappedBy로 지정된 필드는 관계를 읽기 전용으로 참조.


//    @OneToOne(mappedBy = "user") // Food 엔티티의 user 필드에 의해 매핑됨
//
//    private Food food;
//
//    // Food와의 양방햔 관계를 설정하는 메서드
//    public void addFood(Food food) {
//        this.food = food; // User 객체에 Food 객체를 설정
//        food.setUser(this); // Food 객체에 User 객체를 설정하여 양방향 관계를 완성
//    }
}