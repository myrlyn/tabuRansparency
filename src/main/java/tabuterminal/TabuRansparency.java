package tabuterminal;

import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TabuRansparency extends TabuTerminalPlugin_V1 {
	private static final String OPACITY = "opacity";
	TabuTerminal window ;
	MenuBar menubar;
	Double transVal = 100.0;
	Menu transMenu = new Menu("Transparency");
	MenuItem adjustTransparencyItem = new MenuItem("Adjust Transparency");
	Stage dialog = new Stage();
	Button okButton = new Button("OK");
	VBox dialogVBox = new VBox();
	Scene dialogScene = new Scene(dialogVBox, 400, 100);
	Slider transSlider = null;
	
	public TabuRansparency(TabuTerminal jtt) {
		super(jtt);
	}

	@Override
	public void initialize(String jf) {
		window = this.getTerminalWindow();
		menubar = window.getMenuBar();
		transVal = (100.0*(this.getTerminalWindow().getMainWindow().getOpacity()));
		transSlider = new Slider(0,100,transVal);
		transSlider.setAccessibleHelp("Set Opacity");
		transSlider.setMajorTickUnit(5.0);
		transSlider.setShowTickMarks(true);
		transSlider.valueProperty().addListener(val -> {
			window.getMainWindow().setOpacity(transSlider.getValue()/100.0);
		});
		double initialTransparency = window.getMainWindow().getOpacity();
		VBox.setVgrow(transSlider, Priority.ALWAYS);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(window.getMainWindow());
		dialog.setTitle("Transparency");
		dialog.setOnCloseRequest(evt -> {
			window.getMainWindow().setOpacity(initialTransparency);
			transSlider.setValue(transVal=100.0*initialTransparency);
			dialog.close();
		});
		Text tranText = new Text("Set Opacity %");
		VBox.setVgrow(tranText, Priority.ALWAYS);
		dialogVBox.getChildren().add(tranText);
		dialogVBox.getChildren().add(transSlider);
		dialogVBox.getChildren().add(okButton);
		VBox.setMargin(tranText, new Insets(0.2, 0.2, 0.2, 0.2));
		VBox.setMargin(transSlider, new Insets(0.2, 0.2, 0.2, 0.2));
		VBox.setMargin(okButton, new Insets(0.2, 0.2, 0.2, 0.2));
		okButton.setOnAction(avt -> {
			setTransparency();
		});
		menubar.getMenus().add(transMenu);
		transMenu.getItems().add(adjustTransparencyItem);
		dialog.setAlwaysOnTop(false);
		adjustTransparencyItem.setOnAction(avt -> onClick());
		dialog.setScene(dialogScene);
	}

	private void setTransparency() {
		transVal = transSlider.getValue();
		window.getMainWindow().setOpacity(transVal/100.0);
		dialog.hide();
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
		return "TabuRansparency";
	}
	@Override
	public void applySettings() {
		Map<String,Object> settings = this.getTerminalWindow().getSettings();
		if (settings.containsKey(OPACITY)) {
			Object o = settings.get(OPACITY);
			if (o instanceof Double) {
				this.transVal = (Double)o;
				window.getMainWindow().setOpacity(transVal/100.0);
			}
		}
	}
	@Override
	public void saveSettings() {
		Map<String,Object> settings = this.getTerminalWindow().getSettings();
		settings.put(OPACITY, transVal);
	}
}
