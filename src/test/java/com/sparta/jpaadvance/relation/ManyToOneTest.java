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

/*
✅ Spring Boot 환경에서 JPA를 사용하여 N대1 단방향, 양방향 관계를 테스트하는 클래스.
 */

@Transactional // 모든 메서드가 트랜잭션 내에서 실행되도록 설정
@SpringBootTest // Spring Boot 테스트 환경 설정
public class ManyToOneTest {

    @Autowired // 의존성 주입을 통해 UserRepository를 주입받음
    UserRepository userRepository;

    @Autowired // 의존성 주입을 통해 FoodRepository를 주입받음
    FoodRepository foodRepository;

    // 테스트 메서드: N대1 단방향 관계를 테스트, User 객체에 두 개의 Food 객체를 연관 지어 저장
    @Test
    @Rollback(value = false) // 테스트 후 데이터베이스에 반영되도록 설정 (롤백 안함)
    @DisplayName("N대1 단방향 테스트")
    // 테스트의 설명을 설정
    void test1() {
        // 새로운 User 객체 생성 및 설정
        User user = new User();
        user.setName("Robbie");

        // 새로운 Food 객체 생성 및 설정
        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);
//        food.setUser(user); // User 객체와의 연관 관계 설정 (외래 키 설정)

        // 두 번째 Food 객체 생성 및 설정
        Food food2 = new Food();
        food2.setName("양념 치킨");
        food2.setPrice(20000);
//        food2.setUser(user); // 동일한 User 객체와의 연관 관계 설정 (외래 키 설정)

        // User 객체를 데이터베이스에 저장
        userRepository.save(user);

        // 첫 번째 Food 객체를 데이터베이스에 저장
        foodRepository.save(food);

        // 두 번째 Food 객체를 데이터베이스에 저장
        foodRepository.save(food2);
    }



    // N대1 양방향 관계 설정 실패 테스트. 외래 키 설정 없이 User 객체에 Food 객체들을 추가하고 저장
    @Test
    @Rollback(value = false)
    @DisplayName("N대1 양방향 테스트 : 외래 키 저장 실패")
    void test2() {

        // 새로운 Food 객체 생성 및 설정
        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        // 두 번째 Food 객체 생성 및 설정
        Food food2 = new Food();
        food2.setName("양념 치킨");
        food2.setPrice(20000);

        // 외래 키의 주인이 아닌 User 에서 Food 를 저장해보겠습니다.
        // 새로운 User 객체 생성 및 설정
        User user = new User();
        user.setName("Robbie");

        // Food 객체들을 User 객체에 추가 (외래 키 설정 안함)
//        user.getFoodList().add(food);
//        user.getFoodList().add(food2);

        // User 객체와 Food 객체들을 데이터베이스에 저장
        userRepository.save(user);
        foodRepository.save(food);
        foodRepository.save(food2);

        // 확인해 보시면 user_id 값이 들어가 있지 않은 것을 확인하실 수 있습니다.
    }



    // test3: N대1 양방향 관계 설정 성공 테스트. User 객체의 addFoodList 메서드를 통해 외래 키 설정 후 Food 객체들을 추가하고 저장
    @Test
    @Rollback(value = false)
    @DisplayName("N대1 양방향 테스트 : 외래 키 저장 실패 -> 성공")
    void test3() {

        // 새로운 Food 객체 생성 및 설정
        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        // 두 번째 Food 객체 생성 및 설정
        Food food2 = new Food();
        food2.setName("양념 치킨");
        food2.setPrice(20000);

        // 외래 키의 주인이 아닌 User 에서 Food 를 쉽게 저장하기 위해 addFoodList() 메서드 생성하고
        // 해당 메서드에 외래 키(연관 관계) 설정 food.setUser(this); 추가
        // 새로운 User 객체 생성 및 설정
        User user = new User();
        user.setName("Robbie");

        // addFoodList 메서드를 통해 Food 객체들을 User 객체에 추가 (외래 키 설정)
//        user.addFoodList(food);
//        user.addFoodList(food2);

        // User 객체와 Food 객체들을 데이터베이스에 저장
        userRepository.save(user);
        foodRepository.save(food);
        foodRepository.save(food2);
    }



    // test4: N대1 양방향 관계 설정 테스트. User 객체에 두 개의 Food 객체를 연관 지어 저장
    @Test
    @Rollback(value = false)
    @DisplayName("N대1 양방향 테스트")
    void test4() {
        // 새로운 User 객체 생성 및 설정
        User user = new User();
        user.setName("Robbert");

        // 새로운 Food 객체 생성 및 설정
        Food food = new Food();
        food.setName("고구마 피자");
        food.setPrice(30000);
//        food.setUser(user); // 외래 키(연관 관계) 설정

        // 두 번째 Food 객체 생성 및 설정
        Food food2 = new Food();
        food2.setName("아보카도 피자");
        food2.setPrice(50000);
//        food2.setUser(user); // 외래 키(연관 관계) 설정

        // User 객체와 Food 객체들을 데이터베이스에 저장
        userRepository.save(user);
        foodRepository.save(food);
        foodRepository.save(food2);
    }



    // test5: Food 객체 기준으로 연관된 User 객체 정보를 조회
    @Test
    @DisplayName("N대1 조회 : Food 기준 user 정보 조회")
    void test5() {
        // ID가 1인 Food 객체를 조회 (없으면 NullPointerException 발생)
        Food food = foodRepository.findById(1L).orElseThrow(NullPointerException::new);

        // 음식 정보 조회
        System.out.println("food.getName() = " + food.getName());

        // 음식을 주문한 고객 정보 조회
//        System.out.println("food.getUser().getName() = " + food.getUser().getName());
    }



    // test6: User 객체 기준으로 연관된 Food 객체 정보들을 조회
    @Test
    @DisplayName("N대1 조회 : User 기준 food 정보 조회")
    void test6() {
        // ID가 1인 User 객체를 조회 (없으면 NullPointerException 발생)
        User user = userRepository.findById(1L).orElseThrow(NullPointerException::new);

        // 고객 정보 조회
        System.out.println("user.getName() = " + user.getName());

        // 해당 고객이 주문한 음식 정보 조회
//        List<Food> foodList = user.getFoodList();
//        for (Food food : foodList) {
//            System.out.println("food.getName() = " + food.getName());
//            System.out.println("food.getPrice() = " + food.getPrice());
//        }
    }
}
