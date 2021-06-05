package matrixprocessor;

public class Client {

    public void menuLoop(){

        int action;

        do {
            printMenu();
            action = getAction();

            switch (action) {
                case 1:
                    new Sum().execute();
                    break;
                case 2:
                    new MultiplyByConstant().execute();
                    break;
                case 3:
                    new Multiply().execute();
                    break;

                case 4:
                    new Transposition().execute();
                    break;

                case 5:
                    new Determinant().execute();
                    break;

                case 6:
                    new Inversion().execute();
                    break;
            }
        } while (action != 0);
    }

    public void printMenu() {

        System.out.print("1. Add matrices\n" +
                "2. Multiply matrix by a constant\n" +
                "3. Multiply matrices\n" +
                "4. Transpose matrix\n" +
                "5. Calculate a determinant\n" +
                "6. Inverse matrix\n" +
                "0. Exit\n");

        System.out.print("Your choice: ");
    }

    public int getAction() {

        int action = (int) Matrix.getInput();

        if (action > 6 || action < 0) {
            System.out.println("Please choose an option from the menu.\n");
        }

        return action;
    }
}
