package cloud.ptl.harvest.rss;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class RssFeedEntryService {
    private final RssFeedEntryRepository rssFeedEntryRepository;

    public RssFeedEntry save(RssFeedEntry rssFeedEntry) {
        return rssFeedEntryRepository.save(rssFeedEntry);
    }

    public boolean exist(URI uri) {
        return rssFeedEntryRepository.existsByUri(uri);
    }
}
