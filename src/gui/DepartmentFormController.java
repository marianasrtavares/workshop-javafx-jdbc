package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private DepartmentService service;
	
	private Department entity;
	@FXML 
	private TextField txtId;
	
	@FXML 
	private TextField txtName;
	
	@FXML 
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setDepartment(Department entity) {
		this.entity=entity;
		
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service=service;
		
	}
	@FXML
	private void onBtSaveAction(ActionEvent event){
		if(entity==null) {
			throw new IllegalStateException("Entity was null");
		}
		if(service==null) {
			throw new IllegalStateException("Service was null");
		}
		try {
		entity= getFormData();
		service.saveOrUpdate(entity);
		Utils.currentStage(event).close();
		}catch(DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private Department getFormData() {
		Department dep= new Department();
		dep.setId(Utils.tryParseToInt(txtId.getText()));
		dep.setName(txtName.getText());
		return dep;
	}

	@FXML
	private void onBtCancelAction(ActionEvent event){
		Utils.currentStage(event).close();
		
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();		
	}
	
	public void updateFormData() {
		if(entity==null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}

}
