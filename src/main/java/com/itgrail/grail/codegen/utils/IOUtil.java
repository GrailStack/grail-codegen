package com.itgrail.grail.codegen.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author xujin
 * created at 2019/5/27 1:11
 **/
@Slf4j
public class IOUtil {

    public static boolean writeBytes(OutputStream outputStream, byte[] bytes) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        try {
            ByteStreams.copy(inputStream, outputStream);
            outputStream.flush();
            return true;
        } catch (Exception ex) {
        } finally {
            CommonUtil.closeClosable(inputStream);
        }
        return false;
    }

    public static boolean writeAsJson(OutputStream outputStream, Object object) {
        byte[] bytes = JSONObject.toJSONBytes(object,
                SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat);
        return writeBytes(outputStream, bytes);
    }

    public static boolean writeAsJson(PrintWriter printWriter, Object object) {
        String str = JSONObject.toJSONString(object,
                SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat);
        printWriter.append(str);
        return true;
    }

}
