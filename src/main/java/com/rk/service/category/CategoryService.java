package com.rk.service.category;

import com.rk.model.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    void saveCategory(MultipartFile file) throws IllegalAccessException;
    List<Category>findAll();
    void generateCategoryExel(HttpServletResponse response);
    void  save(Category category);

}
