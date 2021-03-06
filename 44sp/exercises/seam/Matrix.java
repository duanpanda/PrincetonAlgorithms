/**
 * number matrix that has the normal mode and transpose mode
 */
class Matrix {
    private int m;
    private int n;
    private boolean isTrans;
    private final double[][] a; // a reference to an external object

    public Matrix(double[][] matrix) {
        // isTransposed == false, row major mode
        this(matrix, false);
    }

    public Matrix(double[][] matrix, boolean isTransposed) {
        assert matrix != null;
        assert matrix[0] != null;
        a = matrix;
        isTrans = isTransposed;
        if (isTrans) {
            m = a[0].length;
            n = a.length;
        }
        else {
            m = a.length;
            n = a[0].length;
        }
    }

    public boolean isTransposed() {
        return isTrans;
    }

    public int rows() {
        return m;
    }

    public int cols() {
        return n;
    }

    /**
     * @throws IllegalArgumentException if p is out of the array bounds
     */
    public double get(Coordinate p) {
        if (!isValidCoordinate(p))
            throw new IllegalArgumentException("p.x=" + p.x + ", p.y=" + p.y
                                               + " is out of bounds. rows="
                                               + m + ", cols=" + n);
        if (isTrans)
            return a[p.x][p.y]; // x is row number, y is column number
        else
            return a[p.y][p.x]; // x is column number, x is row number
    }

    /**
     * @throws IllegalArgumentException if p is out of the array bounds
     */
    public void set(Coordinate p, double v) {
        if (!isValidCoordinate(p))
            throw new IllegalArgumentException("p.x=" + p.x + ", p.y=" + p.y
                                               + " is out of bounds. rows="
                                               + m + ", cols=" + n);

        if (isTrans)
            a[p.x][p.y] = v;
        else
            a[p.y][p.x] = v;
    }

    private boolean isValidCoordinate(Coordinate p) {
        return p.x >= 0 && p.x < n && p.y >= 0 && p.y < m;
    }
}
