/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.article;

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
public class ArticleDAO implements Serializable {

    private List<ArticleDTO> list;

    public List<ArticleDTO> findByLikeContent(String content, int currentPage)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            conn = DBHelpers.getConnection();
            String sql = "SELECT temp.title, temp.registrationEmail, temp.date, temp.image, temp.description, temp.id "
                    + "FROM ("
                    + "	SELECT ROW_NUMBER() OVER (ORDER BY date DESC) AS RowNr, title, registrationEmail, date, description, image, id"
                    + "	From Article"
                    + " Where description LIKE ? AND status = 'Active'"
                    + ")temp "
                    + "Where temp.RowNr Between ? AND ?";

            preStm = conn.prepareStatement(sql);
            preStm.setString(1, "%" + content + "%");
            preStm.setInt(2, 20 * (currentPage - 1) + 1);
            preStm.setInt(3, 20 * (currentPage - 1) + 1 + 19);
            rs = preStm.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                String userEmail = rs.getString("registrationEmail");
                Timestamp ts = rs.getTimestamp("date");
                String image = rs.getString("image");
                String description = rs.getString("description");
                String id = rs.getString("id");
                ArticleDTO dto = new ArticleDTO(title, ts, userEmail, image, description, id);

                if (list == null) {
                    list = new ArrayList<ArticleDTO>();
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

    public ArticleDTO findById(String id)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        ArticleDTO dto = null;
        try {
            conn = DBHelpers.getConnection();
            String sql = "SELECT title, description, image, registrationEmail "
                    + "From Article "
                    + "Where id = ? AND status = 'Active'";

            preStm = conn.prepareStatement(sql);
            preStm.setString(1, id);
            rs = preStm.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                String userEmail = rs.getString("registrationEmail");
                String image = rs.getString("image");
                String description = rs.getString("description");
                dto = new ArticleDTO(title, description, image, userEmail);
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

    public List<String> findArticleIDsByUserEmail(String userEmail)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        ArrayList<String> listArticle = null;
        try {
            conn = DBHelpers.getConnection();
            String sql = "SELECT id "
                    + "From Article "
                    + "Where registrationEmail = ? ";

            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userEmail);

            rs = preStm.executeQuery();
            listArticle = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("id");

                listArticle.add(id);
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

        return listArticle;
    }

    public int countAllContent(String content)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = DBHelpers.getConnection();
            String sql = "SELECT title "
                    + "From Article "
                    + "Where description LIKE ? AND status = 'Active'";

            preStm = conn.prepareStatement(sql);
            preStm.setString(1, "%" + content + "%");
            rs = preStm.executeQuery();

            while (rs.next()) {
                count++;
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

        return count;
    }

    public boolean create(ArticleDTO dto)
            throws NamingException, SQLException {
        Connection connection = null;
        PreparedStatement preStm = null;
        boolean result = false;
        try {
            connection = DBHelpers.getConnection();

            if (connection != null) {
                String sql = "INSERT "
                        + "Article(title, description, image, date, status, registrationEmail, id) "
                        + "values(?, ?, ?, ?, ?, ?, ?)";
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, dto.getTitle());
                preStm.setString(2, dto.getDescription());
                preStm.setString(3, dto.getImage());
                preStm.setTimestamp(4, dto.getDate());
                preStm.setString(5, dto.getStatus());
                preStm.setString(6, dto.getRegistrationEmail());
                preStm.setString(7, dto.getId());
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

    public boolean delete(String id, String registrationEmail)
            throws NamingException, SQLException {
        Connection connection = null;
        PreparedStatement preStm = null;
        boolean result = false;
        try {
            connection = DBHelpers.getConnection();

            if (connection != null) {
                String sql = "Update Article "
                        + "Set status = 'Delete' "
                        + "Where id = ? AND registrationEmail = ?";
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, id);
                preStm.setString(2, registrationEmail);
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

    public boolean deleteViaAdmin(String id)
            throws NamingException, SQLException {
        Connection connection = null;
        PreparedStatement preStm = null;
        boolean result = false;

        try {
            connection = DBHelpers.getConnection();

            if (connection != null) {
                String sql = "Update Article "
                        + "Set status = 'Delete' "
                        + "Where id = ? ";
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, id);
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
}
