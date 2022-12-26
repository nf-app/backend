package cloud.ptl.harvest.rss;

import cloud.ptl.db.converter.URI2StringConverter;
import cloud.ptl.db.converter.URL2StringConverter;
import cloud.ptl.harvest.source.Source;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.net.URI;
import java.net.URL;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RssFeedEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "text")
    private String description;
    @Column(name = "uri")
    @Convert(converter = URI2StringConverter.class)
    private URI uri;
    @Convert(converter = URL2StringConverter.class)
    private URL url;
    @ManyToOne
    private Source source;
    @ColumnDefault("false")
    private boolean isReady;
    private Date publicationDate;
}
