import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import javax.swing.*;

public class BoidVisaulizer extends JPanel implements MouseInputListener{


    int width = 500;
    int height = 500;

    int mouseX = -1;
    int mouseY = -1;

    boolean followMouse = false;

    
    public BoidVisaulizer() {
        this.setPreferredSize(new Dimension(width, height));
        this.addMouseListener(this);
        addMouseMotionListener(this);
        int delay = 10; //milliseconds
            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(followMouse){
                        Boid.updatePosition();

                    } else {
                    // System.out.println(mouseX + " " + mouseY);

                        Boid.updatePosition(mouseX, mouseY);
                    }


                    repaint();
                }
            };
            new Timer(delay, taskPerformer).start();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D m = (Graphics2D) g;
        m.clearRect(0, 0, width, height);
        m.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Boid boid : Boid.getBoids()){
            m.drawOval((int)boid.getX(),(int)boid.getY(), 5, 5);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        followMouse = !followMouse;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
        // mouseX = e.getX();
        // mouseY = e.getY();
        // System.out.println(mouseX + " " + mouseY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // mouseX = e.getX();
        // mouseY = e.getY();
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }
}