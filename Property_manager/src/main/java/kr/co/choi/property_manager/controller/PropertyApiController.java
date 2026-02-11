package kr.co.choi.property_manager.controller;


import kr.co.choi.property_manager.domain.Property;
import kr.co.choi.property_manager.domain.PropertyStatus;
import kr.co.choi.property_manager.domain.PropertyType;
import kr.co.choi.property_manager.repository.PropertyRepository;
import kr.co.choi.property_manager.repository.specs.PropertySpecs;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PropertyApiController {

    private final PropertyRepository propertyRepository;

    public PropertyApiController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/api/properties")
    public List<PropertyMarkerDto> markers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) PropertyStatus status,
            @RequestParam(required = false) PropertyType type,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) Double minLat,
            @RequestParam(required = false) Double maxLat,
            @RequestParam(required = false) Double maxLng,
            @RequestParam(defaultValue = "500") int limit
    ) {
        // 조건 조합
        Specification<Property> spec = Specification
                .where(PropertySpecs.keywordContains(keyword))
                .and(PropertySpecs.statusEq(status))
                .and(PropertySpecs.typeEq(type))
                .and(PropertySpecs.priceBetween(minPrice, maxPrice))
                // 좌표 없는 건 지도 마커 불가 불가 -> 제외
                .and((root, query, cb) -> cb.isNotNull(root.get("lat")))
                .and((root, query, cb) -> cb.isNotNull(root.get("lng")));

        int safeLimit = Math.min(Math.max(limit, 1), 2000); // 방어코드
        var page = propertyRepository.findAll(spec, PageRequest.of(0, safeLimit));

        return page.getContent().stream()
                .map(PropertyMarkerDto::from)
                .toList();
    }

    public record PropertyMarkerDto(
            Long id,
            String title,
            Double lat,
            Double lng,
            PropertyStatus status,
            PropertyType type,
            Long price
    ) {
        static PropertyMarkerDto from(Property p) {
            return new PropertyMarkerDto(
                    p.getId(),
                    p.getTitle(),
                    p.getLat(),
                    p.getLng(),
                    p.getStatus(),
                    p.getType(),
                    p.getPrice()
            );
        }
    }


    }




