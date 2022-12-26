package cloud.ptl.db.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.hibernate.query.sqm.sql.ConversionException;

import java.net.URI;
import java.net.URISyntaxException;

@Converter
public class URI2StringConverter implements AttributeConverter<URI, String> {
    @Override
    public String convertToDatabaseColumn(URI uri) {
        return uri.toString();
    }

    @Override
    public URI convertToEntityAttribute(String s) {
        try {
            return new URI(s);
        } catch (URISyntaxException e) {
            throw new ConversionException("Cannot convert string " + s + " to URI, because of " + e.getMessage());
        }
    }
}
