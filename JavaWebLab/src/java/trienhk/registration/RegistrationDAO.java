/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.registration;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
public class RegistrationDAO implements Serializable {

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA  
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called  
        // to calculate message digest of an input  
        // and return array of byte 
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash) {
        // Convert byte array into signum representation  
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value  
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros 
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public RegistrationDTO checkLogin(String email, String password)
            throws NamingException, SQLException, NoSuchAlgorithmException {
        Connection connection = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        RegistrationDTO dto = null;
        try {
            connection = DBHelpers.getConnection();

            if (connection != null) {
                String sql = "SELECT status, role, name "
                        + "FROM Registration "
                        + "WHERE email = ? AND password = ? ";
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, email);
                preStm.setString(2, toHexString(getSHA(password)));
                rs = preStm.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    String role = rs.getString("role");
                    dto = new RegistrationDTO(email, "****", name, role, status);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (preStm != null) {
                preStm.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return dto;
    }

    public boolean signUp(RegistrationDTO dto)
            throws NamingException, SQLException, NoSuchAlgorithmException {
        Connection connection = null;
        PreparedStatement preStm = null;
        boolean result = false;

        try {
            connection = DBHelpers.getConnection();

            if (connection != null) {
                String sql = "INSERT "
                        + "Registration(email, password, name, role, status) "
                        + "values(?, ?, ?, ?, ?)";
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, dto.getEmail());
                preStm.setString(3, dto.getName());
                preStm.setString(4, dto.getRole());
                preStm.setString(5, dto.getStatus());
                preStm.setString(2, toHexString(getSHA(dto.getPassword())));

                int row = preStm.executeUpdate();
                result = row > 0;
                System.out.println(result);

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
    
    public boolean activateAccount(String id)
            throws NamingException, SQLException{
                Connection conn = null;
        PreparedStatement preStm = null;
        boolean result = false;

        try {
            conn = DBHelpers.getConnection();
            if(conn != null){
                String sql = "UPDATE Registration "
                        + "Set status = 'Active' "
                        + "Where email = ?";
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
