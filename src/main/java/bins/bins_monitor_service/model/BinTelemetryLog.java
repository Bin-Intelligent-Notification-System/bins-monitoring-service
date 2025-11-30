package bins.bins_monitor_service.model;

import bins.bins_monitor_service.enums.BinStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
public class BinTelemetryLog {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid")
    private Bin bin;

    private Double currentLevel;

    private Double fillPercentage;

    @Enumerated(EnumType.STRING)
    private BinStatus status;

    private OffsetDateTime createdAt;

    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }
}
