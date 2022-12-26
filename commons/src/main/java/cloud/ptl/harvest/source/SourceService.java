package cloud.ptl.harvest.source;

import cloud.ptl.harvest.HarvestSourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SourceService {
    private final SourceRepository sourceRepository;

    public Source findByUrl(String url) {
        return sourceRepository.findByUrl(url).orElseThrow();
    }

    public Source findByHarvestSourceType(HarvestSourceType harvestSourceType) {
        return sourceRepository.findByHarvestSourceType(harvestSourceType).orElseThrow();
    }
}
