package com.rk.service.label;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.rk.exception.ImportDataException;
import com.rk.model.Category;
import com.rk.model.Orders;
import com.rk.service.category.CategoryService;
import com.rk.service.orders.OrderService;
import com.rk.service.orders.OrderServiceImpl;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service

public class LabelServiceImpl implements LabelService{
    private final Logger log = LoggerFactory.getLogger(LabelServiceImpl.class);
    @Autowired
    OrderService orderService;
    @Autowired
    private CategoryService categoryService;
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void exportLabels(List<String> orderNumbers) throws IOException {
//        Workbook workbook = new XSSFWorkbook();
//        for (String orderNumber : orderNumbers) {
//
//            Sheet sheet = workbook.createSheet(orderNumber);
//            Orders orders=orderService.findByID(orderNumber);
//            createLabelSheet(sheet, orders);
//        }
//
//        String fileName = "Labels_" + System.currentTimeMillis() + ".xlsx";
//        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
//            workbook.write(fileOut);
//        }
//
//        workbook.close();
//    }
//    private void createLabelSheet(Sheet sheet, Orders order) {
//        Workbook workbook = new XSSFWorkbook();
////         sheet = workbook.createSheet("DeliveryReceipt");
//
//        String deliveryDate = "20/03/2023";
//        String deliveryTime = "19:10";
//        String receiverSignature = "Chữ ký người nhận";
//        String confirmationTime = "Ngày đặt hàng";
//
//        // Create rows and cells
//        int rowNum = 0;
//        Row row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("CÔNG TY VẬN CHUYỂN");
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("Mã đơn hàng:");
//        row.createCell(1).setCellValue(order.getId());
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("Từ:");
//        row.createCell(1).setCellValue("Đến:");
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue(order.getSupplier().getSupplierName());
//        row.createCell(1).setCellValue(order.getReceiver().getReceiverName());
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue(order.getSupplier().getAddress());
//        row.createCell(1).setCellValue(order.getReceiver().getAddress());
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("SĐT: " + order.getSupplier().getPhoneNumber());
//        row.createCell(1).setCellValue("SĐT: " + order.getReceiver().getPhoneNumber());
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("Kho " + order.getWarehouse().getWarehouseName());
//        row.createCell(1).setCellValue(order.getWarehouse().getId());
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("Đơn giao hàng");
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("Ngày đặt hàng");
//        row.createCell(1).setCellValue(deliveryDate);
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue(receiverSignature);
//        row.createCell(1).setCellValue(confirmationTime);
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("Xác nhận hàng nguyên vẹn, không móp/méo, bể/vỡ");
//        row.createCell(1).setCellValue(deliveryTime);
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("Chỉ dẫn giao hàng: Không đồng kiểm; Chuyển hoàn sau 3 lần phát");
//
//        // Auto size columns
//        for (int i = 0; i < 2; i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        // Write the workbook to a file
//        try (FileOutputStream fileOut = new FileOutputStream("DeliveryReceipt.xlsx")) {
//            workbook.write(fileOut);
//            workbook.close();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exportLabels(List<String> orderNumbers) {
        Workbook workbook = new XSSFWorkbook();

        try {
            for (String orderNumber : orderNumbers) {
                try {
                    Sheet sheet = workbook.createSheet(orderNumber);
                    Orders orders = orderService.findByID(orderNumber);
                    createLabelSheet(workbook, sheet, orders);
                } catch (Exception e) {
                    log.error("Error processing orderNumber: " + orderNumber, e);
                }
            }
            // Write the workbook to a file
            String fileName = "Labels_" + System.currentTimeMillis() + ".xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                workbook.write(fileOut);
            } catch (IOException e) {
                throw new RuntimeException("Error writing workbook to file", e);
            } finally {
                try {
                    workbook.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error closing workbook", e);
                }
            }
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                throw new RuntimeException("Error closing workbook", e);
            }
        }
    }



    private static void createLabelSheet(Workbook workbook, Sheet sheet, Orders order) {
        // Set column widths
        sheet.setColumnWidth(0, 7000);
        sheet.setColumnWidth(1, 7000);
        sheet.setColumnWidth(2, 5000);

        // Add QR code
        createQRCode(sheet, order.getId(), 0, 2);

        String deliveryDate = "20/03/2023";
        String deliveryTime = "19:10";
        String receiverSignature = "Chữ ký người nhận";
        String confirmationTime = "Ngày đặt hàng";

        // Create rows and cells
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        Cell cell = row.createCell(0);
        cell.setCellValue("CÔNG TY VẬN CHUYỂN");
        cell.setCellStyle(createHeaderStyle(workbook));

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Mã đơn hàng:");
        row.createCell(1).setCellValue(order.getId());

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Từ:");
        row.createCell(1).setCellValue("Đến:");

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(order.getSupplier().getSupplierName());
        row.createCell(1).setCellValue(order.getReceiver().getReceiverName());

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(order.getSupplier().getAddress());
        row.createCell(1).setCellValue(order.getReceiver().getAddress());

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("SĐT: " + order.getSupplier().getPhoneNumber());
        row.createCell(1).setCellValue("SĐT: " + order.getReceiver().getPhoneNumber());

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Kho " + order.getWarehouse().getWarehouseName());
        row.createCell(1).setCellValue(order.getWarehouse().getId());

        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue("Đơn giao hàng");
        cell.setCellStyle(createHeaderStyle(workbook));

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Ngày đặt hàng");
        row.createCell(1).setCellValue(deliveryDate);

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(receiverSignature);
        row.createCell(1).setCellValue(confirmationTime);

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Xác nhận hàng nguyên vẹn, không móp/méo, bể/vỡ");
        row.createCell(1).setCellValue(deliveryTime);

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Chỉ dẫn giao hàng: Không đồng kiểm; Chuyển hoàn sau 3 lần phát");
        cell = row.createCell(1);
        cell.setCellValue("Chỉ dẫn giao hàng: Không đồng kiểm; Chuyển hoàn sau 3 lần phát");
        cell.setCellStyle(createBorderedStyle(workbook));

        // Auto size columns
        for (int i = 0; i < 2; i++) {
            sheet.autoSizeColumn(i);
        }

        // Apply bordered style to all cells in the sheet
        applyBorderedStyle(sheet);

    }

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private static CellStyle createBorderedStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private static void applyBorderedStyle(Sheet sheet) {
        for (int r = 0; r < sheet.getPhysicalNumberOfRows(); r++) {
            Row row = sheet.getRow(r);
            if (row != null) {
                for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
                    Cell cell = row.getCell(c);
                    if (cell != null) {
                        cell.setCellStyle(createBorderedStyle(sheet.getWorkbook()));
                    }
                }
            }
        }
    }

    private static void createQRCode(Sheet sheet, String data, int rowNum, int colNum) {
        String qrCodeImagePath = "qrcode.png";
        createQRCodeImage(data, qrCodeImagePath);
        insertQRCodeImage(sheet, qrCodeImagePath, rowNum, colNum);
    }

    private static void createQRCodeImage(String data, String filePath) {
        ByteArrayOutputStream stream = QRCode.from(data).to(ImageType.PNG).stream();
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            fileOutputStream.write(stream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Error writing QR code image", e);
        }
    }

    private static void insertQRCodeImage(Sheet sheet, String filePath, int startRow, int startCol) {
        try {
            InputStream inputStream = new FileInputStream(filePath);
            byte[] bytes = inputStream.readAllBytes();

            int pictureIdx = sheet.getWorkbook().addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            CreationHelper helper = sheet.getWorkbook().getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();

            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(startCol);
            anchor.setRow1(startRow);

            Picture pict = drawing.createPicture(anchor, pictureIdx);
            pict.resize();
        } catch (IOException e) {
            throw new RuntimeException("Error inserting QR code image into Excel sheet", e);
        }
    }




