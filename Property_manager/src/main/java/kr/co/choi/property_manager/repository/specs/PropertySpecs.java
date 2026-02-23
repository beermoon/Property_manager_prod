package kr.co.choi.property_manager.repository.specs;

import kr.co.choi.property_manager.domain.Property;
import kr.co.choi.property_manager.domain.PropertyStatus;
import org.springframework.data.jpa.domain.Specification;

public class PropertySpecs {

    public static Specification<Property> keywordContains(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) return cb.conjunction();
            String like = "%" + keyword.trim().toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("title")), like),
                    cb.like(cb.lower(root.get("address")), like)
            );
        };
    }

    public static Specification<Property> statusEq(PropertyStatus status) {
        return (root, query, cb) ->
                status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    // 지도 화면 범위 필터 (선택)
    public static Specification<Property> inBounds(Double minLat, Double maxLat, Double minLng, Double maxLng) {
        return (root, query, cb) -> {
            if (minLat == null || maxLat == null || minLng == null || maxLng == null) return cb.conjunction();
            return cb.and(
                    cb.between(root.get("lat"), minLat, maxLat),
                    cb.between(root.get("lng"), minLng, maxLng)
            );
        };
    }
}