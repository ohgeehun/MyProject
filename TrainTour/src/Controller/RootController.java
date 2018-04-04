package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.javafx.scene.control.SelectedCellsMap;

import Model.CustomerVO;
import Model.ProductsVO;
import Model.ReservationVO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class RootController implements Initializable {

	@FXML
	private TextField txtCusNo;
	@FXML
	private TextField txtProductCode;
	@FXML
	private Button btnCusPrSearch;
	@FXML
	private TextField txtCusName;
	@FXML
	private TextField txtCusPhone;
	@FXML
	private TextField txtAdult;
	@FXML
	private TextField txtChild;
	@FXML
	private DatePicker dpRegistDate;
	@FXML
	private TextField txtPessPhone;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtTotalPrice;
	@FXML
	private TextField txtAccountNumber;
	@FXML
	private Button btnCusTotalPrice;
	@FXML
	private Button btnCusReset;
	@FXML
	private Button btnCusEdit;
	@FXML
	private Button btnCusDelete;
	@FXML
	private Button btnCusRegist;
	@FXML
	private Button btnCusExit;
	@FXML
	private TextField txtCusSearchPhone;
	@FXML
	private TextField txtCusSearchName;
	@FXML
	private Button btnCusSearch;
	@FXML
	private Button btnCusSearchTotal;
	@FXML
	private TableView<ReservationVO> reservationTableView = new TableView<>();
	@FXML
	private TableView<CustomerVO> customerTableView = new TableView<>();
	@FXML
	private TextField txtPrCode;
	@FXML
	private TextField txtTourName;
	@FXML
	private TextField txtStartPoint;
	@FXML
	private DatePicker dpStartDate;
	@FXML
	private TextField txtEndPoint;
	@FXML
	private DatePicker dpEndDate;
	@FXML
	private TextField txtReturnPoint;
	@FXML
	private DatePicker dpReturnDate;
	@FXML
	private TextField txtHotelName;
	@FXML
	private TextField txtAdultPrice;
	@FXML
	private TextField txtChildPrice;
	@FXML
	private TextField txtSeason;
	@FXML
	private TextField txtMaximum;
	@FXML
	private TextField txtMinimum;
	@FXML
	private TextArea txtAreaSchedule;
	@FXML
	private Button btnPrReset;
	@FXML
	private Button btnPrEdit;
	@FXML
	private Button btnPrDelete;
	@FXML
	private Button btnPrRegist;
	@FXML
	private Button btnPrExit;
	@FXML
	private TextField txtPrSearchSeason;
	@FXML
	private TextField txtPrSearchPrCode;
	@FXML
	private DatePicker dpPrSearchDate;
	@FXML
	private Button btnPrSearch;
	@FXML
	private Button btnPrSearchTotal;
	@FXML
	private TableView<ProductsVO> productTableView = new TableView<>();
	@FXML
	private TextField txtSalesSearchPrCode;
	@FXML
	private Button btnSalesSearch;
	@FXML
	private Button btnSalesSearchTotal;
	@FXML
	private TableView<ReservationVO> salesTableView = new TableView<>();
	@FXML
	private Button btnPrCodeMax;
	@FXML
	private Button btnCusCodeMax;
	@FXML
	private BarChart barChart;
	@FXML
	private Button btnSaveFileDir;
	@FXML
	private Button btnExcel;
	@FXML
	private Button btnPDF;
	@FXML
	private TextField txtSaveFileDir;
	@FXML
	private Button btnBarChart;

	private Stage primaryStage;

	ObservableList<ProductsVO> productData = FXCollections.observableArrayList();
	ObservableList<ProductsVO> selectProduct = null; // 상품 테이블에서 선택한 정보 저장
	ObservableList<CustomerVO> customerData = FXCollections.observableArrayList();
	ObservableList<CustomerVO> selectedCustomer = null; // 고객 테이블에서 선택한 정보 저장
	ObservableList<ReservationVO> reservationData = FXCollections.observableArrayList();
	ObservableList<ReservationVO> selectedReservation = null; // 예약 테이블에서 선택한 정보 저장
	ObservableList<ReservationVO> salesData = FXCollections.observableArrayList();
	ObservableList barList = FXCollections.observableArrayList();// 상품별 총액 표현
	ObservableList barNullList = FXCollections.observableArrayList();// 상품별 총액 표현

	int selectedProductIndex; // 상품등록관리 상품 테이블에서 선택한 상품 정보 인덱스 저장
	int deleteProductNo = 0; // 상품등록관리 상품 테이블에서 삭제시 상품테이블에서 선택한 상품번호 저장
	int selectedReservationIndex; // 예약고객관리 예약 테이블에서 선택한 예약 정보 인덱스 저장
	int deleteReservationNo = 0; // 예약고객관리 예약 테이블에서 삭제시 예약테이블에서 선택한 예약번호 저장
	int selectedCustomerIndex; // 예약고객관리 고객 테이블에서 선택한 고객 정보 인덱스 저장
	int deleteCustomerNo = 0; // 예약고객관리 고객 테이블에서 삭제시 고객테이블에서 선택한 고객번호 저장

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		txtProductCode.setDisable(true);
		txtPrCode.setDisable(true);
		txtCusNo.setDisable(true);
		txtTotalPrice.setDisable(true);
		dpStartDate.setValue(LocalDate.now());
		dpEndDate.setValue(LocalDate.now());
		dpReturnDate.setValue(LocalDate.now());
		dpPrSearchDate.setValue(LocalDate.now());
		dpRegistDate.setValue(LocalDate.now());
		dpStartDate.setEditable(false);
		dpEndDate.setEditable(false);
		dpReturnDate.setEditable(false);
		dpRegistDate.setEditable(false);
		dpPrSearchDate.setEditable(false);
		btnExcel.setDisable(true);
		btnPDF.setDisable(true);

		DecimalFormat format = new DecimalFormat("###");

		// 숫자 입력시 길이 제한 이벤트 처리
		txtPrCode.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 5) {
				return null;
			} else {
				return event;
			}
		}));
		txtAdultPrice.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 7) {
				return null;
			} else {
				return event;
			}
		}));
		txtChildPrice.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 7) {
				return null;
			} else {
				return event;
			}
		}));
		txtMaximum.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 3) {
				return null;
			} else {
				return event;
			}
		}));
		txtMinimum.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 3) {
				return null;
			} else {
				return event;
			}
		}));

		txtAdult.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 3) {
				return null;
			} else {
				return event;
			}
		}));

		txtChild.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 3) {
				return null;
			} else {
				return event;
			}
		}));

		txtCusPhone.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 12) {
				return null;
			} else {
				return event;
			}
		}));

		txtPessPhone.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 12) {
				return null;
			} else {
				return event;
			}
		}));

		productTableView.setEditable(false);

		// productTableView 테이블 뷰 컬럼이름 설정

		TableColumn colPrCode = new TableColumn("상품코드");
		colPrCode.setMaxWidth(80);
		colPrCode.setStyle("-fx-allignment:CENTER");
		colPrCode.setCellValueFactory(new PropertyValueFactory<>("prCode"));

		TableColumn colTourName = new TableColumn("여행명");
		colTourName.setMaxWidth(80);
		colTourName.setCellValueFactory(new PropertyValueFactory<>("tourName"));

		TableColumn colStartPoint = new TableColumn("출발지");
		colStartPoint.setMaxWidth(80);
		colStartPoint.setCellValueFactory(new PropertyValueFactory<>("startPoint"));

		TableColumn colStartDate = new TableColumn("출발날짜");
		colStartDate.setMaxWidth(140);
		colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));

		TableColumn colDestination = new TableColumn("도착지");
		colDestination.setMaxWidth(80);
		colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));

		TableColumn colDesDate = new TableColumn("도착날짜");
		colDesDate.setMaxWidth(140);
		colDesDate.setCellValueFactory(new PropertyValueFactory<>("desDate"));

		TableColumn colReturnPoint = new TableColumn("복귀장소");
		colReturnPoint.setMaxWidth(80);
		colReturnPoint.setCellValueFactory(new PropertyValueFactory<>("returnPoint"));

		TableColumn colReturnDate = new TableColumn("복귀날짜");
		colReturnDate.setMaxWidth(140);
		colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

		TableColumn colAdultCharge = new TableColumn("대인요금");
		colAdultCharge.setMaxWidth(60);
		colAdultCharge.setCellValueFactory(new PropertyValueFactory<>("adultCharge"));

		TableColumn colChildCharge = new TableColumn("소인요금");
		colChildCharge.setMaxWidth(60);
		colChildCharge.setCellValueFactory(new PropertyValueFactory<>("childCharge"));

		TableColumn colSeason = new TableColumn("계절");
		colSeason.setMaxWidth(80);
		colSeason.setCellValueFactory(new PropertyValueFactory<>("season"));

		TableColumn colHotelName = new TableColumn("호텔명");
		colHotelName.setMaxWidth(80);
		colHotelName.setCellValueFactory(new PropertyValueFactory<>("hotelName"));

		TableColumn colSchedule = new TableColumn("일정");
		colSchedule.setMaxWidth(60);
		colSchedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));

		TableColumn colMaximum = new TableColumn("Max");
		colMaximum.setMaxWidth(43);
		colMaximum.setCellValueFactory(new PropertyValueFactory<>("maximum"));

		TableColumn colMinimum = new TableColumn("Min");
		colMinimum.setMaxWidth(43);
		colMinimum.setCellValueFactory(new PropertyValueFactory<>("minimum"));
		// 상품관리 테이블의 테이블뷰
		productTableView.getColumns().addAll(colPrCode, colSeason, colTourName, colStartPoint, colStartDate,
				colDestination, colDesDate, colReturnDate, colAdultCharge, colChildCharge, colHotelName, colMaximum,
				colMinimum);

		// 예약고객관리 테이블의 테이블 뷰 칼럼이름 설정
		TableColumn colCusPrCode = new TableColumn("상품코드");
		colCusPrCode.setMaxWidth(80);
		colCusPrCode.setStyle("-fx-allignment:CENTER");
		colCusPrCode.setCellValueFactory(new PropertyValueFactory<>("prCode"));

		TableColumn colResNo = new TableColumn("No.");
		colResNo.setMaxWidth(50);
		colResNo.setCellValueFactory(new PropertyValueFactory<>("resNo"));

		TableColumn colCusNo = new TableColumn("No.");
		colCusNo.setMaxWidth(50);
		colCusNo.setCellValueFactory(new PropertyValueFactory<>("cusNo"));

		TableColumn colName = new TableColumn("성명");
		colName.setMaxWidth(80);
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn colPhone = new TableColumn("휴대폰");
		colPhone.setMaxWidth(165);
		colPhone.setPrefWidth(165);
		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

		TableColumn colAdult = new TableColumn("대인");
		colAdult.setMaxWidth(50);
		colAdult.setCellValueFactory(new PropertyValueFactory<>("adult"));

		TableColumn colChild = new TableColumn("소인");
		colChild.setMaxWidth(50);
		colChild.setCellValueFactory(new PropertyValueFactory<>("child"));

		TableColumn colEmail = new TableColumn("이메일");
		colEmail.setMaxWidth(200);
		colEmail.setPrefWidth(200);
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

		TableColumn colResDate = new TableColumn("등록날짜");
		colResDate.setMaxWidth(175);
		colResDate.setPrefWidth(175);
		colResDate.setCellValueFactory(new PropertyValueFactory<>("resdate"));

		TableColumn colTotalPrice = new TableColumn("총금액");
		colTotalPrice.setMaxWidth(73);
		colTotalPrice.setPrefWidth(73);
		colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalprice"));

		TableColumn colSalesPrCode = new TableColumn("상품코드");
		colSalesPrCode.setMaxWidth(100);
		colSalesPrCode.setPrefWidth(100);
		colSalesPrCode.setCellValueFactory(new PropertyValueFactory<>("totalPrCode"));

		TableColumn colSalesAdult = new TableColumn("총 대인인원");
		colSalesAdult.setMaxWidth(250);
		colSalesAdult.setPrefWidth(250);
		colSalesAdult.setCellValueFactory(new PropertyValueFactory<>("totalAdult"));

		TableColumn colSalesChild = new TableColumn("총 소인인원");
		colSalesChild.setMaxWidth(250);
		colSalesChild.setPrefWidth(250);
		colSalesChild.setCellValueFactory(new PropertyValueFactory<>("totalChild"));

		TableColumn colSalesTotalPrice = new TableColumn("총 금액");
		colSalesTotalPrice.setMaxWidth(360);
		colSalesTotalPrice.setPrefWidth(360);
		colSalesTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalAllPrice"));

		// 매출관리 테이블의 테이블뷰
		salesTableView.getColumns().addAll(colSalesPrCode, colSalesAdult, colSalesChild, colSalesTotalPrice);

		// 예약관리 테이블의 테이블뷰
		customerTableView.getColumns().addAll(colCusNo, colName, colPhone, colEmail);
		reservationTableView.getColumns().addAll(colResNo, colCusPrCode, colAdult, colChild, colTotalPrice, colResDate);
		// 상품 전체 정보
		PrTotalList();
		CusTotalList();
		ResTotalList();
		SalesTotalList();
		productTableView.setItems(productData);
		customerTableView.setItems(customerData);
		reservationTableView.setItems(reservationData);
		salesTableView.setItems(salesData);

		// 상품등록관리 전체보기 버튼
		btnPrSearchTotal.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				try {
					productData.removeAll(productData);
					// 상품 전체 정보
					PrTotalList();
				} catch (Exception e) {

				}
			}
		});
		// 예약고객관리 전체보기 버튼
		btnCusSearchTotal.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				try {
					reservationData.removeAll(reservationData);
					customerData.removeAll(customerData);
					ResTotalList();
					CusTotalList();
				} catch (Exception e) {

				}
			}
		});
		btnSalesSearchTotal.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				try {
					salesData.removeAll(salesData);
					SalesTotalList();
				} catch (Exception e) {

				}
			}
		});

		// 상품등록관리 상품 테이블뷰에서 상품정보 클릭시 기능
		productTableView.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				try {
					// TODO Auto-generated method stub
					selectProduct = productTableView.getSelectionModel().getSelectedItems();
					deleteProductNo = selectProduct.get(0).getPrCode();

				} catch (Exception e) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("상품 선택");
					alert.setHeaderText("선택한 상품이 없습니다.");
					alert.setContentText("상품을 추가한 후에 선택하세요.");
					alert.showAndWait();
				}
				return;
			}

		});
		// 예약고객관리 예약 테이블뷰에서 예약정보 클릭시 기능
		reservationTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() != 2) {
					try {
						// TODO Auto-generated method stub
						selectedReservation = reservationTableView.getSelectionModel().getSelectedItems();
						deleteReservationNo = selectedReservation.get(0).getCusNo();

					} catch (Exception e) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("예약정보 선택");
						alert.setHeaderText("선택한 예약정보가 없습니다.");
						alert.setContentText("예약정보를 추가한 후에 선택하세요.");
						alert.showAndWait();
					}
					return;
				}
			}

		});
		// 예약고객관리 고객 테이블에서 고객정보 클릭시 가능

		customerTableView.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() != 2) {
					try { // TODO Auto-generated method stub selectedCustomer =
						selectedCustomer = customerTableView.getSelectionModel().getSelectedItems();
						deleteCustomerNo = selectedCustomer.get(0).getCusNo();

					} catch (Exception e) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("예약고객 선택");
						alert.setHeaderText("선택한 예약고객 이 없습니다.");
						alert.setContentText("예약고객을 추가한 후에 선택하세요.");
						alert.showAndWait();
					}
					return;
				}
			}

		});

		// 상품등록관리 등록버튼 저장기능
		btnPrRegist.setOnAction(event -> handlerBtnPrRegistAction(event));
		// 상품 삭제 버튼
		btnPrDelete.setOnAction(event -> handlerBtnPrDeleteAction(event));
		// 예약고객관리 삭제 버튼
		btnCusDelete.setOnAction(event -> handlerBtnCusDeleteAction(event));
		// 종료 버튼
		btnPrExit.setOnAction(event -> {
			Platform.exit();
		});
		// 예약고객관리 종료 버튼
		btnCusExit.setOnAction(event -> {
			Platform.exit();
		});
		// 상품등록관리 번호 입력 버튼
		btnPrCodeMax.setOnAction(event -> handlerBtnCodeMaxAction(event));
		// 상품등록관리 검색 버튼
		btnPrSearch.setOnAction(event -> handlerBtnPrSearchAction(event));
		// 상품등록관리 초기화 버튼
		btnPrReset.setOnAction(event -> handlerBtnPrResetAction(event));
		// 상품등록관리 수정 버튼
		btnPrEdit.setOnAction(event -> handlerBtnPrEditAction(event));
		// 예약고객관리 등록버튼
		btnCusRegist.setOnAction(event -> handlerBtnCusRegistAction(event));
		// 예약고객관리 초기화 버튼
		btnCusReset.setOnAction(event -> handlerBtnCusResetAction(event));
		// 예약고객관리 고객번호 버튼
		btnCusCodeMax.setOnAction(event -> handlerBtnCusCodeMaxAction(event));
		// 예약고객관리 상품코드 검색 버튼
		btnCusPrSearch.setOnAction(event -> handlerBtnCusPrSearchAction(event));
		// 예약고객관리 총금액 계산 버튼
		btnCusTotalPrice.setOnAction(event -> handlerBtnCusTotalPriceAction(event));
		// 예약고객관리 검색 버튼
		btnCusSearch.setOnAction(event -> handlerBtnCusSearchAction(event));
		// 예약고객관리 수정 버튼
		btnCusEdit.setOnAction(event -> handlerBtnCusEditAction(event));
		// 매출관리 검색 버튼
		btnSalesSearch.setOnAction(event -> handlerBtnSalesSearchAction(event));
		// 엑셀파일 생성
		btnExcel.setOnAction(event -> handlerBtnExcelFileAction(event));
		// 파일저장폴더 선택
		btnSaveFileDir.setOnAction(event -> handlerBtnSaveFileAction(event));
		// PDF파일 생성
		btnPDF.setOnAction(event -> handlerBtnPDFAction(event));
		// 차트 화면 보기
		btnBarChart.setOnAction(event -> handlerBtnBarChartAction(event));
		// 매출관리 바차트 설정

	}

	// 바차트 확인
	public void handlerBtnBarChartAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnCusRegist.getScene().getWindow());
			dialog.setTitle("막대 그래프 이미지 저장");

			Parent parent = FXMLLoader.load(getClass().getResource("/View/barchart.fxml"));

			BarChart barChart = (BarChart) parent.lookup("#barChart");

			try {
				XYChart.Series seriesPrCode = new XYChart.Series();
				for (int i = 0; i < salesData.size(); i++) {
					barList.add(new XYChart.Data<>(salesData.get(i).getTotalPrCode() + "",
							salesData.get(i).getTotalAllPrice()));
				}
				seriesPrCode.setData(barList);
				barChart.getData().add(seriesPrCode);

				Button btnClose = (Button) parent.lookup("#btnClose");
				btnClose.setOnAction(e -> dialog.close());

				Scene scene = new Scene(parent);
				dialog.setScene(scene);
				dialog.show();

				WritableImage snapShot = scene.snapshot(null);
				ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png",
						new File("chartImage/salesBarChart.png"));

			} catch (Exception e) {
				System.out.println(e.toString());
			}
		} catch (Exception e) {

		}
	}

	// PDF 차트 이미지 포함
	public void handlerBtnPDFAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {

			FXMLLoader loaderPdf = new FXMLLoader();
			loaderPdf.setLocation(getClass().getResource("/View/confirm.fxml"));

			Stage dialogPdf = new Stage(StageStyle.UTILITY);
			dialogPdf.initModality(Modality.WINDOW_MODAL);
			dialogPdf.initOwner(btnPDF.getScene().getWindow());
			dialogPdf.setTitle("매출관리표 PDF 차트 이미지 선택");

			Parent parentPdf = (Parent) loaderPdf.load();

			Button btnPdfSave = (Button) parentPdf.lookup("#btnPdfSave");
			CheckBox cbBarImage = (CheckBox) parentPdf.lookup("#cbBarImage");

			Scene scene = new Scene(parentPdf);
			dialogPdf.setScene(scene);
			dialogPdf.setResizable(false);
			dialogPdf.show();

			// 차트 이미지 포함
			btnPdfSave.setOnAction(e -> {

				try {
					// pdf document 선언.
					// (Rectangle pageSize, float marginLeft, float
					// marginRight, float
					// marginTop, float marginBottom)
					Document document = new Document(PageSize.A4, 0, 0, 30, 30);
					// pdf 파일을 저장할 공간을 선언. pdf파일이 생성된다. 그후 스트림으로 저장.
					String strReportPDFName = "Sales_" + System.currentTimeMillis() + ".pdf";
					PdfWriter.getInstance(document,
							new FileOutputStream(txtSaveFileDir.getText() + "\\" + strReportPDFName));
					// document를 열어 pdf문서를 쓸수있도록한다..
					document.open();
					// 한글지원폰트 설정..
					BaseFont bf = BaseFont.createFont("font/MALGUN.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
					Font font = new Font(bf, 8, Font.NORMAL);
					Font font2 = new Font(bf, 14, Font.BOLD);
					// 타이틀
					Paragraph title = new Paragraph("매출 관리표", font2);
					// 중간정렬
					title.setAlignment(Element.ALIGN_CENTER);
					// 문서에 추가
					document.add(title);
					document.add(new Paragraph("\r\n"));
					// 생성 날짜
					LocalDate date = dpRegistDate.getValue();
					Paragraph writeDay = new Paragraph(date.toString(), font);
					// 오른쪽 정렬
					writeDay.setAlignment(Element.ALIGN_RIGHT);
					// 문서에 추가
					document.add(writeDay);
					document.add(new Paragraph("\r\n"));

					// 테이블생성 Table객체보다 PdfPTable객체가 더 정교하게 테이블을 만들수 있다.
					// 생성자에 컬럼수를 써준다.
					PdfPTable table = new PdfPTable(4);
					// 각각의 컬럼에 width를 정한다.
					table.setWidths(new int[] { 30, 30, 30, 50 });
					// 컬럼 타이틀..
					PdfPCell header1 = new PdfPCell(new Paragraph("상품번호", font));
					PdfPCell header2 = new PdfPCell(new Paragraph("대인", font));
					PdfPCell header3 = new PdfPCell(new Paragraph("소인", font));
					PdfPCell header4 = new PdfPCell(new Paragraph("총 금액", font));

					// 가로정렬
					header1.setHorizontalAlignment(Element.ALIGN_CENTER);
					header2.setHorizontalAlignment(Element.ALIGN_CENTER);
					header3.setHorizontalAlignment(Element.ALIGN_CENTER);
					header4.setHorizontalAlignment(Element.ALIGN_CENTER);

					// 세로정렬
					header1.setVerticalAlignment(Element.ALIGN_MIDDLE);
					header2.setVerticalAlignment(Element.ALIGN_MIDDLE);
					header3.setVerticalAlignment(Element.ALIGN_MIDDLE);
					header4.setVerticalAlignment(Element.ALIGN_MIDDLE);

					// 테이블에 추가..
					table.addCell(header1);
					table.addCell(header2);
					table.addCell(header3);
					table.addCell(header4);

					// DB 연결 및 리스트 선택
					ReservationDAO rDao = new ReservationDAO();
					ReservationVO rVo = new ReservationVO();
					ArrayList<ReservationVO> list;
					list = rDao.getSalesTotal();
					int rowCount = list.size();

					PdfPCell cell1 = null;
					PdfPCell cell2 = null;
					PdfPCell cell3 = null;
					PdfPCell cell4 = null;

					for (int index = 0; index < rowCount; index++) {

						rVo = list.get(index);

						cell1 = new PdfPCell(new Paragraph(rVo.getTotalPrCode() + "", font));
						cell2 = new PdfPCell(new Paragraph(rVo.getTotalAdult() + "", font));
						cell3 = new PdfPCell(new Paragraph(rVo.getTotalChild() + "", font));
						cell4 = new PdfPCell(new Paragraph(rVo.getTotalAllPrice() + "", font));

						// 가로정렬
						cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

						// 세로정렬
						cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

						// 테이블에 셀 추가
						table.addCell(cell1);
						table.addCell(cell2);
						table.addCell(cell3);
						table.addCell(cell4);
					}

					// 문서에 테이블추가..
					document.add(table);
					document.add(new Paragraph("\r\n"));
					Alert alert = new Alert(AlertType.INFORMATION);
					if (cbBarImage.isSelected()) {

						// 막대 그래프 이미지 추가
						Paragraph barImageTitle = new Paragraph("매출 관리표 막대 그래프", font);
						barImageTitle.setAlignment(Element.ALIGN_CENTER);
						document.add(barImageTitle);
						document.add(new Paragraph("\r\n"));
						final String barImageUrl = "chartImage/salesBarChart.png";
						// 기존에 javafx.scene.image.Image 객체을 사용하고 있어 충돌이 생겨 아래와
						// 같이 사용함.

						com.itextpdf.text.Image barImage;
						try {

							if (com.itextpdf.text.Image.getInstance(barImageUrl) != null) {
								barImage = com.itextpdf.text.Image.getInstance(barImageUrl);
								barImage.setAlignment(Element.ALIGN_CENTER);
								barImage.scalePercent(30f);
								document.add(barImage);

							}
						} catch (IOException ee) {

						}

					}
					// 문서를 닫는다.. 쓰기 종료..
					document.close();

					dialogPdf.close();
					txtSaveFileDir.clear();
					btnPDF.setDisable(true);
					btnExcel.setDisable(true);

					alert.setTitle("PDF 파일 생성");
					alert.setHeaderText("학생 목록 PDF 파일 생성 성공.");
					alert.setContentText("학생 목록 PDF 파일 .");
					alert.showAndWait();

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (DocumentException e1) {
					e1.printStackTrace();

				} catch (IOException e1) {
					e1.printStackTrace();
				}

			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 파일 저장 폴더 선택
	public void handlerBtnSaveFileAction(ActionEvent event) {
		// TODO Auto-generated method stub
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		final File selectedDirectory = directoryChooser.showDialog(primaryStage);

		if (selectedDirectory != null) {
			txtSaveFileDir.setText(selectedDirectory.getAbsolutePath());
			btnExcel.setDisable(false);
			btnPDF.setDisable(false);
		}
	}

	// 엑셀 파일 생성
	public void handlerBtnExcelFileAction(ActionEvent event) {
		// TODO Auto-generated method stub
		ReservationDAO rDao = new ReservationDAO();
		boolean saveSuccess;

		ArrayList<ReservationVO> list;
		list = rDao.getSalesTotal();

		SalesExcel excelWriter = new SalesExcel();

		// xlsx 파일쓰기
		saveSuccess = excelWriter.xslxWriter(list, txtSaveFileDir.getText());
		if (saveSuccess) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("엑셀 파일 생성");
			alert.setHeaderText("판매 목록 엑셀 파일 생성 성공.");
			alert.setContentText("판매 목록 엑셀 파일.");
			alert.showAndWait();
		}
		txtSaveFileDir.clear();
		btnExcel.setDisable(true);
		btnPDF.setDisable(true);
	}

	// 예약고객관리 총금액 버튼
	public void handlerBtnCusTotalPriceAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			ProductDAO pDao = null;
			pDao = new ProductDAO();
			ReservationDAO rDao = null;
			rDao = new ReservationDAO();
			int total = pDao.getProductTotalPrice(Integer.parseInt(txtProductCode.getText())).getAdultCharge()
					* Integer.parseInt(txtAdult.getText())
					+ pDao.getProductTotalPrice(Integer.parseInt(txtProductCode.getText())).getChildCharge()
							* Integer.parseInt(txtChild.getText());
			txtTotalPrice.setText(total + "");
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("상품 또는 인원 정보");
			alert.setHeaderText("상품 또는 인원 정보를 정확히 입력해주세요.");
			alert.setContentText("상품 또는 인원 정보 오류!!!");
			alert.showAndWait();
		}
	}

	// 예약고객관리 상품코드 검색 버튼
	public void handlerBtnCusPrSearchAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			// 새로운 모달창을 띄움
			try {
				// 새로운 모달창을 띄움
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/View/prSearch.fxml"));

				Stage dialog = new Stage(StageStyle.UTILITY);
				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.initOwner(btnCusRegist.getScene().getWindow());
				dialog.setTitle("상품번호 검색");

				Parent parentSearch = (Parent) loader.load();
				ProductsVO productSearch = productTableView.getSelectionModel().getSelectedItem();
				selectedProductIndex = productTableView.getSelectionModel().getSelectedIndex();

				TextField prSearchSeason = (TextField) parentSearch.lookup("#txtPrSearchSeason");
				TextField prSearchPrCode = (TextField) parentSearch.lookup("#txtPrSearchPrCode");
				DatePicker prSearchDate = (DatePicker) parentSearch.lookup("#dpPrSearchDate");
				prSearchDate.setValue(LocalDate.now());

				TableView<ProductsVO> productTableView = (TableView) parentSearch.lookup("#productTableView");
				productTableView.setEditable(false);

				// 모달창에 productTableView 테이블 뷰 컬럼이름 설정
				TableColumn colPrCode = new TableColumn("상품코드");
				colPrCode.setMaxWidth(80);
				colPrCode.setStyle("-fx-allignment:CENTER");
				colPrCode.setCellValueFactory(new PropertyValueFactory<>("prCode"));

				TableColumn colTourName = new TableColumn("여행명");
				colTourName.setMaxWidth(80);
				colTourName.setCellValueFactory(new PropertyValueFactory<>("tourName"));

				TableColumn colStartPoint = new TableColumn("출발지");
				colStartPoint.setMaxWidth(80);
				colStartPoint.setCellValueFactory(new PropertyValueFactory<>("startPoint"));

				TableColumn colStartDate = new TableColumn("출발날짜");
				colStartDate.setMaxWidth(140);
				colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));

				TableColumn colDestination = new TableColumn("도착지");
				colDestination.setMaxWidth(80);
				colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));

				TableColumn colDesDate = new TableColumn("도착날짜");
				colDesDate.setMaxWidth(140);
				colDesDate.setCellValueFactory(new PropertyValueFactory<>("desDate"));

				TableColumn colReturnPoint = new TableColumn("복귀장소");
				colReturnPoint.setMaxWidth(80);
				colReturnPoint.setCellValueFactory(new PropertyValueFactory<>("returnPoint"));

				TableColumn colReturnDate = new TableColumn("복귀날짜");
				colReturnDate.setMaxWidth(140);
				colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

				TableColumn colAdultCharge = new TableColumn("대인요금");
				colAdultCharge.setMaxWidth(60);
				colAdultCharge.setCellValueFactory(new PropertyValueFactory<>("adultCharge"));

				TableColumn colChildCharge = new TableColumn("소인요금");
				colChildCharge.setMaxWidth(60);
				colChildCharge.setCellValueFactory(new PropertyValueFactory<>("childCharge"));

				TableColumn colSeason = new TableColumn("계절");
				colSeason.setMaxWidth(80);
				colSeason.setCellValueFactory(new PropertyValueFactory<>("season"));

				TableColumn colHotelName = new TableColumn("호텔명");
				colHotelName.setMaxWidth(80);
				colHotelName.setCellValueFactory(new PropertyValueFactory<>("hotelName"));

				TableColumn colSchedule = new TableColumn("일정");
				colSchedule.setMaxWidth(60);
				colSchedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));

				TableColumn colMaximum = new TableColumn("Max");
				colMaximum.setMaxWidth(43);
				colMaximum.setCellValueFactory(new PropertyValueFactory<>("maximum"));

				TableColumn colMinimum = new TableColumn("Min");
				colMinimum.setMaxWidth(43);
				colMinimum.setCellValueFactory(new PropertyValueFactory<>("minimum"));
				// 상품관리 테이블의 테이블뷰
				productTableView.getColumns().addAll(colPrCode, colSeason, colTourName, colStartPoint, colStartDate,
						colDestination, colDesDate, colReturnDate, colAdultCharge, colChildCharge, colHotelName,
						colMaximum, colMinimum);
				productTableView.setItems(productData);
				// 상품번호 검색에서 계절명 or 여행명 검색버튼
				Button btnPrSearch = (Button) parentSearch.lookup("#btnPrSearch");
				// 상품번호 검색에서 전체보기 버튼
				Button btnPrSearchTotal = (Button) parentSearch.lookup("#btnPrSearchTotal");
				// 상품번호 검색에서 선택버튼
				Button btnPrOk = (Button) parentSearch.lookup("#btnPrOk");

				productTableView.setOnMousePressed(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						try {
							// TODO Auto-generated method stub
							selectProduct = productTableView.getSelectionModel().getSelectedItems();
							deleteProductNo = selectProduct.get(0).getPrCode();
						} catch (Exception e) {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("상품 선택");
							alert.setHeaderText("선택한 상품이 없습니다.");
							alert.setContentText("상품을 추가한 후에 선택하세요.");
							alert.showAndWait();
						}
						return;
					}

				});

				btnPrSearchTotal.setOnAction(e1 -> {
					try {
						productData.removeAll(productData);
						// 상품 전체 정보
						PrTotalList();
					} catch (Exception e2) {

					}
				});

				btnPrOk.setOnAction(e1 -> {
					try {
						if (selectProduct.get(0).getPrCode() + "" == null) {
							PrTotalList();
							return;
						} else {
							txtProductCode.setText(selectProduct.get(0).getPrCode() + "");
							productData.removeAll(productData);
							dialog.close();
							// 상품 전체 정보
							PrTotalList();
						}
					} catch (Exception e2) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("선택하세요.");
						alert.setHeaderText("상품을 선택해주세요!");
						alert.setContentText("상품을 선택해주세요!!!");
						alert.showAndWait();
					}
				});

				btnPrSearch.setOnAction(e1 -> {
					ProductsVO pVoSeason = new ProductsVO();
					ProductsVO pVoPrCode = new ProductsVO();
					ProductsVO pVoDate = new ProductsVO();
					ProductDAO pDao = null;

					Object[][] totalData = null;

					String searchPrCode = "0";
					String searchDate = "";
					String searchSeason = "";
					boolean searchResult = false;

					// 상품계절 or 상품명 or 날짜로 검색
					try {
						searchSeason = prSearchSeason.getText();
						if (!prSearchPrCode.getText().equals("")) {
							searchPrCode = prSearchPrCode.getText();
						}
						searchDate = dpPrSearchDate.getValue() + "";

						pDao = new ProductDAO();
						pVoSeason = pDao.getProductSeasonCheck(searchSeason);
						pVoPrCode = pDao.getProductPrCodeCheck(Integer.parseInt(searchPrCode));
						pVoDate = pDao.getProductDateCheck(searchDate);
						if (searchSeason.equals("") && searchPrCode.equals("0") && searchDate.equals("")) {
							searchResult = true;
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("상품 정보 검색");
							alert.setHeaderText("상품의 계절 또는 상품명을 정확히 입력하시오.");
							alert.setContentText("다음에는 주의하세요!");
							alert.showAndWait();
						}
						if (!searchSeason.equals("") && (pVoSeason != null) && searchPrCode.equals("0")) {
							ArrayList<String> title;
							ArrayList<ProductsVO> list;

							title = pDao.getColumnName();
							int columnCount = title.size();

							list = pDao.getProductTotal();
							int rowCount = list.size();

							totalData = new Object[rowCount][columnCount];

							if (pVoSeason.getSeason().equals(searchSeason)) {
								prSearchSeason.clear();
								productData.removeAll(productData);
								for (int index = 0; index < rowCount; index++) {
									pVoSeason = list.get(index);
									if (pVoSeason.getSeason().equals(searchSeason)) {
										productData.add(pVoSeason);
										searchResult = true;
									}
								}
							}
						}

						if (!searchPrCode.equals("0") && (pVoPrCode != null) && searchSeason.equals("")) {
							ArrayList<String> title;
							ArrayList<ProductsVO> list;

							title = pDao.getColumnName();
							int columnCount = title.size();

							list = pDao.getProductTotal();
							int rowCount = list.size();

							totalData = new Object[rowCount][columnCount];

							if (pVoPrCode.getPrCode() == Integer.parseInt(searchPrCode)) {
								prSearchPrCode.clear();
								productData.removeAll(productData);
								for (int index = 0; index < rowCount; index++) {
									pVoPrCode = list.get(index);
									if (pVoPrCode.getPrCode() == Integer.parseInt(searchPrCode)) {
										productData.add(pVoPrCode);
										searchResult = true;
									}
								}
							}
						}

						if (!searchDate.equals("") && (pVoDate != null) && searchPrCode.equals("0")
								&& searchSeason.equals("")) {
							ArrayList<String> title;
							ArrayList<ProductsVO> list;
							title = pDao.getColumnName();
							int columnCount = title.size();

							list = pDao.getProductTotal();
							int rowCount = list.size();

							totalData = new Object[rowCount][columnCount];
							if (pVoDate.getStartDate().substring(0, 10).equals(searchDate)) {
								productData.removeAll(productData);
								for (int index = 0; index < rowCount; index++) {
									pVoDate = list.get(index);
									if (pVoDate.getStartDate().equals(searchDate)) {
										productData.add(pVoDate);
										searchResult = true;
									}
								}
							}
						}
						if (!searchResult) {
							prSearchPrCode.clear();
							prSearchSeason.clear();
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("상품 정보 검색");
							alert.setHeaderText("상품 정보가 리스트에 없습니다.");
							alert.setContentText(searchDate);
							alert.showAndWait();
						}
					} catch (Exception e2) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("상품 정보 검색 오류");
						alert.setHeaderText("상품 정보 검색에 오류가 발생하였습니다.");
						alert.setContentText("다시 하세요.");
						alert.showAndWait();
						System.out.println(e1.toString());
					}
				});

				Scene scene = new Scene(parentSearch);
				dialog.setScene(scene);
				dialog.setResizable(false);
				dialog.show();
			} catch (IOException e1) {
				System.out.println(e1.toString());
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	// 예약고객관리 고객번호 확인 버튼
	public void handlerBtnCusCodeMaxAction(ActionEvent event) {
		// TODO Auto-generated method stub
		CustomerDAO cDao = null;
		cDao = new CustomerDAO();

		try {
			txtCusNo.setText(cDao.getCusNoMax().getCusNo() + 1 + "");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 예약고객관리 초기화 버튼
	public void handlerBtnCusResetAction(ActionEvent event) {
		// TODO Auto-generated method stub
		CusResInit();
	}

	// 상품등록관리 등록 버튼
	public void handlerBtnPrRegistAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			productData.removeAll(productData);
			ProductsVO pVo = null;
			ProductDAO pDao = new ProductDAO();
			pVo = new ProductsVO(txtTourName.getText(), txtStartPoint.getText(), dpStartDate.getValue() + "",
					txtEndPoint.getText(), dpEndDate.getValue() + "", txtReturnPoint.getText(),
					dpReturnDate.getValue() + "", Integer.parseInt(txtAdultPrice.getText()),
					Integer.parseInt(txtChildPrice.getText()), txtSeason.getText(), txtHotelName.getText(),
					txtAreaSchedule.getText(), Integer.parseInt(txtMaximum.getText()),
					Integer.parseInt(txtMinimum.getText()));
			pDao = new ProductDAO();
			pDao.getProductRegist(pVo);

			if (pDao != null) {
				PrTotalList();

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("상품 정보 입력");
				alert.setHeaderText(txtTourName.getText() + " 상품의 정보가 추가되었습니다.");
				alert.setContentText(dpEndDate.getValue() + "");
				alert.showAndWait();
				PrInit();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("상품 정보 입력");
			alert.setHeaderText("상품 정보를 정확히 입력하세요.");
			alert.setContentText("다음에는 주의하세요!!");
			alert.showAndWait();
		}
	};

	// 예약고객관리 등록 버튼
	public void handlerBtnCusRegistAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			customerData.removeAll(customerData);
			CustomerVO cVo = null;
			CustomerDAO cDao = new CustomerDAO();
			// 고객 등록
			cVo = new CustomerVO(txtCusName.getText(), txtCusPhone.getText(), txtPessPhone.getText(),
					txtEmail.getText(), txtAccountNumber.getText());
			cDao = new CustomerDAO();
			// 고객 등록 SQL문
			cDao.getCustomerRegist(cVo);
			reservationData.removeAll(reservationData);
			ReservationVO rVo = null;
			ReservationDAO rDao = new ReservationDAO();
			salesData.removeAll(salesData);
			// 예약 등록
			rVo = new ReservationVO(dpRegistDate.getValue() + "", Integer.parseInt(txtAdult.getText()),
					Integer.parseInt(txtChild.getText()), Integer.parseInt(txtTotalPrice.getText()),
					Integer.parseInt(txtProductCode.getText()), cDao.getCusNoMax().getCusNo());
			rDao = new ReservationDAO();
			// 예약 등록 SQL문
			rDao.getReservationRegist(rVo);
			if (cDao != null && rDao != null) {
				CusTotalList();
				ResTotalList();
				SalesTotalList();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("예약 정보 입력");
				alert.setHeaderText(txtCusName.getText() + " 고객의 정보가 추가되었습니다.");
				alert.setContentText("예약 정보 추가를 성공했습니다.");
				alert.showAndWait();
				CusResInit();
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("상품 정보 입력");
			alert.setHeaderText("상품 정보를 정확히 입력하세요.");
			alert.setContentText(dpRegistDate.getValue() + "");
			alert.showAndWait();
		}
	}

	// 상품등록관리 초기화 버튼
	public void handlerBtnPrResetAction(ActionEvent event) {
		// TODO Auto-generated method stub
		PrInit();
	}

	// 상품등록관리 초기화 메소드
	public void PrInit() {
		// TODO Auto-generated method stub
		txtPrCode.clear();
		txtTourName.clear();
		txtStartPoint.clear();
		dpStartDate.setValue(LocalDate.now());
		txtEndPoint.clear();
		dpEndDate.setValue(LocalDate.now());
		txtReturnPoint.clear();
		dpReturnDate.setValue(LocalDate.now());
		txtHotelName.clear();
		txtAdultPrice.clear();
		txtChildPrice.clear();
		txtSeason.clear();
		txtMaximum.clear();
		txtMinimum.clear();
		txtAreaSchedule.clear();
	}

	// 예약고객관리 초기화 메소드
	public void CusResInit() {
		txtCusNo.clear();
		txtProductCode.clear();
		txtCusName.clear();
		txtCusPhone.clear();
		txtAdult.clear();
		txtChild.clear();
		txtAdult.setText("0");
		txtChild.setText("0");
		dpRegistDate.setValue(LocalDate.now());
		txtPessPhone.clear();
		txtEmail.clear();
		txtTotalPrice.clear();
		txtTotalPrice.setText("0");
		txtAccountNumber.clear();
	}

	// 상품등록관리 상품번호 입력
	public void handlerBtnCodeMaxAction(ActionEvent event) {

		ProductDAO pDao = null;
		pDao = new ProductDAO();

		try {
			txtPrCode.setText(pDao.getPrNoMax().getPrCode() + 1 + "");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 상품등록관리 상품 삭제
	public void handlerBtnPrDeleteAction(ActionEvent event) {

		ProductDAO pDao = null;
		pDao = new ProductDAO();

		try {
			pDao.getProductDelete(deleteProductNo);
			productData.removeAll(productData);
			// 학생 전체 정보
			PrTotalList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 예약고객관리 삭제
	public void handlerBtnCusDeleteAction(ActionEvent event) {
		ReservationDAO rDao = null;
		rDao = new ReservationDAO();
		CustomerDAO cDao = null;
		cDao = new CustomerDAO();

		try {
			if (deleteCustomerNo != 0 && deleteReservationNo == 0) {
				rDao.getReservationDelete(deleteCustomerNo);
				cDao.getCustomerDelete(deleteCustomerNo);
				customerData.removeAll(customerData);
				reservationData.removeAll(reservationData);
				ResTotalList();
				CusTotalList();
			} else if (deleteCustomerNo == 0 && deleteReservationNo != 0) {
				rDao.getReservationDelete(deleteReservationNo);
				cDao.getCustomerDelete(deleteReservationNo);
				customerData.removeAll(customerData);
				reservationData.removeAll(reservationData);
				ResTotalList();
				CusTotalList();
			} else if (deleteCustomerNo != 0 && deleteReservationNo != 0 && deleteCustomerNo == deleteReservationNo) {

				rDao.getReservationDelete(deleteCustomerNo);
				cDao.getCustomerDelete(deleteCustomerNo);
				customerData.removeAll(customerData);
				reservationData.removeAll(reservationData);
				ResTotalList();
				CusTotalList();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("고객예약 클릭 에러");
				alert.setHeaderText("서로 정보가 다릅니다. 한테이블만 클릭하거나 같은 행을 클릭해주세요.");
				alert.setContentText("서로 정보가 다릅니다. 한테이블만 클릭하거나 같은 행을 클릭해주세요.!!!");
				alert.showAndWait();
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 상품등록관리 검색 메소드(계절, 상품번호, 날짜)
	public void handlerBtnPrSearchAction(ActionEvent event) {
		// TODO Auto-generated method stub
		ProductsVO pVoSeason = new ProductsVO();
		ProductsVO pVoPrCode = new ProductsVO();
		ProductsVO pVoDate = new ProductsVO();
		ProductDAO pDao = null;

		Object[][] totalData = null;

		String searchPrCode = "0";
		String searchDate = "";
		String searchSeason = "";
		boolean searchResult = false;

		// 상품계절 or 상품번호 or 날짜로 검색
		try {
			searchSeason = txtPrSearchSeason.getText();
			if (!txtPrSearchPrCode.getText().equals("")) {
				searchPrCode = txtPrSearchPrCode.getText();
			}
			searchDate = dpPrSearchDate.getValue() + "";

			pDao = new ProductDAO();
			pVoSeason = pDao.getProductSeasonCheck(searchSeason);
			pVoPrCode = pDao.getProductPrCodeCheck(Integer.parseInt(searchPrCode));
			pVoDate = pDao.getProductDateCheck(searchDate);
			if (searchSeason.equals("") && searchPrCode.equals("") && searchDate.equals("")) {
				searchResult = true;
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("상품 정보 검색");
				alert.setHeaderText("상품의 계절 또는 상품명을 정확히 입력하시오.");
				alert.setContentText("다음에는 주의하세요!");
				alert.showAndWait();
			}
			if (!searchSeason.equals("") && (pVoSeason != null) && searchPrCode.equals("0")) {
				ArrayList<String> title;
				ArrayList<ProductsVO> list;

				title = pDao.getColumnName();
				int columnCount = title.size();

				list = pDao.getProductTotal();
				int rowCount = list.size();

				totalData = new Object[rowCount][columnCount];

				if (pVoSeason.getSeason().equals(searchSeason)) {
					txtPrSearchSeason.clear();
					productData.removeAll(productData);
					for (int index = 0; index < rowCount; index++) {
						pVoSeason = list.get(index);
						if (pVoSeason.getSeason().equals(searchSeason)) {
							productData.add(pVoSeason);
							searchResult = true;
						}
					}
				}
			}

			if (!searchPrCode.equals("0") && (pVoPrCode != null) && searchSeason.equals("")) {
				ArrayList<String> title;
				ArrayList<ProductsVO> list;

				title = pDao.getColumnName();
				int columnCount = title.size();

				list = pDao.getProductTotal();
				int rowCount = list.size();

				totalData = new Object[rowCount][columnCount];

				if (pVoPrCode.getPrCode() == Integer.parseInt(searchPrCode)) {
					txtPrSearchPrCode.clear();
					productData.removeAll(productData);
					for (int index = 0; index < rowCount; index++) {
						pVoPrCode = list.get(index);
						if (pVoPrCode.getPrCode() == Integer.parseInt(searchPrCode)) {
							productData.add(pVoPrCode);
							searchResult = true;
						}
					}
				}
			}

			if (!searchDate.equals("") && (pVoDate != null) && searchPrCode.equals("0") && searchSeason.equals("")) {
				ArrayList<String> title;
				ArrayList<ProductsVO> list;
				title = pDao.getColumnName();
				int columnCount = title.size();

				list = pDao.getProductTotal();
				int rowCount = list.size();

				totalData = new Object[rowCount][columnCount];
				if (pVoDate.getStartDate().substring(0, 10).equals(searchDate)) {
					productData.removeAll(productData);
					for (int index = 0; index < rowCount; index++) {
						pVoDate = list.get(index);
						if (pVoDate.getStartDate().equals(searchDate)) {
							productData.add(pVoDate);
							searchResult = true;
						}
					}
				}
			}
			if (!searchResult) {
				txtPrSearchPrCode.clear();
				txtPrSearchSeason.clear();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("상품 정보 검색");
				alert.setHeaderText("상품 정보가 리스트에 없습니다.");
				alert.setContentText(searchDate);
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("상품 정보 검색 오류");
			alert.setHeaderText("상품 정보 검색에 오류가 발생하였습니다.");
			alert.setContentText("다시 하세요.");
			alert.showAndWait();
			System.out.println(e.toString());
		}

	}

	// 매출관리 검색 (상품코드)
	public void handlerBtnSalesSearchAction(ActionEvent event) {
		// TODO Auto-generated method stub
		ReservationVO rVoPrCode = new ReservationVO();

		ReservationDAO rDao = null;

		Object[][] resTotalData = null;

		String searchPrCode = "0";
		boolean searchResult = false;

		try {
			if (!txtSalesSearchPrCode.getText().equals("")) {
				searchPrCode = txtSalesSearchPrCode.getText();
			}
			rDao = new ReservationDAO();
			rVoPrCode = rDao.getSalesPrCodeCheck(Integer.parseInt(searchPrCode));
			if (searchPrCode.equals("")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매출관리 검색");
				alert.setHeaderText("상품코드를 정확히 입력해주세요!");
				alert.setContentText("다음에는 주의하세요!");
				alert.showAndWait();
				System.out.println();
			}
			if (!searchPrCode.equals("0") && rVoPrCode != null) {
				ArrayList<String> title;
				ArrayList<ReservationVO> list;

				title = rDao.getColumnName();
				int columnCount = title.size();

				list = rDao.getSalesTotal();
				int rowCount = list.size();
				resTotalData = new Object[rowCount][columnCount];

				if (rVoPrCode.getTotalPrCode() == Integer.parseInt(searchPrCode)) {
					txtSalesSearchPrCode.clear();
					salesData.removeAll(salesData);
					for (int index = 0; index < rowCount; index++) {
						rVoPrCode = list.get(index);
						if (rVoPrCode.getTotalPrCode() == Integer.parseInt(searchPrCode)) {
							salesData.add(rVoPrCode);
							searchResult = true;
						}
					}
				}
			}
			if (!searchResult) {
				txtPrSearchPrCode.clear();
				txtPrSearchSeason.clear();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("상품 정보 검색");
				alert.setHeaderText("상품 정보가 리스트에 없습니다.");
				alert.setContentText("없어!!!");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("매출 관리 검색 오류");
			alert.setHeaderText("매출 정보 검색에 오류가 발생하였습니다.");
			alert.setContentText("다시 하세요.");
			alert.showAndWait();
			System.out.println(e.toString());
		}
	}

	// 예약고객관리 검색(핸드폰 번호, 이름)
	public void handlerBtnCusSearchAction(ActionEvent event) {
		// TODO Auto-generated method stub
		ReservationVO rVoPhone = new ReservationVO();
		ReservationVO rVoName = new ReservationVO();
		ReservationVO rVoTotal = new ReservationVO();
		CustomerVO cVoPhone = new CustomerVO();
		CustomerVO cVoName = new CustomerVO();
		CustomerVO cVoTotal = new CustomerVO();

		ReservationDAO rDao = null;
		CustomerDAO cDao = null;

		Object[][] resTotalData = null;
		Object[][] cusTotalData = null;

		String searchPhone = "";
		String searchName = "";
		boolean searchResult = false;

		// 고객 핸드폰 번호 or 고객 이름 으로 검색
		try {
			searchPhone = txtCusSearchPhone.getText().trim();
			searchName = txtCusSearchName.getText().trim();

			rDao = new ReservationDAO();
			cDao = new CustomerDAO();

			cVoPhone = cDao.getCustomerPhoneCheck(searchPhone);
			rVoPhone = rDao.getReservationPhoneCheck(searchPhone);

			cVoName = cDao.getCustomerNameCheck(searchName);
			rVoName = rDao.getReservationNameCheck(searchName);

			cVoTotal = cDao.getCustomerNamePhoneCheck(searchName, searchPhone);
			rVoTotal = rDao.getReservationNamePhoneCheck(searchName, searchPhone);
			// 아무것도 없을떄 오류창
			if (searchPhone.equals("") && searchName.equals("")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("예약고객관리 검색");
				alert.setHeaderText("핸드폰 번호 또는 이름을 정확히 입력하세요.");
				alert.setContentText("다음에는 주의하세요!");
				alert.showAndWait();
				System.out.println();
			}
			// 휴대폰번호만 입력 검색
			if (!searchPhone.equals("") && (cVoPhone != null) && (rVoPhone != null) && searchName.equals("")) {
				ArrayList<String> resTitle;
				ArrayList<String> cusTitle;
				ArrayList<ReservationVO> resList;
				ArrayList<CustomerVO> cusList;

				resTitle = rDao.getColumnName();
				int resColumnCount = resTitle.size();

				cusTitle = cDao.getColumnName();
				int cusColumnCount = cusTitle.size();

				resList = rDao.getReservationTotal();
				int resRowCount = resList.size();

				cusList = cDao.getCustomerTotal();
				int cusRowCount = cusList.size();

				resTotalData = new Object[resRowCount][resColumnCount];
				cusTotalData = new Object[cusRowCount][cusColumnCount];

				if (cVoPhone.getPhone().equals(searchPhone)) {
					txtCusSearchPhone.clear();
					txtCusSearchName.clear();
					customerData.removeAll(customerData);
					reservationData.removeAll(reservationData);
					for (int index = 0; index < cusRowCount; index++) {
						cVoPhone = cusList.get(index);
						rVoPhone = resList.get(index);
						if (cVoPhone.getPhone().equals(searchPhone)) {
							customerData.add(cVoPhone);
							reservationData.add(rVoPhone);
							searchResult = true;
						}
					}
				}

			}
			// 이름만 입력 검색
			if (!searchName.equals("") && (cVoName != null) && (rVoName != null) && searchPhone.equals("")) {
				ArrayList<String> resTitle;
				ArrayList<String> cusTitle;
				ArrayList<ReservationVO> resList;
				ArrayList<CustomerVO> cusList;

				resTitle = rDao.getColumnName();
				int resColumnCount = resTitle.size();

				cusTitle = cDao.getColumnName();
				int cusColumnCount = cusTitle.size();

				resList = rDao.getReservationTotal();
				int resRowCount = resList.size();

				cusList = cDao.getCustomerTotal();
				int cusRowCount = cusList.size();

				resTotalData = new Object[resRowCount][resColumnCount];
				cusTotalData = new Object[cusRowCount][cusColumnCount];

				if (cVoName.getName().equals(searchName)) {
					txtCusSearchPhone.clear();
					txtCusSearchName.clear();
					customerData.removeAll(customerData);
					reservationData.removeAll(reservationData);
					for (int index = 0; index < cusRowCount; index++) {
						cVoName = cusList.get(index);
						rVoName = resList.get(index);
						if (cVoName.getName().equals(searchName)) {
							customerData.add(cVoName);
							reservationData.add(rVoName);
							searchResult = true;
						}
					}
				}
			}
			// 이름과 휴대폰 모두 입력해서 검색
			if (!searchName.equals("") && (cVoName != null) && (rVoName != null) && !searchPhone.equals("")
					&& (cVoPhone != null) && (rVoPhone != null)) {
				ArrayList<String> resTitle;
				ArrayList<String> cusTitle;
				ArrayList<ReservationVO> resList;
				ArrayList<CustomerVO> cusList;

				resTitle = rDao.getColumnName();
				int resColumnCount = resTitle.size();

				cusTitle = cDao.getColumnName();
				int cusColumnCount = cusTitle.size();

				resList = rDao.getReservationTotal();
				int resRowCount = resList.size();

				cusList = cDao.getCustomerTotal();
				int cusRowCount = cusList.size();

				resTotalData = new Object[resRowCount][resColumnCount];
				cusTotalData = new Object[cusRowCount][cusColumnCount];

				if (cVoTotal.getName().equals(searchName) && cVoTotal.getPhone().equals(searchPhone)) {
					txtCusSearchPhone.clear();
					txtCusSearchName.clear();
					customerData.removeAll(customerData);
					reservationData.removeAll(reservationData);
					for (int index = 0; index < cusRowCount; index++) {
						cVoTotal = cusList.get(index);
						rVoTotal = resList.get(index);
						if (cVoTotal.getName().equals(searchName) && cVoTotal.getPhone().equals(searchPhone)) {
							customerData.add(cVoTotal);
							reservationData.add(rVoTotal);
							searchResult = true;
						}
					}
				}
			}
			if (!searchResult) {
				txtCusSearchName.clear();
				txtCusSearchPhone.clear();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("상품 정보 검색");
				alert.setHeaderText("상품 정보가 리스트에 없습니다.");
				alert.setContentText("다시 검색하세요.");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("고객정보 검색 오류");
			alert.setHeaderText("고객정보 검색에 오류가 발생하였습니다.");
			alert.setContentText("다시 하세요.");
			alert.showAndWait();
		}

	}

	// 상품관리 전체 정보
	public void PrTotalList() {
		// TODO Auto-generated method stub
		Object[][] totalData;
		ProductDAO pDao = new ProductDAO();
		ProductsVO pVo = null;
		ArrayList<String> title;
		ArrayList<ProductsVO> list;

		title = pDao.getColumnName();
		int columnCount = title.size();

		list = pDao.getProductTotal();
		int rowCount = list.size();

		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			pVo = list.get(index);
			productData.add(pVo);
		}
	}

	// 예약고객 전체 정보
	public void CusTotalList() {
		// TODO Auto-generated method stub
		Object[][] totalData;
		CustomerDAO cDao = new CustomerDAO();
		CustomerVO cVo = null;
		ArrayList<String> title;
		ArrayList<CustomerVO> list;

		title = cDao.getColumnName();
		int columnCount = title.size();

		list = cDao.getCustomerTotal();
		int rowCount = list.size();

		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			cVo = list.get(index);
			customerData.add(cVo);
		}
	}

	// 예약고객 전체 정보
	public void ResTotalList() {
		// TODO Auto-generated method stub
		Object[][] totalData;
		ReservationDAO rDao = new ReservationDAO();
		ReservationVO rVo = null;
		ArrayList<String> title;
		ArrayList<ReservationVO> list;

		title = rDao.getColumnName();
		int columnCount = title.size();

		list = rDao.getReservationTotal();
		int rowCount = list.size();

		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			rVo = list.get(index);
			reservationData.add(rVo);
		}
	}

	// 매출관리 전체 정보
	public void SalesTotalList() {
		// TODO Auto-generated method stub
		Object[][] totalData;
		ReservationDAO rDao = new ReservationDAO();
		ReservationVO rVo = null;
		ArrayList<String> title;
		ArrayList<ReservationVO> list;

		title = rDao.getColumnName();
		int columnCount = title.size();

		list = rDao.getSalesTotal();
		int rowCount = list.size();

		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			rVo = list.get(index);
			salesData.add(rVo);
		}
	}

	/******************
	 * 
	 * 수정 컨트롤러
	 * 
	 ******************/
	// 상품등록관리 수정
	public void handlerBtnPrEditAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/View/prEdit.fxml"));

			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnPrEdit.getScene().getWindow());
			dialog.setTitle("상품정보 수정");

			Parent parentPrEdit = (Parent) loader.load();
			ProductsVO productEdit = productTableView.getSelectionModel().getSelectedItem();
			selectedProductIndex = productTableView.getSelectionModel().getSelectedIndex();

			TextField editPrCode = (TextField) parentPrEdit.lookup("#txtPrCode");
			TextField editTourName = (TextField) parentPrEdit.lookup("#txtTourName");
			TextField editStartPoint = (TextField) parentPrEdit.lookup("#txtStartPoint");
			DatePicker editStartDate = (DatePicker) parentPrEdit.lookup("#dpStartDate");
			TextField editEndPoint = (TextField) parentPrEdit.lookup("#txtEndPoint");
			DatePicker editEndDate = (DatePicker) parentPrEdit.lookup("#dpEndDate");
			TextField editReturnPoint = (TextField) parentPrEdit.lookup("#txtReturnPoint");
			DatePicker editReturnDate = (DatePicker) parentPrEdit.lookup("#dpReturnDate");
			TextField editHotelName = (TextField) parentPrEdit.lookup("#txtHotelName");
			TextField editAdultPrice = (TextField) parentPrEdit.lookup("#txtAdultPrice");
			TextField editChildPrice = (TextField) parentPrEdit.lookup("#txtChildPrice");
			TextField editSeason = (TextField) parentPrEdit.lookup("#txtSeason");
			TextField editMaximum = (TextField) parentPrEdit.lookup("#txtMaximum");
			TextField editMinimum = (TextField) parentPrEdit.lookup("#txtMinimum");
			TextArea editAreaSchedule = (TextArea) parentPrEdit.lookup("#txtAreaSchedule");
			editPrCode.setDisable(true);
			editStartDate.setEditable(false);
			editEndDate.setEditable(false);
			editReturnDate.setEditable(false);
			editPrCode.setText(productEdit.getPrCode() + "");
			editTourName.setText(productEdit.getTourName());
			editStartPoint.setText(productEdit.getStartPoint());
			editStartDate.setValue(LocalDate.parse(productEdit.getStartDate()));
			editEndPoint.setText(productEdit.getDestination());
			editEndDate.setValue(LocalDate.parse(productEdit.getDesDate()));
			editReturnPoint.setText(productEdit.getReturnPoint());
			editReturnDate.setValue(LocalDate.parse(productEdit.getReturnDate()));
			editHotelName.setText(productEdit.getHotelName());
			editAdultPrice.setText(productEdit.getAdultCharge() + "");
			editChildPrice.setText(productEdit.getChildCharge() + "");
			editSeason.setText(productEdit.getSeason());
			editMaximum.setText(productEdit.getMaximum() + "");
			editMinimum.setText(productEdit.getMinimum() + "");
			editAreaSchedule.setText(productEdit.getSchedule());

			Button btnPrRegist = (Button) parentPrEdit.lookup("#btnPrRegist");
			Button btnPrExit = (Button) parentPrEdit.lookup("#btnPrExit");

			btnPrRegist.setOnAction(e -> {
				ProductsVO pVo = null;
				ProductDAO pDao = null;
				TextField txtPrCode = (TextField) parentPrEdit.lookup("#txtPrCode");
				TextField txtTourName = (TextField) parentPrEdit.lookup("#txtTourName");
				TextField txtStartPoint = (TextField) parentPrEdit.lookup("#txtStartPoint");
				DatePicker dpStartDate = (DatePicker) parentPrEdit.lookup("#dpStartDate");
				TextField txtEndPoint = (TextField) parentPrEdit.lookup("#txtEndPoint");
				DatePicker dpEndDate = (DatePicker) parentPrEdit.lookup("#dpEndDate");
				TextField txtReturnPoint = (TextField) parentPrEdit.lookup("#txtReturnPoint");
				DatePicker dpReturnDate = (DatePicker) parentPrEdit.lookup("#dpReturnDate");
				TextField txtHotelName = (TextField) parentPrEdit.lookup("#txtHotelName");
				TextField txtAdultPrice = (TextField) parentPrEdit.lookup("#txtAdultPrice");
				TextField txtChildPrice = (TextField) parentPrEdit.lookup("#txtChildPrice");
				TextField txtSeason = (TextField) parentPrEdit.lookup("#txtSeason");
				TextField txtMaximum = (TextField) parentPrEdit.lookup("#txtMaximum");
				TextField txtMinimum = (TextField) parentPrEdit.lookup("#txtMinimum");
				TextArea txtAreaSchedule = (TextArea) parentPrEdit.lookup("#txtAreaSchedule");

				productData.remove(selectedProductIndex);

				try {
					pVo = new ProductsVO(Integer.parseInt(txtPrCode.getText()), txtTourName.getText(),
							txtStartPoint.getText(), dpStartDate.getValue() + "", txtEndPoint.getText(),
							dpEndDate.getValue() + "", txtReturnPoint.getText(), dpReturnDate.getValue() + "",
							Integer.parseInt(txtAdultPrice.getText()), Integer.parseInt(txtChildPrice.getText()),
							txtSeason.getText(), txtHotelName.getText(), txtAreaSchedule.getText(),
							Integer.parseInt(txtMaximum.getText()), Integer.parseInt(txtMinimum.getText()));
					dialog.close();

					pDao = new ProductDAO();
					pDao.getProductUpdate(pVo, pVo.getPrCode());

					productData.removeAll(productData);
					PrTotalList();
				} catch (Exception e1) {
					System.out.println("RootController 에러 " + e1);
					e1.printStackTrace();
				}
			});

			btnPrExit.setOnAction(e -> {
				try {
					dialog.close();
				} catch (Exception e1) {
					System.out.println("RootController 에러 " + e1);
					e1.printStackTrace();
				}
			});

			Scene scene = new Scene(parentPrEdit);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();

		} catch (IOException e) {
			System.out.println(e.toString());
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("상품클릭 에러");
			alert.setHeaderText("테이블안의 상품을 클릭해주세요.");
			alert.setContentText("상품을 클릭해주세요!!!");
			alert.showAndWait();
		}
	}

	// 예약고객관리 수정
	public void handlerBtnCusEditAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/View/cusEdit.fxml"));

			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnCusEdit.getScene().getWindow());
			dialog.setTitle("예약정보 수정");

			CustomerVO customerEdit = customerTableView.getSelectionModel().getSelectedItem();
			selectedCustomerIndex = customerTableView.getSelectionModel().getSelectedIndex();
			ReservationVO reservationEdit = reservationTableView.getSelectionModel().getSelectedItem();
			selectedReservationIndex = reservationTableView.getSelectionModel().getSelectedIndex();
			Parent parentCusResEdit = (Parent) loader.load();

			if (deleteCustomerNo == deleteReservationNo) {
				TextField editCusNo = (TextField) parentCusResEdit.lookup("#txtCusNo");
				TextField editProductCode = (TextField) parentCusResEdit.lookup("#txtProductCode");
				TextField editCusName = (TextField) parentCusResEdit.lookup("#txtCusName");
				TextField editAdult = (TextField) parentCusResEdit.lookup("#txtAdult");
				TextField editChild = (TextField) parentCusResEdit.lookup("#txtChild");
				TextField editPhone = (TextField) parentCusResEdit.lookup("#txtCusPhone");
				TextField editPessPhone = (TextField) parentCusResEdit.lookup("#txtPessPhone");
				DatePicker editRegistDate = (DatePicker) parentCusResEdit.lookup("#dpRegistDate");
				TextField editEmail = (TextField) parentCusResEdit.lookup("#txtEmail");
				TextField editAccountNumber = (TextField) parentCusResEdit.lookup("#txtAccountNumber");
				TextField editTotalPrice = (TextField) parentCusResEdit.lookup("#txtTotalPrice");

				editCusNo.setText(customerEdit.getCusNo() + "");
				editProductCode.setText(reservationEdit.getPrCode() + "");
				editCusName.setText(customerEdit.getName());
				editAdult.setText(reservationEdit.getAdult() + "");
				editChild.setText(reservationEdit.getChild() + "");
				editPhone.setText(customerEdit.getPhone());
				editPessPhone.setText(customerEdit.getPessangerPhone());
				editRegistDate.setValue(LocalDate.parse(reservationEdit.getResdate()));
				editEmail.setText(customerEdit.getEmail());
				editAccountNumber.setText(customerEdit.getAccountNumber());
				editTotalPrice.setText(reservationEdit.getTotalprice() + "");

				editCusNo.setDisable(true);
				editProductCode.setDisable(true);
				editTotalPrice.setDisable(true);
				editRegistDate.setEditable(false);

				Button btnCusTotalPrice = (Button) parentCusResEdit.lookup("#btnCusTotalPrice");
				Button btnCusRegist = (Button) parentCusResEdit.lookup("#btnCusRegist");
				Button btnCusExit = (Button) parentCusResEdit.lookup("#btnCusExit");
				Button btnCusPrSearch = (Button) parentCusResEdit.lookup("#btnCusPrSearch");

				btnCusRegist.setOnAction(e -> {
					CustomerVO cVo = null;
					CustomerDAO cDao = null;
					ReservationVO rVo = null;
					ReservationDAO rDao = null;
					TextField txtCusNo = (TextField) parentCusResEdit.lookup("#txtCusNo");
					TextField txtProductCode = (TextField) parentCusResEdit.lookup("#txtProductCode");
					TextField txtCusName = (TextField) parentCusResEdit.lookup("#txtCusName");
					TextField txtAdult = (TextField) parentCusResEdit.lookup("#txtAdult");
					TextField txtChild = (TextField) parentCusResEdit.lookup("#txtChild");
					TextField txtCusPhone = (TextField) parentCusResEdit.lookup("#txtCusPhone");
					TextField txtPessPhone = (TextField) parentCusResEdit.lookup("#txtPessPhone");
					DatePicker dpRegistDate = (DatePicker) parentCusResEdit.lookup("#dpRegistDate");
					TextField txtEmail = (TextField) parentCusResEdit.lookup("#txtEmail");
					TextField txtAccountNumber = (TextField) parentCusResEdit.lookup("#txtAccountNumber");
					TextField txtTotalPrice = (TextField) parentCusResEdit.lookup("#txtTotalPrice");
					customerData.remove(selectedCustomerIndex);
					reservationData.remove(selectedReservationIndex);

					try {
						cVo = new CustomerVO(Integer.parseInt(txtCusNo.getText()), txtCusName.getText(),
								txtCusPhone.getText(), txtPessPhone.getText(), txtEmail.getText(),
								txtAccountNumber.getText());
						rVo = new ReservationVO(dpRegistDate.getValue() + "", Integer.parseInt(txtAdult.getText()),
								Integer.parseInt(txtChild.getText()), Integer.parseInt(txtTotalPrice.getText()),
								Integer.parseInt(txtProductCode.getText()), Integer.parseInt(txtCusNo.getText()));
						dialog.close();
						cDao = new CustomerDAO();
						rDao = new ReservationDAO();
						cDao.getCustomerUpdate(cVo, cVo.getCusNo());
						rDao.getReservationUpdate(rVo, rVo.getCusNo());

						customerData.removeAll(customerData);
						reservationData.removeAll(reservationData);
						salesData.removeAll(salesData);
						CusTotalList();
						ResTotalList();
						SalesTotalList();
					} catch (Exception e1) {
						System.out.println("RootController 수정 등록 에러 " + e1);
					}

				});

				btnCusExit.setOnAction(e -> {
					try {
						dialog.close();
					} catch (Exception e1) {
						System.out.println("RootController 수정 닫기 에러 " + e1);
					}
				});

				btnCusTotalPrice.setOnAction(e -> {
					try {
						ProductDAO pDao = null;
						pDao = new ProductDAO();
						ReservationDAO rDao = null;
						rDao = new ReservationDAO();
						int total = pDao.getProductTotalPrice(Integer.parseInt(editProductCode.getText()))
								.getAdultCharge() * Integer.parseInt(editAdult.getText())
								+ pDao.getProductTotalPrice(Integer.parseInt(editProductCode.getText()))
										.getChildCharge() * Integer.parseInt(editChild.getText());
						editTotalPrice.setText(total + "");
					} catch (Exception e1) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("상품 또는 인원 정보");
						alert.setHeaderText("상품 또는 인원 정보를 정확히 입력해주세요.");
						alert.setContentText("상품 또는 인원 정보 오류!!!");
						alert.showAndWait();
					}
				});
				btnCusPrSearch.setOnAction(e -> {
					try {
						// 새로운 모달창을 띄움
						FXMLLoader newLoader = new FXMLLoader();
						newLoader.setLocation(getClass().getResource("/View/prSearch.fxml"));

						Stage newDialog = new Stage(StageStyle.UTILITY);
						newDialog.initModality(Modality.WINDOW_MODAL);
						newDialog.initOwner(btnCusRegist.getScene().getWindow());
						newDialog.setTitle("상품번호 검색");

						Parent parentSearch = (Parent) newLoader.load();
						ProductsVO productSearch = productTableView.getSelectionModel().getSelectedItem();
						selectedProductIndex = productTableView.getSelectionModel().getSelectedIndex();

						TextField prSearchSeason = (TextField) parentSearch.lookup("#txtPrSearchSeason");
						TextField prSearchPrCode = (TextField) parentSearch.lookup("#txtPrSearchPrCode");
						DatePicker prSearchDate = (DatePicker) parentSearch.lookup("#dpPrSearchDate");
						prSearchDate.setValue(LocalDate.now());

						TableView<ProductsVO> productTableView = (TableView) parentSearch.lookup("#productTableView");
						productTableView.setEditable(false);

						// 모달창에 productTableView 테이블 뷰 컬럼이름 설정
						TableColumn colPrCode = new TableColumn("상품코드");
						colPrCode.setMaxWidth(80);
						colPrCode.setStyle("-fx-allignment:CENTER");
						colPrCode.setCellValueFactory(new PropertyValueFactory<>("prCode"));

						TableColumn colTourName = new TableColumn("여행명");
						colTourName.setMaxWidth(80);
						colTourName.setCellValueFactory(new PropertyValueFactory<>("tourName"));

						TableColumn colStartPoint = new TableColumn("출발지");
						colStartPoint.setMaxWidth(80);
						colStartPoint.setCellValueFactory(new PropertyValueFactory<>("startPoint"));

						TableColumn colStartDate = new TableColumn("출발날짜");
						colStartDate.setMaxWidth(140);
						colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));

						TableColumn colDestination = new TableColumn("도착지");
						colDestination.setMaxWidth(80);
						colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));

						TableColumn colDesDate = new TableColumn("도착날짜");
						colDesDate.setMaxWidth(140);
						colDesDate.setCellValueFactory(new PropertyValueFactory<>("desDate"));

						TableColumn colReturnPoint = new TableColumn("복귀장소");
						colReturnPoint.setMaxWidth(80);
						colReturnPoint.setCellValueFactory(new PropertyValueFactory<>("returnPoint"));

						TableColumn colReturnDate = new TableColumn("복귀날짜");
						colReturnDate.setMaxWidth(140);
						colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

						TableColumn colAdultCharge = new TableColumn("대인요금");
						colAdultCharge.setMaxWidth(60);
						colAdultCharge.setCellValueFactory(new PropertyValueFactory<>("adultCharge"));

						TableColumn colChildCharge = new TableColumn("소인요금");
						colChildCharge.setMaxWidth(60);
						colChildCharge.setCellValueFactory(new PropertyValueFactory<>("childCharge"));

						TableColumn colSeason = new TableColumn("계절");
						colSeason.setMaxWidth(80);
						colSeason.setCellValueFactory(new PropertyValueFactory<>("season"));

						TableColumn colHotelName = new TableColumn("호텔명");
						colHotelName.setMaxWidth(80);
						colHotelName.setCellValueFactory(new PropertyValueFactory<>("hotelName"));

						TableColumn colSchedule = new TableColumn("일정");
						colSchedule.setMaxWidth(60);
						colSchedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));

						TableColumn colMaximum = new TableColumn("Max");
						colMaximum.setMaxWidth(43);
						colMaximum.setCellValueFactory(new PropertyValueFactory<>("maximum"));

						TableColumn colMinimum = new TableColumn("Min");
						colMinimum.setMaxWidth(43);
						colMinimum.setCellValueFactory(new PropertyValueFactory<>("minimum"));
						// 상품관리 테이블의 테이블뷰
						productTableView.getColumns().addAll(colPrCode, colSeason, colTourName, colStartPoint,
								colStartDate, colDestination, colDesDate, colReturnDate, colAdultCharge, colChildCharge,
								colHotelName, colMaximum, colMinimum);
						productTableView.setItems(productData);
						// 상품번호 검색에서 계절명 or 여행명 검색버튼
						Button btnPrSearch = (Button) parentSearch.lookup("#btnPrSearch");
						// 상품번호 검색에서 전체보기 버튼
						Button btnPrSearchTotal = (Button) parentSearch.lookup("#btnPrSearchTotal");
						// 상품번호 검색에서 선택버튼
						Button btnPrOk = (Button) parentSearch.lookup("#btnPrOk");

						productTableView.setOnMousePressed(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {
								try {
									// TODO Auto-generated method stub
									selectProduct = productTableView.getSelectionModel().getSelectedItems();
									deleteProductNo = selectProduct.get(0).getPrCode();
								} catch (Exception e) {
									Alert alert = new Alert(AlertType.WARNING);
									alert.setTitle("상품 선택");
									alert.setHeaderText("선택한 상품이 없습니다.");
									alert.setContentText("상품을 추가한 후에 선택하세요.");
									alert.showAndWait();
								}
								return;
							}

						});

						btnPrSearchTotal.setOnAction(e1 -> {
							try {
								productData.removeAll(productData);
								// 상품 전체 정보
								PrTotalList();
							} catch (Exception e2) {

							}
						});

						btnPrOk.setOnAction(e1 -> {
							try {
								if (selectProduct.get(0).getPrCode() + "" == null) {
									PrTotalList();
									return;
								} else {
									newDialog.close();
									editProductCode.setText(selectProduct.get(0).getPrCode() + "");
									productData.removeAll(productData);
									// 상품 전체 정보
									PrTotalList();
								}
							} catch (Exception e2) {
								Alert alert = new Alert(AlertType.WARNING);
								alert.setTitle("선택하세요.");
								alert.setHeaderText("상품을 선택해주세요!");
								alert.setContentText("상품을 선택해주세요!!!");
								alert.showAndWait();
							}
						});

						btnPrSearch.setOnAction(e1 -> {
							ProductsVO pVoSeason = new ProductsVO();
							ProductsVO pVoPrCode = new ProductsVO();
							ProductsVO pVoDate = new ProductsVO();
							ProductDAO pDao = null;

							Object[][] totalData = null;

							String searchPrCode = "0";
							String searchDate = "";
							String searchSeason = "";
							boolean searchResult = false;

							// 상품계절 or 상품명 or 날짜로 검색
							try {
								searchSeason = prSearchSeason.getText();
								if (!prSearchPrCode.getText().equals("")) {
									searchPrCode = prSearchPrCode.getText();
								}
								searchDate = dpPrSearchDate.getValue() + "";

								pDao = new ProductDAO();
								pVoSeason = pDao.getProductSeasonCheck(searchSeason);
								pVoPrCode = pDao.getProductPrCodeCheck(Integer.parseInt(searchPrCode));
								pVoDate = pDao.getProductDateCheck(searchDate);
								if (searchSeason.equals("") && searchPrCode.equals("0") && searchDate.equals("")) {
									searchResult = true;
									Alert alert = new Alert(AlertType.WARNING);
									alert.setTitle("상품 정보 검색");
									alert.setHeaderText("상품의 계절 또는 상품명을 정확히 입력하시오.");
									alert.setContentText("다음에는 주의하세요!");
									alert.showAndWait();
								}
								if (!searchSeason.equals("") && (pVoSeason != null) && searchPrCode.equals("0")) {
									ArrayList<String> title;
									ArrayList<ProductsVO> list;

									title = pDao.getColumnName();
									int columnCount = title.size();

									list = pDao.getProductTotal();
									int rowCount = list.size();

									totalData = new Object[rowCount][columnCount];

									if (pVoSeason.getSeason().equals(searchSeason)) {
										prSearchSeason.clear();
										productData.removeAll(productData);
										for (int index = 0; index < rowCount; index++) {
											pVoSeason = list.get(index);
											if (pVoSeason.getSeason().equals(searchSeason)) {
												productData.add(pVoSeason);
												searchResult = true;
											}
										}
									}
								}

								if (!searchPrCode.equals("0") && (pVoPrCode != null) && searchSeason.equals("")) {
									ArrayList<String> title;
									ArrayList<ProductsVO> list;

									title = pDao.getColumnName();
									int columnCount = title.size();

									list = pDao.getProductTotal();
									int rowCount = list.size();

									totalData = new Object[rowCount][columnCount];

									if (pVoPrCode.getPrCode() == Integer.parseInt(searchPrCode)) {
										prSearchPrCode.clear();
										productData.removeAll(productData);
										for (int index = 0; index < rowCount; index++) {
											pVoPrCode = list.get(index);
											if (pVoPrCode.getPrCode() == Integer.parseInt(searchPrCode)) {
												productData.add(pVoPrCode);
												searchResult = true;
											}
										}
									}
								}

								if (!searchDate.equals("") && (pVoDate != null) && searchPrCode.equals("0")
										&& searchSeason.equals("")) {
									ArrayList<String> title;
									ArrayList<ProductsVO> list;
									title = pDao.getColumnName();
									int columnCount = title.size();

									list = pDao.getProductTotal();
									int rowCount = list.size();

									totalData = new Object[rowCount][columnCount];
									if (pVoDate.getStartDate().substring(0, 10).equals(searchDate)) {
										productData.removeAll(productData);
										for (int index = 0; index < rowCount; index++) {
											pVoDate = list.get(index);
											if (pVoDate.getStartDate().equals(searchDate)) {
												productData.add(pVoDate);
												searchResult = true;
											}
										}
									}
								}
								if (!searchResult) {
									prSearchPrCode.clear();
									prSearchSeason.clear();
									Alert alert = new Alert(AlertType.INFORMATION);
									alert.setTitle("상품 정보 검색");
									alert.setHeaderText("상품 정보가 리스트에 없습니다.");
									alert.setContentText(searchDate);
									alert.showAndWait();
								}
							} catch (Exception e2) {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("상품 정보 검색 오류");
								alert.setHeaderText("상품 정보 검색에 오류가 발생하였습니다.");
								alert.setContentText("다시 하세요.");
								alert.showAndWait();
								System.out.println(e1.toString());
							}
						});

						Scene scene = new Scene(parentSearch);
						newDialog.setScene(scene);
						newDialog.setResizable(false);
						newDialog.show();
					} catch (IOException e1) {
						System.out.println(e1.toString());
					}

				});

				Scene scene = new Scene(parentCusResEdit);
				dialog.setScene(scene);
				dialog.setResizable(false);
				dialog.show();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("고객예약 클릭 에러");
				alert.setHeaderText("서로 정보가 다릅니다. 같은 행을 클릭해주세요.");
				alert.setContentText("서로 정보가 다릅니다. 같은 행을 클릭해주세요. 제발좀!!!!!");
				alert.showAndWait();
				return;
			}

		} catch (IOException e) {
			System.out.println(e.toString());
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("예약고객 클릭 에러");
			alert.setHeaderText("테이블안의 고객정보나 예약정보를 클릭해주세요.");
			alert.setContentText("고객정보나 예약정보를 클릭해주세요!!!");
			alert.showAndWait();
		}
	}

}
