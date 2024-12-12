package com.example.tpdeboyu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiceController {
    private final Dice dice;

    // 构造器注入 Dice 组件
    @Autowired
    public DiceController(Dice dice) {
        this.dice = dice;
    }

    /**
     * 提供一个接口来抛掷骰子
     * @return 返回骰子的结果
     */
    @GetMapping("/roll")
    public int rollDice() {

        return dice.roll();
    }
}
