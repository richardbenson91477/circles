
package circles;

import java.awt.*;
import java.util.*;

class circle_list {
    final private LinkedList <circle> circles = new LinkedList <circle> ();
    private circle circle_active = null;

    enum ACTION {
        ADD, MOVE, RESIZE, COLORIZE
    };
    private ACTION action;
    private long action_t;

    final static private long LONG_HOLD_MIN = 500;
 
    private circle classify (double x, double y) {
        var i = this.circles.descendingIterator ();
        while (i.hasNext ()) {
            circle cir = i.next ();
            double d = cir.dist (x, y);
            if (d > cir.rad)
                continue;
            return cir;
        }
        return null;
    }
 
    public void paint (Graphics2D g2) {
        for (circle cir : this.circles)
            cir.draw (g2);
    }

    public void tap (double x, double y) {
        this.circle_active = this.classify (x, y);
        if (this.circle_active == null) {
            circle cir = new circle (x, y);
            this.circles.add (cir);
            this.circle_active = cir;
            this.action = ACTION.ADD;
        }
        else {
            double d = this.circle_active.dist (x, y);
            if (d < this.circle_active.rad_core)
                this.action = ACTION.MOVE;
            else if (d > this.circle_active.rad_ext)
                this.action = ACTION.RESIZE;
            else {
                this.action = ACTION.COLORIZE;
                this.circle_active.set_chan (this.circle_active.col_chan + 1);
            }
 
            this.action_t = System.currentTimeMillis ();
        }
    }
    
    public void tap_dbl (double x, double y) {
    }

    public void drag (double x, double y) {
        if (this.circle_active == null)
            return;

        if ((System.currentTimeMillis () - this.action_t) >
                this.LONG_HOLD_MIN) {
            if (this.action == ACTION.RESIZE) {
                this.circles.remove (this.circle_active);
                this.circle_active = null;
            }
            else if (this.action == ACTION.MOVE) {
                var n = this.circles.indexOf(this.circle_active);
                if (n > 0) {
                    var prev = this.circles.set (n - 1, this.circle_active);
                    this.circles.set (n, prev);
                    this.circle_active = null;
                }
            } 
            else if (this.action == ACTION.COLORIZE) {
                this.circle_active.alphize (x, y);
            }

            return;
        }

        this.action_t = System.currentTimeMillis ();

        if ((this.action == ACTION.ADD) || (this.action == ACTION.RESIZE)) {
            this.circle_active.resize (x, y);
        }
        else if (this.action == ACTION.MOVE) {
            this.circle_active.move_to (x, y);
        }
        else if (this.action == ACTION.COLORIZE) {
            this.circle_active.colorize (x, y);
        }
    }

    public void release () {
        this.circle_active = null;
    }
}

