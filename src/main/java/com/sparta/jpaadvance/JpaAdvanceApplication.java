package com.sparta.jpaadvance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @EnableJpaAuditing: Spring Data JPA의 Auditing 기능을 활성화
// JPA Auditing은 엔티티가 생성되거나 수정될 때 자동으로 특정 필드를 업데이트하는 기능을 제공
// 예를 들어, 엔티티의 생성 시간, 수정 시간, 생성자, 수정자 등을 자동으로 관리
@EnableJpaAuditing
@SpringBootApplication
public class JpaAdvanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaAdvanceApplication.class, args);
    }

}
