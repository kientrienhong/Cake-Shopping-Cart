/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.codeverify;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
public class CodeVerifyDAO implements Serializable {

    public boolean insert(CodeVerifyDTO dto)
            throws NamingException, SQLException {
        Connection connection = null;
        PreparedStatement preStm = null;
        boolean result = false;
        try {
            connection = DBHelpers.getConnection();

            if (connection != null) {
                String sql = "INSERT "
                        + "CodeVerify(registrationEmail, codeVerify) "
                        + "values(?, ?)";
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, dto.getRegistrationEmail());
                preStm.setString(2, dto.getCodeVerify());
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

    public boolean checkCode(String code, String userEmail)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            conn = DBHelpers.getConnection();
            String sql = "SELECT codeVerify "
                    + "From CodeVerify "
                    + "Where registrationEmail = ? AND codeVerify = ? ";

            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userEmail);
            preStm.setString(2, code);
            rs = preStm.executeQuery();

            while (rs.next()) {
                result = true;
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

        return result;
    }
}
