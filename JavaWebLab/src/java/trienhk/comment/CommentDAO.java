/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.comment;

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
public class CommentDAO implements Serializable {

    private List<CommentDTO> list;

    public List<CommentDTO> getList() {
        return list;
    }

    public List<CommentDTO> loadListByArticleId(String id)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            conn = DBHelpers.getConnection();
            String sql = "SELECT registrationEmail, contentComment, date, id "
                    + "FROM Comment "
                    + "WHERE articleId = ? AND status = 'Active' "
                    + "ORDER BY date DESC";

            preStm = conn.prepareStatement(sql);
            preStm.setString(1, id);

            rs = preStm.executeQuery();

            while (rs.next()) {
                String userEmail = rs.getString("registrationEmail");
                String contentComment = rs.getString("contentComment");
                Timestamp date = rs.getTimestamp("date");
                String commentId = rs.getString("id");
                CommentDTO dto = new CommentDTO(userEmail, contentComment, date, commentId);

                if (list == null) {
                    list = new ArrayList<CommentDTO>();
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

    public boolean addComment(CommentDTO dto) 
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        boolean result = false;

        try {
            conn = DBHelpers.getConnection();
            if(conn != null){
                String sql = "INSERT "
                        + "Comment(articleId, registrationEmail, contentComment, date, status, id) "
                        + "values(?, ?, ?, ?, ?, ?)";
                preStm = conn.prepareStatement(sql); 
                preStm.setString(1, dto.getArticleId());
                preStm.setString(2, dto.getRegistrationEmail());
                preStm.setString(3, dto.getContentComment());
                preStm.setTimestamp(4, dto.getDate()); 
                preStm.setString(5, "Active");
                preStm.setString(6, dto.getId());
                int row = preStm.executeUpdate(); 
                result = row > 0;
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            
            if(conn != null){
                conn.close();
            }
        }
        
        return result;
    }
    
    public boolean deleteComment(String id) 
            throws NamingException, SQLException{
                Connection conn = null;
        PreparedStatement preStm = null;
        boolean result = false;

        try {
            conn = DBHelpers.getConnection();
            if(conn != null){
                String sql = "UPDATE Comment "
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
            
            if(conn != null){
                conn.close();
            }
        }
        
        return result;
    }
}
