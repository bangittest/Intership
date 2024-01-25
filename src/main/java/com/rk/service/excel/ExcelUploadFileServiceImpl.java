package com.rk.service.excel;

import com.rk.model.Category;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class ExcelUploadFileServiceImpl implements ExcelUploadService{
    @Override
    public Boolean inValidExcel(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @Override
    public List<Category> getCategory(InputStream inputStream) {
//        INB_ImportData.xlsx
        List<Category>categories=new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet=workbook.getSheet("Sheet1");
            int rowIndex=0;
            for (Row row:sheet) {
                if (rowIndex==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell>cellIterator=row.iterator();
                int cellIndex=0;
                Category category=new Category();
                while (cellIterator.hasNext()){
                    Cell cell=cellIterator.next();
                    switch (cellIndex){
                        case 0->category.setCategoryId((int) cell.getNumericCellValue());
                        case 1->category.setTitle(cell.getStringCellValue());
                        case 2->category.setDescription(cell.getStringCellValue());
                        default -> {

                        }
                    }
                    cellIndex++;
                }
                categories.add(category);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }

        return categories;
    }
}
