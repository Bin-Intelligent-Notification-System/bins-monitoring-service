package bins.bins_monitor_service.respository;

import bins.bins_monitor_service.model.BinTelemetryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface BinTelemetryLogRepository extends JpaRepository<BinTelemetryLog, UUID> {

}
