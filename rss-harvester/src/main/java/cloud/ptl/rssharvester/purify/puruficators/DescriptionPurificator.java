package cloud.ptl.rssharvester.purify.puruficators;

import cloud.ptl.harvest.rss.RssFeedEntry;
import cloud.ptl.rssharvester.purify.Purificator;
import org.jsoup.Jsoup;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(100)
public class DescriptionPurificator implements Purificator {
    @Override
    public RssFeedEntry purify(RssFeedEntry rssFeedEntry) {
        String cleanDescription = Jsoup.parse(rssFeedEntry.getDescription()).text();
        rssFeedEntry.setDescription(cleanDescription);
        return rssFeedEntry;
    }
}
