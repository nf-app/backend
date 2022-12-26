package cloud.ptl.harvest.source;

import cloud.ptl.harvest.HarvestSourceType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SourceRepository extends CrudRepository<Source, Long> {
    Optional<Source> findByUrl(String url);
    Optional<Source> findByHarvestSourceType(HarvestSourceType harvestSourceType);
}
