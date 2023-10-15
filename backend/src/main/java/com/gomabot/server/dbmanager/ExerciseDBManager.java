package com.gomabot.server.dbmanager;

import com.gomabot.server.dbo.Exercise;
import com.gomabot.utils.ConnectionSupplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDBManager {

    private static final String BASE_QUERY = "SELECT id, " + //
            "id_lesson, " + //
            "id_exam, " + //
            "question, " + //
            "correct_answer, " + //
            "incorrect_answer_1, " + //
            "incorrect_answer_2 " + //
            "FROM exercises WHERE %s";

    private static final String FILTER_BY_LESSON = "id_lesson = ?";
    private static final String FILTER_BY_EXAM = "id_exam = ?";

    public static List<Exercise> getExercisesByLessonId(final ConnectionSupplier connectionSupplier, final int lessonId) throws SQLException {
        try (final Connection connection = connectionSupplier.getNewConnection(); //
             final PreparedStatement ps = connection.prepareStatement(String.format(BASE_QUERY, FILTER_BY_LESSON))) {
            ps.setInt(1, lessonId);
            try (final ResultSet rs = ps.executeQuery()) {
                final List<Exercise> exercises = new ArrayList<>();
                while (rs.next()) {
                    exercises.add(mapResultSetToExercise(rs));
                }
                return exercises;
            }
        }
    }

    public static List<Exercise> getExercisesByExamId(final ConnectionSupplier connectionSupplier, final int examId) throws SQLException {
        try (final Connection connection = connectionSupplier.getNewConnection(); //
             final PreparedStatement ps = connection.prepareStatement(String.format(BASE_QUERY, FILTER_BY_EXAM))) {
            ps.setInt(1, examId);
            try (final ResultSet rs = ps.executeQuery()) {
                final List<Exercise> exercises = new ArrayList<>();
                while (rs.next()) {
                    exercises.add(mapResultSetToExercise(rs));
                }
                return exercises;
            }
        }
    }

    private static Exercise mapResultSetToExercise(final ResultSet rs) throws SQLException {
        final Exercise exercise = new Exercise();
        exercise.setId(rs.getInt("id"));
        exercise.setIdLesson(rs.getInt("id_lesson"));
        exercise.setIdExam(rs.getInt("id_exam"));
        exercise.setQuestion(rs.getString("question"));
        exercise.setCorrectAnswer(rs.getString("correct_answer"));
        exercise.setIncorrectAnswer1(rs.getString("incorrect_answer_1"));
        exercise.setIncorrectAnswer2(rs.getString("incorrect_answer_2"));
        return exercise;
    }
}