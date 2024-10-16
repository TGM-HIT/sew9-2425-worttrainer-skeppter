

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

