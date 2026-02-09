package kr.co.choi.property_manager.repository;

import kr.co.choi.property_manager.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
