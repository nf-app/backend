package cloud.ptl.harvest.rss;

import org.springframework.data.repository.CrudRepository;

import java.net.URI;

public interface RssFeedEntryRepository extends CrudRepository<RssFeedEntry, Long> {
    boolean existsByUri(URI uri);
}
