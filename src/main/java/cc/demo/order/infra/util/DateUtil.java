package cc.demo.order.infra.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static String formatWithSystemTimeZone(long timestamp) {
        Date date = new Date(timestamp);
        format.setTimeZone(TimeZone.getDefault());
        return format.format(date);
    }
}
