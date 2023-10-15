package com.gomabot.server.dbmanager;

import com.gomabot.server.dbo.RegisteredUser;
import com.gomabot.utils.ConnectionSupplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RegisteredUserDBManager {

    public static final String SELECT_BY_ID = "SELECT * FROM registered_users WHERE id = ?";
    public static final String SELECT_BY_USERNAME = "SELECT * FROM registered_users WHERE username = ?";
    public static final String INSERT_NEW_USER = "INSERT INTO registered_users (username, password_hash, lesson) VALUES (?, ?, 0)";
    public static final String UPDATE_LESSON_ID_VALUE = "UPDATE registered_users SET lesson = ? WHERE id = ?";

    public static Optional<RegisteredUser> getUserById(final ConnectionSupplier connectionSupplier, final int id) throws SQLException {
        try (final Connection connection = connectionSupplier.getNewConnection(); //
             final PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(mapResultSetToRegisteredUser(rs));
            }
        }
    }

    public static Optional<RegisteredUser> getUserByUsername(final ConnectionSupplier connectionSupplier, final String username) throws SQLException {
        try (final Connection connection = connectionSupplier.getNewConnection(); //
             final PreparedStatement ps = connection.prepareStatement(SELECT_BY_USERNAME)) {
            ps.setString(1, username);
            try (final ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(mapResultSetToRegisteredUser(rs));
            }
        }
    }

    static RegisteredUser mapResultSetToRegisteredUser(final ResultSet rs) throws SQLException {
        final RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setId(rs.getInt("id"));
        registeredUser.setUsername(rs.getString("username"));
        registeredUser.setPasswordHash(rs.getString("password_hash"));
        registeredUser.setLesson(rs.getInt("lesson"));
        return registeredUser;
    }

    public static void createNewUser(final ConnectionSupplier connectionSupplier, final String username, final String passwordHash) throws SQLException {
        try (final Connection connection = connectionSupplier.getNewConnection(); //
             final PreparedStatement ps = connection.prepareStatement(INSERT_NEW_USER)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.executeUpdate();
        }
    }

    public static void setUserLessonLevel(final ConnectionSupplier connectionSupplier, final int userId, final int newLessonIdValue) throws SQLException {
        try (final Connection connection = connectionSupplier.getNewConnection(); //
             final PreparedStatement ps = connection.prepareStatement(UPDATE_LESSON_ID_VALUE)) {
            ps.setInt(1, newLessonIdValue);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }
}
