package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.prism.ResourceFactoryListener;

import Model.CustomerVO;
import Model.ProductsVO;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ProductDAO {

	// 1. �ű� ��ǰ ���
	public ProductsVO getProductRegist(ProductsVO pvo) throws Exception {
		// 2. ������ ó���� ���� SQL ��
		StringBuffer sql = new StringBuffer();
		sql.append("insert into product ");
		sql.append("(p_code, p_tourName, p_startpoint, p_startdate, p_destination, p_desdate, "
				+ "p_returnpoint, p_returndate, p_adultcharge, p_childcharge, p_season,"
				+ " p_hotelname, p_schedule, p_maximum, p_minimum) ");
		sql.append(" values(p_code_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		Connection con = null;
		PreparedStatement pstmt = null;
		ProductsVO pVo = pvo;

		try {
			// 3. DBUtilŬ������ getConnection() �޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();
			// 4. �Է¹��� ��ǰ ������ ó���ϱ� ���Ͽ� SQL���� �ۼ�
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, pVo.getTourName());
			pstmt.setString(2, pVo.getStartPoint());
			pstmt.setString(3, pVo.getStartDate());
			pstmt.setString(4, pVo.getDestination());
			pstmt.setString(5, pVo.getDesDate());
			pstmt.setString(6, pVo.getReturnPoint());
			pstmt.setString(7, pVo.getReturnDate());
			pstmt.setInt(8, pVo.getAdultCharge());
			pstmt.setInt(9, pVo.getChildCharge());
			pstmt.setString(10, pVo.getSeason());
			pstmt.setString(11, pVo.getHotelName());
			pstmt.setString(12, pVo.getSchedule());
			pstmt.setInt(13, pVo.getMaximum());
			pstmt.setInt(14, pVo.getMinimum());

			// 5.SQL���� ������ ó�� ����� ����
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
		return pvo;
	}

	// �����ͺ��̽� ���� ��ǰ ��ü ����Ʈ
	public ArrayList<ProductsVO> getProductTotal() {
		ArrayList<ProductsVO> list = new ArrayList<ProductsVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select p_code, p_tourName, p_startpoint, p_startdate, p_destination, p_desdate, ");
		sql.append("p_returnpoint, p_returndate, p_adultcharge, p_childcharge, p_season, ");
		sql.append("p_hotelname, p_schedule, p_maximum, p_minimum from product order by p_code");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductsVO pVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				pVo = new ProductsVO();
				pVo.setPrCode(rs.getInt("p_code"));
				pVo.setTourName(rs.getString("p_tourname"));
				pVo.setStartPoint(rs.getString("p_startpoint"));
				pVo.setStartDate(rs.getDate("p_startdate") + "");
				pVo.setDestination(rs.getString("p_destination"));
				pVo.setDesDate(rs.getDate("p_desdate") + "");
				pVo.setReturnPoint(rs.getString("p_returnpoint"));
				pVo.setReturnDate(rs.getDate("p_returndate") + "");
				pVo.setAdultCharge(rs.getInt("p_adultcharge"));
				pVo.setChildCharge(rs.getInt("p_childcharge"));
				pVo.setSeason(rs.getString("p_season"));
				pVo.setHotelName(rs.getString("p_hotelname"));
				pVo.setSchedule(rs.getString("p_schedule"));
				pVo.setMaximum(rs.getInt("p_maximum"));
				pVo.setMinimum(rs.getInt("p_minimum"));

				list.add(pVo);
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

	// �����ͺ��̽����� ��ǰ ���̺��� �÷� ����
	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from product");
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

	// ������ ��ǰ ���� ����
	public ProductsVO getProductUpdate(ProductsVO pvo, int code) throws Exception {
		// ������ ó�� SQL ��
		StringBuffer sql = new StringBuffer();
		sql.append("update product set ");
		sql.append(" p_tourname=?, p_startpoint=?, p_startdate=?, p_destination=?, ");
		sql.append(" p_desdate=?, p_returnpoint=?, p_returndate=?, p_adultcharge=?, ");
		sql.append(" p_childcharge=?, p_season=?, p_hotelname=?, p_schedule=?, p_maximum=?, p_minimum=? ");
		sql.append(" where p_code = ? ");
		Connection con = null;
		PreparedStatement pstmt = null;
		ProductsVO retval = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, pvo.getTourName());
			pstmt.setString(2, pvo.getStartPoint());
			pstmt.setString(3, pvo.getStartDate());
			pstmt.setString(4, pvo.getDestination());
			pstmt.setString(5, pvo.getDesDate());
			pstmt.setString(6, pvo.getReturnPoint());
			pstmt.setString(7, pvo.getReturnDate());
			pstmt.setInt(8, pvo.getAdultCharge());
			pstmt.setInt(9, pvo.getChildCharge());
			pstmt.setString(10, pvo.getSeason());
			pstmt.setString(11, pvo.getHotelName());
			pstmt.setString(12, pvo.getSchedule());
			pstmt.setInt(13, pvo.getMaximum());
			pstmt.setInt(14, pvo.getMinimum());
			pstmt.setInt(15, code);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��ǰ���� ����");
				alert.setHeaderText("��ǰ���� ���� �Ϸ�");
				alert.setContentText("��ǰ���� ���� ����!!!");
				alert.showAndWait();
				retval = new ProductsVO();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��ǰ���� ����");
				alert.setHeaderText("��ǰ���� ���� ����");
				alert.setContentText("��ǰ���� ���� ����!!!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("getProductUpdate SQL ���� " + e);
		} catch (Exception e) {
			System.out.println("getProductUpdate �Ϲ� ���� " + e);
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

	// ��ǰ��ȣ�� �Է¹޾� �˻�
	public ProductsVO getProductPrCodeCheck(int code) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from product where p_code = ");
		sql.append("? ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductsVO pVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, code);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				pVo = new ProductsVO();
				pVo.setPrCode(rs.getInt("p_code"));
				pVo.setTourName(rs.getString("p_tourname"));
				pVo.setStartPoint(rs.getString("p_startpoint"));
				pVo.setStartDate(rs.getString("p_startdate"));
				pVo.setDestination(rs.getString("p_destination"));
				pVo.setDesDate(rs.getString("p_desdate"));
				pVo.setReturnPoint(rs.getString("p_returnpoint"));
				pVo.setReturnDate(rs.getString("p_returndate"));
				pVo.setAdultCharge(rs.getInt("p_adultcharge"));
				pVo.setChildCharge(rs.getInt("p_childcharge"));
				pVo.setSeason(rs.getString("p_season"));
				pVo.setHotelName(rs.getString("p_hotelname"));
				pVo.setSchedule(rs.getString("p_schedule"));
				pVo.setMaximum(rs.getInt("p_maximum"));
				pVo.setMinimum(rs.getInt("p_minimum"));
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
		return pVo;
	}

	// �������� �Է¹޾� ���� �˻�
	public ProductsVO getProductSeasonCheck(String season) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from product where p_season like ");
		sql.append("? ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductsVO pVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + season + "%");

			rs = pstmt.executeQuery();

			if (rs.next()) {
				pVo = new ProductsVO();
				pVo.setPrCode(rs.getInt("p_code"));
				pVo.setTourName(rs.getString("p_tourname"));
				pVo.setStartPoint(rs.getString("p_startpoint"));
				pVo.setStartDate(rs.getString("p_startdate"));
				pVo.setDestination(rs.getString("p_destination"));
				pVo.setDesDate(rs.getString("p_desdate"));
				pVo.setReturnPoint(rs.getString("p_returnpoint"));
				pVo.setReturnDate(rs.getString("p_returndate"));
				pVo.setAdultCharge(rs.getInt("p_adultcharge"));
				pVo.setChildCharge(rs.getInt("p_childcharge"));
				pVo.setSeason(rs.getString("p_season"));
				pVo.setHotelName(rs.getString("p_hotelname"));
				pVo.setSchedule(rs.getString("p_schedule"));
				pVo.setMaximum(rs.getInt("p_maximum"));
				pVo.setMinimum(rs.getInt("p_minimum"));
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
		return pVo;
	}

	// ��¥�� �Է¹޾� �˻�
	public ProductsVO getProductDateCheck(String date) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from product where p_startdate = ");
		sql.append("? ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductsVO pVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, date);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				pVo = new ProductsVO();
				pVo.setPrCode(rs.getInt("p_code"));
				pVo.setTourName(rs.getString("p_tourname"));
				pVo.setStartPoint(rs.getString("p_startpoint"));
				pVo.setStartDate(rs.getString("p_startdate"));
				pVo.setDestination(rs.getString("p_destination"));
				pVo.setDesDate(rs.getString("p_desdate"));
				pVo.setReturnPoint(rs.getString("p_returnpoint"));
				pVo.setReturnDate(rs.getString("p_returndate"));
				pVo.setAdultCharge(rs.getInt("p_adultcharge"));
				pVo.setChildCharge(rs.getInt("p_childcharge"));
				pVo.setSeason(rs.getString("p_season"));
				pVo.setHotelName(rs.getString("p_hotelname"));
				pVo.setSchedule(rs.getString("p_schedule"));
				pVo.setMaximum(rs.getInt("p_maximum"));
				pVo.setMinimum(rs.getInt("p_minimum"));
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
		return pVo;
	}

	// ��ǰ ������ ����
	public void getProductDelete(int no) throws Exception {
		// ������ ó���� ���� SQL ��
		StringBuffer sql = new StringBuffer();
		sql.append("delete from product where p_code = ?");
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			// ������ ���̽� ����
			con = DBUtil.getConnection();
			// SQL�� ������ ó�� ��� ����
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, no);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��ǰ ����");
				alert.setHeaderText("��ǰ ���� �Ϸ�");
				alert.setContentText("��ǰ ���� ����!!!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��ǰ ����");
				alert.setHeaderText("��ǰ ���� ����");
				alert.setContentText("��ǰ ���� ����!!!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// 6.�����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
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

	// �ִ� ������ �ѹ� ����
	public ProductsVO getPrNoMax() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(p_code) from product");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductsVO pVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				pVo = new ProductsVO();
				pVo.setPrCode(rs.getInt("max(p_code)"));
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

		return pVo;

	}

	// �ѱݾ��� ���ϱ� ���� ��ǰ�������� ���ο�ݰ� ���ο���� �޾ƿ�
	public ProductsVO getProductTotalPrice(int code) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select p_adultcharge, p_childcharge from product where p_code = ?");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductsVO pVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, code);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				pVo = new ProductsVO();
				pVo.setAdultCharge(rs.getInt("p_adultcharge"));
				pVo.setChildCharge(rs.getInt("p_childcharge"));
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

		return pVo;
	}

}
