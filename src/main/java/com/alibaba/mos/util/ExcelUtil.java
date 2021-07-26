package com.alibaba.mos.util;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author youxuehu
 * @version v1.0
 * @className TextToExcel
 * @date 2021/7/26 9:06 下午
 * @desrription txt 转 excel util
 */
@Slf4j
public class ExcelUtil {

    public static void main(String[] args) {
        String txtFile = "/Users/youxuehu/Desktop/mos-interview/src/main/resources/data/skus.txt";
        String excelFile = "/Users/youxuehu/Desktop/mos-interview/src/main/resources/data/skus.xls";
        txt2Excel(txtFile, "\\|", excelFile, "skus1", 0);
    }

    /**
     * txt文件转excel文件
     * @param txtFilePath text 文件全路径
     * @param spit text文件列分隔符
     * @param excelFilePath excel文件全路径
     * @param shellName excel shell名称
     * @param shellIndex excel shell index
     */
    public static void txt2Excel(String txtFilePath, String spit, String excelFilePath, String shellName, Integer shellIndex) {
        InputStreamReader read = null;
        String line;
        BufferedReader input = null;
        WritableWorkbook wbook = null;
        WritableSheet sheet;
        try {
            if (!FileUtil.exists(txtFilePath)) {
                throw new RuntimeException("text file not exist, path " + txtFilePath);
            }
            wbook = Workbook.createWorkbook(new File(excelFilePath));
            sheet = wbook.createSheet(shellName, shellIndex);
            int m = 1;
            int n = 0;
            Label t;
            read = new InputStreamReader(new FileInputStream(new File(txtFilePath)), "utf-8");
            input = new BufferedReader(read);
            while ((line = input.readLine()) != null) {
                if (StringUtils.isBlank(line)) {
                    continue;
                }
                String[] cells = line.split(spit);
                for (int i = 0; i < cells.length; i++) {
                    t = new Label(n, m, cells[i].trim());
                    sheet.addCell(t);
                    n++;
                }
                n = 0;
                m++;
            }
        } catch (Exception e) {
            log.warn("txt to excel fail", e);
            throw new RuntimeException(e);
        } finally {
            close(wbook, input, read);
        }
    }

    private static void close(WritableWorkbook wbook, BufferedReader input, InputStreamReader read) {
        try {
            if (wbook != null) {
                wbook.write();
                wbook.close();
            }
            if (input != null) {
                input.close();
            }
            if (read != null) {
                read.close();
            }
        } catch (Exception e) {
            log.warn("txt to excel close stream fail", e);
        }
    }
}
