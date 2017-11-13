package visualizer;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Parameters;
import model.Tile;

public class Controls extends HBox {
	private Button _nextTurn;
	private Visualizer _viz;
	private ToggleGroup gp;
	private control.Controller c;
	private TextField _unitField;
	private TextField _popField;

	public Controls(Visualizer v, control.Controller c) {
		_viz = v;
		_nextTurn = new Button("Next Turn");
		_nextTurn.setOnAction(this::nextTurn);
		this.getChildren().add(_nextTurn);
		VBox mapViewControls = new VBox();
		gp = new ToggleGroup();
		RadioButton dragonGlassMap = new RadioButton("Dragonglass Map");
		RadioButton powerMap = new RadioButton("Power Map");
		RadioButton strengthMap = new RadioButton("Strength Map");
		RadioButton defenseMap = new RadioButton("Defense Map");
		RadioButton wwMap = new RadioButton("White Walker Progress");
		wwMap.setToggleGroup(gp);
		dragonGlassMap.setToggleGroup(gp);
		powerMap.setToggleGroup(gp);
		strengthMap.setToggleGroup(gp);
		defenseMap.setToggleGroup(gp);
		mapViewControls.getChildren().addAll(dragonGlassMap, powerMap,
		        strengthMap, defenseMap, wwMap);
		this.getChildren().add(mapViewControls);
		dragonGlassMap.setOnAction(this::handleRButton);
		powerMap.setOnAction(this::handleRButton);
		strengthMap.setOnAction(this::handleRButton);
		defenseMap.setOnAction(this::handleRButton);
		wwMap.setOnAction(this::handleRButton);
		dragonGlassMap.setUserData("dgmap");
		powerMap.setUserData("pwrmap");
		strengthMap.setUserData("strengthmap");
		defenseMap.setUserData("defmap");
		wwMap.setUserData("wwmap");
		this.c = c;
		defenseMap.setSelected(true);
		VBox textfieldBox = new VBox();
		Label cd = new Label("Create A City");
		Label l = new Label("Garrison");
		_unitField = new TextField();
		Label a = new Label("Population");
		_popField = new TextField();
		textfieldBox.getChildren().addAll(cd, l, _unitField, a, _popField);
		this.getChildren().add(textfieldBox);
		Button b = new Button("Reset WW Progress");
		textfieldBox.getChildren().add(b);
		b.setOnAction((e)->c.reset());
	}

	public void handleRButton(ActionEvent e) {
		c.displayMap(gp.getSelectedToggle().getUserData());
	}

	public int getUnitField() {
		int x = 0;
		try {
			x = Integer.parseInt(_unitField.getText());
		} catch (NumberFormatException nfe) {
			return -1;
		}
		return x;
	}

	public int getPopField() {
		int x = 0;
		try {
			x = Integer.parseInt(_popField.getText());
		} catch (NumberFormatException nfe) {
			return -1;
		}
		return x;
	}

	public Object getSelectedRButton() {
		return gp.getSelectedToggle().getUserData();
	}
	
	public void nextTurn(ActionEvent e) {
		c.advanceWW(Parameters.WHITE_WALKER_MAX_DISTANCE/Tile.TILE_SIZE_IN_PIXELS);
	}

}
