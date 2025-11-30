package bins.bins_monitor_service.dto;

import bins.bins_monitor_service.enums.BinStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BinTelemetryRequest(
        @Positive(message = "Distance must be greater than zero")
        double currentLevel,

        @NotNull(message = "Status is required")
        BinStatus status
) {
}
