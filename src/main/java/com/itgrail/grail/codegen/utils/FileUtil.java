package com.itgrail.grail.codegen.utils;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author xujin
 * created at 2019/5/24 17:40
 **/
@Slf4j
public class FileUtil {

    public static void writeToFile(String content, File toFile) {
        try {
            if (!toFile.exists()) {
                toFile.createNewFile();
            }
            Files.asCharSink(toFile, StandardCharsets.UTF_8).write(content);
        } catch (Exception ex) {
            log.error(String.format("写入文件失败，content=%s, toFile=%s", content, toFile), ex);
        }
    }

    public static void copyFile(File fromFile, File toFile) {
        try {
            if (!toFile.exists()) {
                toFile.createNewFile();
            }
            Files.copy(fromFile, toFile);
        } catch (Exception ex) {
            log.error(String.format("复制文件失败，fromFile=%s, toFile=%s", fromFile, toFile), ex);
        }
    }

    public static void copyResourceToFile(Resource resource, File toFile) throws IOException {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            if (!toFile.exists()) {
                toFile.createNewFile();
            }
            inputStream = resource.getInputStream();
            fileOutputStream = new FileOutputStream(toFile);
            ByteStreams.copy(inputStream, fileOutputStream);
            fileOutputStream.flush();
        } catch (Exception ex) {
            log.error(String.format("复制文件失败，fromFile=%s, toFile=%s", ResourceUtil.getResourceName(resource), toFile), ex);
        } finally {
            CommonUtil.closeClosable(inputStream);
            CommonUtil.closeClosable(fileOutputStream);
        }
    }

    public static void copyDir(File fromDir, File toDir) {
        if (!toDir.exists()) {
            toDir.mkdirs();
        }
        for (File file : fromDir.listFiles()) {
            if (file.isDirectory()) {
                copyDir(file, new File(toDir, file.getName()));
            }
        }
    }

    public static void buildOutputPath(String outPath) {
        File dir = new File(outPath);
        if (!dir.exists()) {
            // 可以多级的生成目录
            dir.mkdirs();
        }
    }

    public static File buildOutputFile(String outPath, String fileName) {
        File file = new File(outPath, fileName);
        File dir = new File(outPath);
        if (!dir.exists()) {
            // 可以多级的生成目录
            dir.mkdirs();
        }
        return file;
    }

    public static byte[] readFileToBytes(File file) {
        try {
            return Files.toByteArray(file);
        } catch (Exception ex) {
            log.error(String.format("读取文件到byte[]失败，file=%s", file), ex);
        }
        return null;
    }

    public static boolean removeDir(File dir) {
        try {
            if (!dir.exists()) {
                return true;
            }
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    removeDir(file);
                } else {
                    file.delete();
                }
            }
            dir.delete();
            return true;
        } catch (Exception ex) {
            log.error(String.format("删除目录失败，dir=%s", dir), ex);
        }
        return false;
    }

}
