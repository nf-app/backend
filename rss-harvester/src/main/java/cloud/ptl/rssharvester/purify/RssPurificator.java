package cloud.ptl.rssharvester.purify;

import cloud.ptl.harvest.rss.RssFeedEntry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RssPurificator {
    private final List<Purificator> purificators;

    public RssFeedEntry purify(RssFeedEntry rssFeedEntry) {
        for (Purificator purificator : purificators) {
            log.info("Running {} on rssFeedEntry {}", purificator.getClass().getSimpleName(), rssFeedEntry.getUri());
            rssFeedEntry = purificator.purify(rssFeedEntry);
        }
        return rssFeedEntry;
    }
}
