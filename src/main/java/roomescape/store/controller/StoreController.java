package roomescape.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.store.controller.dto.response.StoreDetailDto;
import roomescape.store.service.StoreService;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/stores")
    public ResponseEntity<List<StoreDetailDto>> getStores() {
        List<StoreDetailDto> responseData = storeService.readStores().stream()
                .map(StoreDetailDto::from)
                .toList();
        return ResponseEntity.ok(responseData);
    }

}
