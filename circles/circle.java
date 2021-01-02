
package circles;

import java.awt.*;

class circle {
    public double x, y, rad;
    public float col_r, col_g, col_b, col_a;
    public int col_chan;

    public double rad_core, rad_ext;
    public int diam_i;

    private Color color;

    final static private double TWOPI = Math.PI * 2.0;
    final static private double RAD_MIN = 20.0;

    public circle () {
        this.init (0, 0);
    }

    public circle (double x, double y) {
        this.init (x, y);
    }

    public void set_pos (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set_rad (double rad) {
        if (rad < this.RAD_MIN)
            rad = this.RAD_MIN;
        this.rad = rad;

        this.rad_core = rad / 4.0;
        this.rad_ext = rad * 0.75;
        this.diam_i = (int)(this.rad * 2.0);
    }

    public void set_color (float r, float g, float b) {
        this.col_r = r;
        this.col_g = g;
        this.col_b = b;
        this.color = new Color (r, g, b, this.col_a);
    }

    public void set_alpha (float a) {
        this.col_a = a;
        this.color = new Color (this.col_r, this.col_g, this.col_b, a);
    }

    public void set_chan (int c) {
        this.col_chan = c;
        if (c > 2)
            this.col_chan = 0;
    }

    public void init (double x, double y) {
        this.set_pos (x, y);
        this.set_rad (RAD_MIN * 2);
        this.set_alpha (0.95f);
        this.set_color (0.8f, 0.8f, 0.8f);
        this.set_chan (0);
    }
 
    public double dist (double x, double y) {
        double dx = x - this.x;
        double dy = y - this.y;
        return Math.sqrt (dx * dx + dy * dy);
    }

    public void move_to (double x, double y) {
        this.set_pos (x, y);        
    }

    public void resize (double x, double y) {
        this.set_rad (this.dist (x, y));
    }

    public void colorize (double x, double y) {
        float c_new = (float)(0.5 + Math.atan2 (x - this.x, y - this.y) / TWOPI);

        if (this.col_chan == 0)
            this.set_color (c_new, this.col_g, this.col_b);
        else if (this.col_chan == 1)
            this.set_color (this.col_r, c_new, this.col_b);
        else if (this.col_chan == 2)
            this.set_color (this.col_r, this.col_g, c_new);
    }

    public void alphize (double x, double y) {
        float d_clip = (float)(this.dist (x, y) / this.rad);
        if (d_clip > 1.0f)
            d_clip = 1.0f;

        this.set_alpha (d_clip);
    }
 
    public void draw (Graphics2D g2) {
        g2.setPaint (this.color);
        g2.fillOval ((int)(this.x - this.rad), (int)(this.y - this.rad),
            this.diam_i, this.diam_i);
    }
}

