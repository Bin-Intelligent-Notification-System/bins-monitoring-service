package bins.bins_monitor_service.respository;

import bins.bins_monitor_service.model.Bin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface BinRepository extends JpaRepository<Bin, UUID> {
    Optional<Bin> findByName(@NotBlank(message = "Name cannot be blank") @NotNull(message = "Name is required") String name);
}
