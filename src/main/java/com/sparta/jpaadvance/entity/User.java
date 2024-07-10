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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 사용자의 고유 식별자

    private String name;  // 사용자의 이름

    // 지연 로딩 테스트를 위한 예제로 다대일 양방향 관계로 전환
    /*
    @OneToMany(mappedBy = "user"): User 엔티티 클래스에서 Food 엔티티와의 양방향 일대다 관계를 정의
    mappedBy 속성은 Food 엔티티가 User 엔티티에서 어떤 필드에 의해 매핑되었는지를 지정
    이 경우 Food 엔티티의 user 필드가 User 엔티티와의 관계를 매핑
    */

    /*
    📢 'cascade' 속성:

            - 연관된 'Food' 엔티티에 적용할 영속성 전이 작업을 정의

            - CascadeType.PERSIST: User 엔티티가 저장될 때 (userRepository.save(user)),
                                   foodList에 추가된 새로운 Food 엔티티들도 자동으로 저장

            - CascadeType.REMOVE: User 엔티티가 삭제될 때 (userRepository.delete(user)),
                                   foodList에 있는 모든 연관된 Food 엔티티들도 함께 삭제
     */
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Food> foodList = new ArrayList<>();

    // Food 엔티티를 foodList에 추가하고, 해당 Food 엔티티의 user 필드에 자신을 설정하는 메서드
    public void addFoodList(Food food) {
        this.foodList.add(food);
        food.setUser(this); // 외래 키 설정
    }
}
