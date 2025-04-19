package application;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ParametreControler {
	
	@FXML
    private CheckBox checkBoxOption;

    @FXML
    private Slider sliderOption;
    
    @FXML
    private Text appercu;
    
    public boolean isOptionChecked() {
        return checkBoxOption.isSelected();
    }

    public double getSliderValue() {
        return sliderOption.getValue();
    }
    public void modifyAppercu(MouseEvent event) {
    	appercu.setFont(new Font(sliderOption.getValue()));
    }
}
