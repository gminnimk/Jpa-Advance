package com.sparta.jpaadvance.orphan;

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
✅ JPA를 사용하여 DB에서 User와 Food 엔티티를 다루는 기능을 테스트하는 용도.
 */

@SpringBootTest
public class OrphanTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FoodRepository foodRepository;


    // OrphanTest
    @Test
    @Transactional
    @Rollback(value = false)
    void init() {
        // 유저 객체들을 생성하고 저장
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setName("Robbie");
        userList.add(user1);

        User user2 = new User();
        user2.setName("Robbert");
        userList.add(user2);
        userRepository.saveAll(userList);

        // 음식 객체들을 생성하고 저장
        List<Food> foodList = new ArrayList<>();
        Food food1 = new Food();
        food1.setName("고구마 피자");
        food1.setPrice(30000);
        food1.setUser(user1); // 유저와의 연관 관계(외래 키) 설정
        foodList.add(food1);

        Food food2 = new Food();
        food2.setName("아보카도 피자");
        food2.setPrice(50000);
        food2.setUser(user1); // 유저와의 연관 관계 설정
        foodList.add(food2);

        Food food3 = new Food();
        food3.setName("후라이드 치킨");
        food3.setPrice(15000);
        food3.setUser(user1); // 유저와의 연관 관계 설정
        foodList.add(food3);

        Food food4 = new Food();
        food4.setName("양념 치킨");
        food4.setPrice(20000);
        food4.setUser(user2); // 유저와의 연관 관계 설정
        foodList.add(food4);

        Food food5 = new Food();
        food5.setName("고구마 피자");
        food5.setPrice(30000);
        food5.setUser(user2); // 유저와의 연관 관계 설정
        foodList.add(food5);

        foodRepository.saveAll(foodList);
    }


    // 연관관계 제거
    // orphanRemoval 옵션을 사용하면 연관관계를 제거하는 것 만으로도 해당하는 엔티티를 삭제할 수 있음
    // cascadeType.Remove 옵션과 마찬가지로 해당 엔티티를 삭제하면 연관된 엔티티를 자동으로 삭제함
    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("연관관계 제거")
    void test1() {
        // 이름이 'Robbie'인 유저를 조회
        User user = userRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        // 연관된 음식 Entity 중에서 '후라이드 치킨'을 찾아 제거
        Food chicken = null;
        for (Food food : user.getFoodList()) {
            if (food.getName().equals("후라이드 치킨")) {
                chicken = food;
            }
        }
        if (chicken != null) {
            user.getFoodList().remove(chicken);
        }

        // 연관관계 제거 후 남은 음식들을 출력
        for (Food food : user.getFoodList()) {
            System.out.println("food.getName() = " + food.getName());
        }
    }
}
