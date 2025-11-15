package bins.bins_monitor_service.dto;

import bins.bins_monitor_service.enums.BinType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateBinRequest(

        @NotBlank(message = "Name cannot be blank")
        @NotNull(message = "Name is required")
        String name,

        @NotBlank(message = "Location cannot be blank")
        @NotNull(message = "Location is required")
        String location,

        @NotNull(message = "Type is required")
        BinType type,

        @Positive(message = "Total capacity must be greater than zero")
        double totalCapacity
) implements BinRequest {}