//
//    public void exportLabels(List<String> orderNumbers) {
//        Workbook workbook = new XSSFWorkbook();
//
//        try {
//            for (String orderNumber : orderNumbers) {
//                try {
//                    Sheet sheet = workbook.createSheet(orderNumber);
//                    Orders orders = orderService.findByID(orderNumber);
//                    createLabelSheet(workbook, sheet, orders);
//                } catch (Exception e) {
//                    log.error("Error processing orderNumber: " + orderNumber, e);
//                }
//            }
//
//            // Xuất sang Excel
//            String excelFileName = "Labels_" + System.currentTimeMillis() + ".xlsx";
//            try (FileOutputStream excelFileOut = new FileOutputStream(excelFileName)) {
//                workbook.write(excelFileOut);
//            } catch (IOException e) {
//                throw new RuntimeException("Error writing workbook to Excel file", e);
//            }
//
//            // Xuất sang PDF
//            String pdfFileName = convertExcelToPDF(excelFileName);
//            System.out.println("PDF File: " + pdfFileName);
//        } finally {
//            try {
//                workbook.close();
//            } catch (IOException e) {
//                throw new RuntimeException("Error closing workbook", e);
//            }
//        }
//    }

//    private void createLabelSheet(Workbook workbook, Sheet sheet, Orders order) {
//        // Set column widths
//        sheet.setColumnWidth(0, 7000);
//        sheet.setColumnWidth(1, 7000);
//        sheet.setColumnWidth(2, 5000);
//
//        // Add QR code
//        createQRCode(sheet, order.getId(), 0, 2);
//
//        String deliveryDate = "20/03/2023";
//        String deliveryTime = "19:10";
//        String receiverSignature = "Chữ ký người nhận";
//        String confirmationTime = "Ngày đặt hàng";
//
//        // Create rows and cells
//        int rowNum = 0;
//        Row row = sheet.createRow(rowNum++);
//        Cell cell = row.createCell(0);
//        cell.setCellValue("CÔNG TY VẬN CHUYỂN");
//        cell.setCellStyle(createHeaderStyle(workbook));
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("Mã đơn hàng:");
//        row.createCell(1).setCellValue(order.getId());
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("Từ:");
//        row.createCell(1).setCellValue("Đến:");
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue(order.getSupplier().getSupplierName());
//        row.createCell(1).setCellValue(order.getReceiver().getReceiverName());
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue(order.getSupplier().getAddress());
//        row.createCell(1).setCellValue(order.getReceiver().getAddress());
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("SĐT: " + order.getSupplier().getPhoneNumber());
//        row.createCell(1).setCellValue("SĐT: " + order.getReceiver().getPhoneNumber());
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("Kho " + order.getWarehouse().getWarehouseName());
//        row.createCell(1).setCellValue(order.getWarehouse().getId());
//
//        row = sheet.createRow(rowNum++);
//        cell = row.createCell(0);
//        cell.setCellValue("Đơn giao hàng");
//        cell.setCellStyle(createHeaderStyle(workbook));
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("Ngày đặt hàng");
//        row.createCell(1).setCellValue(deliveryDate);
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue(receiverSignature);
//        row.createCell(1).setCellValue(confirmationTime);
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("Xác nhận hàng nguyên vẹn, không móp/méo, bể/vỡ");
//        row.createCell(1).setCellValue(deliveryTime);
//
//        row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue("Chỉ dẫn giao hàng: Không đồng kiểm; Chuyển hoàn sau 3 lần phát");
//        cell = row.createCell(1);
//        cell.setCellValue("Chỉ dẫn giao hàng: Không đồng kiểm; Chuyển hoàn sau 3 lần phát");
//        cell.setCellStyle(createBorderedStyle(workbook));
//
//        // Auto size columns
//        for (int i = 0; i < 2; i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        // Apply bordered style to all cells in the sheet
//        applyBorderedStyle(sheet);
//
//        // Thêm mã QR vào Excel
//        int qrCodeCol = 2; // Cột để chèn mã QR
//        int qrCodeRow = 3; // Dòng để chèn mã QR
//        createQRCode(sheet, order.getId(), qrCodeRow, qrCodeCol);
//    }
//
//    private void createQRCode(Sheet sheet, String data, int rowNum, int colNum) {
//        String qrCodeImagePath = "qrcode.png";
//        createQRCodeImage(data, qrCodeImagePath);
//        insertQRCodeImage(sheet, qrCodeImagePath, rowNum, colNum);
//    }
//
//    private void createQRCodeImage(String data, String filePath) {
//        ByteArrayOutputStream stream = QRCode.from(data).to(ImageType.PNG).stream();
//        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
//            fileOutputStream.write(stream.toByteArray());
//        } catch (IOException e) {
//            throw new RuntimeException("Error writing QR code image", e);
//        }
//    }
//
//    private void insertQRCodeImage(Sheet sheet, String filePath, int startRow, int startCol) {
//        try {
//            InputStream inputStream = new FileInputStream(filePath);
//            byte[] bytes = IOUtils.toByteArray(inputStream);
//
//            int pictureIdx = sheet.getWorkbook().addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
//            inputStream.close();
//
//            CreationHelper helper = sheet.getWorkbook().getCreationHelper();
//            Drawing drawing = sheet.createDrawingPatriarch();
//
//            ClientAnchor anchor = helper.createClientAnchor();
//            anchor.setCol1(startCol);
//            anchor.setRow1(startRow);
//
//            Picture pict = drawing.createPicture(anchor, pictureIdx);
//            pict.resize();
//        } catch (IOException e) {
//            throw new RuntimeException("Error inserting QR code image into Excel sheet", e);
//        }
//    }
//
//    private String convertExcelToPDF(String excelFileName) {
//        try {
//            String pdfFileName = "Labels_" + System.currentTimeMillis() + ".pdf";
//            Workbook workbook = WorkbookFactory.create(new File(excelFileName));
//
//            Document document = new Document(PageSize.A4.rotate());
//            PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));
//            document.open();
//
//            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
//                Sheet sheet = workbook.getSheetAt(i);
//                BufferedImage image = ImageIO.read((File) sheet);
//
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                ImageIO.write(image, "png", baos);
//                byte[] bytes = baos.toByteArray();
//
//                Image img = Image.getInstance(bytes);
//                document.add(img);
//                document.newPage();
//            }
//
//            document.close();
//            return pdfFileName;
//        } catch (IOException | DocumentException e) {
//            throw new RuntimeException("Error converting Excel to PDF", e);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    private CellStyle createHeaderStyle(Workbook workbook) {
//        CellStyle style = workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setBold(true);
//        style.setFont(font);
//        return style;
//    }

