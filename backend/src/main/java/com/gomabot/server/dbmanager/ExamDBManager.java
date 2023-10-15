package com.gomabot.server.dbmanager;

import com.gomabot.server.dbo.Exam;
import com.gomabot.utils.ConnectionSupplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamDBManager {

    public static List<Exam> getAllExams(final ConnectionSupplier connectionSupplier) throws SQLException {
        try (final Connection connection = connectionSupplier.getNewConnection();
             final PreparedStatement ps = connection.prepareStatement("SELECT * FROM exams");
             final ResultSet rs = ps.executeQuery()) {

            final List<Exam> exams = new ArrayList<>();
            while (rs.next()) {
                final Exam exam = new Exam();
                exam.setId(rs.getInt("id"));
                exam.setTitle(rs.getString("title"));
                exams.add(exam);
            }
            return exams;
        }
    }
}


