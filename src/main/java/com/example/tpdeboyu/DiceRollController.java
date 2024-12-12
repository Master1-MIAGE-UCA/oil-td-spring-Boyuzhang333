package com.example.tpdeboyu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api") // 定义基础路径
public class DiceRollController {

    private final DiceRollLogRepository repository;
    private final Random random = new Random();

    @Autowired
    public DiceRollController(DiceRollLogRepository repository) {
        this.repository = repository;
    }

    /**
     * Endpoint: Lancer un seul dé
     * @return 返回单次骰子抛掷的结果
     */
    @GetMapping("/rollDice")
    public int rollSingleDice() {
        int result = random.nextInt(6) + 1; // 生成 1 到 6 的随机数
        saveLog(1, List.of(result)); // 保存日志
        return result;
    }

    /**
     * Endpoint: Lancer X dés
     * @param count 骰子的数量
     * @return 返回 X 次骰子抛掷的结果
     */
    @GetMapping("/rollDices/{count}")
    public List<Integer> rollMultipleDice(@PathVariable int count) {
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            results.add(random.nextInt(6) + 1); // 生成 1 到 6 的随机数
        }
        saveLog(count, results); // 保存日志
        return results;
    }

    /**
     * 保存抛掷记录到数据库
     * @param count 抛掷骰子的数量
     * @param results 抛掷的结果
     */
    private void saveLog(int count, List<Integer> results) {
        DiceRollLog log = new DiceRollLog(count, results, LocalDateTime.now());
        repository.save(log);
    }
}
