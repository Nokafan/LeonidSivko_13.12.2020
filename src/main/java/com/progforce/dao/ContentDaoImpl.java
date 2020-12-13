package com.progforce.dao;

import com.progforce.exception.DataProcessingException;
import com.progforce.model.Content;
import com.progforce.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ContentDaoImpl implements ContentDao {
    @Override
    public Content getContent() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT ContentID, FileName, ParentId, IsDirectory"
                    + " FROM san_content_999_calculated"
                    + " ORDER BY FileName;");
            ResultSet resultSet = statement.executeQuery();
            Content root = null;
            Map<Long, Content> contentIdMap = new HashMap<>();
            if (resultSet.next()) {
                root = getContentFromResultSet(resultSet);
                contentIdMap.put(root.getId(), root);
            }
            while (resultSet.next()) {
                Content current = getContentFromResultSet(resultSet);
                contentIdMap.put(current.getId(), current);
                if (current.isDirectory()) {
                    contentIdMap.get(current.getParentId()).getDirectories().add(current);
                } else {
                    contentIdMap.get(current.getParentId()).getFiles().add(current);
                }
            }
            return root;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get items from DB", e);
        }
    }

    private Content getContentFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ContentID");
        String fileName = resultSet.getString("FileName");
        long parentId = resultSet.getLong("ParentId");
        boolean isDirectory = resultSet.getBoolean("IsDirectory");
        return new Content(id, fileName, parentId, isDirectory);
    }
}
