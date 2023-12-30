package com.itgrail.grail.codegen.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ZipUtil {

    static String FILE_ZIP = "zip";
    static String ENCODING_UTF_8 = "utf-8";

    public static File[] unzip(String zipFilePath, String password) throws ZipException {
        File zipFile = new File(zipFilePath);
        File parentDir = zipFile.getParentFile();

        return unzip(zipFile, parentDir.getAbsolutePath(), password);
    }

    public static File[] unzip(String zipFilePath, String destPath, String password) throws ZipException {
        File zipFile = new File(zipFilePath);

        return unzip(zipFile, destPath, password);
    }

    @SuppressWarnings("unchecked")
    public static File[] unzip(File zipFile, String destPath, String password) throws ZipException {
        ZipFile zFile = new ZipFile(zipFile);
        zFile.setFileNameCharset(ENCODING_UTF_8);
        if (!zFile.isValidZipFile()) {
            throw new ZipException("Invalid zip files, it may be damaged");
        }

        File destDir = new File(destPath);
        if (destDir.isDirectory() && !destDir.exists()) {
            destDir.mkdir();
        }

        if (zFile.isEncrypted()) {
            if (StringUtils.isEmpty(password)) {
                throw new ZipException("Password can't be empty with encryption mode");
            }
            zFile.setPassword(password.toCharArray());
        }
        zFile.extractAll(destPath);

        List<FileHeader> headerList = zFile.getFileHeaders();
        List<File> extractedFileList = new ArrayList<File>();
        for (FileHeader fileHeader : headerList) {
            if (!fileHeader.isDirectory()) {
                extractedFileList.add(new File(destDir, fileHeader.getFileName()));
            }
        }

        File[] extractedFiles = new File[extractedFileList.size()];
        extractedFileList.toArray(extractedFiles);

        return extractedFiles;
    }

    public static String zip(String srcPath) throws ZipException {
        return zip(srcPath, null, null);
    }

    public static File zipToFile(String srcPath) throws ZipException {
        return new File(zip(srcPath));
    }

    public static String zip(String srcPath, String password) throws ZipException {
        return zip(srcPath, null, password);
    }

    public static String zip(String srcPath, String destPath, String password) throws ZipException {
        return zip(srcPath, destPath, true, password);
    }

    public static String zip(String srcPath, String destPath, boolean isCreateDir, String password) throws ZipException {
        File srcFile = new File(srcPath);
        destPath = buildDestinationZipFilePath(srcFile, destPath);
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // 压缩方式
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 压缩级别
        if (StringUtils.isNotEmpty(password)) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
            parameters.setPassword(password.toCharArray());
        }

        ZipFile zipFile = new ZipFile(destPath);
        if (srcFile.isDirectory()) {
            // 如果不创建目录的话，将直接把给定目录下的文件压缩到压缩文件，即没有目录结构
            if (!isCreateDir) {
                File[] subFiles = srcFile.listFiles();
                ArrayList<File> subFileList = new ArrayList<File>();
                Collections.addAll(subFileList, subFiles);
                zipFile.addFiles(subFileList, parameters);

                return destPath;
            }
            zipFile.addFolder(srcFile, parameters);
        } else {
            zipFile.addFile(srcFile, parameters);
        }

        return destPath;
    }

    private static String buildDestinationZipFilePath(File srcFile, String destPath) {
        if (StringUtils.isEmpty(destPath)) {
            if (srcFile.isDirectory()) {
                destPath = srcFile.getParent() + File.separator + srcFile.getName() + "." + FILE_ZIP;
            } else {
                String fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
                destPath = srcFile.getParent() + File.separator + fileName + "." + FILE_ZIP;
            }
        } else {
            createDestDirectoryIfNecessary(destPath); // 在指定路径不存在的情况下将其创建出来
            if (destPath.endsWith(File.separator)) {
                String fileName = "";
                if (srcFile.isDirectory()) {
                    fileName = srcFile.getName();
                } else {
                    fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
                }
                destPath += fileName + "." + FILE_ZIP;
            }
        }

        return destPath;
    }

    private static void createDestDirectoryIfNecessary(String destPath) {
        File destDir = null;
        if (destPath.endsWith(File.separator)) {
            destDir = new File(destPath);
        } else {
            destDir = new File(destPath.substring(0, destPath.lastIndexOf(File.separator)));
        }

        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }
}