package com.example.tpdeboyu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DiceRollLogController {

    private final DiceRollLogRepository repository;

    @Autowired
    public DiceRollLogController(DiceRollLogRepository repository) {
        this.repository = repository;
    }

    /**
     * Endpoint: 获取所有骰子抛掷记录
     * @return 返回所有历史记录的 JSON 数据
     */
    @GetMapping("/diceLogs")
    public List<DiceRollLog> getAllDiceLogs() {
        return repository.findAll();
    }
}