//    private CellStyle createBorderedStyle(Workbook workbook) {
//        CellStyle style = workbook.createCellStyle();
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBorderTop(BorderStyle.THIN);
//        style.setBorderLeft(BorderStyle.THIN);
//        style.setBorderRight(BorderStyle.THIN);
//        return style;
//    }
//
//    private void applyBorderedStyle(Sheet sheet) {
//        for (int r = 0; r < sheet.getPhysicalNumberOfRows(); r++) {
//            Row row = sheet.getRow(r);
//            for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
//                Cell cell = row.getCell(c);
//                cell.setCellStyle(createBorderedStyle(sheet.getWorkbook()));
//            }
//        }
//    }







    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void processOrders(MultipartFile file) {
        try (InputStream fileInputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);

            List<String> errorMessages = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                try {
                    Category category = createCategoryFromRow(row);
                    List<String> validationErrors = validateCategory(category, row.getLastCellNum());
                    if (!validationErrors.isEmpty()) {
                        int columnIndex = 1;
                        for (String validationError : validationErrors) {
                            errorMessages.add("Dòng " + (i + 1) + ", Cột " + columnIndex + ": " + validationError);
                            columnIndex++;
                        }
                    } else {
                        categoryService.save(category);
                    }
                } catch (Exception e) {
                    log.error("Hàng xử lý lỗi " + (i + 1), e);
                    errorMessages.add("Row " + (i + 1) + ": " + e.getMessage());
                }
            }

            if (!errorMessages.isEmpty()) {
                createErrorExcel(errorMessages);
                throw new RuntimeException("Lỗi xảy ra trong quá trình xử lý");
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc tệp file", e);
        }
    }

    private List<String> validateCategory(Category category, int columnCount) {
        List<String> validationErrors = new ArrayList<>();

        Integer categoryId = category.getCategoryId();
        if (categoryId == null || categoryId <= 0 ) {
            validationErrors.add("Id bị lỗi");
        }

        String description = category.getDescription();
        if (description == null || description.isEmpty()) {
            validationErrors.add("mô tả không được bỏ trống");
        } else if (description.length() > 100) {
            validationErrors.add("mô tả không được vượt quá " + columnCount + " ký tự");
        }

        return validationErrors;
    }

    private void createErrorExcel(List<String> errorMessages) {
        Workbook errorWorkbook = new XSSFWorkbook();
        Sheet errorSheet = errorWorkbook.createSheet("ErrorSheet");
        Row headerRow = errorSheet.createRow(0);
        headerRow.createCell(0).setCellValue("Thông tin lỗi");
        int rowNum = 1;
        for (String errorMessage : errorMessages) {
            Row errorRow = errorSheet.createRow(rowNum++);
            errorRow.createCell(0).setCellValue(errorMessage);
        }

        String errorFileName = "INB_ImportError_" + System.currentTimeMillis() + ".xlsx";
        try (FileOutputStream errorFileOut = new FileOutputStream(errorFileName)) {
            errorWorkbook.write(errorFileOut);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi tạo tệp Excel", e);
        } finally {
            try {
                errorWorkbook.close();
            } catch (IOException e) {
                throw new RuntimeException("Lỗi khi đóng sổ làm việc lỗi", e);
            }
        }
    }

    private Category createCategoryFromRow(Row row) {
        Category category = new Category();

        Cell cellCategoryId = row.getCell(0);
        if (cellCategoryId != null) {
            if (cellCategoryId.getCellType() == CellType.NUMERIC) {
                category.setCategoryId((int) cellCategoryId.getNumericCellValue());
            } else if (cellCategoryId.getCellType() == CellType.STRING) {
                try {
                    category.setCategoryId(Integer.parseInt(cellCategoryId.getStringCellValue()));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Lỗi phân tích phân tích danh mục là số nguyên");
                }
            } else {
                throw new RuntimeException("Unsupported không được hỗ trợ CategoryId");
            }
        }

        Cell cellDescription = row.getCell(2);
        if (cellDescription != null) {
            switch (cellDescription.getCellType()) {
                case STRING:
                    category.setDescription(cellDescription.getStringCellValue());
                    break;
                case NUMERIC:
                    category.setDescription(String.valueOf(cellDescription.getNumericCellValue()));
                    break;
                case BOOLEAN:
                    category.setDescription(String.valueOf(cellDescription.getBooleanCellValue()));
                    break;
                case FORMULA:
                    category.setDescription(cellDescription.getCellFormula());
                    break;
                default:
                    throw new RuntimeException("Unsupported không được hỗ trợ Description");
            }
        }

        Cell cellTitle = row.getCell(1);
        if (cellTitle != null) {
            switch (cellTitle.getCellType()) {
                case STRING:
                    category.setTitle(cellTitle.getStringCellValue());
                    break;
                case NUMERIC:
                    category.setTitle(String.valueOf(cellTitle.getNumericCellValue()));
                    break;
                case BOOLEAN:
                    category.setTitle(String.valueOf(cellTitle.getBooleanCellValue()));
                    break;
                case FORMULA:
                    category.setTitle(cellTitle.getCellFormula());
                    break;
                default:
                    throw new RuntimeException("Unsupported không được hỗ trợ Title");
            }
        }
        return category;
    }


}
