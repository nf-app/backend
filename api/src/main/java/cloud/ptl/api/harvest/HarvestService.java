package cloud.ptl.api.harvest;

import cloud.ptl.harvest.HarvestSourceType;
import cloud.ptl.kafka.message.HarvestRequestMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HarvestService {
    @Value("${kafka.order-topic}")
    private String orderTopic;
    private final KafkaTemplate<String, HarvestRequestMessage> kafkaTemplate;

    public void startHarvest(HarvestSourceType harvestSourceType) {
        kafkaTemplate.send(orderTopic, createRequestMessage(harvestSourceType));
    }

    private HarvestRequestMessage createRequestMessage(HarvestSourceType harvestSourceType) {
        return new HarvestRequestMessage(harvestSourceType);
    }
}
