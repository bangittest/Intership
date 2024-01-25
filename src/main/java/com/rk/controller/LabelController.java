package com.rk.controller;

import com.rk.model.Category;
import com.rk.service.category.CategoryService;
import com.rk.service.label.LabelService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class LabelController {
    @Autowired
    private LabelService labelService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/export-sss")
    public String exportLabels(@RequestBody List<String> orderNumbers) {
        try {
            labelService.exportLabels(orderNumbers);
            return "Labels exported successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error exporting labels.";
        }
    }


    @PostMapping("/lad-port")
    @Transactional
    public ResponseEntity<?> importOrders(@RequestParam("file") MultipartFile file) {
        try {
            labelService.processOrders(file);
            return ResponseEntity.ok("Import successful");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during import: " + e.getMessage());
        }
    }

//    @Transactional
//    public void processOrders(MultipartFile file) throws IOException {
//        try (InputStream fileInputStream = file.getInputStream()) {
//            Workbook workbook = new XSSFWorkbook(fileInputStream);
//            Sheet sheet = workbook.getSheetAt(0);
//
//            List<String> errorMessages = new ArrayList<>();
//
//            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//                Row row = sheet.getRow(i);
//                try {
//                    createOrder(row);
//                } catch (Exception e) {
//                    errorMessages.add("Dòng " + (i + 1) + ": " + e.getMessage());
//                }
//            }
//            if (!errorMessages.isEmpty()) {
//                exportErrorFile(errorMessages);
//            }
//            workbook.close();
//        }
//    }
//
//    private void createOrder(Row row) {
//        try {
//            Category category = new Category();
//            category.setCategoryId((int) row.getCell(0).getNumericCellValue());
//            category.setDescription(row.getCell(1).getStringCellValue());
//            category.setTitle(row.getCell(2).getStringCellValue());
//            categoryService.save(category);
//        } catch (Exception e) {
//            throw new RuntimeException("Dòng " + (row.getRowNum() + 1) + ": " + e.getMessage());
//        }
//    }
//
//    private void exportErrorFile(List<String> errorMessages) throws IOException {
//        Workbook errorWorkbook = new XSSFWorkbook();
//        Sheet errorSheet = errorWorkbook.createSheet("ErrorSheet");
//        Row headerRow = errorSheet.createRow(0);
//        headerRow.createCell(0).setCellValue("Thông tin lỗi");
//        int rowNum = 1;
//        for (String errorMessage : errorMessages) {
//            Row errorRow = errorSheet.createRow(rowNum++);
//            errorRow.createCell(0).setCellValue(errorMessage);
//        }
//
//        String errorFileName = "INB_ImportError_" + System.currentTimeMillis() + ".xlsx";
//        try (FileOutputStream errorFileOut = new FileOutputStream(errorFileName)) {
//            errorWorkbook.write(errorFileOut);
//        } finally {
//            errorWorkbook.close();
//        }
//    }
}
