package com.sparta.jpaadvance.relation;

import com.sparta.jpaadvance.entity.Food;
import com.sparta.jpaadvance.entity.User;
import com.sparta.jpaadvance.repository.FoodRepository;
import com.sparta.jpaadvance.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 📢 JPA 학습할 때 모든 기능들을 쿼리랑 1:1 매핑하면서 학습하기

/*
✅ Food와 User 엔티티 간의 1대 N 단방향 관계를 설정하고, 저장 및 조회를 테스트하는 클래스
 */

@Transactional // 모든 메서드가 트랜잭션 내에서 실행되도록 설정
@SpringBootTest // Spring Boot 테스트 환경 설정
public class OneToManyTest {

    @Autowired // UserRepository 의존성 주입
    UserRepository userRepository;

    @Autowired // FoodRepository 의존성 주입
    FoodRepository foodRepository;

    // 테스트 메서드: 1대N 단방향 관계를 테스트, 한 개의 Food 객체에 두 개의 User 객체를 연관 지어 저장
    @Test
    @Rollback(value = false) // 테스트 후 데이터베이스에 반영되도록 설정 (롤백 안함)
    @DisplayName("1대N 단방향 테스트") // 테스트의 설명을 설정
    void test1() {
        // 새로운 User 객체 생성 및 설정
        User user = new User();
        user.setName("Robbie");

        // 두 번째 User 객체 생성 및 설정
        User user2 = new User();
        user2.setName("Robbert");

        // 새로운 Food 객체 생성 및 설정
        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        // Food 객체와 User 객체들 간의 연관 관계 설정 (외래 키 설정)
        food.getUserList().add(user);
        food.getUserList().add(user2);

        // User 객체들을 데이터베이스에 저장
        userRepository.save(user);
        userRepository.save(user2);

        // Food 객체를 데이터베이스에 저장
        foodRepository.save(food);

        // 추가적인 UPDATE 쿼리 발생을 확인할 수 있습니다.
    }

    // 테스트 메서드: 1대N 조회를 테스트, ID가 1인 Food 객체를 조회하고, 해당 Food 객체에 연관된 모든 User 객체들의 정보를 출력
    @Test
    @DisplayName("1대N 조회 테스트")
    void test2() {
        // ID가 1인 Food 객체를 조회 (없으면 NullPointerException 발생)
        Food food = foodRepository.findById(1L).orElseThrow(NullPointerException::new);
        System.out.println("food.getName() = " + food.getName());

        // 해당 음식을 주문한 고객 정보 조회
        List<User> userList = food.getUserList();
        for (User user : userList) {
            System.out.println("user.getName() = " + user.getName());
        }
    }

}