package kr.co.choi.property_manager.repository.specs;



import kr.co.choi.property_manager.domain.Property;
import kr.co.choi.property_manager.domain.PropertyStatus;
import kr.co.choi.property_manager.domain.PropertyType;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class PropertySpecs {

    public static Specification<Property> keywordContains(String keyword) {
        return (root, query,cb) -> {
            if(keyword == null || keyword.isBlank()) return cb.conjunction();
            String like = "%" + keyword.trim().toLowerCase() + "%";
            return cb.or (
                    cb.like(cb.lower(root.get("title")), like),
                    cb.like(cb.lower(root.get("address")), like)
                    );
        };
    }

    public static Specification<Property> statusEq(PropertyStatus status) {
        return (root, qeury, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"),status);
    }

    public static Specification<Property> typeEq(PropertyType type) {
        return (root, query, cb) -> type == null ? cb.conjunction() : cb.equal(root.get("type"), type);
    }

    public static Specification<Property> priceBetween(Long minPrice, Long maxPrice) {
        return (root, query, cb) -> {
            List<Predicate> ps = new ArrayList<>();
            if(minPrice !=null) ps.add(cb.greaterThanOrEqualTo(root.get("price"),minPrice));
            if (maxPrice !=null) ps.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            return ps.isEmpty() ? cb.conjunction() : cb.and(ps.toArray(new Predicate[0]));
        };
    }

    public static Specification<Property> inBounds(Double minLat, Double maxLat, Double minLng, Double maxLng) {
        return (root,query,cb) ->  {
            if(minLat == null || maxLat == null || minLng == null || maxLng ==null) return cb.conjunction();
            return cb.and (
                    cb.between(root.get("lat"), minLat, maxLat),
                    cb.between(root.get("lng"), minLng, maxLng)
            );
        };
    }



}
