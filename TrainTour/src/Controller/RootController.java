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
	ObservableList<ProductsVO> selectProduct = null; // ��ǰ ���̺��� ������ ���� ����
	ObservableList<CustomerVO> customerData = FXCollections.observableArrayList();
	ObservableList<CustomerVO> selectedCustomer = null; // �� ���̺��� ������ ���� ����
	ObservableList<ReservationVO> reservationData = FXCollections.observableArrayList();
	ObservableList<ReservationVO> selectedReservation = null; // ���� ���̺��� ������ ���� ����
	ObservableList<ReservationVO> salesData = FXCollections.observableArrayList();
	ObservableList barList = FXCollections.observableArrayList();// ��ǰ�� �Ѿ� ǥ��
	ObservableList barNullList = FXCollections.observableArrayList();// ��ǰ�� �Ѿ� ǥ��

	int selectedProductIndex; // ��ǰ��ϰ��� ��ǰ ���̺��� ������ ��ǰ ���� �ε��� ����
	int deleteProductNo = 0; // ��ǰ��ϰ��� ��ǰ ���̺��� ������ ��ǰ���̺��� ������ ��ǰ��ȣ ����
	int selectedReservationIndex; // ��������� ���� ���̺��� ������ ���� ���� �ε��� ����
	int deleteReservationNo = 0; // ��������� ���� ���̺��� ������ �������̺��� ������ �����ȣ ����
	int selectedCustomerIndex; // ��������� �� ���̺��� ������ �� ���� �ε��� ����
	int deleteCustomerNo = 0; // ��������� �� ���̺��� ������ �����̺��� ������ ����ȣ ����

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

		// ���� �Է½� ���� ���� �̺�Ʈ ó��
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

		// productTableView ���̺� �� �÷��̸� ����

		TableColumn colPrCode = new TableColumn("��ǰ�ڵ�");
		colPrCode.setMaxWidth(80);
		colPrCode.setStyle("-fx-allignment:CENTER");
		colPrCode.setCellValueFactory(new PropertyValueFactory<>("prCode"));

		TableColumn colTourName = new TableColumn("�����");
		colTourName.setMaxWidth(80);
		colTourName.setCellValueFactory(new PropertyValueFactory<>("tourName"));

		TableColumn colStartPoint = new TableColumn("�����");
		colStartPoint.setMaxWidth(80);
		colStartPoint.setCellValueFactory(new PropertyValueFactory<>("startPoint"));

		TableColumn colStartDate = new TableColumn("��߳�¥");
		colStartDate.setMaxWidth(140);
		colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));

		TableColumn colDestination = new TableColumn("������");
		colDestination.setMaxWidth(80);
		colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));

		TableColumn colDesDate = new TableColumn("������¥");
		colDesDate.setMaxWidth(140);
		colDesDate.setCellValueFactory(new PropertyValueFactory<>("desDate"));

		TableColumn colReturnPoint = new TableColumn("�������");
		colReturnPoint.setMaxWidth(80);
		colReturnPoint.setCellValueFactory(new PropertyValueFactory<>("returnPoint"));

		TableColumn colReturnDate = new TableColumn("���ͳ�¥");
		colReturnDate.setMaxWidth(140);
		colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

		TableColumn colAdultCharge = new TableColumn("���ο��");
		colAdultCharge.setMaxWidth(60);
		colAdultCharge.setCellValueFactory(new PropertyValueFactory<>("adultCharge"));

		TableColumn colChildCharge = new TableColumn("���ο��");
		colChildCharge.setMaxWidth(60);
		colChildCharge.setCellValueFactory(new PropertyValueFactory<>("childCharge"));

		TableColumn colSeason = new TableColumn("����");
		colSeason.setMaxWidth(80);
		colSeason.setCellValueFactory(new PropertyValueFactory<>("season"));

		TableColumn colHotelName = new TableColumn("ȣ�ڸ�");
		colHotelName.setMaxWidth(80);
		colHotelName.setCellValueFactory(new PropertyValueFactory<>("hotelName"));

		TableColumn colSchedule = new TableColumn("����");
		colSchedule.setMaxWidth(60);
		colSchedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));

		TableColumn colMaximum = new TableColumn("Max");
		colMaximum.setMaxWidth(43);
		colMaximum.setCellValueFactory(new PropertyValueFactory<>("maximum"));

		TableColumn colMinimum = new TableColumn("Min");
		colMinimum.setMaxWidth(43);
		colMinimum.setCellValueFactory(new PropertyValueFactory<>("minimum"));
		// ��ǰ���� ���̺��� ���̺��
		productTableView.getColumns().addAll(colPrCode, colSeason, colTourName, colStartPoint, colStartDate,
				colDestination, colDesDate, colReturnDate, colAdultCharge, colChildCharge, colHotelName, colMaximum,
				colMinimum);

		// ��������� ���̺��� ���̺� �� Į���̸� ����
		TableColumn colCusPrCode = new TableColumn("��ǰ�ڵ�");
		colCusPrCode.setMaxWidth(80);
		colCusPrCode.setStyle("-fx-allignment:CENTER");
		colCusPrCode.setCellValueFactory(new PropertyValueFactory<>("prCode"));

		TableColumn colResNo = new TableColumn("No.");
		colResNo.setMaxWidth(50);
		colResNo.setCellValueFactory(new PropertyValueFactory<>("resNo"));

		TableColumn colCusNo = new TableColumn("No.");
		colCusNo.setMaxWidth(50);
		colCusNo.setCellValueFactory(new PropertyValueFactory<>("cusNo"));

		TableColumn colName = new TableColumn("����");
		colName.setMaxWidth(80);
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn colPhone = new TableColumn("�޴���");
		colPhone.setMaxWidth(165);
		colPhone.setPrefWidth(165);
		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

		TableColumn colAdult = new TableColumn("����");
		colAdult.setMaxWidth(50);
		colAdult.setCellValueFactory(new PropertyValueFactory<>("adult"));

		TableColumn colChild = new TableColumn("����");
		colChild.setMaxWidth(50);
		colChild.setCellValueFactory(new PropertyValueFactory<>("child"));

		TableColumn colEmail = new TableColumn("�̸���");
		colEmail.setMaxWidth(200);
		colEmail.setPrefWidth(200);
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

		TableColumn colResDate = new TableColumn("��ϳ�¥");
		colResDate.setMaxWidth(175);
		colResDate.setPrefWidth(175);
		colResDate.setCellValueFactory(new PropertyValueFactory<>("resdate"));

		TableColumn colTotalPrice = new TableColumn("�ѱݾ�");
		colTotalPrice.setMaxWidth(73);
		colTotalPrice.setPrefWidth(73);
		colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalprice"));

		TableColumn colSalesPrCode = new TableColumn("��ǰ�ڵ�");
		colSalesPrCode.setMaxWidth(100);
		colSalesPrCode.setPrefWidth(100);
		colSalesPrCode.setCellValueFactory(new PropertyValueFactory<>("totalPrCode"));

		TableColumn colSalesAdult = new TableColumn("�� �����ο�");
		colSalesAdult.setMaxWidth(250);
		colSalesAdult.setPrefWidth(250);
		colSalesAdult.setCellValueFactory(new PropertyValueFactory<>("totalAdult"));

		TableColumn colSalesChild = new TableColumn("�� �����ο�");
		colSalesChild.setMaxWidth(250);
		colSalesChild.setPrefWidth(250);
		colSalesChild.setCellValueFactory(new PropertyValueFactory<>("totalChild"));

		TableColumn colSalesTotalPrice = new TableColumn("�� �ݾ�");
		colSalesTotalPrice.setMaxWidth(360);
		colSalesTotalPrice.setPrefWidth(360);
		colSalesTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalAllPrice"));

		// ������� ���̺��� ���̺��
		salesTableView.getColumns().addAll(colSalesPrCode, colSalesAdult, colSalesChild, colSalesTotalPrice);

		// ������� ���̺��� ���̺��
		customerTableView.getColumns().addAll(colCusNo, colName, colPhone, colEmail);
		reservationTableView.getColumns().addAll(colResNo, colCusPrCode, colAdult, colChild, colTotalPrice, colResDate);
		// ��ǰ ��ü ����
		PrTotalList();
		CusTotalList();
		ResTotalList();
		SalesTotalList();
		productTableView.setItems(productData);
		customerTableView.setItems(customerData);
		reservationTableView.setItems(reservationData);
		salesTableView.setItems(salesData);

		// ��ǰ��ϰ��� ��ü���� ��ư
		btnPrSearchTotal.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				try {
					productData.removeAll(productData);
					// ��ǰ ��ü ����
					PrTotalList();
				} catch (Exception e) {

				}
			}
		});
		// ��������� ��ü���� ��ư
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

		// ��ǰ��ϰ��� ��ǰ ���̺�信�� ��ǰ���� Ŭ���� ���
		productTableView.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				try {
					// TODO Auto-generated method stub
					selectProduct = productTableView.getSelectionModel().getSelectedItems();
					deleteProductNo = selectProduct.get(0).getPrCode();

				} catch (Exception e) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("��ǰ ����");
					alert.setHeaderText("������ ��ǰ�� �����ϴ�.");
					alert.setContentText("��ǰ�� �߰��� �Ŀ� �����ϼ���.");
					alert.showAndWait();
				}
				return;
			}

		});
		// ��������� ���� ���̺�信�� �������� Ŭ���� ���
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
						alert.setTitle("�������� ����");
						alert.setHeaderText("������ ���������� �����ϴ�.");
						alert.setContentText("���������� �߰��� �Ŀ� �����ϼ���.");
						alert.showAndWait();
					}
					return;
				}
			}

		});
		// ��������� �� ���̺��� ������ Ŭ���� ����

		customerTableView.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() != 2) {
					try { // TODO Auto-generated method stub selectedCustomer =
						selectedCustomer = customerTableView.getSelectionModel().getSelectedItems();
						deleteCustomerNo = selectedCustomer.get(0).getCusNo();

					} catch (Exception e) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("����� ����");
						alert.setHeaderText("������ ����� �� �����ϴ�.");
						alert.setContentText("������� �߰��� �Ŀ� �����ϼ���.");
						alert.showAndWait();
					}
					return;
				}
			}

		});

		// ��ǰ��ϰ��� ��Ϲ�ư ������
		btnPrRegist.setOnAction(event -> handlerBtnPrRegistAction(event));
		// ��ǰ ���� ��ư
		btnPrDelete.setOnAction(event -> handlerBtnPrDeleteAction(event));
		// ��������� ���� ��ư
		btnCusDelete.setOnAction(event -> handlerBtnCusDeleteAction(event));
		// ���� ��ư
		btnPrExit.setOnAction(event -> {
			Platform.exit();
		});
		// ��������� ���� ��ư
		btnCusExit.setOnAction(event -> {
			Platform.exit();
		});
		// ��ǰ��ϰ��� ��ȣ �Է� ��ư
		btnPrCodeMax.setOnAction(event -> handlerBtnCodeMaxAction(event));
		// ��ǰ��ϰ��� �˻� ��ư
		btnPrSearch.setOnAction(event -> handlerBtnPrSearchAction(event));
		// ��ǰ��ϰ��� �ʱ�ȭ ��ư
		btnPrReset.setOnAction(event -> handlerBtnPrResetAction(event));
		// ��ǰ��ϰ��� ���� ��ư
		btnPrEdit.setOnAction(event -> handlerBtnPrEditAction(event));
		// ��������� ��Ϲ�ư
		btnCusRegist.setOnAction(event -> handlerBtnCusRegistAction(event));
		// ��������� �ʱ�ȭ ��ư
		btnCusReset.setOnAction(event -> handlerBtnCusResetAction(event));
		// ��������� ����ȣ ��ư
		btnCusCodeMax.setOnAction(event -> handlerBtnCusCodeMaxAction(event));
		// ��������� ��ǰ�ڵ� �˻� ��ư
		btnCusPrSearch.setOnAction(event -> handlerBtnCusPrSearchAction(event));
		// ��������� �ѱݾ� ��� ��ư
		btnCusTotalPrice.setOnAction(event -> handlerBtnCusTotalPriceAction(event));
		// ��������� �˻� ��ư
		btnCusSearch.setOnAction(event -> handlerBtnCusSearchAction(event));
		// ��������� ���� ��ư
		btnCusEdit.setOnAction(event -> handlerBtnCusEditAction(event));
		// ������� �˻� ��ư
		btnSalesSearch.setOnAction(event -> handlerBtnSalesSearchAction(event));
		// �������� ����
		btnExcel.setOnAction(event -> handlerBtnExcelFileAction(event));
		// ������������ ����
		btnSaveFileDir.setOnAction(event -> handlerBtnSaveFileAction(event));
		// PDF���� ����
		btnPDF.setOnAction(event -> handlerBtnPDFAction(event));
		// ��Ʈ ȭ�� ����
		btnBarChart.setOnAction(event -> handlerBtnBarChartAction(event));
		// ������� ����Ʈ ����

	}

	// ����Ʈ Ȯ��
	public void handlerBtnBarChartAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnCusRegist.getScene().getWindow());
			dialog.setTitle("���� �׷��� �̹��� ����");

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

	// PDF ��Ʈ �̹��� ����
	public void handlerBtnPDFAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {

			FXMLLoader loaderPdf = new FXMLLoader();
			loaderPdf.setLocation(getClass().getResource("/View/confirm.fxml"));

			Stage dialogPdf = new Stage(StageStyle.UTILITY);
			dialogPdf.initModality(Modality.WINDOW_MODAL);
			dialogPdf.initOwner(btnPDF.getScene().getWindow());
			dialogPdf.setTitle("�������ǥ PDF ��Ʈ �̹��� ����");

			Parent parentPdf = (Parent) loaderPdf.load();

			Button btnPdfSave = (Button) parentPdf.lookup("#btnPdfSave");
			CheckBox cbBarImage = (CheckBox) parentPdf.lookup("#cbBarImage");

			Scene scene = new Scene(parentPdf);
			dialogPdf.setScene(scene);
			dialogPdf.setResizable(false);
			dialogPdf.show();

			// ��Ʈ �̹��� ����
			btnPdfSave.setOnAction(e -> {

				try {
					// pdf document ����.
					// (Rectangle pageSize, float marginLeft, float
					// marginRight, float
					// marginTop, float marginBottom)
					Document document = new Document(PageSize.A4, 0, 0, 30, 30);
					// pdf ������ ������ ������ ����. pdf������ �����ȴ�. ���� ��Ʈ������ ����.
					String strReportPDFName = "Sales_" + System.currentTimeMillis() + ".pdf";
					PdfWriter.getInstance(document,
							new FileOutputStream(txtSaveFileDir.getText() + "\\" + strReportPDFName));
					// document�� ���� pdf������ �����ֵ����Ѵ�..
					document.open();
					// �ѱ�������Ʈ ����..
					BaseFont bf = BaseFont.createFont("font/MALGUN.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
					Font font = new Font(bf, 8, Font.NORMAL);
					Font font2 = new Font(bf, 14, Font.BOLD);
					// Ÿ��Ʋ
					Paragraph title = new Paragraph("���� ����ǥ", font2);
					// �߰�����
					title.setAlignment(Element.ALIGN_CENTER);
					// ������ �߰�
					document.add(title);
					document.add(new Paragraph("\r\n"));
					// ���� ��¥
					LocalDate date = dpRegistDate.getValue();
					Paragraph writeDay = new Paragraph(date.toString(), font);
					// ������ ����
					writeDay.setAlignment(Element.ALIGN_RIGHT);
					// ������ �߰�
					document.add(writeDay);
					document.add(new Paragraph("\r\n"));

					// ���̺���� Table��ü���� PdfPTable��ü�� �� �����ϰ� ���̺��� ����� �ִ�.
					// �����ڿ� �÷����� ���ش�.
					PdfPTable table = new PdfPTable(4);
					// ������ �÷��� width�� ���Ѵ�.
					table.setWidths(new int[] { 30, 30, 30, 50 });
					// �÷� Ÿ��Ʋ..
					PdfPCell header1 = new PdfPCell(new Paragraph("��ǰ��ȣ", font));
					PdfPCell header2 = new PdfPCell(new Paragraph("����", font));
					PdfPCell header3 = new PdfPCell(new Paragraph("����", font));
					PdfPCell header4 = new PdfPCell(new Paragraph("�� �ݾ�", font));

					// ��������
					header1.setHorizontalAlignment(Element.ALIGN_CENTER);
					header2.setHorizontalAlignment(Element.ALIGN_CENTER);
					header3.setHorizontalAlignment(Element.ALIGN_CENTER);
					header4.setHorizontalAlignment(Element.ALIGN_CENTER);

					// ��������
					header1.setVerticalAlignment(Element.ALIGN_MIDDLE);
					header2.setVerticalAlignment(Element.ALIGN_MIDDLE);
					header3.setVerticalAlignment(Element.ALIGN_MIDDLE);
					header4.setVerticalAlignment(Element.ALIGN_MIDDLE);

					// ���̺� �߰�..
					table.addCell(header1);
					table.addCell(header2);
					table.addCell(header3);
					table.addCell(header4);

					// DB ���� �� ����Ʈ ����
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

						// ��������
						cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

						// ��������
						cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

						// ���̺� �� �߰�
						table.addCell(cell1);
						table.addCell(cell2);
						table.addCell(cell3);
						table.addCell(cell4);
					}

					// ������ ���̺��߰�..
					document.add(table);
					document.add(new Paragraph("\r\n"));
					Alert alert = new Alert(AlertType.INFORMATION);
					if (cbBarImage.isSelected()) {

						// ���� �׷��� �̹��� �߰�
						Paragraph barImageTitle = new Paragraph("���� ����ǥ ���� �׷���", font);
						barImageTitle.setAlignment(Element.ALIGN_CENTER);
						document.add(barImageTitle);
						document.add(new Paragraph("\r\n"));
						final String barImageUrl = "chartImage/salesBarChart.png";
						// ������ javafx.scene.image.Image ��ü�� ����ϰ� �־� �浹�� ���� �Ʒ���
						// ���� �����.

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
					// ������ �ݴ´�.. ���� ����..
					document.close();

					dialogPdf.close();
					txtSaveFileDir.clear();
					btnPDF.setDisable(true);
					btnExcel.setDisable(true);

					alert.setTitle("PDF ���� ����");
					alert.setHeaderText("�л� ��� PDF ���� ���� ����.");
					alert.setContentText("�л� ��� PDF ���� .");
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

	// ���� ���� ���� ����
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

	// ���� ���� ����
	public void handlerBtnExcelFileAction(ActionEvent event) {
		// TODO Auto-generated method stub
		ReservationDAO rDao = new ReservationDAO();
		boolean saveSuccess;

		ArrayList<ReservationVO> list;
		list = rDao.getSalesTotal();

		SalesExcel excelWriter = new SalesExcel();

		// xlsx ���Ͼ���
		saveSuccess = excelWriter.xslxWriter(list, txtSaveFileDir.getText());
		if (saveSuccess) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("���� ���� ����");
			alert.setHeaderText("�Ǹ� ��� ���� ���� ���� ����.");
			alert.setContentText("�Ǹ� ��� ���� ����.");
			alert.showAndWait();
		}
		txtSaveFileDir.clear();
		btnExcel.setDisable(true);
		btnPDF.setDisable(true);
	}

	// ��������� �ѱݾ� ��ư
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
			alert.setTitle("��ǰ �Ǵ� �ο� ����");
			alert.setHeaderText("��ǰ �Ǵ� �ο� ������ ��Ȯ�� �Է����ּ���.");
			alert.setContentText("��ǰ �Ǵ� �ο� ���� ����!!!");
			alert.showAndWait();
		}
	}

	// ��������� ��ǰ�ڵ� �˻� ��ư
	public void handlerBtnCusPrSearchAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			// ���ο� ���â�� ���
			try {
				// ���ο� ���â�� ���
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/View/prSearch.fxml"));

				Stage dialog = new Stage(StageStyle.UTILITY);
				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.initOwner(btnCusRegist.getScene().getWindow());
				dialog.setTitle("��ǰ��ȣ �˻�");

				Parent parentSearch = (Parent) loader.load();
				ProductsVO productSearch = productTableView.getSelectionModel().getSelectedItem();
				selectedProductIndex = productTableView.getSelectionModel().getSelectedIndex();

				TextField prSearchSeason = (TextField) parentSearch.lookup("#txtPrSearchSeason");
				TextField prSearchPrCode = (TextField) parentSearch.lookup("#txtPrSearchPrCode");
				DatePicker prSearchDate = (DatePicker) parentSearch.lookup("#dpPrSearchDate");
				prSearchDate.setValue(LocalDate.now());

				TableView<ProductsVO> productTableView = (TableView) parentSearch.lookup("#productTableView");
				productTableView.setEditable(false);

				// ���â�� productTableView ���̺� �� �÷��̸� ����
				TableColumn colPrCode = new TableColumn("��ǰ�ڵ�");
				colPrCode.setMaxWidth(80);
				colPrCode.setStyle("-fx-allignment:CENTER");
				colPrCode.setCellValueFactory(new PropertyValueFactory<>("prCode"));

				TableColumn colTourName = new TableColumn("�����");
				colTourName.setMaxWidth(80);
				colTourName.setCellValueFactory(new PropertyValueFactory<>("tourName"));

				TableColumn colStartPoint = new TableColumn("�����");
				colStartPoint.setMaxWidth(80);
				colStartPoint.setCellValueFactory(new PropertyValueFactory<>("startPoint"));

				TableColumn colStartDate = new TableColumn("��߳�¥");
				colStartDate.setMaxWidth(140);
				colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));

				TableColumn colDestination = new TableColumn("������");
				colDestination.setMaxWidth(80);
				colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));

				TableColumn colDesDate = new TableColumn("������¥");
				colDesDate.setMaxWidth(140);
				colDesDate.setCellValueFactory(new PropertyValueFactory<>("desDate"));

				TableColumn colReturnPoint = new TableColumn("�������");
				colReturnPoint.setMaxWidth(80);
				colReturnPoint.setCellValueFactory(new PropertyValueFactory<>("returnPoint"));

				TableColumn colReturnDate = new TableColumn("���ͳ�¥");
				colReturnDate.setMaxWidth(140);
				colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

				TableColumn colAdultCharge = new TableColumn("���ο��");
				colAdultCharge.setMaxWidth(60);
				colAdultCharge.setCellValueFactory(new PropertyValueFactory<>("adultCharge"));

				TableColumn colChildCharge = new TableColumn("���ο��");
				colChildCharge.setMaxWidth(60);
				colChildCharge.setCellValueFactory(new PropertyValueFactory<>("childCharge"));

				TableColumn colSeason = new TableColumn("����");
				colSeason.setMaxWidth(80);
				colSeason.setCellValueFactory(new PropertyValueFactory<>("season"));

				TableColumn colHotelName = new TableColumn("ȣ�ڸ�");
				colHotelName.setMaxWidth(80);
				colHotelName.setCellValueFactory(new PropertyValueFactory<>("hotelName"));

				TableColumn colSchedule = new TableColumn("����");
				colSchedule.setMaxWidth(60);
				colSchedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));

				TableColumn colMaximum = new TableColumn("Max");
				colMaximum.setMaxWidth(43);
				colMaximum.setCellValueFactory(new PropertyValueFactory<>("maximum"));

				TableColumn colMinimum = new TableColumn("Min");
				colMinimum.setMaxWidth(43);
				colMinimum.setCellValueFactory(new PropertyValueFactory<>("minimum"));
				// ��ǰ���� ���̺��� ���̺��
				productTableView.getColumns().addAll(colPrCode, colSeason, colTourName, colStartPoint, colStartDate,
						colDestination, colDesDate, colReturnDate, colAdultCharge, colChildCharge, colHotelName,
						colMaximum, colMinimum);
				productTableView.setItems(productData);
				// ��ǰ��ȣ �˻����� ������ or ����� �˻���ư
				Button btnPrSearch = (Button) parentSearch.lookup("#btnPrSearch");
				// ��ǰ��ȣ �˻����� ��ü���� ��ư
				Button btnPrSearchTotal = (Button) parentSearch.lookup("#btnPrSearchTotal");
				// ��ǰ��ȣ �˻����� ���ù�ư
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
							alert.setTitle("��ǰ ����");
							alert.setHeaderText("������ ��ǰ�� �����ϴ�.");
							alert.setContentText("��ǰ�� �߰��� �Ŀ� �����ϼ���.");
							alert.showAndWait();
						}
						return;
					}

				});

				btnPrSearchTotal.setOnAction(e1 -> {
					try {
						productData.removeAll(productData);
						// ��ǰ ��ü ����
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
							// ��ǰ ��ü ����
							PrTotalList();
						}
					} catch (Exception e2) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("�����ϼ���.");
						alert.setHeaderText("��ǰ�� �������ּ���!");
						alert.setContentText("��ǰ�� �������ּ���!!!");
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

					// ��ǰ���� or ��ǰ�� or ��¥�� �˻�
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
							alert.setTitle("��ǰ ���� �˻�");
							alert.setHeaderText("��ǰ�� ���� �Ǵ� ��ǰ���� ��Ȯ�� �Է��Ͻÿ�.");
							alert.setContentText("�������� �����ϼ���!");
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
							alert.setTitle("��ǰ ���� �˻�");
							alert.setHeaderText("��ǰ ������ ����Ʈ�� �����ϴ�.");
							alert.setContentText(searchDate);
							alert.showAndWait();
						}
					} catch (Exception e2) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("��ǰ ���� �˻� ����");
						alert.setHeaderText("��ǰ ���� �˻��� ������ �߻��Ͽ����ϴ�.");
						alert.setContentText("�ٽ� �ϼ���.");
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

	// ��������� ����ȣ Ȯ�� ��ư
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

	// ��������� �ʱ�ȭ ��ư
	public void handlerBtnCusResetAction(ActionEvent event) {
		// TODO Auto-generated method stub
		CusResInit();
	}

	// ��ǰ��ϰ��� ��� ��ư
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
				alert.setTitle("��ǰ ���� �Է�");
				alert.setHeaderText(txtTourName.getText() + " ��ǰ�� ������ �߰��Ǿ����ϴ�.");
				alert.setContentText(dpEndDate.getValue() + "");
				alert.showAndWait();
				PrInit();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("��ǰ ���� �Է�");
			alert.setHeaderText("��ǰ ������ ��Ȯ�� �Է��ϼ���.");
			alert.setContentText("�������� �����ϼ���!!");
			alert.showAndWait();
		}
	};

	// ��������� ��� ��ư
	public void handlerBtnCusRegistAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			customerData.removeAll(customerData);
			CustomerVO cVo = null;
			CustomerDAO cDao = new CustomerDAO();
			// �� ���
			cVo = new CustomerVO(txtCusName.getText(), txtCusPhone.getText(), txtPessPhone.getText(),
					txtEmail.getText(), txtAccountNumber.getText());
			cDao = new CustomerDAO();
			// �� ��� SQL��
			cDao.getCustomerRegist(cVo);
			reservationData.removeAll(reservationData);
			ReservationVO rVo = null;
			ReservationDAO rDao = new ReservationDAO();
			salesData.removeAll(salesData);
			// ���� ���
			rVo = new ReservationVO(dpRegistDate.getValue() + "", Integer.parseInt(txtAdult.getText()),
					Integer.parseInt(txtChild.getText()), Integer.parseInt(txtTotalPrice.getText()),
					Integer.parseInt(txtProductCode.getText()), cDao.getCusNoMax().getCusNo());
			rDao = new ReservationDAO();
			// ���� ��� SQL��
			rDao.getReservationRegist(rVo);
			if (cDao != null && rDao != null) {
				CusTotalList();
				ResTotalList();
				SalesTotalList();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� ���� �Է�");
				alert.setHeaderText(txtCusName.getText() + " ���� ������ �߰��Ǿ����ϴ�.");
				alert.setContentText("���� ���� �߰��� �����߽��ϴ�.");
				alert.showAndWait();
				CusResInit();
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("��ǰ ���� �Է�");
			alert.setHeaderText("��ǰ ������ ��Ȯ�� �Է��ϼ���.");
			alert.setContentText(dpRegistDate.getValue() + "");
			alert.showAndWait();
		}
	}

	// ��ǰ��ϰ��� �ʱ�ȭ ��ư
	public void handlerBtnPrResetAction(ActionEvent event) {
		// TODO Auto-generated method stub
		PrInit();
	}

	// ��ǰ��ϰ��� �ʱ�ȭ �޼ҵ�
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

	// ��������� �ʱ�ȭ �޼ҵ�
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

	// ��ǰ��ϰ��� ��ǰ��ȣ �Է�
	public void handlerBtnCodeMaxAction(ActionEvent event) {

		ProductDAO pDao = null;
		pDao = new ProductDAO();

		try {
			txtPrCode.setText(pDao.getPrNoMax().getPrCode() + 1 + "");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ��ǰ��ϰ��� ��ǰ ����
	public void handlerBtnPrDeleteAction(ActionEvent event) {

		ProductDAO pDao = null;
		pDao = new ProductDAO();

		try {
			pDao.getProductDelete(deleteProductNo);
			productData.removeAll(productData);
			// �л� ��ü ����
			PrTotalList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��������� ����
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
				alert.setTitle("������ Ŭ�� ����");
				alert.setHeaderText("���� ������ �ٸ��ϴ�. �����̺� Ŭ���ϰų� ���� ���� Ŭ�����ּ���.");
				alert.setContentText("���� ������ �ٸ��ϴ�. �����̺� Ŭ���ϰų� ���� ���� Ŭ�����ּ���.!!!");
				alert.showAndWait();
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ��ǰ��ϰ��� �˻� �޼ҵ�(����, ��ǰ��ȣ, ��¥)
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

		// ��ǰ���� or ��ǰ��ȣ or ��¥�� �˻�
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
				alert.setTitle("��ǰ ���� �˻�");
				alert.setHeaderText("��ǰ�� ���� �Ǵ� ��ǰ���� ��Ȯ�� �Է��Ͻÿ�.");
				alert.setContentText("�������� �����ϼ���!");
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
				alert.setTitle("��ǰ ���� �˻�");
				alert.setHeaderText("��ǰ ������ ����Ʈ�� �����ϴ�.");
				alert.setContentText(searchDate);
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("��ǰ ���� �˻� ����");
			alert.setHeaderText("��ǰ ���� �˻��� ������ �߻��Ͽ����ϴ�.");
			alert.setContentText("�ٽ� �ϼ���.");
			alert.showAndWait();
			System.out.println(e.toString());
		}

	}

	// ������� �˻� (��ǰ�ڵ�)
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
				alert.setTitle("������� �˻�");
				alert.setHeaderText("��ǰ�ڵ带 ��Ȯ�� �Է����ּ���!");
				alert.setContentText("�������� �����ϼ���!");
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
				alert.setTitle("��ǰ ���� �˻�");
				alert.setHeaderText("��ǰ ������ ����Ʈ�� �����ϴ�.");
				alert.setContentText("����!!!");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("���� ���� �˻� ����");
			alert.setHeaderText("���� ���� �˻��� ������ �߻��Ͽ����ϴ�.");
			alert.setContentText("�ٽ� �ϼ���.");
			alert.showAndWait();
			System.out.println(e.toString());
		}
	}

	// ��������� �˻�(�ڵ��� ��ȣ, �̸�)
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

		// �� �ڵ��� ��ȣ or �� �̸� ���� �˻�
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
			// �ƹ��͵� ������ ����â
			if (searchPhone.equals("") && searchName.equals("")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("��������� �˻�");
				alert.setHeaderText("�ڵ��� ��ȣ �Ǵ� �̸��� ��Ȯ�� �Է��ϼ���.");
				alert.setContentText("�������� �����ϼ���!");
				alert.showAndWait();
				System.out.println();
			}
			// �޴�����ȣ�� �Է� �˻�
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
			// �̸��� �Է� �˻�
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
			// �̸��� �޴��� ��� �Է��ؼ� �˻�
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
				alert.setTitle("��ǰ ���� �˻�");
				alert.setHeaderText("��ǰ ������ ����Ʈ�� �����ϴ�.");
				alert.setContentText("�ٽ� �˻��ϼ���.");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("������ �˻� ����");
			alert.setHeaderText("������ �˻��� ������ �߻��Ͽ����ϴ�.");
			alert.setContentText("�ٽ� �ϼ���.");
			alert.showAndWait();
		}

	}

	// ��ǰ���� ��ü ����
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

	// ����� ��ü ����
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

	// ����� ��ü ����
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

	// ������� ��ü ����
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
	 * ���� ��Ʈ�ѷ�
	 * 
	 ******************/
	// ��ǰ��ϰ��� ����
	public void handlerBtnPrEditAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/View/prEdit.fxml"));

			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnPrEdit.getScene().getWindow());
			dialog.setTitle("��ǰ���� ����");

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
					System.out.println("RootController ���� " + e1);
					e1.printStackTrace();
				}
			});

			btnPrExit.setOnAction(e -> {
				try {
					dialog.close();
				} catch (Exception e1) {
					System.out.println("RootController ���� " + e1);
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
			alert.setTitle("��ǰŬ�� ����");
			alert.setHeaderText("���̺���� ��ǰ�� Ŭ�����ּ���.");
			alert.setContentText("��ǰ�� Ŭ�����ּ���!!!");
			alert.showAndWait();
		}
	}

	// ��������� ����
	public void handlerBtnCusEditAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/View/cusEdit.fxml"));

			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnCusEdit.getScene().getWindow());
			dialog.setTitle("�������� ����");

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
						System.out.println("RootController ���� ��� ���� " + e1);
					}

				});

				btnCusExit.setOnAction(e -> {
					try {
						dialog.close();
					} catch (Exception e1) {
						System.out.println("RootController ���� �ݱ� ���� " + e1);
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
						alert.setTitle("��ǰ �Ǵ� �ο� ����");
						alert.setHeaderText("��ǰ �Ǵ� �ο� ������ ��Ȯ�� �Է����ּ���.");
						alert.setContentText("��ǰ �Ǵ� �ο� ���� ����!!!");
						alert.showAndWait();
					}
				});
				btnCusPrSearch.setOnAction(e -> {
					try {
						// ���ο� ���â�� ���
						FXMLLoader newLoader = new FXMLLoader();
						newLoader.setLocation(getClass().getResource("/View/prSearch.fxml"));

						Stage newDialog = new Stage(StageStyle.UTILITY);
						newDialog.initModality(Modality.WINDOW_MODAL);
						newDialog.initOwner(btnCusRegist.getScene().getWindow());
						newDialog.setTitle("��ǰ��ȣ �˻�");

						Parent parentSearch = (Parent) newLoader.load();
						ProductsVO productSearch = productTableView.getSelectionModel().getSelectedItem();
						selectedProductIndex = productTableView.getSelectionModel().getSelectedIndex();

						TextField prSearchSeason = (TextField) parentSearch.lookup("#txtPrSearchSeason");
						TextField prSearchPrCode = (TextField) parentSearch.lookup("#txtPrSearchPrCode");
						DatePicker prSearchDate = (DatePicker) parentSearch.lookup("#dpPrSearchDate");
						prSearchDate.setValue(LocalDate.now());

						TableView<ProductsVO> productTableView = (TableView) parentSearch.lookup("#productTableView");
						productTableView.setEditable(false);

						// ���â�� productTableView ���̺� �� �÷��̸� ����
						TableColumn colPrCode = new TableColumn("��ǰ�ڵ�");
						colPrCode.setMaxWidth(80);
						colPrCode.setStyle("-fx-allignment:CENTER");
						colPrCode.setCellValueFactory(new PropertyValueFactory<>("prCode"));

						TableColumn colTourName = new TableColumn("�����");
						colTourName.setMaxWidth(80);
						colTourName.setCellValueFactory(new PropertyValueFactory<>("tourName"));

						TableColumn colStartPoint = new TableColumn("�����");
						colStartPoint.setMaxWidth(80);
						colStartPoint.setCellValueFactory(new PropertyValueFactory<>("startPoint"));

						TableColumn colStartDate = new TableColumn("��߳�¥");
						colStartDate.setMaxWidth(140);
						colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));

						TableColumn colDestination = new TableColumn("������");
						colDestination.setMaxWidth(80);
						colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));

						TableColumn colDesDate = new TableColumn("������¥");
						colDesDate.setMaxWidth(140);
						colDesDate.setCellValueFactory(new PropertyValueFactory<>("desDate"));

						TableColumn colReturnPoint = new TableColumn("�������");
						colReturnPoint.setMaxWidth(80);
						colReturnPoint.setCellValueFactory(new PropertyValueFactory<>("returnPoint"));

						TableColumn colReturnDate = new TableColumn("���ͳ�¥");
						colReturnDate.setMaxWidth(140);
						colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

						TableColumn colAdultCharge = new TableColumn("���ο��");
						colAdultCharge.setMaxWidth(60);
						colAdultCharge.setCellValueFactory(new PropertyValueFactory<>("adultCharge"));

						TableColumn colChildCharge = new TableColumn("���ο��");
						colChildCharge.setMaxWidth(60);
						colChildCharge.setCellValueFactory(new PropertyValueFactory<>("childCharge"));

						TableColumn colSeason = new TableColumn("����");
						colSeason.setMaxWidth(80);
						colSeason.setCellValueFactory(new PropertyValueFactory<>("season"));

						TableColumn colHotelName = new TableColumn("ȣ�ڸ�");
						colHotelName.setMaxWidth(80);
						colHotelName.setCellValueFactory(new PropertyValueFactory<>("hotelName"));

						TableColumn colSchedule = new TableColumn("����");
						colSchedule.setMaxWidth(60);
						colSchedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));

						TableColumn colMaximum = new TableColumn("Max");
						colMaximum.setMaxWidth(43);
						colMaximum.setCellValueFactory(new PropertyValueFactory<>("maximum"));

						TableColumn colMinimum = new TableColumn("Min");
						colMinimum.setMaxWidth(43);
						colMinimum.setCellValueFactory(new PropertyValueFactory<>("minimum"));
						// ��ǰ���� ���̺��� ���̺��
						productTableView.getColumns().addAll(colPrCode, colSeason, colTourName, colStartPoint,
								colStartDate, colDestination, colDesDate, colReturnDate, colAdultCharge, colChildCharge,
								colHotelName, colMaximum, colMinimum);
						productTableView.setItems(productData);
						// ��ǰ��ȣ �˻����� ������ or ����� �˻���ư
						Button btnPrSearch = (Button) parentSearch.lookup("#btnPrSearch");
						// ��ǰ��ȣ �˻����� ��ü���� ��ư
						Button btnPrSearchTotal = (Button) parentSearch.lookup("#btnPrSearchTotal");
						// ��ǰ��ȣ �˻����� ���ù�ư
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
									alert.setTitle("��ǰ ����");
									alert.setHeaderText("������ ��ǰ�� �����ϴ�.");
									alert.setContentText("��ǰ�� �߰��� �Ŀ� �����ϼ���.");
									alert.showAndWait();
								}
								return;
							}

						});

						btnPrSearchTotal.setOnAction(e1 -> {
							try {
								productData.removeAll(productData);
								// ��ǰ ��ü ����
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
									// ��ǰ ��ü ����
									PrTotalList();
								}
							} catch (Exception e2) {
								Alert alert = new Alert(AlertType.WARNING);
								alert.setTitle("�����ϼ���.");
								alert.setHeaderText("��ǰ�� �������ּ���!");
								alert.setContentText("��ǰ�� �������ּ���!!!");
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

							// ��ǰ���� or ��ǰ�� or ��¥�� �˻�
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
									alert.setTitle("��ǰ ���� �˻�");
									alert.setHeaderText("��ǰ�� ���� �Ǵ� ��ǰ���� ��Ȯ�� �Է��Ͻÿ�.");
									alert.setContentText("�������� �����ϼ���!");
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
									alert.setTitle("��ǰ ���� �˻�");
									alert.setHeaderText("��ǰ ������ ����Ʈ�� �����ϴ�.");
									alert.setContentText(searchDate);
									alert.showAndWait();
								}
							} catch (Exception e2) {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("��ǰ ���� �˻� ����");
								alert.setHeaderText("��ǰ ���� �˻��� ������ �߻��Ͽ����ϴ�.");
								alert.setContentText("�ٽ� �ϼ���.");
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
				alert.setTitle("������ Ŭ�� ����");
				alert.setHeaderText("���� ������ �ٸ��ϴ�. ���� ���� Ŭ�����ּ���.");
				alert.setContentText("���� ������ �ٸ��ϴ�. ���� ���� Ŭ�����ּ���. ������!!!!!");
				alert.showAndWait();
				return;
			}

		} catch (IOException e) {
			System.out.println(e.toString());
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("����� Ŭ�� ����");
			alert.setHeaderText("���̺���� �������� ���������� Ŭ�����ּ���.");
			alert.setContentText("�������� ���������� Ŭ�����ּ���!!!");
			alert.showAndWait();
		}
	}

}
