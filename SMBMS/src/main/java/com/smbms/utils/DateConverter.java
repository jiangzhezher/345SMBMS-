package com.smbms.utils;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jz
 * @date 2020/4/27 - 18:09
 */
//日期转换器
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
