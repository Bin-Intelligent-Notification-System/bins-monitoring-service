package bins.bins_monitor_service.model;

import bins.bins_monitor_service.enums.BinPopularity;
import bins.bins_monitor_service.enums.BinType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String location;

    private String name;

    @Enumerated(EnumType.STRING)
    private BinType type;

    @Enumerated(EnumType.STRING)
    BinPopularity popularity;

    private double totalCapacity;

    double currentLevel;

    LocalDateTime lastUpdated;


}
