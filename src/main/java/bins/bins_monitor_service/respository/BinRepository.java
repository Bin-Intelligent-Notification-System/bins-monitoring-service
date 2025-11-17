package bins.bins_monitor_service.respository;

import bins.bins_monitor_service.model.Bin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface BinRepository extends JpaRepository<Bin, UUID> {
    Optional<Bin> findByName(String name);
}
