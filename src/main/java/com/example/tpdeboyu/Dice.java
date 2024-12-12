package com.example.tpdeboyu;

import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class Dice {
    private final Random random;

    // 构造器初始化
    public Dice() {
        this.random = new Random();
    }

    /**
     * 抛掷骰子的方法
     * @return 返回 1 到 6 的随机整数
     */
    public int roll() {
        return random.nextInt(6) + 1; // 生成 1 到 6 的随机数
    }
}
