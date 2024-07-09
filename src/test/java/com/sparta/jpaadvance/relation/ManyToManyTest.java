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
✅ Spring Boot 환경에서 JPA를 사용하여 N대M 단방향 관계를 테스트하는 클래스.
*/

@Transactional // 모든 메서드가 트랜잭션 내에서 실행되도록 설정
@SpringBootTest // Spring Boot 테스트 환경 설정
public class ManyToManyTest {

    @Autowired // UserRepository를 주입받음
    UserRepository userRepository;

    @Autowired // FoodRepository를 주입받음
    FoodRepository foodRepository;

    // 테스트 메서드: N대M 단방향 관계를 테스트
    @Test
    @Rollback(value = false) // 테스트 후 데이터베이스에 반영되도록 설정 (롤백 안함)
    @DisplayName("N대M 단방향 테스트") // 테스트의 설명을 설정
    void test1() {
        // 새로운 User 객체 생성 및 설정
        User user = new User();
        user.setName("Robbie");

        // 새로운 User 객체 생성 및 설정
        User user2 = new User();
        user2.setName("Robbert");

        // 새로운 Food 객체 생성 및 설정
        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        // Food 객체와 User 객체들의 관계 설정
        food.getUserList().add(user);
        food.getUserList().add(user2);

        // User 객체들을 데이터베이스에 저장
        userRepository.save(user);
        userRepository.save(user2);

        // Food 객체를 데이터베이스에 저장하면서 자동으로 중간 테이블인 orders에 관계가 저장됨
        foodRepository.save(food);
    }
}

