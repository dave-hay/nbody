import java.lang.Math;

public class Body {
    // instance variables
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    // 
    public double dx;
    public double dy;
    public double distance;  // r squared
    public double r;  // r
    public double pairwise;

    // netForce
    public double netForceX;
    public double netForceY;

    // 
    public static double gravConst = 6.67e-11;

    // new velocity
    public double dt;
    public double fX;
    public double fY;

    // instance methods
    public Body(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b) {
        Body c = new Body(b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    public void calcDistance(Body b) {
        // distance between this and Body b
        dx = b.xxPos - this.xxPos;
        dy = b.yyPos - this.yyPos;
        distance = Math.pow(dx, 2) + Math.pow(dy, 2);
        r = (Math.sqrt(distance));
    }

    public void calcForceExertedBy(Body b) {
        // gets F
        calcDistance(b);
        pairwise = (gravConst * this.mass * b.mass) / distance;
    }

    // NetForce

    public double calcNetForceExertedByX(Body b) { // gets f_x
        calcForceExertedBy(b); 
        netForceX = (pairwise * dx) / r;
        return netForceX;
    }

    public double calcNetForceExertedByY(Body b) { // gets f_y
        calcForceExertedBy(b);  
        netForceY = (pairwise * dy) / r;
        return netForceY;
    }

    public double calcNetForceExertedByX(Body[] ar) {
        netForceX = 0.0;
        for (Body i: ar) {
            if (this != i) {
                calcForceExertedBy(i); 
                netForceX = netForceX + (pairwise * dx) / r;
            }
        }
        return netForceX;
    }

    public double calcNetForceExertedByY(Body[] ar) {
        netForceY = 0.0;
        for (Body i: ar) {
            if (this != i) {
                calcForceExertedBy(i);
                netForceY = netForceY + (pairwise * dy) / r;
            }
        }
        return netForceY;
    }

    public void update(double dt, double fX, double fY) {

        // calculate acceleration
        double ax = fX / mass;
        double ay = fY / mass;

        // calculate new velocity 
        xxVel = xxVel + (dt * ax);
        yyVel = yyVel + (dt * ay);

        // calculate new position
        xxPos = xxPos + (dt * xxVel);
        yyPos = yyPos + (dt * yyVel);

    }

    public void draw() {
        // draw bodies image in position
        String filename = "images/" + imgFileName;
        StdDraw.picture(xxPos, yyPos, filename);
    }
}













