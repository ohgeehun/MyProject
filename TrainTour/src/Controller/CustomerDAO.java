package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.CustomerVO;
import Model.ProductsVO;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CustomerDAO {

	// 1. 신규 고객 등록
	public CustomerVO getCustomerRegist(CustomerVO cvo) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into customer ");
		sql.append("(c_no, c_name, c_phone, c_pessengerphone, c_email, c_accountnumber) ");
		sql.append(" values(c_no_seq.nextval,?,?,?,?,?)");
		Connection con = null;
		PreparedStatement pstmt = null;
		CustomerVO cVo = cvo;

		try {
			// DBUtil로 데이터베이스와 연결
			con = DBUtil.getConnection();
			// 입력받은 고객 정보 처리 SQL문 작성
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, cVo.getName());
			pstmt.setString(2, cVo.getPhone());
			pstmt.setString(3, cVo.getPessangerPhone());
			pstmt.setString(4, cVo.getEmail());
			pstmt.setString(5, cVo.getAccountNumber());

			// SQL문을 수행후 처리결과를 얻어옴
			int i = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}
		return cvo;
	}

	// 데이터베이스에서 고객정보 전체 리스트
	public ArrayList<CustomerVO> getCustomerTotal() {
		ArrayList<CustomerVO> list = new ArrayList<CustomerVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select c_no, c_name, c_phone, c_pessengerphone, c_email, c_accountnumber ");
		sql.append("from customer order by c_no");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerVO cVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cVo = new CustomerVO();
				cVo.setCusNo(rs.getInt("c_no"));
				cVo.setName(rs.getString("c_name"));
				cVo.setPhone(rs.getString("c_phone"));
				cVo.setPessangerPhone(rs.getString("c_pessengerphone"));
				cVo.setEmail(rs.getString("c_email"));
				cVo.setAccountNumber(rs.getString("c_accountnumber"));

				list.add(cVo);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}

		return list;

	}

	// 컬럼 갯수
	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from customer");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ResultSetMetaData rsmd = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				columnName.add(rsmd.getColumnName(i));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}
		return columnName;

	}

	// 고객 수정
	public CustomerVO getCustomerUpdate(CustomerVO cvo, int no) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update customer set ");
		sql.append(" c_name=?, c_phone=?, c_pessengerphone=?, c_email=?, c_accountnumber=? ");
		sql.append(" where c_no=? ");
		Connection con = null;
		PreparedStatement pstmt = null;
		CustomerVO retval = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, cvo.getName());
			pstmt.setString(2, cvo.getPhone());
			pstmt.setString(3, cvo.getPessangerPhone());
			pstmt.setString(4, cvo.getEmail());
			pstmt.setString(5, cvo.getAccountNumber());
			pstmt.setInt(6, cvo.getCusNo());

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("고객정보 수정");
				alert.setHeaderText("고객정보 수정 완료");
				alert.setContentText("고객정보 수정 성공!!!");
				alert.showAndWait();
				retval = new CustomerVO();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("고객정보 수정");
				alert.setHeaderText("고객정보 수정 실패");
				alert.setContentText("고객정보 수정 실패!!!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("getCustomerUpdate SQL 에러 " + e);
		} catch (Exception e) {
			System.out.println("getCustomerUpdate 일반 에러 " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}
		return retval;
	}

	// 고객 시퀀스 번호 입력
	public CustomerVO getCusNoMax() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(c_no) from customer");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerVO cVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				cVo = new CustomerVO();
				cVo.setCusNo(rs.getInt("max(c_no)"));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}

		return cVo;

	}

	// 고객 삭제
	public void getCustomerDelete(int no) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from customer where c_no = ?");
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, no);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("예약고객 삭제");
				alert.setHeaderText("예약고객 삭제 완료");
				alert.setContentText("예약고객 삭제 성공!!!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("예약고객 삭제");
				alert.setHeaderText("예약고객 삭제 실패");
				alert.setContentText("예약고객 삭제 실패!!!");
				alert.showAndWait();
			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}
	}

	// 고객 휴대폰 번호로 고객 테이블에서 검색
	public CustomerVO getCustomerPhoneCheck(String phone) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("select * from customer where c_phone like ?");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerVO cVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + phone + "%");

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cVo = new CustomerVO();
				cVo.setCusNo(rs.getInt("c_no"));
				cVo.setName(rs.getString("c_name"));
				cVo.setPhone(rs.getString("c_phone"));
				cVo.setPessangerPhone(rs.getString("c_pessengerphone"));
				cVo.setEmail(rs.getString("c_email"));
				cVo.setAccountNumber(rs.getString("c_accountnumber"));
			}
		} catch (SQLException e) {
			System.out.println("getCustomerPhoneCheck SQL 에러 : " + e);
		} catch (Exception e) {
			System.out.println("getCustomerPhoneCheck 일반 에러 : " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}

		return cVo;

	}

	// 고객 이름으로 고객 테이블에서 검색
	public CustomerVO getCustomerNameCheck(String name) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("select * from customer where c_name like ?");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerVO cVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + name + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				cVo = new CustomerVO();
				cVo.setCusNo(rs.getInt("c_no"));
				cVo.setName(rs.getString("c_name"));
				cVo.setPhone(rs.getString("c_phone"));
				cVo.setPessangerPhone(rs.getString("c_pessengerphone"));
				cVo.setEmail(rs.getString("c_email"));
				cVo.setAccountNumber(rs.getString("c_accountnumber"));
			}
		} catch (SQLException e) {
			System.out.println("getCustomerNameCheck SQL 에러 : " + e);
		} catch (Exception e) {
			System.out.println("getCustomerNameCheck 일반 에러 : " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}

		return cVo;

	}

	// 고객 이름으로 고객 테이블에서 검색
	public CustomerVO getCustomerNamePhoneCheck(String name, String phone) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("select * from customer where c_name like ? and c_phone like ?");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerVO cVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + name + "%");
			pstmt.setString(2, "%" + phone + "%");

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cVo = new CustomerVO();
				cVo.setCusNo(rs.getInt("c_no"));
				cVo.setName(rs.getString("c_name"));
				cVo.setPhone(rs.getString("c_phone"));
				cVo.setPessangerPhone(rs.getString("c_pessengerphone"));
				cVo.setEmail(rs.getString("c_email"));
				cVo.setAccountNumber(rs.getString("c_accountnumber"));
			}
		} catch (SQLException e) {
			System.out.println("getCustomerNamePhoneCheck SQL 에러 : " + e);
		} catch (Exception e) {
			System.out.println("getCustomerNamePhoneCheck 일반 에러 : " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}

		return cVo;

	}

}
