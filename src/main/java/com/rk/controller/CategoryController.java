package com.rk.controller;

import com.rk.model.Category;
import com.rk.service.category.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/category")
    public ResponseEntity<?>create(@RequestParam(name = "file")MultipartFile file) throws IllegalAccessException {
        categoryService.saveCategory(file);
        return ResponseEntity.ok(Map.of("Message","category upload"));
    }
    @GetMapping("/category")
    public ResponseEntity<?>findAll(){
        List<Category>list=categoryService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/excel")
    public void generateExel(HttpServletResponse response){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        response.setContentType("application/octet-stream");
        String headerKey="Content-Disposition";
        String headerValue="attachment;filename=Report01_"+currentDateTime+".xlsx";
        response.setHeader(headerKey,headerValue);
        categoryService.generateCategoryExel(response);
    }


//    public void exportOrderStatistics(List<Warehouse> warehouses, Date startDate, Date endDate, String filePath) throws IOException {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("BaoCao");
//
//        // Create header row
//        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("Kho hàng");
//        headerRow.createCell(1).setCellValue("Tổng số đơn hàng");
//
//        // Format date for file name
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        String formattedDate = dateFormat.format(new Date());
//
//        // Iterate through warehouses and populate data
//        int rowNum = 1;
//        for (Warehouse warehouse : warehouses) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(warehouse.getWarehouseName());
//            int orderCount = getOrderCountForWarehouse(warehouse, startDate, endDate);
//            row.createCell(1).setCellValue(orderCount);
//        }
//
//        // Save workbook to file
//        try (FileOutputStream fileOut = new FileOutputStream("Report01_" + formattedDate + ".xlsx")) {
//            workbook.write(fileOut);
//        }
//
//        workbook.close();
//    }
//
//    private int getOrderCountForWarehouse(Warehouse warehouse, Date startDate, Date endDate) {
//        // Implement logic to retrieve order count for the specified warehouse and date range
//        // You may use your repository or service methods here
//        return 0; // Replace with the actual order count
//    }
//
//    public static void main(String[] args) throws IOException {
//        // Example usage
//        OrderStatisticsExporter exporter = new OrderStatisticsExporter();
//        List<Warehouse> warehouses = /* Retrieve your list of warehouses */;
//        Date startDate = /* Set your start date */;
//        Date endDate = /* Set your end date */;
//        exporter.exportOrderStatistics(warehouses, startDate, endDate, "your_file_path");
//    }


//    import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//    public class DeliveryPerformanceExporter {
//
//        public void exportDeliveryPerformance(List<Warehouse> warehouses, Date startDate, Date endDate, String filePath) throws IOException {
//            Workbook workbook = new XSSFWorkbook();
//
//            // Format date for file name
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//            String formattedDate = dateFormat.format(new Date());
//
//            // Iterate through warehouses and create a sheet for each warehouse
//            for (Warehouse warehouse : warehouses) {
//                Sheet sheet = workbook.createSheet(warehouse.getId()); // Use warehouse ID as sheet name
//
//                // Create header row
//                Row headerRow = sheet.createRow(0);
//                headerRow.createCell(0).setCellValue("Ngày");
//                headerRow.createCell(1).setCellValue("Giao thành công");
//                headerRow.createCell(2).setCellValue("Giao thất bại");
//                headerRow.createCell(3).setCellValue("Lý do giao thất bại");
//
//                // Iterate through dates and populate data
//                int rowNum = 1;
//                Date currentDate = startDate;
//                while (!currentDate.after(endDate)) {
//                    Row row = sheet.createRow(rowNum++);
//                    row.createCell(0).setCellValue(currentDate.toString());
//
//                    Map<String, Integer> deliveryStats = getDeliveryStatsForDate(warehouse, currentDate);
//                    row.createCell(1).setCellValue(deliveryStats.get("success"));
//                    row.createCell(2).setCellValue(deliveryStats.get("failure"));
//                    row.createCell(3).setCellValue(deliveryStats.get("reasonCode"));
//
//                    currentDate = incrementDate(currentDate);
//                }
//            }
//
//            // Save workbook to file
//            try (FileOutputStream fileOut = new FileOutputStream("Report02_" + formattedDate + ".xlsx")) {
//                workbook.write(fileOut);
//            }
//
//            workbook.close();
//        }
//
//        private Map<String, Integer> getDeliveryStatsForDate(Warehouse warehouse, Date date) {
//            // Implement logic to retrieve delivery statistics for the specified warehouse and date
//            // You may use your repository or service methods here
//            return null; // Replace with the actual statistics map
//        }
//
//        private Date incrementDate(Date date) {
//            // Implement logic to increment the date by one day
//            return null; // Replace with the actual implementation
//        }
//
//        public static void main(String[] args) throws IOException {
//            // Example usage
//            DeliveryPerformanceExporter exporter = new DeliveryPerformanceExporter();
//            List<Warehouse> warehouses = /* Retrieve your list of warehouses */;
//            Date startDate = /* Set your start date */;
//            Date endDate = /* Set your end date */;
//            exporter.exportDeliveryPerformance(warehouses, startDate, endDate, "your_file_path");
//        }
//    }


}
