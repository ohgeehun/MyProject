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

	// 1. 신규 상품 등록
	public ProductsVO getProductRegist(ProductsVO pvo) throws Exception {
		// 2. 데이터 처리를 위한 SQL 문
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
			// 3. DBUtil클래스의 getConnection() 메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();
			// 4. 입력받은 상품 정보를 처리하기 위하여 SQL문장 작성
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

			// 5.SQL문을 수행후 처리 결과를 얻어옴
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

	// 데이터베이스 에서 상품 전체 리스트
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

	// 데이터베이스에서 상품 테이블의 컬럼 갯수
	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from product");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// ResultSetMetaData 객체 변수 선언
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

	// 선택한 상품 정보 수정
	public ProductsVO getProductUpdate(ProductsVO pvo, int code) throws Exception {
		// 데이터 처리 SQL 문
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
				alert.setTitle("상품정보 수정");
				alert.setHeaderText("상품정보 수정 완료");
				alert.setContentText("상품정보 수정 성공!!!");
				alert.showAndWait();
				retval = new ProductsVO();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("상품정보 수정");
				alert.setHeaderText("상품정보 수정 실패");
				alert.setContentText("상품정보 수정 실패!!!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("getProductUpdate SQL 에러 " + e);
		} catch (Exception e) {
			System.out.println("getProductUpdate 일반 에러 " + e);
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

	// 상품번호을 입력받아 검색
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

	// 계절명을 입력받아 정보 검색
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

	// 날짜를 입력받아 검색
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

	// 상품 데이터 삭제
	public void getProductDelete(int no) throws Exception {
		// 데이터 처리를 위한 SQL 문
		StringBuffer sql = new StringBuffer();
		sql.append("delete from product where p_code = ?");
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			// 데이터 베이스 연결
			con = DBUtil.getConnection();
			// SQL문 수행후 처리 결과 얻어옴
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, no);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("상품 삭제");
				alert.setHeaderText("상품 삭제 완료");
				alert.setContentText("상품 삭제 성공!!!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("상품 삭제");
				alert.setHeaderText("상품 삭제 실패");
				alert.setContentText("상품 삭제 실패!!!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// 6.데이터베이스와의 연결에 사용되었던 오브젝트를 해제
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

	// 최대 시퀀스 넘버 얻어옴
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

	// 총금액을 구하기 위해 상품정보에서 대인요금과 소인요금을 받아옴
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
