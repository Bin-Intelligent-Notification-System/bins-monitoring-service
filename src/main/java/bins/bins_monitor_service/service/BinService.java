package bins.bins_monitor_service.service;

import bins.bins_monitor_service.dto.BinResponse;
import bins.bins_monitor_service.dto.BinTelemetryRequest;
import bins.bins_monitor_service.dto.CreateBinRequest;
import bins.bins_monitor_service.dto.UpdateBinRequest;
import bins.bins_monitor_service.enums.BinStatus;
import bins.bins_monitor_service.exception.BinAlreadyExistsException;
import bins.bins_monitor_service.exception.BinNotFoundException;
import bins.bins_monitor_service.mapper.BinMapper;
import bins.bins_monitor_service.model.Bin;
import bins.bins_monitor_service.model.BinTelemetryLog;
import bins.bins_monitor_service.respository.BinRepository;
import bins.bins_monitor_service.respository.BinTelemetryLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class BinService {
    private final BinMapper binMapper;
    private final BinRepository binRepository;
    private final BinTelemetryLogRepository binTelemetryLogRepository;

    public List<BinResponse> getAllBins() {
        var bins = binRepository.findAll();
        if (bins.isEmpty()) {
            return List.of();
        }

        return bins.stream().map(binMapper::toBinResponse).toList();
    }

    public BinResponse fetchBinById(UUID binId) {
        var bin = binRepository.findById(binId)
                .orElseThrow(() -> new BinNotFoundException(
                        format("Bin with id %s does not exist", binId)));

        return binMapper.toBinResponse(bin);
    }

    @Transactional
    public BinResponse createBin(CreateBinRequest createBinRequest) {
        log.info("Persisting bin with name {}", createBinRequest.name());

        var existingBin = binRepository.findByName(createBinRequest.name());
        if (existingBin.isPresent()) {
            throw new BinAlreadyExistsException(
                    format("Bin with name %s already exists", createBinRequest.name()));
        }
        return binMapper.toBinResponse(binRepository.save(binMapper.toBin(createBinRequest)));
    }

    @Transactional
    public void updateBin(UpdateBinRequest updateBinRequest){
        log.info("Updating bin with ID {} ", updateBinRequest.id());

        UUID binId = updateBinRequest.id();
        Bin bin = binRepository.findById(binId)
                .orElseThrow(() -> new BinNotFoundException(
                        format("Bin with Keycloak ID %s not found in DB", binId)
                ));
        mergeBin(updateBinRequest, bin);
        binRepository.save(bin);
    }

    @Transactional
    public void deleteBin(UUID binId) {
        log.info("Deleting bin  with id {}", binId);
        binRepository.findById(binId).orElseThrow(
                () -> new BinNotFoundException(format("Bin with id %s was not found", binId)));
        binRepository.deleteById(binId);
    }

    @Transactional
    public void addTelemetryData(UUID binId, BinTelemetryRequest binTelemetryRequest) {
        log.info("Adding telemetry data to bin with id {}", binId);
        Bin bin = binRepository.findById(binId)
                .orElseThrow(() -> new BinNotFoundException(
                        format("Bin with id %s was not found", binId)));

        double currentLevel = binTelemetryRequest.currentLevel();
        BinStatus status = binTelemetryRequest.status();
        binRepository.save(bin);

        double totalCapacity = bin.getTotalCapacity();
        Double fillPercentage = computeFillPercentage(currentLevel, totalCapacity);

        BinTelemetryLog logEntry = BinTelemetryLog.builder()
                .bin(bin)
                .currentLevel(currentLevel)
                .fillPercentage(fillPercentage)
                .status(status)
                .build();

        binTelemetryLogRepository.save(logEntry);

        bin.setCurrentLevel(currentLevel);
        bin.setBinStatus(status);
        bin.setFillPercentage(fillPercentage);
        bin.setLastUpdated(LocalDateTime.now());

        binRepository.save(bin);
    }

    private void mergeBin(UpdateBinRequest dto, Bin bin) {
        if (dto.name() != null && StringUtils.isNotBlank(dto.name())) {
            bin.setName(dto.name());
        }
        if (dto.location() != null && StringUtils.isNotBlank(dto.location())) {
            bin.setLocation(dto.location());
        }
        if (dto.type() != null) {
            bin.setType(dto.type());
        }
        if (dto.totalCapacity() > 0) {
            bin.setTotalCapacity(dto.totalCapacity());
        }
    }

    private Double computeFillPercentage(double currentLevel, Double totalCapacity) {
        if (totalCapacity <= 0) {
            return null;
        }
        double used = Math.max(0, totalCapacity - currentLevel);
        return Math.min(100.0, (used / totalCapacity) * 100.0);
    }

}
