package com.alibaba.mos.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * @author youxuehu
 * @version v1.0
 * @className FileUtil
 * @date 2021/7/26 9:18 下午
 * @desrription 这是类的描述信息
 */
public class FileUtil {

    public static boolean exists(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    public static String getLine(String file) {
        try {
            LineIterator lineIterator = FileUtils.lineIterator(new File(file), "UTF-8");
            while (lineIterator.hasNext()) {
                String line = lineIterator.nextLine();

            }
        } catch (Exception e) {

        }
        return null;
    }
}
