package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek,TreeMap<TimeOfDay,List<TrainingSession>>> timetable;

    public Timetable() {
        this.timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        List<TrainingSession> listTrainingSession;

        if (!timetable.containsKey(trainingSession.getDayOfWeek())) {
            TreeMap<TimeOfDay, List<TrainingSession>> timeOfDayTrainingSession = new TreeMap<>();
            listTrainingSession = new ArrayList<>();
            listTrainingSession.add(trainingSession);
            timeOfDayTrainingSession.put(trainingSession.getTimeOfDay(), listTrainingSession);
            timetable.put(trainingSession.getDayOfWeek(), timeOfDayTrainingSession);
        } else {
            TreeMap<TimeOfDay, List<TrainingSession>> existingMap = timetable.get(trainingSession.getDayOfWeek());
            if (!existingMap.containsKey(trainingSession.getTimeOfDay())) {
                listTrainingSession = new ArrayList<>();
                existingMap.put(trainingSession.getTimeOfDay(), listTrainingSession);
            } else {
                listTrainingSession = existingMap.get(trainingSession.getTimeOfDay());
            }
            listTrainingSession.add(trainingSession);
        }
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        List<TrainingSession> listTrainingSession = new ArrayList<>();

        if (!timetable.containsKey(dayOfWeek)) {
            return listTrainingSession;
        }

        for (Map.Entry<TimeOfDay,List<TrainingSession>> entry : timetable.get(dayOfWeek).entrySet()) {
            listTrainingSession.addAll(entry.getValue());
        }

        return listTrainingSession;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (!timetable.containsKey(dayOfWeek) || !timetable.get(dayOfWeek).containsKey(timeOfDay)) {
            return new ArrayList<>();
        }

        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<String, Integer> coachTrainingCount = new HashMap<>();

        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> entry : timetable.entrySet()) {
            TreeMap<TimeOfDay, List<TrainingSession>> treeMap = entry.getValue();

            for (Map.Entry<TimeOfDay, List<TrainingSession>> innerEntry : treeMap.entrySet()) {
                List<TrainingSession> trainingSessions = innerEntry.getValue();

                for (TrainingSession trainingSession : trainingSessions) {

                    String coachName = trainingSession.getCoach().getSurname()
                                    + " " + trainingSession.getCoach().getName()
                                    + " " + trainingSession.getCoach().getMiddleName();
                    if (coachTrainingCount.containsKey(coachName)) {
                        int count = coachTrainingCount.get(coachName);
                        coachTrainingCount.put(coachName, count + 1);
                    } else {
                        coachTrainingCount.put(coachName, 1);
                    }
                }
            }
        }

        List<CounterOfTrainings> counterTrainingsList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : coachTrainingCount.entrySet()) {
            counterTrainingsList.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        Collections.sort(counterTrainingsList, (c1, c2) -> Integer.compare(c2.getTrainingCount(), c1.getTrainingCount()));

        return counterTrainingsList;
    }
}
