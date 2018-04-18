package org.wxy.express.util;


import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class POIUtils {
	private static final Logger logger = LoggerFactory.getLogger(POIUtils.class);
    /**
     * 将数据写入EXCEL
     *
     * @param <T>
     * @param fileName  EXCEL文件名称
     * @param sheetName EXCEL页名
     * @param data      待写入数据
     * @throws Exception
     */
    public static <T> void writeExcel(final String fileName, final String sheetName, final List<T[]> data) {
        OutputStream out = null;
        try {
            // 创建 EXCEL 工作簿
            XSSFWorkbook workBook = new XSSFWorkbook();
            // 创建 EXCEL Sheet 页
            XSSFSheet sheet = workBook.createSheet(sheetName);
            // 声明 EXCEL 行
            XSSFRow row;
            // 声明 EXCEL 单元格
            XSSFCell cell;

            // 迭代设置EXCEL每行数据
            int rowNo = 0; // 行号
            for (T[] objs : data) {
                row = sheet.createRow(rowNo++);
                // 迭代设置EXCEL当前行每个单元格数据
                int cellNo = 0; // 列号
                for (T obj : objs) {
                    cell = row.createCell(cellNo++);
                    cell.setCellValue(String.valueOf(obj));
                }
            }

            // 创建文件所在目录
            String filePath = FilenameUtils.getFullPath(fileName);
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            // 设置文件输出流，写入EXCEL数据
            String excelName = StringUtils.join(filePath, FilenameUtils.getBaseName(fileName), ".xlsx");

            out = new FileOutputStream(excelName);
            // 写入文件流
            workBook.write(out);
            workBook.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                logger.error("关闭文件输出流出错", e);
            }
        }
    }

    /**
     * 读取EXCEL中指定下标页的数据
     *
     * @param fileName
     * @param sheetIndex
     * @return
     */
    public static List<String[]> readExcel(final String fileName, final Integer sheetIndex) {
        List<String[]> list = null;
        InputStream in = null;
        try {
            // 获取文件输入流
            String excelName = StringUtils.join(FilenameUtils.getFullPath(fileName), FilenameUtils.getBaseName(fileName), ".xlsx");
            in = new FileInputStream(excelName);
            // 创建 EXCEL 工作簿
            XSSFWorkbook workBook = new XSSFWorkbook(in);
            // 获取 EXCEL Sheet 页
            XSSFSheet sheet = workBook.getSheetAt(sheetIndex);

            list = new ArrayList<String[]>();
            String[] strArr = null;
            // 遍历每行记录
            for (Row row : sheet) {
                strArr = new String[row.getPhysicalNumberOfCells()];
                // 遍历每单元格记录
                for (Cell cell : row) {
                    // 根据单元格的类型获取不同数据类型的值
                    CellType cellType = cell.getCellTypeEnum();
                    if (CellType.NUMERIC.equals(cellType))
                        strArr[cell.getColumnIndex()] = String.valueOf(cell.getNumericCellValue());
                    else if (CellType.STRING.equals(cellType))
                        strArr[cell.getColumnIndex()] = cell.getStringCellValue();
                }
                // 将数据放入集合
                list.add(strArr);
            }

            workBook.close();
        } catch (Exception e) {
            logger.error("读取EXCEL中指定下标页的数据出错", e);
        } finally {
            try {
                if (null != in)
                    in.close();
            } catch (IOException e) {
                logger.error("关闭文件输入流出错", e);
            }
        }
        return list;
    }
}