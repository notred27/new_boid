import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import javax.swing.*;

public class BoidVisaulizer extends JPanel implements MouseInputListener{


    private int width;
    private int height;
    private int border;

    private int mouseX = -1;
    private int mouseY = -1;

    private boolean followMouse = false;
    private BoidEnvironment env;

    public static boolean showVision = false;

    
    public BoidVisaulizer(BoidEnvironment env, int width, int height, int border) {
        this.env = env;
        this.width = width;
        this.height = height;
        this.border = border;
        this.setPreferredSize(new Dimension(width, height));
        this.addMouseListener(this);
        addMouseMotionListener(this);
        int delay = 10; //milliseconds
            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(followMouse){
                        env.updatePosition();

                    } else {
                    // System.out.println(mouseX + " " + mouseY);

                        env.updatePosition(mouseX, mouseY);
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

        m.setColor(new Color(200, 200, 200));
        m.drawRect(border, border, width - border * 2, height - border * 2);
        m.setColor(Color.BLACK);
        for (Boid boid : env.getBoids()){
            m.drawOval((int)boid.getX() - 2,(int)boid.getY() - 2, 4, 4);
            if(showVision) {
                m.setColor(Color.GREEN);
                m.drawOval((int)boid.getX()  - ((int)env.OUTER_RANGE),(int)boid.getY() - ((int)env.OUTER_RANGE), (int)env.OUTER_RANGE * 2, (int)env.OUTER_RANGE * 2);
                m.setColor(Color.BLACK);

            }
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