package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.ReservationVO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

	@FXML
	private TextField txtId;
	@FXML
	private TextField txtPassword;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnCancel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		btnLogin.setOnAction(event -> handlerBtnLoginAction(event));
		btnCancel.setOnAction(event -> handlerBtnCancelAction(event));
		txtId.setOnKeyPressed(KeyEvent -> handlerTxtIdAction(KeyEvent));
		txtPassword.setOnKeyPressed(KeyEvent -> handlerTxtPasswordAction(KeyEvent));

	}

	private void handlerTxtPasswordAction(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
		if (keyEvent.getCode() == KeyCode.ENTER) {
			login();
		}
	}

	private void handlerTxtIdAction(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
		if (keyEvent.getCode() == KeyCode.ENTER) {
			login();
		}
	}

	private void handlerBtnCancelAction(ActionEvent event) {
		// TODO Auto-generated method stub
		Platform.exit();
	}

	private void handlerBtnLoginAction(ActionEvent event) {
		// TODO Auto-generated method stub
		Alert alert;

		login();
	}

	public void login() {
		Alert alert;

		if (txtId.getText().equals("") || txtPassword.getText().equals("")) {
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("�α��� ����");
			alert.setHeaderText("���̵�� ��й�ȣ ���Է�");
			alert.setContentText("���̵�� ��й�ȣ�� �Է����� ���� �׸��� �ֽ��ϴ�. \n�ٽ� ����� �Է��ϼ���.");
			alert.showAndWait();
			return;
		}

		if (txtId.getText().equals("ohgeehun") && txtPassword.getText().equals("dhrlgns1")) {
			// ���̵� �н����� ��ġ��
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/root.fxml"));
				Parent rootView = (Parent) loader.load();
				Scene scene = new Scene(rootView);
				Stage rootStage = new Stage();
				rootStage.setScene(scene);
				rootStage.setResizable(false);
				Stage oldStage = (Stage) btnLogin.getScene().getWindow();
				oldStage.close();
				rootStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("����" + e);
			}
		} else {
			// ���̵� �н����� Ȯ���϶�� â
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("�α��� ����");
			alert.setHeaderText("���̵�� ��й�ȣ ����ġ");
			alert.setContentText("���̵�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�. \n�ٽ� ����� �Է��ϼ���.");
			alert.showAndWait();
		}
	}

}
