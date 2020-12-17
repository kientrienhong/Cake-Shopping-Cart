/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.emotion;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
public class EmotionDAO implements Serializable {

    private List<EmotionDTO> list;

    public List<EmotionDTO> getList() {
        return list;
    }

    public List<EmotionDTO> loadListByArticleId(String id)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            conn = DBHelpers.getConnection();
            String sql = "SELECT registrationEmail, isLike "
                    + "FROM Emotion "
                    + "WHERE articleId = ? AND status = 'ACTIVE'";

            preStm = conn.prepareStatement(sql);
            preStm.setString(1, id);

            rs = preStm.executeQuery();

            while (rs.next()) {
                String userEmail = rs.getString("registrationEmail");
                boolean isLike = rs.getBoolean("isLike");
                EmotionDTO dto = new EmotionDTO(userEmail, isLike);

                if (list == null) {
                    list = new ArrayList<EmotionDTO>();
                }
                list.add(dto);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (preStm != null) {
                preStm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return list;
    }

    public EmotionDTO findById(String id)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        EmotionDTO dto = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT isLike, status, id "
                        + "From Emotion "
                        + "Where id = ?";

                preStm = conn.prepareStatement(sql);
                preStm.setString(1, id);

                rs = preStm.executeQuery();

                if (rs.next()) {
                    boolean isLike = rs.getBoolean("isLike");
                    String status = rs.getString("status");
                    
                    dto = new EmotionDTO(id, isLike, status);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (preStm != null) {
                preStm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
        return dto;
    }

    public boolean insert(EmotionDTO dto)
            throws NamingException, SQLException {
        Connection connection = null;
        PreparedStatement preStm = null;
        boolean result = false;

        try {
            connection = DBHelpers.getConnection();

            if (connection != null) {
                String sql = "INSERT "
                        + "Emotion(id, articleId, registrationEmail, isLike, status) "
                        + "values(?, ?, ?, ?, ?)";
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, dto.getId());
                preStm.setString(2, dto.getArticleId());
                preStm.setString(3, dto.getRegistrationEmail());
                preStm.setBoolean(4, dto.isIsLike());
                preStm.setString(5, "ACTIVE");

                int row = preStm.executeUpdate();
                result = row > 0;
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return result;
    }

    public boolean delete(String id)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        boolean result = false;

        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "Update Emotion "
                        + "Set status = 'Deactive' "
                        + "Where id = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, id);

                int row = preStm.executeUpdate();

                result = row > 0;
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public boolean update(boolean isLike, String id)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        boolean result = false;

        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "Update Emotion "
                        + "Set isLike = ? "
                        + "Where id = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setBoolean(1, isLike);
                preStm.setString(2, id);

                int row = preStm.executeUpdate();

                result = row > 0;

            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }
    
    public boolean reInteractEmotion (String id, boolean isLike) 
            throws NamingException, SQLException{
        Connection conn = null;
        PreparedStatement preStm = null;
        boolean result = false;

        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "Update Emotion "
                        + "Set isLike = ?, status = 'ACTIVE' "
                        + "Where id = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setBoolean(1, isLike);
                preStm.setString(2, id);

                int row = preStm.executeUpdate();

                result = row > 0;

            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }
}
