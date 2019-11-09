import java.util.function.Supplier;
import java.lang.StringBuilder;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinTask;
import java.lang.Runtime;

/**
 * Encapsulate a square matrix of double values.
 */
class Matrix {
  /**
   * 2D square array of double values, storing the matrix.
   */
  double[][] m;
  /**
   * The number of columns and rows in the matrix.
   */
  int dimension;

  private static final int THRESHOLD = 2;

  /**
   * Checks if two matrices are equals.
   * @param   m1  First matrices to check
   * @param   m2  Second matrices to check against
   * @return  true if every elements in m1 and m2 are the same; false otherwise.
   */
  public static boolean equals(Matrix m1, Matrix m2) {
    if (m1.dimension != m2.dimension) {
      return false;
    }
    for (int i = 0; i < m1.dimension; i++) {
      for (int j = 0; j < m1.dimension; j++) {
        if (Math.abs(m1.m[i][j] - m2.m[i][j]) > 0.000001) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * A constructor for the matrix.
   * @param  dimension The number of rows.
   */
  Matrix(int dimension) {
    this.dimension = dimension;
    this.m = new double[dimension][dimension];
  }

  /**
   * Generate a matrix of d x d according to the given supplier.
   * @param  dimension The dimension of the matrix
   * @param  supplier The lambda to generate the matrix with.
   * @return The new matrix.
   */
  static Matrix generate(int dimension, Supplier<Double> supplier) {
    Matrix matrix = new Matrix(dimension);
    for (int row = 0; row < dimension; row++) {
      for (int col = 0; col < dimension; col++) {
        matrix.m[row][col] = supplier.get();
      }
    }
    return matrix;
  }

  /**
   * Return a string representation of the matrix, pretty-printed
   * with each row on a single line.
   * @return The string representation of this matrix.
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (int row = 0; row < dimension; row++) {
      for (int col = 0; col < dimension; col++) {
        s.append(String.format("%.4f", m[row][col]) + " ");
      }
      s.append("\n");
    }
    return s.toString();
  }

  /**
   * Multiply matrix m with this matrix, return a new result matrix.
   * @param  m1 The matrix to multiply with.
   * @param  m2 The matrix to multiply with.
   * @param  m1Row The starting row of m1.
   * @param  m1Col The starting col of m1.
   * @param  m2Row The starting row of m2.
   * @param  m2Col The starting col of m2.
   * @param  dimension The dimension of the input (sub)-matrices and the size
   *     of the output matrix.
   * @return The new matrix.
   */
  public static Matrix nonRecursiveMultiply(Matrix m1, Matrix m2,
      int m1Row, int m1Col, int m2Row, int m2Col, int dimension) {
    Matrix result = new Matrix(dimension);
    for (int row = 0; row < dimension; row++) {
      for (int col = 0; col < dimension; col++) {
        double sum = 0;
        // multiply row to col
        for (int i = 0; i < dimension; i++) {
          sum += m1.m[row + m1Row][i + m1Col] * m2.m[i + m2Row][col + m2Col];
        }
        result.m[row][col] = sum;
      }
    }
    return result;
  }
  
  /**
   * Multiple two matrices non-recursively.
   * @param m1 The matrix to multiply with.
   * @param m2 The matrix to multiply with.
   * @return The resulting matrix m1 * m2
   */
  public static Matrix nonRecursiveMultiply(Matrix m1, Matrix m2) {
    return Matrix.nonRecursiveMultiply(m1, m2, 0, 0, 0, 0, m1.dimension);
  }

  /**
   * Multiply matrix m with this matrix, return a new result matrix.
   * @param  m1 The matrix to multiply with.
   * @param  m2 The matrix to multiply with.
   * @param  m1Row The starting row of m1.
   * @param  m1Col The starting col of m1.
   * @param  m2Row The starting row of m2.
   * @param  m2Col The starting col of m2.
   * @param  dimension The dimension of the input (sub)-matrices and the size
   *     of the output matrix.
   * @return The resulting matrix m1 * m2
   */
  public static Matrix recursiveMultiply(Matrix m1, Matrix m2,
      int m1Row, int m1Col, int m2Row, int m2Col, int dimension) {

    // If the matrix is small enough, just multiple non-recursively.
    if (dimension <= THRESHOLD) {
      return Matrix.nonRecursiveMultiply(m1, m2, m1Row, m1Col, m2Row, m2Col, dimension);
    }

    // Else, cut the matrix into four blocks of equal size, recursively
    // multiply then sum the multiplication result.
    int size = dimension / 2;
    Matrix result = new Matrix(dimension);
    Matrix a11b11 = recursiveMultiply(m1, m2, m1Row, m1Col, m2Row,
        m2Col, size);
    Matrix a12b21 = recursiveMultiply(m1, m2, m1Row, m1Col + size,
        m2Row + size, m2Col, size);
    for (int i = 0; i < size; i++) {
      double[] m1m = a11b11.m[i];
      double[] m2m = a12b21.m[i];
      double[] r1m = result.m[i];
      for (int j = 0; j < size; j++) {
        r1m[j] = m1m[j] + m2m[j];
      }
    }

    Matrix a11b12 = recursiveMultiply(m1, m2, m1Row, m1Col, m2Row,
        m2Col + size, size);
    Matrix a12b22 = recursiveMultiply(m1, m2, m1Row, m1Col + size,
        m2Row + size, m2Col + size, size);
    for (int i = 0; i < size; i++) {
      double[] m1m = a11b12.m[i];
      double[] m2m = a12b22.m[i];
      double[] r1m = result.m[i];
      for (int j = 0; j < size; j++) {
        r1m[j + size] = m1m[j] + m2m[j];
      }
    }

    Matrix a21b11 = recursiveMultiply(m1, m2, m1Row + size, m1Col,
        m2Row, m2Col, size);
    Matrix a22b21 = recursiveMultiply(m1, m2, m1Row + size, m1Col + size,
        m2Row + size, m2Col, size);
    for (int i = 0; i < size; i++) {
      double[] m1m = a21b11.m[i];
      double[] m2m = a22b21.m[i];
      double[] r1m = result.m[i + size];
      for (int j = 0; j < size; j++) {
        r1m[j] = m1m[j] + m2m[j];
      }
    }

    Matrix a21b12 = recursiveMultiply(m1, m2, m1Row + size, m1Col,
        m2Row, m2Col + size, size);
    Matrix a22b22 = recursiveMultiply(m1, m2, m1Row + size, m1Col + size,
        m2Row + size, m2Col + size, size);
    for (int i = 0; i < size; i++) {
      double[] m1m = a21b12.m[i];
      double[] m2m = a22b22.m[i];
      double[] r1m = result.m[i + size];
      for (int j = 0; j < size; j++) {
        r1m[j + size] = m1m[j] + m2m[j];
      }
    }
    return result;
  }

  /**
   * Multiple two matrices recursively but sequentially with
   * divide-and-conquer algorithm.
   * @param m1 The matrix to multiply with.
   * @param m2 The matrix to multiply with.
   * @return The resulting matrix m1 * m2
   */
  public static Matrix recursiveMultiply(Matrix m1, Matrix m2) {
    return Matrix.recursiveMultiply(m1, m2, 0, 0, 0, 0, m1.dimension);
  }

  /**
   * Multiple two matrices recursively and parallely with
   * divide-and-conquer algorithm.
   * @param m1 The matrix to multiply with.
   * @param m2 The matrix to multiply with.
   * @return The resulting matrix m1 * m2
   */
  public static Matrix parallelMultiply(Matrix m1, Matrix m2) {
    return new MatrixMultiplication(m1, m2, 0, 0, 0, 0, m1.dimension)
        .compute();
  }
}
