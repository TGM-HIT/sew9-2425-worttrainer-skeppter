

Diese Anwendung ist ein Rechtschreibtrainer, der dem Benutzer Bilder anzeigt und ihn dazu auffordert, das entsprechende Wort einzugeben. Sie hilft dabei, die Rechtschreibung zu üben und den Wortschatz zu erweitern. Die Anwendung speichert den Fortschritt des Benutzers, sodass das Training jederzeit fortgesetzt werden kann.

## Übersicht der Klassen

Die Anwendung besteht aus mehreren Klassen, die jeweils spezifische Aufgaben erfüllen:

- [`Main`](#main): Einstiegspunkt der Anwendung, steuert den Programmfluss.
- [`ImageUtils`](#imageutils): Dienstprogrammklasse zum Laden, Skalieren und Cachen von Bildern.
- [`PersistenceManager`](#persistencemanager): Verantwortlich für das Speichern und Laden des Anwendungszustands.
- [`SpellingTrainer`](#spellingtrainer): Kernlogik des Trainers, verwaltet die Wort-Bild-Paare und die Benutzereingaben.
- [`Statistics`](#statistics): Hält die Statistik des Benutzers während des Trainings.
- [`WordImagePair`](#wordimagepair): Datenklasse, die ein Wort und die zugehörige Bild-URL repräsentiert.

---

### Main

Die `Main`-Klasse ist der Einstiegspunkt der Anwendung und steuert den Hauptprogrammablauf.

**Hauptfunktionen:**

- **Initialisierung:**
    - Lädt vorhandene Trainingsdaten mithilfe des `PersistenceManager`.
    - Falls keine Daten vorhanden sind, wird ein Standard-`SpellingTrainer` erstellt.
- **Hauptschleife:**
    - Wählt zufällig ein Wort-Bild-Paar aus.
    - Zeigt dem Benutzer das Bild und fordert zur Eingabe des entsprechenden Wortes auf.
    - Aktualisiert die Statistik basierend auf der Benutzereingabe.
    - Gibt Rückmeldung über richtige oder falsche Antworten.
- **Beenden und Speichern:**
    - Speichert den aktuellen Fortschritt des Benutzers vor dem Beenden der Anwendung.

**Wichtige Methoden:**

- `public static void main(String[] args)`: Startpunkt der Anwendung.
- `private static SpellingTrainer createDefaultTrainer()`: Erstellt einen `SpellingTrainer` mit voreingestellten Wort-Bild-Paaren.

---

### ImageUtils

Die `ImageUtils`-Klasse bietet Hilfsmethoden zum Laden, Skalieren und Cachen von Bildern. Sie stellt sicher, dass Bilder effizient geladen und an die benötigten Größen angepasst werden.

**Hauptfunktionen:**

- **Bildskalierung:**
    - Skaliert Bilder auf eine maximale Breite und Höhe unter Beibehaltung des Seitenverhältnisses.
- **Caching:**
    - Verwendet ein Cache, um bereits geladene und skalierte Bilder wiederzuverwenden, was die Leistung verbessert.

**Wichtige Methoden:**

- `public static ImageIcon getScaledImageIcon(String urlString, int maxWidth, int maxHeight)`: Lädt ein Bild von einer URL, skaliert es und gibt es als `ImageIcon` zurück.
- `public static ImageIcon getScaledImageIcon(ImageIcon icon, int maxWidth, int maxHeight)`: Skaliert ein vorhandenes `ImageIcon` auf die angegebenen Abmessungen.

---

### PersistenceManager

Der `PersistenceManager` ist für das Speichern und Laden des Anwendungszustands verantwortlich. Er ermöglicht es, den Fortschritt des Benutzers zwischen den Sitzungen zu bewahren.

**Hauptfunktionen:**

- **Speichern:**
    - Serialisiert den Zustand des `SpellingTrainer` (verfügbare Paare, Statistik, aktuelles Paar) in eine JSON-Datei.
- **Laden:**
    - Deserialisiert die JSON-Datei und rekonstruiert den Zustand des `SpellingTrainer`.

**Wichtige Methoden:**

- `public void save(SpellingTrainer trainer)`: Speichert den aktuellen Zustand des Trainers in die Datei.
- `public SpellingTrainer load()`: Lädt den Trainerzustand aus der Datei.

---

### SpellingTrainer

Der `SpellingTrainer` verwaltet die Logik des Rechtschreibtrainers. Er hält die Liste der zu lernenden Wort-Bild-Paare und verarbeitet die Benutzereingaben.

**Hauptfunktionen:**

- **Verwaltung der Wort-Bild-Paare:**
    - Hält eine Liste der verfügbaren Paare, aus denen zufällig ausgewählt wird.
- **Benutzereingaben:**
    - Überprüft die vom Benutzer eingegebenen Wörter auf Korrektheit.
    - Aktualisiert die Statistik basierend auf den Eingaben.
- **Statistiken:**
    - Hält die Anzahl der Versuche, korrekten und inkorrekten Antworten fest.

**Wichtige Methoden:**

- `public void selectRandomPair()`: Wählt zufällig ein Wort-Bild-Paar aus der verfügbaren Liste aus.
- `public void submitAnswer(String answer)`: Verarbeitet die Benutzereingabe und aktualisiert die Statistik.
- `public WordImagePair getCurrentPair()`: Gibt das aktuell ausgewählte Wort-Bild-Paar zurück.
- `public Statistics getStatistics()`: Gibt die aktuelle Statistik zurück.

---

### Statistics

Die `Statistics`-Klasse hält die Leistungsdaten des Benutzers während des Trainings fest.

**Hauptfunktionen:**

- **Zähler:**
    - Hält die Gesamtzahl der Versuche, sowie die Anzahl der korrekten und inkorrekten Antworten.

**Wichtige Methoden:**

- `public void incrementTotal()`: Erhöht die Gesamtzahl der Versuche um eins.
- `public void incrementCorrect()`: Erhöht die Anzahl der korrekten Antworten um eins.
- `public void incrementIncorrect()`: Erhöht die Anzahl der inkorrekten Antworten um eins.
- Getter und Setter für die Attribute `total`, `correct` und `incorrect`.

---

### WordImagePair

Die `WordImagePair`-Klasse ist eine Datenklasse, die ein Wort und die zugehörige Bild-URL repräsentiert.

**Hauptfunktionen:**

- **Validierung:**
    - Stellt sicher, dass sowohl das Wort als auch die Bild-URL gültig sind.
- **Datenhaltung:**
    - Speichert das Wort und die Bild-URL für den Zugriff durch andere Klassen.

**Wichtige Methoden:**

- `public WordImagePair(String word, String imageUrl)`: Konstruktor, der das Wort und die Bild-URL initialisiert.
- `public String getWord()`: Gibt das Wort zurück.
- `public String getImageUrl()`: Gibt die Bild-URL zurück.
- Override von `equals` und `hashCode` für die korrekte Funktion in Datenstrukturen.

---
