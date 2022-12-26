package cloud.ptl.rssharvester;

import cloud.ptl.harvest.rss.RssFeedEntryService;
import cloud.ptl.harvest.source.SourceService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Import({RssFeedEntryService.class, SourceService.class})
@EnableJpaRepositories({"cloud.ptl.harvest.rss", "cloud.ptl.harvest.source"})
@EntityScan({"cloud.ptl.harvest.rss", "cloud.ptl.harvest.source"})
public class InteriaHarvesterApplication {

    public static void main(String[] args) {
        SpringApplication.run(InteriaHarvesterApplication.class, args);
    }
}
