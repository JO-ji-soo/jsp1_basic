package project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.vo.ProductVO;

public class TblProductDao {
    public static final String URL ="jdbc:oracle:thin:@//localhost:1521/xe";
    public static final String USERNAME = "c##idev";
    private static final String PASSWORD = "1234";

    private  Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
public  List<ProductVO> selectByCategory(String category){
    List<ProductVO> list = new ArrayList<>();
    String sql = "SELECT * FROM TBL_PRODUCT tp WHERE CATEGORY = ? ORDER BY PNAME";
    try(
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
        pstmt.setString(1, category);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) { // 조회결과를 n행 가능성 예측
            list.add(new ProductVO(rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getInt(4)));
        }
}catch (SQLException e) {
    System.out.println("예외 발생 !!! " + e.getMessage());
}return list;
}

public List<ProductVO> selsctByPname(String pname){
List<ProductVO> list = new ArrayList<>();
    String sql = "SELECT * FROM TBL_PRODUCT tp \r\n" + 
                "WHERE PNAME LIKE '%' || ? || '%' ORDER BY CATEGORY";
    try(Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
        pstmt.setString(1, pname);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            list.add(new ProductVO(rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getInt(4)));
        }
}catch (SQLException e) {
    System.out.println("예외 발생 !!! " + e.getMessage());
    e.printStackTrace();
}return list;

}
public Map<String,Integer> getPriceTable(){
        Map<String,Integer> map = new HashMap<>();
        String sql = "SELECT PCODE, PRICE FROM TBL_PRODUCT";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
             ResultSet rs = pstmt.executeQuery(); 
             
            while (rs.next()) {
                map.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            System.out.println("예외 발생 !!! " + e.getMessage());
        }
        
        return map;
    }
}
