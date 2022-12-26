package cloud.ptl.harvest.source;

import cloud.ptl.harvest.HarvestSourceType;
import cloud.ptl.harvest.rss.RssFeedEntry;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.util.List;

@Entity
@Data
public class Source {
    @Id
    @Generated
    private Long id;
    private String url;
    @Enumerated(EnumType.STRING)
    private HarvestSourceType harvestSourceType;
    @OneToMany(mappedBy = "source")
    private List<RssFeedEntry> entries;
}
