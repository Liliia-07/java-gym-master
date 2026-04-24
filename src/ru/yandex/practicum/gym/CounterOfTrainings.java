package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private String coachName;
    private int trainingCount;

    public CounterOfTrainings(String coachName, int trainingCount) {
        this.coachName = coachName;
        this.trainingCount = trainingCount;
    }

    public String getCoachName() {
        return coachName;
    }

    @Override
    public int compareTo(CounterOfTrainings other) {
        return Integer.compare(other.trainingCount, this.trainingCount);
    }

    public int getTrainingCount() {
        return trainingCount;
    }
}
