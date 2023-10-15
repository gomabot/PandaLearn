package com.gomabot.server.dbmanager;

import com.gomabot.server.dbo.Lesson;
import com.gomabot.utils.ConnectionSupplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonDBManager {

    public static List<Lesson> getAllLessons(final ConnectionSupplier connectionSupplier) throws SQLException {
        try (final Connection connection = connectionSupplier.getNewConnection(); //
             final PreparedStatement ps = connection.prepareStatement("SELECT * FROM lessons"); //
             final ResultSet rs = ps.executeQuery()) {
            final List<Lesson> lessons = new ArrayList<>();
            while (rs.next()) {
                final Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setName(rs.getString("name"));
                lessons.add(lesson);
            }
            return lessons;
        }
    }

    public static int getHighestLessonId(final ConnectionSupplier connectionSupplier) throws SQLException {
        return getAllLessons(connectionSupplier).stream() //
                .map(Lesson::getId) //
                .max(Integer::compareTo) //
                .orElse(0);
    }
}