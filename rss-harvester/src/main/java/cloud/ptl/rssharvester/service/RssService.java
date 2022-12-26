package cloud.ptl.rssharvester.service;

import cloud.ptl.harvest.HarvestSourceType;
import cloud.ptl.harvest.rss.RssFeedEntry;
import cloud.ptl.harvest.rss.RssFeedEntryService;
import cloud.ptl.harvest.source.Source;
import cloud.ptl.harvest.source.SourceService;
import cloud.ptl.kafka.message.HarvestRequestMessage;
import cloud.ptl.rssharvester.exception.HarvestingException;
import cloud.ptl.rssharvester.purify.RssPurificator;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.FeedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
@Slf4j
@RequiredArgsConstructor
public class RssService {
    private final RssFeedEntryService rssFeedEntryService;
    private final RssHarvester harvester;
    private final KafkaTemplate<String, Long> outputKafkaTemplate;
    private final RssPurificator purificator;
    private final SourceService sourceService;
    @Value("${kafka.output-topic}")
    private String outputTopic;
    private static Map<HarvestSourceType, String> SOURCE_URLS = Map.of(
            HarvestSourceType.INTERIA, "https://wydarzenia.interia.pl/feed",
            HarvestSourceType.BANKIER, "https://www.bankier.pl/rss/wiadomosci.xml"
    );

    public Stream<RssFeedEntry> checkForNewEntries(HarvestRequestMessage message) throws HarvestingException {
        try {
            log.info("Checking if feed {} has new entries", message.harvestSourceType().name());
            Source source = findSource(message);
            List<SyndEntry> newFeedEntries = harvester.harvest(message);
            return harvester.persistEntries(newFeedEntries, source);
        } catch (IOException | FeedException e) {
            log.error("Cannot read feed {}, because of {}", message.harvestSourceType(), e.getMessage());
            throw new HarvestingException("Feed reading exception " + e.getMessage());
        }
    }

    private Source findSource(HarvestRequestMessage message) {
        return sourceService.findByHarvestSourceType(message.harvestSourceType());
    }

    public RssFeedEntry purify(RssFeedEntry rssFeedEntry) {
        purificator.purify(rssFeedEntry);
        rssFeedEntry.setReady(true);
        log.info("Saving purified entry {}", rssFeedEntry.getUri());
        return rssFeedEntryService.save(rssFeedEntry);
    }

    public void send(RssFeedEntry rssFeedEntry) {
        outputKafkaTemplate.send(outputTopic, rssFeedEntry.getId());
    }
}
