

public class NBody {
    // ans = 2.50e+11.
    public static int nPlanets;
    public static double radius;
    public static double numRows;
    public static String imageToDraw = "images/starfield.jpg";

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Body[] bodies = readBodies(filename);

        // Start drawing

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, imageToDraw);
        for (Body body : bodies) {
            body.draw();
        }
        StdDraw.show();
        StdDraw.pause(10);
        StdAudio.play("audio/2001.mid");
        // animation
        double time = 0;
        while (time <= T) {
            double[] xForces = new double[nPlanets];
            double[] yForces = new double[nPlanets];
            int i = 0;
            for (Body body : bodies) {
                xForces[i] = body.calcNetForceExertedByX(bodies);
                yForces[i] = body.calcNetForceExertedByY(bodies);
                i++;
            }
            i = 0;
            for (Body body : bodies) {
                body.update(dt, xForces[i], yForces[i]);
                i++;
            }
            StdDraw.picture(0, 0, imageToDraw);
            for (Body body : bodies) {
                body.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);

            time = time + dt;

        }

    }

    public static double readRadius(String fp) {
        In in = new In (fp);

        nPlanets = in.readInt();
        radius = in.readDouble();

        return radius;
    }

    public static Body[] readBodies(String fp) {
        In in = new In (fp);
        nPlanets = in.readInt();
        radius = in.readDouble();

        Body[] arPlanet =  new Body[nPlanets];

        int i = 0;
        while(i < nPlanets) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();

            Body newBod = new Body(xP, yP, xV, yV, m, img);
            arPlanet[i] = newBod;
            i++;

        }
        return arPlanet;
    }
}