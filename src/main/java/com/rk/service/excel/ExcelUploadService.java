package com.rk.service.excel;

import com.rk.model.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface ExcelUploadService {
    Boolean inValidExcel(MultipartFile file);
    List<Category>getCategory(InputStream inputStream);

}
