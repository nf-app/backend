package cloud.ptl.kafka.message;

import cloud.ptl.harvest.HarvestSourceType;

public record HarvestRequestMessage(
        HarvestSourceType harvestSourceType
) {

}
