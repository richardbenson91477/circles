
package circles;

import java.awt.*;
import java.awt.event.*;

class circle_win extends Frame
        implements MouseListener, MouseMotionListener {
    static final long serialVersionUID = 1L;

    final private int win_w = 800;
    final private int win_h = 600;
    final private Color win_bgcolor = Color.black;
    final private String win_s = "circles";

    final private circle_list c_list = new circle_list ();

    private Image dbimage;
    private Graphics2D dbg2;

    public circle_win () {
        super ();
        this.setBackground (this.win_bgcolor);
        this.setSize (this.win_w, this.win_h);
        this.setTitle (this.win_s);
        this.setLayout (null);

        addWindowListener (new WindowAdapter () {
            public void windowClosing (WindowEvent event) {
                System.exit (0);
            }
        });
        this.addMouseListener (this);
        this.addMouseMotionListener (this);

        this.setVisible (true);

        dbimage = createImage (this.win_w, this.win_h);
        dbg2 = (Graphics2D) this.dbimage.getGraphics ();
    }

    public void paint (Graphics g) {
        super.paint (g);

        this.dbg2.setPaint (this.win_bgcolor);
        this.dbg2.fillRect (0, 0, this.win_w, this.win_h);
        this.c_list.paint (this.dbg2);
        g.drawImage (this.dbimage, 0, 0, this);
    }

    public void update (Graphics g) {
        this.paint (g);
    }

    public void mousePressed (MouseEvent event) {
        switch (event.getClickCount()) {
            case 1:
                this.c_list.tap ((double)event.getX(), (double)event.getY());
                break;
            case 2:
                this.c_list.tap_dbl ((double)event.getX(), (double)event.getY());
                break;
        }
        this.repaint ();
    }

    public void mouseDragged (MouseEvent event) {
        this.c_list.drag ((double)event.getX (), (double)event.getY ());
        this.repaint ();
    }

    public void mouseReleased (MouseEvent event) {
        this.c_list.release ();
        this.repaint ();
    }
    
    public void mouseMoved (MouseEvent event) {
    }
    public void mouseClicked (MouseEvent event) {
    }
    public void mouseEntered (MouseEvent event) {
    }
    public void mouseExited (MouseEvent event) {
    }

    public static void main (String[] args) {
        new circle_win ();
    }
}

