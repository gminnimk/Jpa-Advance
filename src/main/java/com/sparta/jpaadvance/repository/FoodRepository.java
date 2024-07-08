package com.sparta.jpaadvance.repository;

import com.sparta.jpaadvance.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 ✅ JpaRepository를 활용하여 Food 엔티티와 관련된 데이터베이스 접근을 담당하는 인터페이스

    ➡️ FoodRepository 인터페이스는 Spring Data JPA의 JpaRepository 인터페이스를 확장하여 구현

    ➡️ Food 엔티티와 관련된 DB CRUD 작업을 수행하는 메서드를 제공
 */


// 제네릭 타입 설명 :
// Food: JpaRepository가 다루는 엔티티 클래스 타입, 여기서는 Food 엔티티를 다루므로 Food 클래스를 지정
// Long: 엔티티의 기본 키 타입, Food 엔티티의 기본 키 타입이 Long이므로 Long을 지정

// 주요 메서드 설명 :
// JpaRepository 인터페이스는 기본적으로 데이터베이스 작업을 위한 여러 메서드를 제공. 예를 들어, save, findById, findAll, delete 등
// FoodRepository 인터페이스를 사용하면 이러한 메서드를 직접 구현하지 않고도 데이터베이스의 food 테이블과 매핑된 Food 엔티티를 쉽게 다룰 수 있음

public interface FoodRepository extends JpaRepository<Food, Long> {
}
