package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/*
✅ DB 테이블 orders 와 매핑되는 엔티티, 이 클래스는 주문 정보를 저장, 음식과 사용자와의 관계를 정의, 주문이 생성된 날짜와 시간을 자동으로 기록

    ➡️ 주문 정보를 관리하는 Entity, 특정 음식과 사용자와의 관계를 정의하고, 주문이 생성된 날짜와 시간을 자동으로 기록.

    📢 중간 테이블 역할 :

            - Order 엔티티가 User 와 Food 사이의 중간 테이블 역할을 함.

            - 각 Order는 하나의 User와 하나의 Food를 참조하여, User와 Food 사이의 다대다 관계를 일대다, 다대일 관계로 분해.

            - 이 방식으로, 'Order' 엔티티는 'User'와 'Food' 사이의 다대다 관계를 효과적으로 관리, 추가적인 관계 데이터를 저장.
 */


@Entity  // 이 클래스가 JPA 엔티티임을 나타냄
@Getter
@Setter
@Table(name = "orders")  // 이 엔티티가 매핑될 데이터베이스 테이블의 이름을 "orders"로 지정
@EntityListeners(AuditingEntityListener.class)  // 엔티티의 생명주기 이벤트를 리스닝하여 자동으로 생성일을 기록
public class Order {

    @Id  // 이 필드가 엔티티의 기본 키임을 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 기본 키 값을 자동으로 생성하는 전략을 지정
    private Long id;  // 주문의 고유 ID

    @ManyToOne  // 다대일 관계를 정의, 여러 주문이 하나의 음식을 가리킬 수 있음
    @JoinColumn(name = "food_id")  // 외래 키 열의 이름을 "food_id"로 지정
    private Food food;  // 주문과 관련된 음식 엔티티

    @ManyToOne  // 다대일 관계를 정의, 여러 주문이 하나의 사용자를 가리킬 수 있음
    @JoinColumn(name = "user_id")  // 외래 키 열의 이름을 "user_id"로 지정
    private User user;  // 주문과 관련된 사용자 엔티티

    // 중간 테이블 Order 주문일 추가
    @CreatedDate  // 엔티티가 생성될 때 자동으로 날짜와 시간이 기록되도록
    @Temporal(TemporalType.TIMESTAMP)  // 날짜와 시간을 TIMESTAMP 형식으로 지정
    private LocalDateTime orderDate;  // 주문이 생성된 날짜와 시간
}