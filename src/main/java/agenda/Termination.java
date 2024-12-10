package agenda;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Termination {

    //Définition des attributs
    private LocalDate terminationDateInclusive;
    private long numberOfOccurrences;

    //Définition des deux constructeurs
    public Termination(LocalDate dateInclusive) {
        this.terminationDateInclusive = dateInclusive;
        this.numberOfOccurrences = Long.MAX_VALUE; // Défini pour les dates
    }

    public Termination(long numberOfOccurrences) {
        this.terminationDateInclusive = null;
        this.numberOfOccurrences = numberOfOccurrences;
    }

    public LocalDate terminationDateInclusive() {
        return terminationDateInclusive;
    }

    public long numberOfOccurrences() {
        return numberOfOccurrences;
    }


    public LocalDate calculateTerminationDate(LocalDate start, ChronoUnit frequency) {
    if (numberOfOccurrences != Long.MAX_VALUE) {
        return start.plus((numberOfOccurrences - 1), frequency);
    }
    return terminationDateInclusive;
}

    public long calculateNumberOfOccurrences(LocalDate start, ChronoUnit frequency) {
        if (terminationDateInclusive != null) {
            return ChronoUnit.DAYS.between(start, terminationDateInclusive) / frequency.getDuration().toDays() + 1;
        }
        return numberOfOccurrences;
    }

}  