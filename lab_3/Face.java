public class Face implements Cloneable {
    private int[][] grid;

    public Face(int[][] grid) {
        this.grid = grid;
    }

    public Face right() {
        int[][] rightGrid = new int[3][3];
        
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j++) {
                rightGrid[i][j] = this.grid[2 - j][i];
            }
        }

        return new Face(rightGrid);
    }

    public Face left() {
        int[][] leftGrid = new int[3][3];

        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j++) {
                leftGrid[i][j] = this.grid[j][2 - i];
            }
        }

        return new Face(leftGrid);
    }

    public Face half() {
        return this.right().right();
    }

    public int[][] toIntArray() {
        return this.grid;
    }
    
    @Override
    public Face clone() {
        int[][] cloneGrid = new int[3][3];

        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j++) {
                cloneGrid[i][j] = this.grid[i][j];
            }
        }

        return new Face(cloneGrid);
    }

    @Override
    public String toString() {
        String output = "";
        
        for (int i = 0; i < this.grid.length; i++) {
            output += "\n";
            for (int j = 0; j < this.grid[i].length; j++) {
                output += String.format("%02d", this.grid[i][j]);
            }
        }

        return output + "\n";
    }
}
