package com.sgugo.sbtest.poi;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;

public class POITest {

    public void test1() throws Exception{
        //1. 创建一个Excel文件（工作簿）
        XSSFWorkbook excel = new XSSFWorkbook();

        //2. 创建一个sheet（工作表）
        XSSFSheet sheet = excel.createSheet("第一个sheet");

        //3. 创建行，参数表示第几行，从0开始，0表示第一行
        XSSFRow row1 = sheet.createRow(0);
        XSSFRow row2 = sheet.createRow(1);
        XSSFRow row3 = sheet.createRow(2);

        //4. 创建单元格，参数表示第几个单元格，从0开始，0表示第一个单元格
        XSSFCell row1cell1 = row1.createCell(0);
        XSSFCell row1cell2 = row1.createCell(1);
        XSSFCell row1cell3 = row1.createCell(2);
        XSSFCell row2cell1 = row1.createCell(0);
        XSSFCell row2cell2 = row1.createCell(1);
        XSSFCell row2cell3 = row1.createCell(2);

        //5. 设置单元格的值
        row1cell1.setCellValue("姓名");
        row1cell2.setCellValue("年龄");
        row1cell3.setCellValue("注册日期");
        row2cell1.setCellValue("Victor");
        row2cell2.setCellValue(20);
        row2cell3.setCellValue(LocalDateTime.now());

        //6.通过输出流将数据导出到Excel文件
        FileOutputStream out = new FileOutputStream(new File("files/out.xlsx"));
        excel.write(out);

        //7. 关闭资源
        out.flush();
        out.close();
        excel.close();

    }
}
