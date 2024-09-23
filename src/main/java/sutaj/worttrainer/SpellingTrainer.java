package sutaj.worttrainer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SpellingTrainer {
    private List<WordImagePair> availablePairs;
    private Set<WordImagePair> selectedPairs;
    private WordImagePair currentPair;
    private Statistics statistics;
    private Random random;

    public SpellingTrainer(List<WordImagePair> pairs) {
        if (pairs == null) {
            throw new IllegalArgumentException("Paarliste darf nicht null sein.");
        }
        this.availablePairs = new ArrayList<>(pairs);
        this.selectedPairs = new HashSet<>();
        this.currentPair = null;
        this.statistics = new Statistics();
        this.random = new Random();
    }
}
