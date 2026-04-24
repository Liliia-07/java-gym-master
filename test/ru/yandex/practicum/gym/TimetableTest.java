package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        //Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        // Проверить, что за четверг вернулось два занятия
        Assertions.assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());

        // Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDaySorted() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession session = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(session);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession session1 = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession session2 = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession session3 = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);

        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursdaySessions.size());

        Assertions.assertEquals(2, thursdaySessions.size());

        // Проверяем порядок занятий
        Assertions.assertEquals(new TimeOfDay(13, 0), thursdaySessions.get(0).getTimeOfDay());
        Assertions.assertEquals(new TimeOfDay(20, 0), thursdaySessions.get(1).getTimeOfDay());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,new TimeOfDay(13, 0)).size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,new TimeOfDay(14, 0)).size());
    }

    @Test
    void testAddMultipleSessionsAtSameTime() {
        Timetable timetable = new Timetable();
        Group group1 = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Group group2 = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Иванов", "Иван", "Иванович");

        TrainingSession trainingSession1 = new TrainingSession(group1, coach, DayOfWeek.MONDAY, new TimeOfDay(12, 0));
        TrainingSession trainingSession2 = new TrainingSession(group2, coach, DayOfWeek.MONDAY, new TimeOfDay(12, 0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(12, 0));
        Assertions.assertEquals(2, sessions.size());
        Assertions.assertTrue(sessions.contains(trainingSession1));
        Assertions.assertTrue(sessions.contains(trainingSession2));
    }

    @Test
    void testEmptyTimetable() {
        Timetable timetable = new Timetable();
        
        List<TrainingSession> sessionsForDay = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertTrue(sessionsForDay.isEmpty());

        List<TrainingSession> sessionsForTime = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.TUESDAY, new TimeOfDay(10, 0));
        Assertions.assertTrue(sessionsForTime.isEmpty());

        List<CounterOfTrainings> counts = timetable.getCountByCoaches();
        Assertions.assertTrue(counts.isEmpty());
    }

    @Test
    void testGetCountCoachesInCounterOfTrainings() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Group group2 = new Group("Акробатика для детей", Age.CHILD, 60);

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Пётр", "Петрович");

        TrainingSession session1 = new TrainingSession(group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group2, coach2, DayOfWeek.WEDNESDAY, new TimeOfDay(11, 0));
        TrainingSession session3 = new TrainingSession(group1, coach1, DayOfWeek.FRIDAY, new TimeOfDay(9, 0)); // Второй тренинг у Иванова

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);

        List<CounterOfTrainings> counterOfTrainings = timetable.getCountByCoaches();
        Assertions.assertEquals(2, counterOfTrainings.size());
    }

    @Test
    void testGetCountTrainingByCoaches() {
        Timetable timetable = new Timetable();

        List<CounterOfTrainings> counterOfTrainings = timetable.getCountByCoaches();

        for (CounterOfTrainings counterOfTraining : counterOfTrainings) {
            if (counterOfTraining.getCoachName().equals("Иванов Иван Иванович")){
                Assertions.assertEquals(2, counterOfTraining.getTrainingCount());
            }

            if (counterOfTraining.getCoachName().equals("Петров Пётр Петрович")){
                Assertions.assertEquals(1, counterOfTraining.getTrainingCount());
            }
        }
    }

    private static Timetable getTimetable() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Group group2 = new Group("Акробатика для детей", Age.CHILD, 60);

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Пётр", "Петрович");

        TrainingSession session1 = new TrainingSession(group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group2, coach2, DayOfWeek.WEDNESDAY, new TimeOfDay(11, 0));
        TrainingSession session3 = new TrainingSession(group1, coach1, DayOfWeek.FRIDAY, new TimeOfDay(9, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);
        return timetable;
    }

    @Test
    void testGetCountTrainingByCoachesSorted() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Group group2 = new Group("Акробатика для детей", Age.CHILD, 60);

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Пётр", "Петрович");

        TrainingSession session1 = new TrainingSession(group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group2, coach2, DayOfWeek.WEDNESDAY, new TimeOfDay(11, 0));
        TrainingSession session3 = new TrainingSession(group1, coach1, DayOfWeek.FRIDAY, new TimeOfDay(9, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);

        List<CounterOfTrainings> counterOfTrainings = timetable.getCountByCoaches();

        CounterOfTrainings firstCoach = counterOfTrainings.get(0);
        CounterOfTrainings secondCoach = counterOfTrainings.get(1);

        Assertions.assertTrue(firstCoach.getTrainingCount() >= secondCoach.getTrainingCount());
    }

}
