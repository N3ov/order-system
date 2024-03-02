package cc.demo.order.infra.util;


import java.util.UUID;

public class UidUtil {
    public static String generateUid(int length) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }
}
