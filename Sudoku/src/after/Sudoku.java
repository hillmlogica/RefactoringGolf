package after;

/**
 * <p/>
 * The following is an example of a Sudoku problem:
 * <p/>
 * <pre>
 * -----------------------
 * |   8   | 4   2 |   6   |
 * |   3 4 |       | 9 1   |
 * | 9 6   |       |   8 4 |
 *  -----------------------
 * |       | 2 1 6 |       |
 * |       |       |       |
 * |       | 3 5 7 |       |
 *  -----------------------
 * | 8 4   |       |   7 5 |
 * |   2 6 |       | 1 3   |
 * |   9   | 7   1 |   4   |
 *  -----------------------
 * </pre>
 * <p/>
 * The goal is to fill in the missing numbers so that every row, column and box
 * contains each of the numbers <code>1-9</code>. Here is the solution to the
 * problem above:
 * <p/>
 * <pre>
 *  -----------------------
 * | 1 8 7 | 4 9 2 | 5 6 3 |
 * | 5 3 4 | 6 7 8 | 9 1 2 |
 * | 9 6 2 | 1 3 5 | 7 8 4 |
 *  -----------------------
 * | 4 5 8 | 2 1 6 | 3 9 7 |
 * | 2 7 3 | 8 4 9 | 6 5 1 |
 * | 6 1 9 | 3 5 7 | 4 2 8 |
 *  -----------------------
 * | 8 4 1 | 9 6 3 | 2 7 5 |
 * | 7 2 6 | 5 8 4 | 1 3 9 |
 * | 3 9 5 | 7 2 1 | 8 4 6 |
 *  -----------------------
 * </pre>
 * <p/>
 * Note that the first row <code>187492563</code> contains each number exactly
 * once, as does the first column <code>159426873</code>, the upper-left box
 * <code>187534962</code>, and every other row, column and box.
 * <p/>
 * <p/>
 * See <a href="http://en.wikipedia.org/wiki/Sudoku">Wikipedia: Sudoku</a> for more information on Sudoku.
 * <p/>
 * <p/>
 * The algorithm employed is similar to the standard backtracking <a href="http://en.wikipedia.org/wiki/Eight_queens_puzzle">eight queens algorithm</a>.
 *
 * @author <a href="http://www.colloquial.com/carp">Bob Carpenter</a>
 * @version 1.0
 */
public class Sudoku {
    public static int[][] solve(int[][] matrix) {
        Grid grid = new Grid(matrix);
        if (grid.solve(0, 0)) // solves in place
            return matrix;
        return null;
    }

    static int[][] parseProblem(String grid) {
        int[][] problem = new int[9][9];
        for (int i = 0; i < 81; i++) {
            int x = i / 9;
            int y = i % 9;
            problem[x][y] = Integer.parseInt("" + grid.charAt(i));
        }
        return problem;
    }

    public static String prettyPrint(int[][] grid) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 9; ++i) {
            if (i % 3 == 0)
                buffer.append(" -----------------------\n");
            for (int j = 0; j < 9; ++j) {
                if (j % 3 == 0) {
                    buffer.append("| ");
                }
                buffer.append(grid[i][j] == 0 ? " " : Integer.toString(grid[i][j]));
                buffer.append(' ');
            }
            buffer.append("|\n");
        }
        buffer.append(" -----------------------\n");
        return buffer.toString();
    }
}
