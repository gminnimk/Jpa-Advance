package com.sparta.jpaadvance.repository;

import com.sparta.jpaadvance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
✅ JpaRepository를 활용하여 User 엔티티와 관련된 DB 접근을 담당하는 인터페이스

    ➡️ 해당 인터페이스는 User 엔티티와 관련된 DB CRUD 작업을 수행하는 메서드를 제공
 */


// 제네릭 타입 설명:
// User: JpaRepository가 다루는 엔티티 클래스 타입. 여기서는 User 엔티티를 다루므로 User 클래스를 지정
// Long: 엔티티의 기본 키 타입. User 엔티티의 기본 키 타입이 Long이므로 Long을 지정
public interface UserRepository extends JpaRepository<User, Long> {
}
