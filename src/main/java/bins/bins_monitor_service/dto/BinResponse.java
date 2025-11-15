package bins.bins_monitor_service.dto;

import bins.bins_monitor_service.enums.BinPopularity;
import bins.bins_monitor_service.enums.BinStatus;
import bins.bins_monitor_service.enums.BinType;

import java.time.LocalDateTime;
import java.util.UUID;

public record BinResponse(

        UUID uuid,

        String name,

        String location,

        BinType type,

        BinPopularity popularity,

        BinStatus status,

        double totalCapacity,

        double currentLevel,

        LocalDateTime lastUpdated
) {
}
