package com.itgrail.grail.codegen.utils;

import java.io.Closeable;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author xujin
 * created at 2019/5/24 15:45
 **/
public class CommonUtil {

    public static void closeClosable(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception ex) {
        }
    }

    public static UUID genUUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong());
    }

}
