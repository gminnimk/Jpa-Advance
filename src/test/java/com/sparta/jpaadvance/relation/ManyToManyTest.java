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
✅ Spring Boot 환경에서 JPA를 사용하여 N대M(다대다) 단방향, 양방향 관계를 테스트하는 클래스.
*/

@Transactional // 모든 메서드가 트랜잭션 내에서 실행되도록 설정
@SpringBootTest // Spring Boot 테스트 환경 설정
public class ManyToManyTest {

    @Autowired // UserRepository를 주입받음
    UserRepository userRepository;

    @Autowired // FoodRepository를 주입받음
    FoodRepository foodRepository;

    // 테스트 메서드: N대M 단방향 관계를 테스트
    // 여러 사용자가 한 가지 음식을 주문하는 상황을 시뮬레이션
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


    // 테스트 메서드: N대M 양방향 관계를 테스트
    // 외래 키의 주인이 아닌 엔티티에서 엔티티를 저장하려고 시도하면 예상치 못한 결과가 발생하는 경우
    @Test
    @Rollback(value = false)
    @DisplayName("N대M 양방향 테스트 : 외래 키 저장 실패")
    void test2() {

        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        Food food2 = new Food();
        food2.setName("양념 치킨");
        food2.setPrice(20000);

        // 외래 키의 주인이 아닌 User 에서 Food 를 저장해보겠습니다.
        User user = new User();
        user.setName("Robbie");
        user.getFoodList().add(food);
        user.getFoodList().add(food2);

        userRepository.save(user);
        foodRepository.save(food);
        foodRepository.save(food2);

        // 확인해 보시면 orders 테이블에 food_id, user_id 값이 들어가 있지 않은 것을 확인하실 수 있습니다.
    }


    // test3(): addFoodList() 메서드를 사용하여 N
    // 다대다 양방향 관계를 테스트합니다. 이 방법을 통해 외래 키의 주인이 아닌 엔티티에서 엔티티를 쉽게 저장할 수 있음
    @Test
    @Rollback(value = false)
    @DisplayName("N대M 양방향 테스트 : 외래 키 저장 실패 -> 성공")
    void test3() {

        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        Food food2 = new Food();
        food2.setName("양념 치킨");
        food2.setPrice(20000);

        // 외래 키의 주인이 아닌 User 에서 Food 를 쉽게 저장하기 위해 addFoodList() 메서드를 생성해서 사용합니다.
        // 외래 키(연관 관계) 설정을 위해 Food 에서 userList 를 호출해 user 객체 자신을 add 합니다.
        User user = new User();
        user.setName("Robbie");
        user.addFoodList(food);
        user.addFoodList(food2);

        userRepository.save(user);
        foodRepository.save(food);
        foodRepository.save(food2);
    }


    // test4(): N
    // 다대다 양방향 관계를 테스트하면서, 엔티티 간의 관계 설정과 데이터베이스 저장
    @Test
    @Rollback(value = false)
    @DisplayName("N대M 양방향 테스트")
    void test4() {

        User user = new User();
        user.setName("Robbie");

        User user2 = new User();
        user2.setName("Robbert");


        Food food = new Food();
        food.setName("아보카도 피자");
        food.setPrice(50000);
        food.getUserList().add(user); // 외래 키(연관 관계) 설정
        food.getUserList().add(user2); // 외래 키(연관 관계) 설정

        Food food2 = new Food();
        food2.setName("고구마 피자");
        food2.setPrice(30000);
        food2.getUserList().add(user); // 외래 키(연관 관계) 설정

        userRepository.save(user);
        userRepository.save(user2);
        foodRepository.save(food);
        foodRepository.save(food2);

        // User 를 통해 food 의 정보 조회
        System.out.println("user.getName() = " + user.getName());

        List<Food> foodList = user.getFoodList();
        for (Food f : foodList) {
            System.out.println("f.getName() = " + f.getName());
            System.out.println("f.getPrice() = " + f.getPrice());
        }

        // 외래 키의 주인이 아닌 User 객체에 Food 의 정보를 넣어주지 않아도 DB 저장에는 문제가 없지만
        // 이처럼 User 를 사용하여 food 의 정보를 조회할 수는 없습니다.
    }


    // test5(): addUserList() 메서드를 사용하여 N
    // 다대다 양방향 관계를 테스트합니다. 이 방법을 통해 엔티티 간의 양방향 관계를 설정하고 데이터베이스에 저장하는 과정
    @Test
    @Rollback(value = false)
    @DisplayName("N대M 양방향 테스트 : 객체와 양방향의 장점 활용")
    void test5() {

        User user = new User();
        user.setName("Robbie");

        User user2 = new User();
        user2.setName("Robbert");


        // addUserList() 메서드를 생성해 user 정보를 추가하고
        // 해당 메서드에 객체 활용을 위해 user 객체에 food 정보를 추가하는 코드를 추가합니다. user.getFoodList().add(this);
        Food food = new Food();
        food.setName("아보카도 피자");
        food.setPrice(50000);
        food.addUserList(user);
        food.addUserList(user2);

        Food food2 = new Food();
        food2.setName("고구마 피자");
        food2.setPrice(30000);
        food2.addUserList(user);

        userRepository.save(user);
        userRepository.save(user2);
        foodRepository.save(food);
        foodRepository.save(food2);

        // User 를 통해 food 의 정보 조회
        System.out.println("user.getName() = " + user.getName());

        List<Food> foodList = user.getFoodList();
        for (Food f : foodList) {
            System.out.println("f.getName() = " + f.getName());
            System.out.println("f.getPrice() = " + f.getPrice());
        }
    }



    // 테스트 메서드: N대M 양방향 관계를 조회
    // test6(): N
    // 관계에서 Food를 기준으로 User 정보를 조회하는 테스트를 수행
    @Test
    @DisplayName("N대M 조회 : Food 기준 user 정보 조회")
    void test6() {
        Food food = foodRepository.findById(1L).orElseThrow(NullPointerException::new);
        // 음식 정보 조회
        System.out.println("food.getName() = " + food.getName());

        // 음식을 주문한 고객 정보 조회
        List<User> userList = food.getUserList();
        for (User user : userList) {
            System.out.println("user.getName() = " + user.getName());
        }
    }


    // test7(): N
    // 관계에서 User를 기준으로 Food 정보를 조회하는 테스트를 수행
    @Test
    @DisplayName("N대M 조회 : User 기준 food 정보 조회")
    void test7() {
        User user = userRepository.findById(1L).orElseThrow(NullPointerException::new);
        // 고객 정보 조회
        System.out.println("user.getName() = " + user.getName());

        // 해당 고객이 주문한 음식 정보 조회
        List<Food> foodList = user.getFoodList();
        for (Food food : foodList) {
            System.out.println("food.getName() = " + food.getName());
            System.out.println("food.getPrice() = " + food.getPrice());
        }
    }
}
