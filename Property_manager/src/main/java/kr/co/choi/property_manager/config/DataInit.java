package kr.co.choi.property_manager.config;

import kr.co.choi.property_manager.domain.*;
import kr.co.choi.property_manager.repository.PropertyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInit {

    @Bean
    CommandLineRunner init(PropertyRepository propertyRepository) {
        return args -> {
            Property p1 = new Property("강남 원룸", "서울 강남구 테헤란로 1", 80000000L, PropertyType.ONE_ROOM);
            p1.updateLocation(37.498095, 127.027610); // 임시 좌표

            p1.addMemo(new Memo("첫 상담 완료"));
            p1.addMemo(new Memo("가격 협의 필요"));

            Property p2 = new Property("홍대 상가", "서울 마포구 양화로 1", 250000000L, PropertyType.STORE);
            p2.updateLocation(37.556327, 126.923592);

            p2.addMemo(new Memo("유동 인구 많음"));

            propertyRepository.save(p1);
            propertyRepository.save(p2);
        };
    }
}