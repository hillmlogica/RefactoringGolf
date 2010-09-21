public class GridAgain {
    private int[][] cells;

    public GridAgain(int[][] cells) {
        this.cells = cells;
    }

    boolean solve(int i, int j) {
        if (i == 9) {
            i = 0;
            if (++j == 9)
                return true;
        }
        if (cells[i][j] != 0) // skip filled cells
            return solve(i + 1, j);

        for (int val = 1; val <= 9; ++val) {
            if (legal(i, j, val)) {
                cells[i][j] = val;
                if (solve(i + 1, j))
                    return true;
            }
        }
        cells[i][j] = 0; // reset on backtrack
        return false;
    }

    boolean legal(int i, int j, int val) {
        for (int k = 0; k < 9; ++k)
            // row
            if (val == cells[k][j])
                return false;

        for (int k = 0; k < 9; ++k)
            // col
            if (val == cells[i][k])
                return false;

        int boxRowOffset = (i / 3) * 3;
        int boxColOffset = (j / 3) * 3;
        for (int k = 0; k < 3; ++k)
            // box
            for (int m = 0; m < 3; ++m)
                if (val == cells[boxRowOffset + k][boxColOffset + m])
                    return false;

        return true; // no violations, so it's legal
    }

    public String prettyPrint() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 9; ++i) {
            if (i % 3 == 0)
                buffer.append(" -----------------------\n");
            for (int j = 0; j < 9; ++j) {
                if (j % 3 == 0) {
                    buffer.append("| ");
                }
                buffer.append(cells[i][j] == 0 ? " " : Integer.toString(cells[i][j]));
                buffer.append(' ');
            }
            buffer.append("|\n");
        }
        buffer.append(" -----------------------\n");
        return buffer.toString();
    }
}
