package cloud.ptl.db.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.hibernate.query.sqm.sql.ConversionException;

import java.net.MalformedURLException;
import java.net.URL;

@Converter
public class URL2StringConverter implements AttributeConverter<URL, String> {
    @Override
    public String convertToDatabaseColumn(URL url) {
        return url.toString();
    }

    @Override
    public URL convertToEntityAttribute(String s) {
        try {
            return new URL(s);
        } catch (MalformedURLException e) {
            throw new ConversionException("Cannot convert string " + s + " to URL, because of " + e.getMessage());
        }
    }
}
