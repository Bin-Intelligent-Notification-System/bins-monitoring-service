package bins.bins_monitor_service.controller;

import bins.bins_monitor_service.dto.BinResponse;
import bins.bins_monitor_service.dto.CreateBinRequest;
import bins.bins_monitor_service.dto.UpdateBinRequest;
import bins.bins_monitor_service.service.BinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/bin")
public class BinController {
    private final BinService binService;

    @GetMapping("/getAllBins")
    public ResponseEntity<List<BinResponse>> getAllBins(){
        return ResponseEntity.ok(binService.getAllBins());
    }

    @GetMapping("/id/{binId}")
    public ResponseEntity<BinResponse> fetchBinById(@PathVariable UUID binId) {
        return ResponseEntity.ok(binService.fetchBinById(binId));
    }

    @PostMapping("/create")
    public ResponseEntity<BinResponse> createUser(@Valid @RequestBody CreateBinRequest createBinRequest){
        return ResponseEntity.ok(binService.createBin(createBinRequest));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateBinRequest updateBinRequest){
        binService.updateBin(updateBinRequest);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/delete/{binId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID binId){
        binService.deleteBin(binId);
        return ResponseEntity.accepted().build();
    }
}
