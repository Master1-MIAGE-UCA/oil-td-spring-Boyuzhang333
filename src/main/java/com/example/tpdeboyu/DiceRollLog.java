package com.example.tpdeboyu;
import jakarta.persistence.*;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table(name = "DiceRollLog")
public class DiceRollLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int diceCount;

    @ElementCollection
    private List<Integer> results;

    private LocalDateTime timestamp;

    public DiceRollLog() {};

    public DiceRollLog(int diceCount, List<Integer> results, LocalDateTime timestamp) {
        this.diceCount = diceCount;
        this.results = results;
        this.timestamp = timestamp;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getDiceCount() {
        return diceCount;
    }
    public void setDiceCount(int diceCount) {
        this.diceCount = diceCount;
    }
    public List<Integer> getResults() {
        return results;
    }
    public void setResults(List<Integer> results) {
        this.results = results;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    @Override
    public String toString() {
        return "DiceRollLog{" +
                "id=" + id +
                ", diceCount=" + diceCount +
                ", results=" + results +
                ", timestamp=" + timestamp +
                '}';
    }

}