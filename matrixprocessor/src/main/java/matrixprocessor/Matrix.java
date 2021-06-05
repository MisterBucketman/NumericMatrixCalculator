package matrixprocessor;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Scanner;

public class Matrix {

    private static final Scanner scan = new Scanner(System.in);
    protected double[][] values;
    protected int rows;
    protected int columns;

    public Matrix() {

        int m = (int) getInput();
        int n = (int) getInput();
        this.rows = m;
        this.columns = n;
        this.values = new double[rows][columns];
    }
    public Matrix(int rows, int columns) {

        this.rows = rows;
        this.columns = columns;
        this.values = new double[rows][columns];
    }

    public void fill() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.values[i][j] = getInput();
            }
        }
    }

    public void print() {

        System.out.println("The result is:");
        DecimalFormat df = new DecimalFormat("#.###");
        if (this.values != null) {
            for (double[] row : this.values) {
                for (double element : row) {
                    if (element % 1 != 0) {
                        System.out.printf(Locale.ENGLISH,"%.2f ", Math.floor(element * 100) / 100);
                    } else {
                        System.out.print((int) element + " ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static double getInput() {

        boolean inputReceived = false;
        double input = 0.0;

        while (!inputReceived) {
            try {
                if (scan.hasNextDouble()) {
                    input = scan.nextDouble();
                    inputReceived = true;
                } else {
                    System.out.print("Please enter numbers. Try again: ");
                    scan.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return input;
    }
}
