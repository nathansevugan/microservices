package com.sabre.as.flight.schedule.repositories.converter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * Created by sg0501095 on 5/9/18.
 */
@ReadingConverter
public enum StringToDateTimeReadingConverter implements GenericConverter {
    INSTANCE;

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, DateTime.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }

        DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
        return parser.parseDateTime(String.valueOf(source));
    }
}

