/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.notification;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
public class NotificationDAO implements Serializable {

    private List<NotificationDTO> list;

    public NotificationDAO() {
    }

    public List<NotificationDTO> findByAritcleIds(List<String> listArticle)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                for (int i = 0; i < listArticle.size(); i++) {
                    String sql = "SELECT registrationEmail, type, date "
                            + "From Notification "
                            + "Where articleId = ? "
                            + "ORDER BY date DESC";

                    preStm = conn.prepareStatement(sql);
                    preStm.setString(1, listArticle.get(i));

                    rs = preStm.executeQuery();

                    while (rs.next()) {
                        String userEmail = rs.getString("registrationEmail");
                        String type = rs.getString("type");
                        Timestamp ts = rs.getTimestamp("date");

                        NotificationDTO dto = new NotificationDTO(userEmail, listArticle.get(i), type, ts);

                        if (list == null) {
                            list = new ArrayList<>();
                        }

                        list.add(dto);
                    }
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
        return list;
    }

    public boolean insert(NotificationDTO dto)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        boolean result = false;

        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "INSERT Notification(registrationEmail, articleId, date, type, status) "
                        + "values(?, ?, ?, ?, ?)";
                preStm = conn.prepareStatement(sql);

                preStm.setString(1, dto.getRegistrationEmail());
                preStm.setString(2, dto.getAriticleId());
                preStm.setTimestamp(3, dto.getDate());
                preStm.setString(4, dto.getType());
                preStm.setString(5, "Active");

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

    public boolean update(NotificationDTO dto)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        boolean result = false;

        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "INSERT Notification(registrationEmail, articleId, date, type, status, id) "
                        + "values(?, ?, ?, ?, ?, ?)";
                preStm = conn.prepareStatement(sql);

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
