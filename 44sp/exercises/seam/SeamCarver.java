import java.awt.Color;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
    private Picture pic;
    private int width;
    private int height;
    private double energy[];

    /**
     * create a seam carver object based on the given picture
     */
    public SeamCarver(Picture picture) {
        pic = new Picture(picture);
        width = pic.width();
        height = pic.height();
        energy = new double[width * height];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++) {
                computeEnergy(col, row);
            }
    }

    /**
     * current picture
     */
    public Picture picture() {
        return pic;
    }

    /**
     * width of current picture
     */
    public int width() {
        return width;
    }

    /**
     * height of current picture
     */
    public int height() {
        return height;
    }

    // column x, row y
    private int index(int x, int y) {
        return y * width + x;
    }

    private int[] index2D(int i) {
        int y = i / width;      // row
        int x = i - y * width;  // column
        return new int[] { x, y };
    }

    // column x, row y
    private void computeEnergy(int x, int y) {
        if (x == 0 || x == width - 1
            || y == 0 || y == height - 1) {
            energy[index(x, y)] = 1000.0;
            return;
        }
        int[] xx = new int[] { x+1, x-1, x, x }; // right,left,down,up
        int[] yy = new int[] { y, y, y+1, y-1 }; // right,left,down,up
        final int N = xx.length;     // neighbors: right,left,down,up
        Color[] c = new Color[N];
        int[] r = new int[N];
        int[] g = new int[N];
        int[] b = new int[N];
        for (int i = 0; i < N; i++) {
            c[i] = pic.get(xx[i], yy[i]);
            r[i] = c[i].getRed();
            g[i] = c[i].getGreen();
            b[i] = c[i].getBlue();
        }

        double rxsq = Math.pow(r[1] - r[0], 2);
        double gxsq = Math.pow(g[1] - g[0], 2);
        double bxsq = Math.pow(b[1] - b[0], 2);
        double dxsq = rxsq + gxsq + bxsq;

        double rysq = Math.pow(r[3] - r[2], 2);
        double gysq = Math.pow(g[3] - g[2], 2);
        double bysq = Math.pow(b[3] - b[2], 2);
        double dysq = rysq + gysq + bysq;

        energy[index(x, y)] = Math.sqrt(dxsq + dysq);
    }

    /**
     * energy of pixel at column x and row y
     */
    public double energy(int x, int y) {
        return energy[index(x, y)];
    }

    /**
     * sequence of indices for horizontal seam
     */
    public int[] findHorizontalSeam() {
        return null;
    }

    /**
     * sequence of indices for vertical seam
     */
    public int[] findVerticalSeam() {
        return null;
    }

    /**
     * remove horizontal seam from current picture
     */
    public void removeHorizontalSeam(int[] seam) {
    }

    /**
     * remove vertical seam from current picture
     */
    public void removeVerticalSeam(int[] seam) {
    }

    public static void main(String[] args) {
        Picture pic = new Picture(args[0]);
        SeamCarver seam = new SeamCarver(pic);
        StdOut.println("width=" + seam.width());
        StdOut.println("height=" + seam.height());
        StdOut.printf("energy(1,2)=%.2f\n", seam.energy(1, 2));
        StdOut.printf("energy(1,1)=%.2f\n", seam.energy(1, 1));
    }
}
