package com.example.tpdeboyu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DiceRollService {

    private final DiceRollLogRepository repository;
    private final Random random = new Random();

    @Autowired
    public DiceRollService(DiceRollLogRepository repository) {
        this.repository = repository;
    }

    /**
     * 处理骰子抛掷逻辑
     * @param count 抛掷的骰子数量
     * @return 返回抛掷的结果列表
     */
    public List<Integer> rollDiceAndSave(int count) {
        // 骰子抛掷逻辑
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            results.add(random.nextInt(6) + 1); // 生成 1 到 6 的随机数
        }

        // 保存到数据库
        saveLog(count, results);

        // 返回结果
        return results;
    }

    /**
     * 保存抛掷记录到数据库
     * @param count 骰子的数量
     * @param results 抛掷结果
     */
    private void saveLog(int count, List<Integer> results) {
        DiceRollLog log = new DiceRollLog(count, results, LocalDateTime.now());
        repository.save(log);
    }
}
