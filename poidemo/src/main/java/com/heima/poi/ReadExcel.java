package com.heima.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class ReadExcel {
    public static void main(String[] args) throws IOException {
        //创建Excel文件
        Workbook workbook = new XSSFWorkbook("d:\\read.xlsx");
        //获取工作表
        Sheet sheet = workbook.getSheetAt(0);
        //遍历行，从下表为0开始
        for (Row row:sheet){
            //遍历获取单元格
            for (Cell cell:row){
                //单元格类型
                int cellType = cell.getCellType();
                if (Cell.CELL_TYPE_NUMERIC==cellType){
                    //数值单元格
                    System.out.println(cell.getNumericCellValue()+",");
                }else {
                    System.out.println(cell.getStringCellValue()+",");
                }

            }
            System.out.println();
        }
        //关闭Excel文件
        workbook.close();
    }

}
