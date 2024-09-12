
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSlider;

import java.awt.GridLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ValueSlider extends JPanel{
    

    public ValueSlider(String label, int startValue, int min, int max, double divFactor, SliderFunc func) {
        this.setLayout(new GridLayout(1,2));

        JLabel val_label = new JLabel(label + (startValue / divFactor));
        this.add(val_label);

        JSlider val_slider = new JSlider(min, max);
        val_slider.addChangeListener(new ChangeListener() {
            private int value = val_slider.getValue();

            @Override
            public void stateChanged(ChangeEvent e) {
                this.value = val_slider.getValue();
                // env.OUTER_RANGE = val_slider.getValue();
                val_label.setText(label + (this.value / divFactor));
                func.apply(this.value / divFactor);
            }
        });
        val_slider.setValue(startValue);

        this.add(val_slider);
    }

}
