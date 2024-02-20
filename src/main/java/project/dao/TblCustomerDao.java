package project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.vo.CustomerVO;


public class TblCustomerDao {
	public static final String DRIVER ="oracle.jdbc.driver.OracleDriver";
    public static final String URL ="jdbc:oracle:thin:@//localhost:1521/xe";
    public static final String USERNAME = "c##idev";
    private static final String PASSWORD = "1234";

    private Connection getConnection() throws SQLException {
    	Connection conn=null;
    	try {
    		Class.forName(DRIVER);
    		conn=DriverManager.getConnection(URL,USERNAME,PASSWORD);
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}
        return conn;
    }
    // 회원 가입
    public void join(CustomerVO vo){
        String sql = "insert into tbl_custom(custom_id,name,email,age,reg_date) " + 
                        "values (?,?,?,?,sysdate)";        // 할 일 : insert
        try (
            Connection connection = getConnection();        // 1) 서버와의 연결
            PreparedStatement pstmt = connection.prepareStatement(sql);){       // 2) 연결된 서버로 실행할 SQL전달, 서바가 SQL 컴파일.
            // 할 일 : 매개변수 바인딩   
            pstmt.setString(1, vo.getCustomId());
            pstmt.setString(2, vo.getName());
            pstmt.setString(3, vo.getEmail());
            pstmt.setInt(4, vo.getAge());

            pstmt.executeUpdate();          // 3) 연결된 서버에 실행 요청
            }catch (SQLException e) {
           System.out.println("join 실행 예외 발생 : " + e.getMessage());}     
    }
    // 회원 정보 수정
    public void modify(CustomerVO vo){
            String sql = "update tbl_custom set email = ?, age = ? where custom_Id = ?";  
            try (
            Connection connection = getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);){
            // 매개변수 바인딩   
            pstmt.setString(1, vo.getEmail());
            pstmt.setInt(2, vo.getAge());
            pstmt.setString(3, vo.getCustomId());

            pstmt.executeUpdate();
            }catch (SQLException e) {
           System.out.println("join 실행 예외 발생 : " + e.getMessage());}     

            }
    // 회원 탈퇴
    public void delete(int buy_idx){
        String sql=  "delete from tbl_custom where custom_Id = ?";
        try (Connection connection = getConnection();       //auto close
            PreparedStatement pstmt = connection.prepareStatement(sql);)
            {   //매개변수 바인딩
                pstmt.setInt(1, buy_idx);
               

                pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("join 실행 예외 발생 : " + e.getMessage());
        }
    }
    // 회원 정보★PK★조회 - 조회 결과 행 개수는?? : select * from tbl_custom where custom_id = ?
    //                            ㄴ 0개 : 조회 결과 없다. 조회결과가 있다면 only 1개. !!
    //                            ㄴ 리턴타입 CustomerVO
    public CustomerVO getCustomer(String customerId){
        CustomerVO vo = null;
        String sql  = "select * from tbl_custom where custom_id = ? ";
        try (Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql)){
        pstmt.setString(1, customerId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {    // 첫 번째 행 조회결과가 있으면 true, 없으면 false
                            // 할 일 : 객체 만들어서 vo 변수에 참조하기
            vo = new CustomerVO(rs.getString(1), 
            rs.getString(2), 
            rs.getString(3), 
            rs.getInt(4), 
            rs.getDate(5));
        }

        }catch(SQLException e){
            System.out.println("getCustomer 예외 발생 : " + e.getMessage());
        }
        
        return vo;
    }

    // 관리자를 위한 기능 : 모든 회원 정보 조회 select * from tbl_custom
    public List<CustomerVO> allCustomers(){
    List<CustomerVO> list = new ArrayList<>();
    String sql  = "select * from tbl_custom";
    try (Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql)){

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
          
            list.add(new CustomerVO(rs.getString(1), 
                                    rs.getString(2), 
                                    rs.getString(3), 
                                    rs.getInt(4), 
                                    rs.getDate(5)));
        }        
        // dap 메소드에는 특별한 목적이 아니면 출력문 작성을 하지 않는다.
    } catch (SQLException e) {
        System.out.println("예외 발생 !!! " + e.getMessage());
        e.printStackTrace();
    } return list;      // select 조회 결과를 자바 객체 List와 매핑하여 리턴
    
 }
    
    public List<CustomerVO> selectByNameAge(String name, int age){
        String sql ="select * from tbl_custom where name =? and age = ?";
        List<CustomerVO> list = new ArrayList<>();
        try (Connection connection = getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
           ) {pstmt.setString(1, name);
               pstmt.setInt(2, age);
               ResultSet rs = pstmt.executeQuery();
               while (rs.next()) {
                  list.add(new CustomerVO(rs.getString(1),
                                    rs.getNString(2),
                                    rs.getString(3),
                                    rs.getInt(4),
                                    rs.getDate(5)));
               }
           } catch (SQLException e) {
              System.out.println("getCustomer 실행 예외 : "+e.getMessage());
           }
           return list;
    }



}
