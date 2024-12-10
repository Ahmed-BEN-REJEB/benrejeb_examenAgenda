package agenda;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class Repetition {

    /**
     * Stores the frequency of this repetition, one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     */
    private ChronoUnit myFrequency;

    //Déclaration des attributs complémentaires
    private Termination termination;
    private Set<LocalDate> exceptions;

    //Définition du constructeur
    public Repetition(ChronoUnit frequency) {
        this.myFrequency = frequency;
        this.termination = null;
        this.exceptions = new HashSet<>();
    }

    /**
     * Les exceptions à la répétition
     * @param date un date à laquelle l'événement ne doit pas se répéter
     */
    public void addException(LocalDate day) {
        exceptions.add(day);
    }

    /**
     * La terminaison d'une répétition (optionnelle)
     * @param termination la terminaison de la répétition
     */
    public void setTermination(Termination termination) {
        this.termination = termination;
    }

    public Termination getTermination() {
        return termination;
    }

    public boolean isInDay(Event event, LocalDate day) {
        LocalDate current = event.getStart().toLocalDate();
        int occurrences = 1;
    
        while (!current.isAfter(day)) {
            if (current.equals(day) && !exceptions.contains(day)) {
                return true;
            }
    
            if (termination != null) {
                // Arrêter si les occurrences dépassent la limite
                if (termination.numberOfOccurrences() != Long.MAX_VALUE && occurrences >= termination.numberOfOccurrences()) {
                    break;
                }
                // Arrêter si la date actuelle dépasse la date de terminaison
                if (termination.terminationDateInclusive() != null && current.isAfter(termination.terminationDateInclusive())) {
                    break;
                }
            }
    
            current = current.plus(1, myFrequency);
            occurrences++;
        }
        return false;
    }

    public ChronoUnit getFrequency(){
        return this.myFrequency;
    }
    
}