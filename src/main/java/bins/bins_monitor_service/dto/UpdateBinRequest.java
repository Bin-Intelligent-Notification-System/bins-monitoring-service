package bins.bins_monitor_service.dto;

import bins.bins_monitor_service.enums.BinType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record UpdateBinRequest(

        @NotNull(message = "ID is required")
        UUID id,

        String name,

        String location,

        BinType type,

        @Positive(message = "Total capacity must be greater than zero")
        double totalCapacity
) {
}
