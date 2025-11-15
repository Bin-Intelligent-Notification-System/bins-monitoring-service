package bins.bins_monitor_service.mapper;

import bins.bins_monitor_service.dto.BinResponse;
import bins.bins_monitor_service.dto.CreateBinRequest;
import bins.bins_monitor_service.enums.BinPopularity;
import bins.bins_monitor_service.model.Bin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        imports = { BinPopularity.class, java.time.LocalDateTime.class }
)
public interface BinMapper {

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "popularity", expression = "java(BinPopularity.MEDIUM)")
    @Mapping(target = "currentLevel", constant = "0")
    @Mapping(target = "lastUpdated", expression = "java(LocalDateTime.now())")
    Bin toBin(CreateBinRequest createBinRequest);

    BinResponse toBinResponse(Bin bin);
}
