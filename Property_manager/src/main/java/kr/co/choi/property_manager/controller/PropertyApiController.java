package kr.co.choi.property_manager.controller;


import kr.co.choi.property_manager.domain.Property;
import kr.co.choi.property_manager.repository.PropertyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PropertyApiController {

    private final PropertyRepository propertyRepository;

    public PropertyApiController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/api/properties")
    public List<PropertyMarkerDto> markers() {
        return propertyRepository.findAll().stream()
                .filter(p -> p.getLat() != null && p.getLng() != null)
                .map(PropertyMarkerDto::from)
                .toList();
    }

    public record PropertyMarkerDto(Long id, String title, Double lat, Double lng) {
        static PropertyMarkerDto from(Property p) {
            return new PropertyMarkerDto(
                p.getId(),
                p.getTitle(),
                p.getLat(),
                p.getLng()
                );
        }
    }

}


