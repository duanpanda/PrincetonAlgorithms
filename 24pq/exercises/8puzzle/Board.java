import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Board {
    private int n;          // dimension
    private int[][] blocks;

    public Board() {}
    
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.n = blocks.length;
        this.blocks = blocks;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int s = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (!(i == n-1 && j == n-1) && (blocks[i][j] != i*n + j + 1))
                    s++;
        return s;
    }

    // Manhattan distance of two "points", each board position is a point
    private int manhattanDistance(int i1, int j1, int i2, int j2) {
        return Math.abs(i2 - i1) + Math.abs(j2 - j1);
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int s = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int v = blocks[i][j];    // current block value
                if (v != 0) {
                    int gi = (v - 1) / n; // goal position index i
                    int gj = v - 1 - gi * n; // goal position index j

                    // Add Manhattan distance between the current block position
                    // and its goal position.
                    s += manhattanDistance(i, j, gi, gj);
                }                
            }
        }
        return s;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (i == n-1 && j == n-1) {
                    if (blocks[i][j] != 0)
                        return false;
                }
                else {
                    if (blocks[i][j] != i*n + j + 1)
                        return false;
                }
        return true;
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] nb = blocks.clone();
        for (int i = 0; i < nb.length; i++) {
            nb[i] = blocks[i].clone();
        }

        boolean firstSelected = false;
        boolean secondSelected = false;
        int i1 = 0, j1 = 0, i2 = 0, j2 = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (firstSelected) {
                    // then select the second
                    if (secondSelected) {
                        break;
                    }
                    else {
                        if (nb[i][j] != 0) {
                            i2 = i;
                            j2 = j;
                            secondSelected = true;
                        }
                    }
                }
                else {          // select the first
                    if (nb[i][j] != 0) {
                        i1 = i;
                        j1 = j;
                        firstSelected = true;
                    }
                    else
                        ;       // continue checking next position
                }
            }
        }
        assert firstSelected && secondSelected;

        int t = nb[i2][j2];
        nb[i2][j2] = nb[i1][j1];
        nb[i1][j1] = t;

        return new Board(nb);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.n != that.n)
            return false;
        if (!java.util.Arrays.deepEquals(this.blocks, that.blocks))
            return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new NeighborIterable();
    }

    private class NeighborIterable implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return new NeighborIterator();
        }
    }

    private class NeighborIterator implements Iterator<Board> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Board next() {
            return new Board();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d", blocks[i][j]));
                if (j == n-1)
                    s.append("\n");
                else
                    s.append(" ");
            }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();

        Board initial = new Board(blocks);
        StdOut.println(initial.dimension());
        StdOut.println(initial);
        StdOut.println(initial.isGoal());
        StdOut.println("hamming: " + initial.hamming());
        StdOut.println("manhattan: " + initial.manhattan());
        StdOut.println("twin:");
        Board twin = initial.twin();
        StdOut.println(twin);
        Board b2 = new Board(blocks);
        StdOut.println(b2.equals(twin));
        StdOut.println(b2.equals(initial));
    }
}
