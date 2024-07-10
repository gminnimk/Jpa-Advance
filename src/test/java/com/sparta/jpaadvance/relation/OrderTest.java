package com.sparta.jpaadvance.relation;

import com.sparta.jpaadvance.entity.Food;
import com.sparta.jpaadvance.entity.Order;
import com.sparta.jpaadvance.entity.User;
import com.sparta.jpaadvance.repository.FoodRepository;
import com.sparta.jpaadvance.repository.OrderRepository;
import com.sparta.jpaadvance.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/*
✅ OrderTest 클래스는 JPA를 사용한 DB 연관 관게를 테스트하는 JUnit 테스트 클래스.

    ➡️ User, Food, Order 엔티티 간의 관계를 설정하고 DB에 저장, 조회하는 테스트를 수행
 */

@Transactional // 각 테스트 메서드가 트랜잭션 안에서 실행되며, 테스트 완료 후 롤백됨
@SpringBootTest // Spring Boot 테스트 환경을 제공
public class OrderTest {

    @Autowired
    UserRepository userRepository; // User 엔티티와 관련된 DB 작업을 수행하는 레포지토리
    @Autowired
    FoodRepository foodRepository; // Food 엔티티와 관련된 DB 작업을 수행하는 레포지토리
    @Autowired
    OrderRepository orderRepository; // Order 엔티티와 관련된 DB 작업을 수행하는 레포지토리

    @Test
    @Rollback(value = false) // 테스트 후 데이터베이스 변경 사항을 롤백하지 않음
    @DisplayName("중간 테이블 Order Entity 테스트")
    void test1() {
        // 새로운 사용자 생성
        User user = new User();
        user.setName("Robbie");

        // 새로운 음식 생성
        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        // 새로운 주문 생성
        Order order = new Order();
        order.setUser(user); // 주문에 사용자 정보 설정 (외래 키 연관 관계)
        order.setFood(food); // 주문에 음식 정보 설정 (외래 키 연관 관계)

        // 사용자, 음식, 주문을 데이터베이스에 저장
        userRepository.save(user);
        foodRepository.save(food);
        orderRepository.save(order);
    }

    @Test
    @DisplayName("중간 테이블 Order Entity 조회")
    void test2() {
        // ID가 1인 주문을 조회, 없으면 예외 발생
        Order order = orderRepository.findById(1L).orElseThrow(NullPointerException::new);

        // 주문에서 사용자 정보 조회
        User user = order.getUser();
        System.out.println("user.getName() = " + user.getName());

        // 주문에서 음식 정보 조회
        Food food = order.getFood();
        System.out.println("food.getName() = " + food.getName());
        System.out.println("food.getPrice() = " + food.getPrice());
    }
}
