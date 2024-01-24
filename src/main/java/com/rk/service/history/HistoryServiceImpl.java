package com.rk.service.history;

import com.rk.model.History;
import com.rk.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService{
    @Autowired
    private HistoryRepository historyRepository;
    @Override
    public void save(History history) {
        historyRepository.save(history);
    }
}
