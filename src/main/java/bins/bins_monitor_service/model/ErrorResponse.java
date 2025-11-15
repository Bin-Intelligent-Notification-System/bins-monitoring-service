package bins.bins_monitor_service.model;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
