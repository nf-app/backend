package cloud.ptl.rssharvester.purify;

import cloud.ptl.harvest.rss.RssFeedEntry;

public interface Purificator {
    RssFeedEntry purify(RssFeedEntry rssFeedEntry);
}
