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

    // 지연 로딩 테스트를 위한 예제로 다대일 양방향 관계로 전환
    @ManyToOne
    // @ManyToOne(fetch = FetchType.LAZY) // FetchType을 인위적으로 수정 하여 지연 로딩으로 설정,
    // 📢 오류 발생 원인은 지연 로딩이 된 엔티티의 정보 조화를 위해서는 반드시 영속성 컨텍스트가 존재해야 함
    // 영속성 컨텍스트가 존재해야 한다는 의미는 스프링 컨테이너 환경에서는 트랜잭션이 적용되어 있어야 한다는 의미와 동일
    @JoinColumn(name = "user_id")
    private User user;

}