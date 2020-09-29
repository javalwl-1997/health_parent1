package com.heima.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteExcel {
    public static void main(String[] args) throws IOException {
        //创建Excel工作簿
        Workbook workbook = new XSSFWorkbook();
        //创建工作表
        Sheet sheet = workbook.createSheet("测试写一个Excel");
        //在工作表中创建行
        Row row = sheet.createRow(0);
        //使用行创建单元格,单元格下标为0
        Cell cell = row.createCell(0);
        //给单元格赋值
        //表头
        cell.setCellValue("姓名");
        row.createCell(1).setCellValue("年龄");
        row.createCell(2).setCellValue("地址");



        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("小李");
        row1.createCell(1).setCellValue(20);
        row1.createCell(2).setCellValue("深圳");

        Row row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("小张");
        row2.createCell(1).setCellValue(30);
        row2.createCell(2).setCellValue("广州");

        //保存工作簿，持久化本地硬盘里
        workbook.write(new FileOutputStream(new File("d:\\createExcel.xlsx")));
        //关闭工作簿
        workbook.close();
    }
}
