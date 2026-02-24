package kr.co.choi.property_manager.init;

import kr.co.choi.property_manager.controller.dto.PropertyCreateRequest;
import kr.co.choi.property_manager.domain.DealType;

import java.util.Random;

public class AdvancedMockFactory {

    private static final Random random = new Random();

    private static final String[] regions = {"해운대", "수영", "동래"};

    public static PropertyCreateRequest create(int i) {

        String region = regions[random.nextInt(regions.length)];

        DealType dealType = random.nextBoolean()
                ? DealType.MONTHLY
                : DealType.JEONSE;

        PropertyCreateRequest req = new PropertyCreateRequest();

        req.setTitle(region + " 테스트 매물 " + i);
        req.setRegion(region);
        req.setAddress("부산광역시 " + region + "구 테스트로 " + i);

        req.setDealType(dealType);

        if (dealType == DealType.MONTHLY) {
            req.setDeposit(300L + random.nextInt(700));     // 300~1000 만원
            req.setMonthlyRent(20L + random.nextInt(80));   // 20~100 만원
        } else {
            req.setDeposit(5000L + random.nextInt(15000));  // 5000~20000 만원
        }

        req.setBuiltYear(1995 + random.nextInt(30));
        req.setRoomCount(1 + random.nextInt(4));
        req.setArea(8.0 + random.nextDouble() * 20);

        req.setHasElevator(random.nextBoolean());
        req.setHasParking(random.nextBoolean());
        req.setPetAllowed(random.nextBoolean());
        req.setLhAvailable(random.nextBoolean());

        return req;
    }
}