package com.gomabot.server.dbmanager;

import com.gomabot.server.dbo.VocabularyItem;
import com.gomabot.utils.ConnectionSupplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VocabularyDBManager {
    public static List<VocabularyItem> getAllVocabulary(final ConnectionSupplier connectionSupplier) throws SQLException {
        try (final Connection connection = connectionSupplier.getNewConnection(); //
             final PreparedStatement ps = connection.prepareStatement("SELECT * FROM vocabulary"); //
             final ResultSet rs = ps.executeQuery()) {
            final List<VocabularyItem> vocabularyItems = new ArrayList<>();
            while (rs.next()) {
                final VocabularyItem vocabularyItem = new VocabularyItem();
                vocabularyItem.setId(rs.getInt("id"));
                vocabularyItem.setLessonId(rs.getInt("lesson_id"));
                vocabularyItem.setWord(rs.getString("word"));
                vocabularyItem.setPinyin(rs.getString("pinyin"));
                vocabularyItem.setTranslation(rs.getString("translation"));
                vocabularyItems.add(vocabularyItem);
            }
            return vocabularyItems;
        }
    }
}