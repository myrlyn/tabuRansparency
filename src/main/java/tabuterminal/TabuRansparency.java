package tabuterminal;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TabuRansparency extends TabuTerminalPlugin_V1 {
	TabuTerminal window ;
	MenuBar menubar;
	Spinner<Integer> tranSpinner;
	SpinnerValueFactory<Integer> spinValFac;
	Integer transVal = 100;
	Menu transMenu = new Menu("Transparency");
	MenuItem adjustTransparencyItem = new MenuItem("Adjust Transparency");
	Stage dialog = new Stage();
	Button okButton = new Button("OK");
	VBox dialogVBox = new VBox();
	Scene dialogScene = new Scene(dialogVBox, 1115, 755);

	public TabuRansparency(TabuTerminal jtt) {
		super(jtt);
	}

	@Override
	public void initialize(String jf) {
		window = this.getTerminalWindow();
		menubar = window.getMenuBar();
		spinValFac =new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,100);
		tranSpinner = new Spinner<>();
		transVal = 100;
		VBox.setVgrow(tranSpinner, Priority.ALWAYS);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(window.getMainWindow());
		dialog.setTitle("Transparency");
		Text tranText = new Text("Set Opacity %");
		VBox.setVgrow(tranText, Priority.ALWAYS);
		dialogVBox.getChildren().add(tranText);
		dialogVBox.getChildren().add(tranSpinner);
		dialogVBox.getChildren().add(okButton);
		VBox.setMargin(tranText, new Insets(0.2, 0.2, 0.2, 0.2));
		VBox.setMargin(tranSpinner, new Insets(0.2, 0.2, 0.2, 0.2));
		VBox.setMargin(okButton, new Insets(0.2, 0.2, 0.2, 0.2));
		okButton.setOnAction(avt -> {
			window.getMainWindow().setOpacity(tranSpinner.getValue()/100.0);
			transVal = tranSpinner.getValue();
			dialog.hide();
		});
		tranSpinner.setValueFactory(spinValFac);
		menubar.getMenus().add(transMenu);
		transMenu.getItems().add(adjustTransparencyItem);
		dialog.setAlwaysOnTop(false);
		adjustTransparencyItem.setOnAction(avt -> onClick());
		dialog.setScene(dialogScene);
	}
	public void onClick() {
		dialog.setAlwaysOnTop(true);
		dialog.showAndWait();

	}

	@Override
	public void removePlugin() {
		window.getMainWindow().setOpacity(1.0);
		dialog.hide();
		dialog.setAlwaysOnTop(false);
		
	}

	@Override
	public String getPluginName() {
		return null;
	}

}