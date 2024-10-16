package sutaj.worttrainer;


/**
 * @author Drinor Sutaj
 * @version 14.10.2024
 */
public class Statistics {
    private int total;
    private int correct;
    private int incorrect;

    public Statistics() {
        this.total = 0;
        this.correct = 0;
        this.incorrect = 0;
    }

    public void incrementTotal() {
        total++;
    }

    public void incrementCorrect() {
        correct++;
    }

    public void incrementIncorrect() {
        incorrect++;
    }

    public int getTotal() {
        return total;
    }

    public int getCorrect() {
        return correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

    // Neue Setter
    public void setTotal(int total) {
        this.total = total;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }
}
