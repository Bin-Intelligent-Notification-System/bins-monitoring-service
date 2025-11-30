package bins.bins_monitor_service.dto;

import bins.bins_monitor_service.enums.BinStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record BinTelemetryRequest(
        @PositiveOrZero(message = "Distance must be zero or greater")
        double currentLevel,

        @NotNull(message = "Status is required")
        BinStatus status
) {
}
