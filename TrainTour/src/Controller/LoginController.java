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
			alert.setTitle("로그인 실패");
			alert.setHeaderText("아이디와 비밀번호 미입력");
			alert.setContentText("아이디와 비밀번호중 입력하지 않은 항목이 있습니다. \n다시 제대로 입력하세요.");
			alert.showAndWait();
			return;
		}

		if (txtId.getText().equals("ohgeehun") && txtPassword.getText().equals("dhrlgns1")) {
			// 아이디 패스워드 일치시
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
				System.out.println("오류" + e);
			}
		} else {
			// 아이디 패스워드 확인하라는 창
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("로그인 실패");
			alert.setHeaderText("아이디와 비밀번호 불일치");
			alert.setContentText("아이디와 비밀번호가 일치하지 않습니다. \n다시 제대로 입력하세요.");
			alert.showAndWait();
		}
	}

}
