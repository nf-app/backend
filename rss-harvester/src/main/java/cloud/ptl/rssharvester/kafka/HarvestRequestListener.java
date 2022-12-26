package cloud.ptl.rssharvester.kafka;

import cloud.ptl.kafka.message.HarvestRequestMessage;
import cloud.ptl.rssharvester.exception.HarvestingException;
import cloud.ptl.rssharvester.service.RssService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HarvestRequestListener {
    private final RssService rssService;

    @KafkaListener(topics = "${kafka.order-topic}", groupId = "${kafka.group-id}")
    public void listenHarvestRequest(@Payload HarvestRequestMessage message) {
        try {
            log.info("Got harvest order for source {}", message.harvestSourceType());
            rssService.checkForNewEntries(message)
                    .map(rssService::purify)
                    .forEach(rssService::send);
        } catch (HarvestingException e) {
            log.error(e.getMessage());
        }
    }
}
