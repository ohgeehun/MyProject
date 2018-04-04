package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.CustomerVO;
import Model.ProductsVO;
import Model.ReservationVO;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ReservationDAO {

	// �ű� ���� ���
	public ReservationVO getReservationRegist(ReservationVO rvo) throws Exception {
		// ������ ó�� SQL ��
		StringBuffer sql = new StringBuffer();
		sql.append("insert into reservation ");
		sql.append("(r_no, r_date, r_adult, r_child, r_totalprice, p_code, c_no) ");
		sql.append(" values (r_no_seq.nextval,?,?,?,?,?,?)");
		Connection con = null;
		PreparedStatement pstmt = null;
		ReservationVO rVo = rvo;

		try {
			// DBUtil�� �����ͺ��̽� ����
			con = DBUtil.getConnection();
			// �Է¹��� �������� ó������ SQL�� �ۼ�
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, rVo.getResdate());
			pstmt.setInt(2, rVo.getAdult());
			pstmt.setInt(3, rVo.getChild());
			pstmt.setInt(4, rVo.getTotalprice());
			pstmt.setInt(5, rVo.getPrCode());
			pstmt.setInt(6, rVo.getCusNo());

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
		return rvo;
	}

	// �����ͺ��̽����� �������� ��ü ����Ʈ
	public ArrayList<ReservationVO> getReservationTotal() {
		ArrayList<ReservationVO> list = new ArrayList<ReservationVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select r_no, p_code, r_adult, r_child, r_totalprice, c_no, r_date ");
		sql.append(" from reservation order by c_no");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReservationVO rVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				rVo = new ReservationVO();
				rVo.setResNo(rs.getInt("r_no"));
				rVo.setAdult(rs.getInt("r_adult"));
				rVo.setChild(rs.getInt("r_child"));
				rVo.setTotalprice(rs.getInt("r_totalprice"));
				rVo.setPrCode(rs.getInt("p_code"));
				rVo.setCusNo(rs.getInt("c_no"));
				rVo.setResdate(rs.getDate("r_date") + "");

				list.add(rVo);
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

	// �����ͺ��̽����� ���� �� ���� ��ü ����Ʈ
	public ArrayList<ReservationVO> getSalesTotal() {
		ArrayList<ReservationVO> list = new ArrayList<ReservationVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select p_code, sum(r_adult), sum(r_child), sum(r_totalprice) ");
		sql.append(" from reservation group by p_code order by p_code ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReservationVO rVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				rVo = new ReservationVO();
				rVo.setTotalPrCode(rs.getInt("p_code"));
				rVo.setTotalAdult(rs.getInt("sum(r_adult)"));
				rVo.setTotalChild(rs.getInt("sum(r_child)"));
				rVo.setTotalAllPrice(rs.getInt("sum(r_totalprice)"));
				list.add(rVo);
			}
		} catch (SQLException e) {
			System.out.println("1");
			System.out.println(e);
		} catch (Exception e) {
			System.out.println("2");
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

	// �����ͺ��̽����� ���� ���̺��� Į�� ����
	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from reservation");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// ResultSetMetaData ��ü ���� ����
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

	// ���� ����
	public ReservationVO getReservationUpdate(ReservationVO rvo, int no) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update reservation set ");
		sql.append(" r_date=?, r_adult=?, r_child=?, r_totalprice=?, p_code=? ");
		sql.append(" where c_no=? ");
		Connection con = null;
		PreparedStatement pstmt = null;
		ReservationVO retval = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, rvo.getResdate());
			pstmt.setInt(2, rvo.getAdult());
			pstmt.setInt(3, rvo.getChild());
			pstmt.setInt(4, rvo.getTotalprice());
			pstmt.setInt(5, rvo.getPrCode());
			pstmt.setInt(6, rvo.getCusNo());

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("�������� ����");
				alert.setHeaderText("�������� ���� �Ϸ�");
				alert.setContentText("�������� ���� ����!!!");
				alert.showAndWait();
				retval = new ReservationVO();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("�������� ����");
				alert.setHeaderText("�������� ���� ����");
				alert.setContentText("�������� ���� ����!!!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("getReservationUpdate SQL ���� " + e);
		} catch (Exception e) {
			System.out.println("getReservationUpdate �Ϲ� ���� " + e);
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

	// ���� ������ ����
	public void getReservationDelete(int no) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from reservation where c_no = ?");
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, no);

			int i = pstmt.executeUpdate();

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

	// ��ǰ��ȣ�� ������� ���̺��� �˻�
	public ReservationVO getSalesPrCodeCheck(int prCode) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("select p_code, sum(r_adult), sum(r_child), sum(r_totalprice) ");
		sql.append(" from reservation group by p_code having p_code = ? ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReservationVO rVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, prCode);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				rVo = new ReservationVO();
				rVo = new ReservationVO();
				rVo.setTotalPrCode(rs.getInt("p_code"));
				rVo.setTotalAdult(rs.getInt("sum(r_adult)"));
				rVo.setTotalChild(rs.getInt("sum(r_child)"));
				rVo.setTotalAllPrice(rs.getInt("sum(r_totalprice)"));
			}
		} catch (SQLException e) {
			System.out.println("getSalesPrCodeCheck SQL ���� : " + e);
		} catch (Exception e) {
			System.out.println("getSalesPrCodeCheck �Ϲ� ���� : " + e);
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

		return rVo;

	}

	// �� �ڵ��� ��ȣ�� ���� ���̺��� �˻�
	public ReservationVO getReservationPhoneCheck(String phone) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("select * from reservation ");
		sql.append("where c_no in (select c_no ");
		sql.append("from customer where c_phone like ?)");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReservationVO rVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + phone + "%");

			rs = pstmt.executeQuery();

			if (rs.next()) {
				rVo = new ReservationVO();
				rVo.setResNo(rs.getInt("r_no"));
				rVo.setResdate(rs.getString("r_date"));
				rVo.setAdult(rs.getInt("r_adult"));
				rVo.setChild(rs.getInt("r_child"));
				rVo.setTotalprice(rs.getInt("r_totalprice"));
				rVo.setPrCode(rs.getInt("p_code"));
				rVo.setCusNo(rs.getInt("c_no"));
			}
		} catch (SQLException e) {
			System.out.println("getReservationPhoneCheck SQL ���� : " + e);
		} catch (Exception e) {
			System.out.println("getReservationPhoneCheck �Ϲ� ���� : " + e);
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

		return rVo;

	}

	// �� �̸����� �������̺��� �˻�
	public ReservationVO getReservationNameCheck(String name) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("select * from reservation ");
		sql.append("where c_no in (select c_no ");
		sql.append("from customer where c_name like ?)");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReservationVO rVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + name + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rVo = new ReservationVO();
				rVo.setResNo(rs.getInt("r_no"));
				rVo.setResdate(rs.getString("r_date"));
				rVo.setAdult(rs.getInt("r_adult"));
				rVo.setChild(rs.getInt("r_child"));
				rVo.setTotalprice(rs.getInt("r_totalprice"));
				rVo.setPrCode(rs.getInt("p_code"));
				rVo.setCusNo(rs.getInt("c_no"));
			}
		} catch (SQLException e) {
			System.out.println("getReservationNameCheck SQL ���� : " + e);
		} catch (Exception e) {
			System.out.println("getReservationNameCheck �Ϲ� ���� : " + e);
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

		return rVo;

	}

	// �� �̸�, ��ȭ��ȣ�� ���� �������̺��� �˻�
	public ReservationVO getReservationNamePhoneCheck(String name, String phone) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("select * from reservation ");
		sql.append("where c_no in (select c_no ");
		sql.append("from customer where c_name like ? and c_phone like ?)");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReservationVO rVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + name + "%");
			pstmt.setString(2, "%" + phone + "%");

			rs = pstmt.executeQuery();

			if (rs.next()) {
				rVo = new ReservationVO();
				rVo.setResNo(rs.getInt("r_no"));
				rVo.setResdate(rs.getString("r_date"));
				rVo.setAdult(rs.getInt("r_adult"));
				rVo.setChild(rs.getInt("r_child"));
				rVo.setTotalprice(rs.getInt("r_totalprice"));
				rVo.setPrCode(rs.getInt("p_code"));
				rVo.setCusNo(rs.getInt("c_no"));
			}
		} catch (SQLException e) {
			System.out.println("getReservationNamePhoneCheck SQL ���� : " + e);
		} catch (Exception e) {
			System.out.println("getReservationNamePhoneCheck �Ϲ� ���� : " + e);
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

		return rVo;

	}

}
