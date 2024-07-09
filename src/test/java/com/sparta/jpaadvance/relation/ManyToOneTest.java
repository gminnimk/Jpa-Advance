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

/*
✅ Spring Boot 환경에서 JPA를 사용하여 N대1 단방향 관계를 테스트하는 클래스.
 */

@Transactional // 모든 메서드가 트랜잭션 내에서 실행되도록 설정
@SpringBootTest // Spring Boot 테스트 환경 설정
public class ManyToOneTest {

    @Autowired // 의존성 주입을 통해 UserRepository를 주입받음
    UserRepository userRepository;

    @Autowired // 의존성 주입을 통해 FoodRepository를 주입받음
    FoodRepository foodRepository;

    // 테스트 메서드: N대1 단방향 관계를 테스트
    @Test
    @Rollback(value = false) // 테스트 후 데이터베이스에 반영되도록 설정 (롤백 안함)
    @DisplayName("N대1 단방향 테스트") // 테스트의 설명을 설정
    void test1() {
        // 새로운 User 객체 생성 및 설정
        User user = new User();
        user.setName("Robbie");

        // 새로운 Food 객체 생성 및 설정
        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);
        food.setUser(user); // User 객체와의 연관 관계 설정 (외래 키 설정)

        // 두 번째 Food 객체 생성 및 설정
        Food food2 = new Food();
        food2.setName("양념 치킨");
        food2.setPrice(20000);
        food2.setUser(user); // 동일한 User 객체와의 연관 관계 설정 (외래 키 설정)

        // User 객체를 데이터베이스에 저장
        userRepository.save(user);

        // 첫 번째 Food 객체를 데이터베이스에 저장
        foodRepository.save(food);

        // 두 번째 Food 객체를 데이터베이스에 저장
        foodRepository.save(food2);
    }
}
