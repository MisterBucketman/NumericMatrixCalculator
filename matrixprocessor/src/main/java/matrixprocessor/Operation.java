package matrixprocessor;

public abstract class Operation {

    protected Matrix one;
    protected Matrix two;

    public abstract void execute();

    protected void create(String amount) {

        switch (amount) {
            case "one":
                System.out.print("Enter matrix size: ");
                one = new Matrix();

                System.out.println("Enter matrix:");
                one.fill();
                break;
            case "two":
                System.out.print("Enter size of first matrix: ");
                one = new Matrix();
                System.out.println("Enter first matrix:");
                one.fill();

                System.out.print("Enter size of second matrix: ");
                two = new Matrix();

                System.out.println("Enter second matrix:");
                two.fill();
                break;
        }
    }
}

class Sum extends Operation {

    public void execute() {
        create("two");
        sum().print();
    }

    public Matrix sum() {

        Matrix result = new Matrix(one.rows, one.columns);

        if (one.rows != two.rows || one.columns != two.columns) {
            System.out.println("ERROR");
        } else {
            result = new Matrix(one.rows, two.columns);

            for (int i = 0; i < one.rows; i++) {
                for (int j = 0; j < one.columns; j++) {
                    result.values[i][j] = one.values[i][j] + two.values[i][j];
                }
            }
        }
        return result;
    }
}

class MultiplyByConstant extends Operation {

    public void execute() {
        create("one");

        System.out.print("Enter constant: ");
        double constant = Matrix.getInput();

        multiplyBy(constant).print();
    }

    public Matrix multiplyBy(double constant) {

        for (int i = 0; i < one.rows; i++) {
            for (int j = 0; j < one.columns; j++) {
                one.values[i][j] *= constant;
            }
        }
        return one;
    }
}

class Multiply extends Operation {

    public void execute() {
        create("two");
        multiply().print();
    }

    public Matrix multiply() {

        Matrix result = new Matrix(one.rows, two.columns);

        if (one.columns != two.rows) {
            System.out.println("ERROR: Invalid matrix dimensions.");
        } else {
            for (int i = 0; i < one.rows; i++) {
                for (int j = 0; j < two.columns; j++) {
                    for (int k = 0; k < two.rows; k++) {
                        result.values[i][j] += one.values[i][k] * two.values[k][j];
                    }
                }
            }
        }
        return result;
    }
}

class Transposition extends Operation {

    public void execute() {
        int action = chooseAction();
        create("one");
        transpose(action).print();
    }

    public int chooseAction() {

        System.out.println();
        System.out.print("1. Main diagonal\n" +
                         "2. Side diagonal\n" +
                         "3. Vertical line\n" +
                         "4. Horizontal line\n" +
                         "Your choice: ");
        return (int) Matrix.getInput();
    }

    public Matrix transpose(int action) {

        Matrix result = new Matrix(one.rows, one.columns);

        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.columns; j++) {
                switch (action) {
                    case 1:
                        result.values[i][j] = one.values[j][i];
                        break;
                    case 2:
                        result.values[i][j] = one.values[one.rows - 1 - j][one.columns - 1 - i];
                        break;
                    case 3:
                        result.values[i][j] = one.values[i][one.columns - 1 - j];
                        break;
                    case 4:
                        result.values[i][j] = one.values[one.rows - 1 - i][j];
                        break;
                }
            }
        }
        return result;
    }
}

class Determinant extends Operation {

    public void execute() {
        create("one");

        if (one.rows != one.columns) {
            System.out.println("Error. Must be quadratic!");
        } else {
            System.out.println(determinate(one));
        }

    }


    public double determinate(Matrix matrix) {

        double result = 0.0;
        try {
            result = sumSubDeterminants(matrix);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }



    private double sumSubDeterminants(Matrix matrix) {

        double subTotal = 0.0;

        if (matrix.rows != matrix.columns) {
            throw new IllegalArgumentException("Error. Must be quadratic!");
        } else if (matrix.rows == 2) {
            return matrix.values[0][0] * matrix.values[1][1] -
                    matrix.values[1][0] * matrix.values[0][1];
        } else {
            double product;

            for (int i = 0; i < matrix.columns; i++) {
                Matrix temp = createSubMatrix(0, i, matrix);
                if (i % 2 == 1) {
                    product = -1 * matrix.values[0][i] * sumSubDeterminants(temp);
                } else {
                    product = matrix.values[0][i] * sumSubDeterminants(temp);
                }
                subTotal += product;
            }
            return subTotal;
        }
    }

    public Matrix getCofactors(Matrix matrix) {

        Matrix cofactors = new Matrix(matrix.rows, matrix.columns);
        for (int i = 0; i < matrix.rows; i++) {
            for (int j = 0; j < matrix.columns; j++) {
                Matrix sub = createSubMatrix(i, j, matrix);
                cofactors.values[i][j] = Math.pow(-1, (i + j)) * determinate(sub);
            }
        }
        return cofactors;
    }

    private Matrix createSubMatrix(int row, int col, Matrix matrix) {

        Matrix sub = new Matrix(matrix.rows - 1, matrix.columns - 1);
        int i = 0;
        int j = 0;

        for (int x = 0; x < matrix.rows; x++) {
            for (int y = 0; y < matrix.columns; y++) {
                if (x != row && y != col) {
                    sub.values[i][j++] = matrix.values[x][y];
                    if (j == matrix.columns - 1) {
                        i++;
                        j = 0;
                    }
                }
            }
        }
        return sub;
    }
}

class Inversion extends Operation {

    public void execute() {
        create("one");
        invert().print();
    }

    public Matrix invert() {

        Determinant determinant = new Determinant();
        double constant = 1 / determinant.determinate(this.one);

        // get cofactor matrix:
        Matrix cofactors = determinant.getCofactors(this.one);

        // transpose and overwrite cofactor matrix
        Transposition transposition = new Transposition();
        transposition.one = cofactors;
        cofactors = transposition.transpose(1);

        //mbc matrix = matrix of cofactors, multiplied by constant 1 / det(this.one);
        MultiplyByConstant mbc = new MultiplyByConstant();
        mbc.one = cofactors;
        return mbc.multiplyBy(constant);
    }
}
