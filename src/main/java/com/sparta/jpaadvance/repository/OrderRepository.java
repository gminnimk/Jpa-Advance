package com.sparta.jpaadvance.repository;

import com.sparta.jpaadvance.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/*
✅ JpaRepository를 활용하여 Order 엔티티와 관련된 DB 접근을 담당하는 인터페이스

    ➡️ 해당 인터페이스는 Order 엔티티와 관련된 DB CRUD 작업을 수행하는 메서드를 제공

    ➡️ JpaRepository는 기본적인 CRUD 작업을 위한 메서드를 제공
 */


// 제네릭 타입 설명:
// Order: JpaRepository가 다루는 엔티티 클래스 타입. 여기서는 Order 엔티티를 다루므로 Order 클래스를 지정
// Long: 엔티티의 기본 키 타입. Order 엔티티의 기본 키 타입이 Long이므로 Long을 지정
public interface OrderRepository extends JpaRepository<Order, Long> {
}
