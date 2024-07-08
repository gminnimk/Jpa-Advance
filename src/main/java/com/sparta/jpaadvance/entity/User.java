package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
}