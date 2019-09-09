public class Rubik implements Cloneable, SideViewable {
    protected int[][][] grid;

    public Rubik(int[][][] grid) {
        this.grid = grid;
    }

    public Rubik(Rubik rubik) {
        this.grid = rubik.clone().grid;
    }

    public Rubik right() {
        Rubik rightTurn = this.clone();

        // Rotate front face
        Face frontFace = new Face(rightTurn.grid[2]).clone();
        rightTurn.grid[2] = frontFace.right().toIntArray();

        // Copy top adjacent tiles
        int[] topBottomRow = rightTurn.grid[0][2].clone();

        // Move left to top
        rightTurn.grid[0][2][0] = rightTurn.grid[1][2][2];
        rightTurn.grid[0][2][1] = rightTurn.grid[1][1][2];
        rightTurn.grid[0][2][2] = rightTurn.grid[1][0][2];

        // Move down to left
        rightTurn.grid[1][0][2] = rightTurn.grid[4][0][0];
        rightTurn.grid[1][1][2] = rightTurn.grid[4][0][1];
        rightTurn.grid[1][2][2] = rightTurn.grid[4][0][2];

        // Move right to down
        rightTurn.grid[4][0][0] = rightTurn.grid[3][2][0];
        rightTurn.grid[4][0][1] = rightTurn.grid[3][1][0];
        rightTurn.grid[4][0][2] = rightTurn.grid[3][0][0];

        // Move top to right
        rightTurn.grid[3][0][0] = topBottomRow[0];
        rightTurn.grid[3][1][0] = topBottomRow[1];
        rightTurn.grid[3][2][0] = topBottomRow[2];
        
        return rightTurn;
    }

    public Rubik left() {
        Rubik leftTurn = this.clone();

        // Rotate front face
        Face frontFace = new Face(leftTurn.grid[2]).clone();
        leftTurn.grid[2] = frontFace.left().toIntArray();

        // Copy top adjacent tiles
        int[] topBottomRow = leftTurn.grid[0][2].clone();

        // Move right to top
        leftTurn.grid[0][2][0] = leftTurn.grid[3][0][0];
        leftTurn.grid[0][2][1] = leftTurn.grid[3][1][0];
        leftTurn.grid[0][2][2] = leftTurn.grid[3][2][0];

        // Move down to right
        leftTurn.grid[3][2][0] = leftTurn.grid[4][0][0];
        leftTurn.grid[3][1][0] = leftTurn.grid[4][0][1];
        leftTurn.grid[3][0][0] = leftTurn.grid[4][0][2];

        // Move left to down
        leftTurn.grid[4][0][0] = leftTurn.grid[1][0][2];
        leftTurn.grid[4][0][1] = leftTurn.grid[1][1][2];
        leftTurn.grid[4][0][2] = leftTurn.grid[1][2][2];

        // Move top to left
        leftTurn.grid[1][2][2] = topBottomRow[0];
        leftTurn.grid[1][1][2] = topBottomRow[1];
        leftTurn.grid[1][0][2] = topBottomRow[2];

        return leftTurn;
    }

    public Rubik half() {
        return this.right().right();
    }

    @Override
    public Rubik rightView() {
        Rubik rvRubik = this.clone();

        // Rotate top and down faces
        rvRubik.grid[0] = new Face(rvRubik.grid[0]).right().toIntArray();
        rvRubik.grid[4] = new Face(rvRubik.grid[4]).left().toIntArray();
        
        // Copy left face
        Face grid1 = new Face(rvRubik.grid[1]).clone();
        
        // Move front face to left face
        rvRubik.grid[1] = rvRubik.grid[2];

        // Move right face to front face
        rvRubik.grid[2] = rvRubik.grid[3];

        // Move back face to right face and rotate
        rvRubik.grid[3] = new Face(rvRubik.grid[5]).half().toIntArray();

        // Move left face to back face and rotate
        rvRubik.grid[5] = grid1.half().toIntArray();

        return rvRubik;
    }

    @Override
    public Rubik leftView() {
        Rubik lvRubik = this.clone();

        // Rotate top and down faces
        lvRubik.grid[0] = new Face(lvRubik.grid[0]).left().toIntArray();
        lvRubik.grid[4] = new Face(lvRubik.grid[4]).right().toIntArray();

        // Copy right face
        Face grid3 = new Face(lvRubik.grid[3]).clone();

        // Move front face to right face
        lvRubik.grid[3] = lvRubik.grid[2];

        // Move left face to front face
        lvRubik.grid[2] = lvRubik.grid[1];

        // Move back face to left face and rotate
        lvRubik.grid[1] = new Face(lvRubik.grid[5]).half().toIntArray();

        // Move right face to back face and rotate
        lvRubik.grid[5] = grid3.half().toIntArray();

        return lvRubik;
    }

    @Override
    public Rubik upView() {
        Rubik uvRubik = this.clone();

        // Rotate left and right faces
        uvRubik.grid[1] = new Face(uvRubik.grid[1]).right().toIntArray();
        uvRubik.grid[3] = new Face(uvRubik.grid[3]).left().toIntArray();

        // Copy back face
        Face grid5 = new Face(uvRubik.grid[5]).clone();

        // Move down face to back face
        uvRubik.grid[5] = uvRubik.grid[4];

        // Move front face to down face
        uvRubik.grid[4] = uvRubik.grid[2];

        // Move top face to front face
        uvRubik.grid[2] = uvRubik.grid[0];

        // Move back face to top face
        uvRubik.grid[0] = grid5.toIntArray();

        return uvRubik;
    }

    @Override
    public Rubik downView() {
        Rubik dvRubik = this.clone();

        // Rotate left and right faces
        dvRubik.grid[1] = new Face(dvRubik.grid[1]).left().toIntArray();
        dvRubik.grid[3] = new Face(dvRubik.grid[3]).right().toIntArray();

        // Copy top face
        Face grid0 = new Face(dvRubik.grid[0]).clone();

        // Move front face to top face
        dvRubik.grid[0] = dvRubik.grid[2];

        // Move down face to front face
        dvRubik.grid[2] = dvRubik.grid[4];

        // Move back face to down face
        dvRubik.grid[4] = dvRubik.grid[5];

        // Move top face to back face
        dvRubik.grid[5] = grid0.toIntArray();

        return dvRubik;
    }

    @Override
    public Rubik backView() {
        return this.rightView().rightView();
    }

    @Override
    public Rubik frontView() {
        return this;
    }

    @Override
    public Rubik clone() {
        int[][][] cloneGrid = new int[6][3][3];
        
        for (int i = 0; i < this.grid.length; i++) {
            cloneGrid[i] = new Face(this.grid[i]).clone().toIntArray();
        }

        return new Rubik(cloneGrid);
    }

    public String singleFace(int[][] faceGrid) {
        String filler = "......";
        String output = "";

        for (int i = 0; i < faceGrid.length; i++) {
            output += filler;
            for (int j = 0; j < faceGrid[i].length; j++) {
                output += String.format("%02d", faceGrid[i][j]);
            }
            output += filler + "\n";
        }

        return output;
    }

    public String getValuesInRow(int[][] faceGrid, int row) {
        String output = "";
        for (int i = 0; i < faceGrid[row].length; i++) {
            output += String.format("%02d", faceGrid[row][i]);
        }
        
        return output;
    }

    public String threeFaces(int[][] faceGridA, int[][] faceGridB, int[][] faceGridC) {
        String output = "";

        for (int i = 0; i < faceGridA.length; i++) {
            output += getValuesInRow(faceGridA, i) + getValuesInRow(faceGridB, i) + getValuesInRow(faceGridC, i);
            output += "\n";
        }

        return output;
    }

    @Override
    public String toString() {
        String filler = "......";
        String output = "\n";
        
        for (int i = 0; i < this.grid.length; i++) {
            if (i < 1 || i > 3) {
                output += singleFace(this.grid[i]);
            } else if (i == 1) {
                output += threeFaces(this.grid[i], this.grid[i+1], this.grid[i+2]);
            }
        }

        return output;
    }
}
