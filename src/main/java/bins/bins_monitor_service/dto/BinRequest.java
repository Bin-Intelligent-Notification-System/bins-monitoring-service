package bins.bins_monitor_service.dto;

import bins.bins_monitor_service.enums.BinType;

public interface BinRequest {
    String name();
    String location();
    BinType type();
    double totalCapacity();
}
