package com.sparta.jpaadvance.fetch;

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

import java.util.ArrayList;
import java.util.List;

/*

JPA에서는 가져오는 방법을 FetchType 이라고 명칭, 2가지 종류가 존재


    ➡️ Eager Loading(즉시 로딩) : 즉시 정보를 가져옴

            - @ManyToOne : 디폴트가 Eager


    ➡️ Lazy Loading(지연 로딩) : 필요한 시점에 정보를 가져옴

            - @OneToMany : 디폴트가 Lazy


    ➡️ 다른 에노테이션 FetchType을 구분하는 방법으로는

            - @이름 뒤에 Many가 붙어있으면 디폴트가 Lazy (Ex. @OneToMany)

            - 그 반대로 @ManyToOne 처럼 @이름 뒤에 One 이면 디폴트가 Eager


    ➡️ FetchType을 수정할 수 있음

            - Ex) @ManyToOne(fetch = FetchType.LAZY)
 */

@SpringBootTest
public class FetchTypeTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FoodRepository foodRepository;

    @Test
    // @Transactional // 트랜잭션을 설정함으로써 지연로딩으로 설정한 유저를 가져오는데 오류를 해결
    @Rollback(value = false)
    void init() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setName("Robbie");
        userList.add(user1);

        User user2 = new User();
        user2.setName("Robbert");
        userList.add(user2);
        userRepository.saveAll(userList);

        List<Food> foodList = new ArrayList<>();
        Food food1 = new Food();
        food1.setName("고구마 피자");
        food1.setPrice(30000);
        food1.setUser(user1); // 외래 키(연관 관계) 설정
        foodList.add(food1);

        Food food2 = new Food();
        food2.setName("아보카도 피자");
        food2.setPrice(50000);
        food2.setUser(user1); // 외래 키(연관 관계) 설정
        foodList.add(food2);

        Food food3 = new Food();
        food3.setName("후라이드 치킨");
        food3.setPrice(15000);
        food3.setUser(user1); // 외래 키(연관 관계) 설정
        foodList.add(food3);

        Food food4 = new Food();
        food4.setName("후라이드 치킨");
        food4.setPrice(15000);
        food4.setUser(user2); // 외래 키(연관 관계) 설정
        foodList.add(food4);

        Food food5 = new Food();
        food5.setName("고구마 피자");
        food5.setPrice(30000);
        food5.setUser(user2); // 외래 키(연관 관계) 설정
        foodList.add(food5);
        foodRepository.saveAll(foodList);
    }


    // 아보카도 피자 조회 테스트
    @Test
    @DisplayName("아보카도 피자 조회")
    void test1() {
        Food food = foodRepository.findById(2L).orElseThrow(NullPointerException::new);

        System.out.println("food.getName() = " + food.getName());
        System.out.println("food.getPrice() = " + food.getPrice());

        System.out.println("아보카도 피자를 주문한 회원 정보 조회");
        System.out.println("food.getUser().getName() = " + food.getUser().getName());
    }


    // 고객 조회
    @Test
    @Transactional // 지연로딩을 오류 없이 실행하기 위해 Transactional 명시
    @DisplayName("Robbie 고객 조회")
    void test2() {
        User user = userRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        System.out.println("Robbie가 주문한 음식 이름 조회");
        for (Food food : user.getFoodList()) {
            System.out.println(food.getName());
        }
    }


    // 고객 조회 실패
    @Test
    @DisplayName("Robbie 고객 조회 실패")
    void test3() {
        User user = userRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        System.out.println("Robbie가 주문한 음식 이름 조회");
        for (Food food : user.getFoodList()) {
            System.out.println(food.getName());
        }
    }
}