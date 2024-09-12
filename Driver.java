import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.*;
import java.awt.*;


import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Driver {


    public static void main(String[] args) {

        BoidEnvironment env = new BoidEnvironment(500, 500, 50);
        for(int i = 0; i < 20; i++) {
            env.addBoid(250, 250);

        }
        

        
        JFrame f = new JFrame();
        JPanel p = new BoidVisaulizer(env, 500, 500, 50);


        

        

        JLabel numBoids = new JLabel("# Boids: " + env.getNumBoids());

        JButton addBoid = new JButton("Add Boid");
        addBoid.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                env.addBoid(250, 250);
                numBoids.setText("# Boids: " + env.getNumBoids());
            }
        });

        JButton removeBoid = new JButton("Remove Boid");
        removeBoid.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                env.removeBoid();
                numBoids.setText("# Boids: " + env.getNumBoids());

            }
        });


        JPanel settingsBar = new JPanel();
        settingsBar.setLayout(new BoxLayout(settingsBar, BoxLayout.Y_AXIS));


        JPanel boidButtons = new JPanel(new GridLayout(1,2));

        boidButtons.add(addBoid);
        boidButtons.add(removeBoid);



        settingsBar.add(numBoids);
        settingsBar.add(boidButtons);


        // settingsBar.add(showVision);

        JLabel val_label = new JLabel("Boid Vision : 20");
        settingsBar.add(val_label);


        JPanel visionContainer = new JPanel(new GridLayout(1,2));

        JSlider visionSlider = new JSlider(10, 30);
        visionSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                env.OUTER_RANGE = visionSlider.getValue();
                val_label.setText("Val: " + env.OUTER_RANGE);
            }
        });
        visionSlider.setValue(20);

        // JButton visionBtn = new JButton("👁");
        // visionBtn.addActionListener( new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         BoidVisaulizer.showVision = !BoidVisaulizer.showVision;
        //     }
        // });

        // visionContainer.add(visionSlider);
        // visionContainer.add(visionBtn);

        settingsBar.add(visionContainer);

        ValueSlider vision = new ValueSlider("Boid Vision: ", 20, 10 , 30, 1, (double val ) -> env.setOuter(val));
        settingsBar.add(vision);

        JButton visionBtn = new JButton("👁");
        visionBtn.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoidVisaulizer.showVision = !BoidVisaulizer.showVision;
            }
        });
        settingsBar.add(visionBtn);


// iyems
        JPanel speedContainer = new JPanel(new GridLayout(2,2));

        JLabel maxSpeed = new JLabel("Max Speed: 3");



        JSlider maxSpeedSlider = new JSlider(1, 50);
        maxSpeedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double val = (double)maxSpeedSlider.getValue() / 10.0;
                if(val >= env.MIN_SPEED) {
                    env.MAX_SPEED = val;
                    maxSpeed.setText("Max Speed: " + env.MAX_SPEED);
                } else {
                    maxSpeedSlider.setValue((int)(env.MIN_SPEED * 10));
                }
                
            }
        });
        maxSpeedSlider.setValue(30);

        JLabel minSpeed = new JLabel("Min Speed: 3");

        JSlider minSpeedSlider = new JSlider(1, 50);
        minSpeedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double val = (double)minSpeedSlider.getValue() / 10.0;

                if(val <= env.MAX_SPEED) {
                    env.MIN_SPEED = val;
                    minSpeed.setText("Min Speed: " + env.MIN_SPEED);
                } else {
                    minSpeedSlider.setValue((int)(env.MAX_SPEED * 10));
                }
            }
        });
        minSpeedSlider.setValue(20);

        speedContainer.add(maxSpeed);
        speedContainer.add(maxSpeedSlider);
        speedContainer.add(minSpeed);
        speedContainer.add(minSpeedSlider);

        
        settingsBar.add(speedContainer);

        ValueSlider avoid = new ValueSlider("Avoiding: ", 5, 0 , 10, 100, (double val ) -> env.setAvoid(val));
        settingsBar.add(avoid);
        ValueSlider align = new ValueSlider("Alignment: ", 5, 0 , 10, 100, (double val ) -> env.setAlign(val));
        settingsBar.add(align);
        ValueSlider center = new ValueSlider("Centering: ", 5, 0 , 10, 10000, (double val ) -> env.setCenter(val));
        settingsBar.add(center);

        JPanel container = new JPanel();
        container.add(p);
        container.add(settingsBar);

        f.add(container);
        f.pack();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);

    }


    
}