import java.util.Scanner;

public class Main {
    public static int[][][] readCube(Scanner sc) {
        int[][][] grid = new int[6][3][3];
        for (int k = 0; k < 6; k++) {
            int[][] face = new int[3][3];

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    face[i][j] = sc.nextInt();
                }
            }

            grid[k] = face;
        }

        return grid;
    }

    public static Rubik executeTurns(Rubik rubik, Scanner sc) {
        while (sc.hasNext()) {
            String command = sc.next();
            Rubik side = rubik;
            switch (command.substring(0, 1)) {
                case "F":
                    break;
                case "R":
                    side = new RubikRight(rubik);
                    break;
                case "U":
                    side = new RubikUp(rubik);
                    break;
                case "L":
                    side = new RubikLeft(rubik);
                    break;
                case "B":
                    side = new RubikBack(rubik);
                    break;
                case "D":
                    side = new RubikDown(rubik);
                    break;
            }

            if (command.contains("'")) {
                // Anti clockcwise
                rubik = side.left();
            } else if (command.contains("2")) {
                // Half
                rubik = side.half();
            } else {
                // Clockwise
                rubik = side.right();
            }
        }

        return rubik;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[][][] grid = readCube(sc);
        Rubik rubik = new Rubik(grid);
    
        Rubik endState = executeTurns(rubik, sc);

        System.out.println(endState);
    }
}
