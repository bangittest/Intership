package com.rk.service.category;

import com.rk.model.Category;
import com.rk.repository.CategoryRepository;
import com.rk.service.excel.ExcelUploadService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ExcelUploadService excelUploadService;
    @Override
    public void saveCategory(MultipartFile file) throws IllegalAccessException {
        if (excelUploadService.inValidExcel(file)){
            try {
                List<Category>categories=excelUploadService.getCategory(file.getInputStream());
                categoryRepository.saveAll(categories);
            } catch (IOException e) {
                throw new IllegalAccessException("the file is not valid excel upload");
            }
        }
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void generateCategoryExel(HttpServletResponse response) {
        List<Category>list=categoryRepository.findAll();
        Workbook workbook=new XSSFWorkbook();
        Sheet sheet=workbook.createSheet("BaoCao");
        Row row=sheet.createRow(0);
        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("title");
        row.createCell(2).setCellValue("description");

        int dateRowIndex=1;
        for (Category category:list) {
            Row dateRow=sheet.createRow(dateRowIndex);
            dateRow.createCell(0).setCellValue(category.getCategoryId());
            dateRow.createCell(1).setCellValue(category.getTitle());
            dateRow.createCell(2).setCellValue(category.getDescription());
            dateRowIndex++;
        }
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }


}
