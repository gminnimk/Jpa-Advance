package com.sparta.jpaadvance.relation;

import com.sparta.jpaadvance.entity.Food;
import com.sparta.jpaadvance.entity.User;
import com.sparta.jpaadvance.repository.FoodRepository;
import com.sparta.jpaadvance.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

/*
 ✅ JPA에서 1:1 단방향 관계를 테스트하기 위한 클래스

    ➡️ Spring Boot의 @SrpingBootTest 어노테이션을 통해 스프링 컨텍스트를 로드하여 테스트 수행

    ➡️ 해당 테스트 클래스를 통해 JPA에서 1:1 단방향 관계를 어떻게 설정하고 사용하는지를 실제 예시를 통해 학습
 */



// @Transactional : 메서드나 클래스에 적용되어 해당 범위 내의 모든 데이터베이스 작업을 트랜잭션 내에서 실행되도록 함
// 📢 트랜잭션이란 DB에서 일련의 연산들을 하나의 논리적인 작업 단위로 묶어 관리하는 것.
// @Transactional이 적용된 메서드가 실행되면 트랜잭션이 시작되고, 메서드가 종료될 때 트랜잭션이 커밋, 만약 예외가 발생하면 트랜잭션이 롤백
// 클래스 레벨에 적용하면 해당 클래스의 모든 메서드에 트랜잭션이 적용

// DB 상태를 변경하거나 데이터의 무결성을 보장해야 하는 상황에서 주로 사용.

// @Transactional 어노테이션이 선언되어 있어, 각각의 테스트 메서드는 트랜잭션 내에서 실행
@Transactional
@SpringBootTest // 해당 어노테이션은 스프링 부트 기반의 통합 테스트를 수행할 때 사용
public class OneToOneTest {

    // @Autowired : 스프링의 의존성 주입을 자동으로 처리, 스프링 컨텍스트에 의해 관리되는 빈을 자동으로 주입할 수 있도록 함
    @Autowired
    FoodRepository foodRepository;

    @Autowired
    UserRepository userRepository;


     // 1:1 단방향 관계를 테스트
     // @Test : JUnit 프레임워크에서 제공하는 어노테이션, 테스트 메서드를 정의할 때 사용,
     // @Test가 붙은 메서드는 테스트 실행 시 JUnit에 의해 실행
    @Test
    @Rollback(value = false) // 테스트에서는 @Transactional에 의해 자동 rollback 되므로 false 설정

    // @DisplayName : JUnit 5에서 제공하는 어노테이션으로, 테스트 클래스나 메서드에 대해 읽기 쉽고 표현력 있는 이름을 지정할 수 있음
    // 테스트 결과 보고서에서 지정된 이름이 표시됨
    @DisplayName("1대1 단방향 테스트")
    void test1() {
        // 사용자(User) 엔티티 생성
        User user = new User();
        user.setName("Robbie");

        // 음식(Food) 엔티티 생성
        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        // 음식 엔티티에 사용자 엔티티 설정 (단방향 관계 설정)
        food.setUser(user); // 외래 키(연관 관계) 설정

        // 사용자 엔티티 저장
        userRepository.save(user);

        // 음식 엔티티 저장
        foodRepository.save(food);
    }
}
