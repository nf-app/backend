package cloud.ptl.rssharvester.service;

import cloud.ptl.harvest.rss.RssFeedEntry;
import cloud.ptl.harvest.rss.RssFeedEntryService;
import cloud.ptl.harvest.source.Source;
import cloud.ptl.harvest.source.SourceRepository;
import cloud.ptl.kafka.message.HarvestRequestMessage;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class RssHarvester {

    private final RssFeedEntryService rssFeedEntryService;
    private final SourceRepository sourceService;

    public List<SyndEntry> harvest(HarvestRequestMessage request) throws FeedException, IOException {
        SyndFeed feed = getFeed(request);
        List<SyndEntry> feedEntries = feed.getEntries();
        return filterNewEntries(feedEntries);
    }

    private List<SyndEntry> filterNewEntries(List<SyndEntry> feedEntries) {
        return feedEntries.stream().filter(this::checkIfRssEntryExist).toList();
    }

    private boolean checkIfRssEntryExist(SyndEntry entry) {
        String textUri = entry.getUri();
        if (textUri != null && !textUri.isBlank()) {
            try {
                URI uri = new URI(textUri);
                if (!rssFeedEntryService.exist(uri)) {
                    log.info("There is new feed entry to harvest with uri {}", textUri);
                    return true;
                }
            } catch (URISyntaxException e) {
                log.error("Cannot check if feed entry is correct because this is not uri {}", textUri);
                return false;
            }
        } else {
            log.error("There is no uri in feed entry {}", entry.getLink());
            return false;
        }
        log.info("This entry " + textUri + " was harvested previously, not harvesting");
        return true;
    }

    private SyndFeed getFeed(HarvestRequestMessage message) throws IOException, FeedException {
        URL url = getFeedUrl(message);
        SyndFeedInput input = new SyndFeedInput();
        return input.build(new XmlReader(url));
    }

    private URL getFeedUrl(HarvestRequestMessage message) throws MalformedURLException {
        Source source = sourceService.findByHarvestSourceType(message.harvestSourceType()).orElseThrow();
        String textUrl = source.getUrl();
        return new URL(textUrl);
    }

    public Stream<RssFeedEntry> persistEntries(List<SyndEntry> newFeedEntries, Source source) {
        return newFeedEntries.stream()
                .map(this::convertEntryToEntity)
                .filter(Objects::nonNull)
                .map(e -> addSource(e, source))
                .map(rssFeedEntryService::save);
    }

    private RssFeedEntry addSource(RssFeedEntry rssFeedEntry, Source source) {
        rssFeedEntry.setSource(source);
        return rssFeedEntry;
    }

    private RssFeedEntry convertEntryToEntity(SyndEntry syndEntry) {
        try {
            return RssFeedEntry.builder()
                    .title(syndEntry.getTitle())
                    .description(syndEntry.getDescription().getValue())
                    .uri(new URI(syndEntry.getUri()))
                    .url(new URL(syndEntry.getLink()))
                    .publicationDate(syndEntry.getPublishedDate())
                    .build();
        } catch (URISyntaxException | MalformedURLException e) {
            log.error("Cannot convert SyndFeed to RssFeedEntry because of {}", e.getMessage());
            return null;
        }
    }
}
