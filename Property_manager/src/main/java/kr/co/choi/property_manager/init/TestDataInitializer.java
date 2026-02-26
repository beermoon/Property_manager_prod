
// 더미데이터 파일



//package kr.co.choi.property_manager.init;
//
//import kr.co.choi.property_manager.controller.dto.PropertyCreateRequest;
//import kr.co.choi.property_manager.domain.Property;
//import kr.co.choi.property_manager.repository.PropertyRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//@Component
//@Profile("local") // 로컬에서만 자동 시딩
//public class TestDataInitializer implements CommandLineRunner {
//
//    private final PropertyRepository propertyRepository;
//
//    public TestDataInitializer(PropertyRepository propertyRepository) {
//        this.propertyRepository = propertyRepository;
//    }
//
//    @Override
//    public void run(String... args) {
//        // 이미 데이터가 있으면 중복 생성 방지 (원하면 주석 처리)
//        if (propertyRepository.count() > 0) return;
//
//        int N = 200;
//
//        for (int i = 1; i <= N; i++) {
//            PropertyCreateRequest req = AdvancedMockFactory.create(i);
//
//            Property p = new Property();
//            p.updateAll(req);
//
//            // 좌표는 지역 기반 랜덤
//            double[] latlng = randomLatLngByRegion(req.getRegion());
//            p.updateLocation(latlng[0], latlng[1]);
//
//            propertyRepository.save(p);
//        }
//
//        System.out.println("[SEED] inserted " + N + " properties");
//    }
//
//    private double[] randomLatLngByRegion(String region) {
//        double baseLat = 35.1631; // 해운대 근사
//        double baseLng = 129.1635;
//
//        if ("수영".equals(region)) { baseLat = 35.1456; baseLng = 129.1134; }
//        if ("동래".equals(region)) { baseLat = 35.2050; baseLng = 129.0836; }
//
//        return new double[]{
//                baseLat + (Math.random() - 0.5) * 0.03, // +- 약 1~2km 느낌
//                baseLng + (Math.random() - 0.5) * 0.03
//        };
//    }
//}