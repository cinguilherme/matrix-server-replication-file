package gcc.main.logic.utils;

public class Generators {

    public static int[][] zeroMatrix() {
        var matrix = new int[3][3];
        matrix[0][0] = 0;
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[1][0] = 0;
        matrix[1][1] = 0;
        matrix[1][2] = 0;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 0;
        return matrix;
    }

    public static int[][] zeroMatrix(int size) {
        var matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = 0;
            }
        }

        return matrix;
    }

}
