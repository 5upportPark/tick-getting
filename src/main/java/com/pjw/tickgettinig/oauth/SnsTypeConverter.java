package com.pjw.tickgettinig.oauth;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SnsTypeConverter implements AttributeConverter<SnsType, String> {

  @Override
  public String convertToDatabaseColumn(SnsType snsType) {
    if (snsType == null) {
      return null;
    }
    return snsType.name();
  }

  @Override
  public SnsType convertToEntityAttribute(String str) {
    if (StringUtils.isBlank(str)) {
      return null;
    }
    return SnsType.of(str);
  }
}
