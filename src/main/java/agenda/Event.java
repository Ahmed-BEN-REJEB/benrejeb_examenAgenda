package agenda;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Event {

    /**
     * The myTitle of this event
     */
    private String myTitle;
    
    /**
     * The starting time of the event
     */
    private LocalDateTime myStart;

    /**
     * The durarion of the event 
     */
    private Duration myDuration;


    /**
     * The repetition frequency of the specified event
     */
    private Repetition eventRepetition;


    /**
     * Constructs an event
     *
     * @param title the title of this event
     * @param start the start time of this event
     * @param duration the duration of this event
     */
    public Event(String title, LocalDateTime start, Duration duration) {
        this.myTitle = title;
        this.myStart = start;
        this.myDuration = duration;
        this.eventRepetition= null;
    }

    public void setRepetition(ChronoUnit frequency) {
        //Assign a specific frequency to an event
        this.eventRepetition= new Repetition(frequency);

    }

    public void addException(LocalDate day) {
        if (eventRepetition != null) {
            eventRepetition.addException(day);
        }
    }

    public void setTermination(LocalDate dateInclusive) {
        if (eventRepetition != null) {
            eventRepetition.setTermination(new Termination(dateInclusive));
        }
    }

    public void setTermination(long numberOfOccurrences) {
        if (eventRepetition != null) {
            eventRepetition.setTermination(new Termination(numberOfOccurrences));
        }
    }

    public int getNumberOfOccurrences() {
        if (eventRepetition != null && eventRepetition.getTermination() != null) {
            return (int) eventRepetition.getTermination().calculateNumberOfOccurrences(myStart.toLocalDate(), eventRepetition.getFrequency());
        }
        return 1;
    }

    public LocalDate getTerminationDate() {
        if (eventRepetition != null && eventRepetition.getTermination() != null) {
            return eventRepetition.getTermination().calculateTerminationDate(myStart.toLocalDate(), eventRepetition.getFrequency());
        }
        return myStart.toLocalDate();
    }

    /**
     * Tests if an event occurs on a given day
     *
     * @param aDay the day to test
     * @return true if the event occurs on that day, false otherwise
     */
    public boolean isInDay(LocalDate day) {
        LocalDate startDate = myStart.toLocalDate();
        LocalDate endDate = myStart.plus(myDuration).toLocalDate();
        
        // Vérification pour un événement simple
        if (!startDate.isAfter(day) && !endDate.isBefore(day)) {
            return true;
        }
        
        // Vérification pour les répétitions
        if (eventRepetition != null) {
            return eventRepetition.isInDay(this, day);
        }
        
        return false;
    }


    /**
     * @return the myTitle
     */
    public String getTitle() {
        return myTitle;
    }

    /**
     * @return the myStart
     */
    public LocalDateTime getStart() {
        return myStart;
    }

    /**
     * @return the myDuration
     */
    public Duration getDuration() {
        return myDuration;
    }

    /**
     * 
     * @return the repetition frequency
     */
    public Repetition getRepetition(){
        return this.eventRepetition;
    }

    @Override
    public String toString() {
        return "Event{title='%s', start=%s, duration=%s}".formatted(myTitle, myStart, myDuration);
    }
}
   