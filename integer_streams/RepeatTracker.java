import java.util.List;
import java.util.ArrayList;

public class RepeatTracker {
    private boolean adjacent;
    private int occurences;

    public RepeatTracker() {
        this.adjacent = false;
        this.occurences = 0;
    }

    public boolean getAdjacent() {
        return this.adjacent;
    }

    public void setAdjacent(boolean adjacent) {
        this.adjacent = adjacent;
    }

    public void incrementOccurences() {
        this.adjacent = true;
        this.occurences++;
    }

    public int getOccurrences() {
        return this.occurences;
    }
}
