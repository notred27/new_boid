import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Driver {

    static int val = 0;
    public static void main(String[] args) {

        for(int i = 0; i < 100; i++) {
            new Boid(150, 150);

        }
        

        
        JFrame f = new JFrame();
        JPanel p = new BoidVisaulizer();


        JLabel val_label = new JLabel("Val : 0");
        JSlider val_slider = new JSlider(0, 50);
        val_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                val = val_slider.getValue();
                val_label.setText("Val: " + val);
            }
        });
        val_slider.setValue(0);

        JButton showVision = new JButton();
        showVision.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });


        JPanel settingsBar = new JPanel();
        settingsBar.setLayout(new BoxLayout(settingsBar, BoxLayout.Y_AXIS));
        settingsBar.add(val_label);
        settingsBar.add(val_slider);



        JPanel container = new JPanel();
        container.add(p);
        container.add(settingsBar);

        f.add(container);
        f.pack();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);

    }
}