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
 ✅ JPA에서 1:1 단방향, 양방향 관계를 테스트하기 위한 클래스

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
    FoodRepository foodRepository; // Food 엔티티에 대한 데이터베이스 작업을 처리하는 레포지토리

    @Autowired
    UserRepository userRepository; // User 엔티티에 대한 데이터베이스 작업을 처리하는 레포지토리


    // 📢📢📢 왜래 키 주인만이 왜리 키를 컨트롤 할 수 있다. 📢📢📢
    // 그것을 증명하기 위한 코드





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
//        food.setUser(user); // 외래 키(연관 관계) 설정

        // 사용자 엔티티 저장
        userRepository.save(user);

        // 음식 엔티티 저장
        foodRepository.save(food);
    }


    // test2 ~ test4 : 양방향 테스트


    @Test
    @Rollback(value = false) // 테스트가 끝난 후에도 데이터베이스 변경 사항을 롤백하지 않음
    @DisplayName("1대1 양방향 테스트 : 외래 키 저장 실패")
    void test2() {
        Food food = new Food();
        food.setName("고구마 피자");
        food.setPrice(30000);

        // 외래 키의 주인이 아닌 User 에서 Food 를 저장해보겠습니다.
        User user = new User();
        user.setName("Robbie");
//        user.setFood(food); // Food 객체를 User 객체에 설정

        userRepository.save(user); // User 객체를 데이터베이스에 저장
        foodRepository.save(food); // Food 객체를 데이터베이스에 저장

        // 확인해 보시면 user_id 값이 들어가 있지 않은 것을 확인하실 수 있습니다.
    }

    @Test
    @Rollback(value = false) // 테스트가 끝난 후에도 데이터베이스 변경 사항을 롤백하지 않음
    @DisplayName("1대1 양방향 테스트 : 외래 키 저장 실패 -> 성공")
    void test3() {
        Food food = new Food();
        food.setName("고구마 피자");
        food.setPrice(30000);

        // 외래 키의 주인이 아닌 User 에서 Food 를 저장하기 위해 addFood() 메서드 추가
        // 외래 키(연관 관계) 설정 food.setUser(this); 추가
        User user = new User();
        user.setName("Robbie");
//        user.addFood(food); // User 객체에 Food 객체를 설정하는 메서드를 사용

        userRepository.save(user); // User 객체를 데이터베이스에 저장
        foodRepository.save(food); // Food 객체를 데이터베이스에 저장
    }

    @Test
    @Rollback(value = false) // 테스트가 끝난 후에도 데이터베이스 변경 사항을 롤백하지 않음
    @DisplayName("1대1 양방향 테스트")
    void test4() {
        User user = new User();
        user.setName("Robbert");

        Food food = new Food();
        food.setName("고구마 피자");
        food.setPrice(30000);
//        food.setUser(user); // 외래 키(연관 관계) 설정

        userRepository.save(user); // User 객체를 데이터베이스에 저장
        foodRepository.save(food); // Food 객체를 데이터베이스에 저장
    }



    // test5 ~ test6 : 조회 테스트

    @Test
    @DisplayName("1대1 조회 : Food 기준 user 정보 조회")
    void test5() {
        Food food = foodRepository.findById(1L).orElseThrow(NullPointerException::new); // ID가 1인 Food 객체를 조회
        // 음식 정보 조회
        System.out.println("food.getName() = " + food.getName());

        // 음식을 주문한 고객 정보 조회
//        System.out.println("food.getUser().getName() = " + food.getUser().getName());
    }

    @Test
    @DisplayName("1대1 조회 : User 기준 food 정보 조회")
    void test6() {
        User user = userRepository.findById(1L).orElseThrow(NullPointerException::new); // ID가 1인 User 객체를 조회
        // 고객 정보 조회
        System.out.println("user.getName() = " + user.getName());

        // 해당 고객이 주문한 음식 정보 조회
//        Food food = user.getFood(); // User 객체에 설정된 Food 객체를 조회
//        System.out.println("food.getName() = " + food.getName());
//        System.out.println("food.getPrice() = " + food.getPrice());
    }
}