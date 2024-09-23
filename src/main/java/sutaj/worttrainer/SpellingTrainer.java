package sutaj.worttrainer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SpellingTrainer {
    private List availablePairs; // Liste an image pairs
    private Set selectedPairs; // Ein set an image pairs
    private WordImagePair currentPair; // Zu implementieren
    private Statistics statistics; // zu implementieren
    private Random random;

    public SpellingTrainer(List pairs) {
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
