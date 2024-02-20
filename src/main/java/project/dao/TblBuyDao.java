package project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import project.vo.BuyVO;
import project.vo.CustomerBuyVo;


public class TblBuyDao {
    
    public static final String URL ="jdbc:oracle:thin:@//localhost:1521/xe";
    public static final String USERNAME = "c##idev";
    private static final String PASSWORD = "1234";
    
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
// executeUpdate 메소드는 insert, update, delete가 정상 실행(반영되는 행이 있다면)_ 1을 리턴,
//                                  ㄴ update, delete는 조건에 맞는 행이 없어 반영되는 행이 없다면 0을 리턴.
// 구매하기
public int insert(BuyVO vo){
    String sql = "insert into tbl_buy VALUES" +
    "(buy_pk_seq.nextval, ? , ? , ? , sysdate)";
    int result = 0;
    try(Connection connection = getConnection();        
        PreparedStatement pstmt = connection.prepareStatement(sql);) {
        pstmt.setString(1, vo.getCustomId());
        pstmt.setString(2, vo.getPcode());
        pstmt.setInt(3, vo.getQuantity());

            result = pstmt.executeUpdate();
    } catch (SQLException e) {
        // customid와 pcode는 참조 테이블에 존재하는 값으로 안 한다면 무결성 위반 오류
        System.out.println("구매하기 실행 예외 발생 : " + e.getMessage());
    }return result;
}

// 구매 수량 변경 - PK는 행식별 - 특정 행을 수정하려면 where 조건컬럼은 buy_idx(pk)
public int modify (Map<String, Integer> arg){ //(BuyVO vo){
    int result = 0;
    String sql = "UPDATE TBL_BUY SET QUANTITY = ? WHERE buy_idx = ?";
    try (Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql);){
            pstmt.setInt(1, arg.get("quantity"));
            pstmt.setInt(2, arg.get("idx"));
            result = pstmt.executeUpdate();  // 실행
            // buy_idx 컬럼에 없는 값이면 오류는 아니고 update 반영한 행의 개수가 0이다.
    } catch (SQLException e) {
        System.out.println("구매 수량 변경 예외 발생 : " + e.getMessage());
    } return result;

}

// 구매취소 - PK는 행식별 - 특정 행을 삭제하려면 where 조건컬럼은 buy_idx(pk)
public int delete(int buy_idx){
    int result = 0;
    String sql = "DELETE FROM TBL_BUY WHERE buy_idx = ?";
    try (Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql);){
            pstmt.setInt(1, buy_idx);
            result=pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("구매 취소 예외 발생 : " + e.getMessage());
    }return result;
}

public List<CustomerBuyVo> selectCustomerBuyList(String customId){
    List<CustomerBuyVo> list = new ArrayList<>();
    String sql = "SELECT BUY_IDX, tb.PCODE, PNAME, PRICE, QUANTITY , BUY_DATE \r\n" + //
            "FROM TBL_BUY tb \r\n" + //
            "JOIN TBL_PRODUCT tp \r\n" + //
            "ON tb.PCODE = tp.PCODE \r\n" + //
            "WHERE tb.CUSTOMID = ?\r\n" + //
            "ORDER BY BUY_DATE DESC";
    try(Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new CustomerBuyVo(rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getInt(5),
                rs.getTimestamp(6)));
            }
        
    } catch (SQLException e) {
        System.out.println("예외 발생 !!! " + e.getMessage());
    } return list;
}

// 장바구니 모두 구매
//  ㄴ batch (배치)는 일괄처리 : 실행할 insert, update, delete 등의 데이터 저장 DML을 
//                            : 여러 개 모아 두었다가 한 번에 실행한다.
//  ㄴ트랜잭션 : 특정 요구사항에 대한 기능을 실행 할 여러 SQL 명령들로 구성된 '작업단위'
//  ㄴ 예시 : cart에 저장된 상품 중 하나라도 참조값이 없는 pcode가 있으면 rollback, 모두 정상이면 commit
//          : 트랜잭션 commit 모드가 auto에서 수동으로 변경
public int insertMany(List<BuyVO> cart){    // 여러 번(cart 크기)의 insert를 실행한다.
    String sql = "INSERT INTO TBL_BUY VALUES (buy_pk_seq.nextval,?,?,?,sysdate)";
    Connection connection = null;
    PreparedStatement pstmt = null;
    int count = 0;
    try {connection = getConnection();        
        pstmt = connection.prepareStatement(sql); 
        connection.setAutoCommit(false);    // ★ auto 커밋 해제 ★
        for(BuyVO vo : cart){
        pstmt.setString(1, vo.getCustomId());
        pstmt.setString(2, vo.getPcode());
        pstmt.setInt(3, vo.getQuantity());
        pstmt.addBatch();   count++;        // ★ sql을 메모리에 모아두기. ★ 
                                            // insert sql에 대입되는 매개변수 값은 매번 다르다.
        }
        pstmt.executeBatch();               // ☆ 모아둔 sql을 일괄 실행하기. 실행 중에 무결성 오류 생기면
        connection.commit();                //    catch에서 rollback ☆
    } catch (SQLException e) {      // 예외발생 : 트랜잭션 처리
        try{
            connection.rollback();
        }catch(SQLException e1){}
        count = -1;
        System.out.println("구매 불가능한 상품이 있습니다.");
        System.out.println("장바구니 구매 실행 예외 발생 : " + e.getMessage());
    }finally{           // 모든 경우에 자원해제
        try{            // 트랜잭션 처리를 위해 connection을 사용해야 하므로 직접 close한다.
            pstmt.close();
            connection.close();
        }catch(SQLException e1){}
    }
    return count;   
}






}