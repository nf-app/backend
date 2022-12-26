package cloud.ptl.api.harvest;

import cloud.ptl.harvest.HarvestSourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/harvest")
public class HarvestController {
    private final HarvestService harvestService;

    @GetMapping("/start")
    public String order(HarvestSourceType harvestSourceType) {
        harvestService.startHarvest(harvestSourceType);
        return "Ordered";
    }
}
