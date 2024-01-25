package com.rk.service.label;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LabelService {
    void exportLabels(List<String> orderNumbers) throws IOException;
    void processOrders(MultipartFile file);
}
