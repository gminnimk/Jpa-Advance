package com.sparta.jpaadvance.cascade;

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
✅ Spring Boot와 Spring Data JPA를 사용하여 영속성 전이(Cascade) 개념을 실험하고 테스트하는 목적으로 작성

    ➡️ 'User' 엔티티와 'Food' 엔티티 간의 관계를 설정, 영속성 전이가 어떻게 작동하는지 실제 테스트 코드를 통해 보여줌.

        ➡️ 이를 통해 JPA의 관계 설정과 관련된 중요한 개념을 학습하고 이해


    📢 영속성 전이(Cascade) :

        DB에서 영속 상태의 엔티티에서 수행되는 작업들이 연관된 엔티티까지 전파되는 상황을 의미

        Ex) 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장하고 싶을 때 사용

        영속성 전이를 적용하여서 해당 엔티티를 저장할 때 해당 엔티티와 연관된 엔티티가지 자동으로 저장하기 위해서는
        자동으로 저장하려고 하는 연관된 엔티티에 추가한 이 연관관계 에노테이션에 CascadeType.PERSIST 옵션을 설정하기
        아래 예문처럼 User 클래스에
        Ex) @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
 */

@SpringBootTest
public class CascadeTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FoodRepository foodRepository;


    // test1(): Robbie가 후라이드 치킨과 양념 치킨을 주문하고, 이를 저장
    // User 엔티티와 Food 엔티티 간의 관계를 설정하고 저장하는 예시
    @Test
    @DisplayName("Robbie 음식 주문")
    void test1() {
        // 고객 Robbie 가 후라이드 치킨과 양념 치킨을 주문합니다.
        User user = new User();
        user.setName("Robbie");

        // 후라이드 치킨 주문
        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        user.addFoodList(food);

        Food food2 = new Food();
        food2.setName("양념 치킨");
        food2.setPrice(20000);

        user.addFoodList(food2);

        userRepository.save(user);
        foodRepository.save(food);
        foodRepository.save(food2);
    }

    // test2(): 영속성 전이를 테스트합니다. Robbie가 후라이드 치킨과 양념 치킨을 주문하고,
    // User 엔티티를 저장할 때 Food 엔티티들도 자동으로 저장
    @Test
    @DisplayName("영속성 전이 저장")
    void test2() {
        // 고객 Robbie 가 후라이드 치킨과 양념 치킨을 주문합니다.
        User user = new User();
        user.setName("Robbie");

        // 후라이드 치킨 주문
        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        user.addFoodList(food);

        Food food2 = new Food();
        food2.setName("양념 치킨");
        food2.setPrice(20000);

        user.addFoodList(food2);

        userRepository.save(user);
    }


    // test3(): Robbie가 주문한 음식을 조회하고 삭제하는 테스트
    // User 엔티티와 관련된 Food 엔티티들을 삭제
    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("Robbie 탈퇴")
    void test3() {
        // 고객 Robbie 를 조회합니다.
        User user = userRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        // Robbie 가 주문한 음식 조회
        for (Food food : user.getFoodList()) {
            System.out.println("food.getName() = " + food.getName());
        }

        // 주문한 음식 데이터 삭제
        foodRepository.deleteAll(user.getFoodList());

        // Robbie 탈퇴
        userRepository.delete(user);
    }


    // test4(): 영속성 전이를 통해 삭제하는 테스트
    // Robbie를 조회하고 삭제하면 연결된 Food 엔티티들도 자동으로 삭제
    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("영속성 전이 삭제")
    void test4() {
        // 고객 Robbie 를 조회합니다.
        User user = userRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        // Robbie 가 주문한 음식 조회
        for (Food food : user.getFoodList()) {
            System.out.println("food.getName() = " + food.getName());
        }

        // Robbie 탈퇴
        userRepository.delete(user);
    }
}
