import java.io.IOException;
import java.util.Scanner;

public class Menu {

    String fileReader;
    String fileWriter;
    String key;

    public void show() throws InterruptedException, IOException {

        //You should put the following code into a menu or Menu class
        System.out.println(ConsoleColour.WHITE);
        System.out.println("************************************************************");
        System.out.println("*     1  ATU - Dept. Computer Science & Applied Physics     *");
        System.out.println("*                                                          *");
        System.out.println("*                   ADFGVX File Encryption                 *");
        System.out.println("*                                                          *");
        System.out.println("************************************************************");
        //Output a menu of options and solicit text from the user
        System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);

        InputOutputOperations inputOutputOperations = new InputOutputOperations();
        Menu runner = new Menu();
        while (true) {

            System.out.println("(1) Specify Input File Directory");
            System.out.println("(2) Specify Output File Directory");
            System.out.println("(3) Set Key");
            System.out.println("(4) Encrypt");
            System.out.println("(5) Decrypt");
            System.out.println("(6) Options"); //Add as many menu items as you like.
            System.out.println("(7) Quit");
            System.out.print("Select Option [1-4]>");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Input file");
                    String fileName = sc.next();
                    runner.fileReader = inputOutputOperations.specificInputFile(fileName);
                }
                case 2 -> {
                    System.out.println("output file");
                    String fileName = sc.next();
                    runner.fileWriter = inputOutputOperations.specificOutputFile(fileName);
                }
                case 3 -> {
                    runner.key = sc.next();
                }
                case 4 -> {
                    System.out.println("Encryption");
                    inputOutputOperations.fileParse(runner.fileReader, runner.key, runner.fileWriter, "encryption");
                }
                case 5 -> {
                    System.out.println("Decryption");
                    inputOutputOperations.fileParse(runner.fileReader, runner.key, runner.fileWriter, "decryption");
                }
                case 6 -> System.exit(0);
            }

            //You may want to include a progress meter in you assignment!
            System.out.print(ConsoleColour.YELLOW);    //Change the colour of the console text
            int size = 1;                            //The size of the meter. 100 equates to 100%
            for (int i = 0; i < size; i++) {        //The loop equates to a sequence of processing steps
                printProgress(i + 1, size);        //After each (some) steps, update the progress meter
                Thread.sleep(10);                    //Slows things down so the animation is visible
            }

            System.out.println();
        }


    }

    public static void printProgress(int index, int total) {
        if (index > total) return;    //Out of range
        int size = 50;                //Must be less than console width
        char done = '█';            //Change to whatever you like.
        char todo = '░';            //Change to whatever you like.

        //Compute basic metrics for the meter
        int complete = (100 * index) / total;
        int completeLen = size * complete / 100;

        /*
         * A StringBuilder should be used for string concatenation inside a
         * loop. However, as the number of loop iterations is small, using
         * the "+" operator may be more efficient as the instructions can
         * be optimized by the compiler. Either way, the performance overhead
         * will be marginal.
         */
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append((i < completeLen) ? done : todo);
        }

        /*
         * The line feed escape character "\r" returns the cursor to the
         * start of the current line. Calling print(...) overwrites the
         * existing line and creates the illusion of an animation.
         */
        System.out.print("\r" + sb + "] " + complete + "%");

        //Once the meter reaches its max, move to a new line.
        if (done == total) System.out.println("\n");
    }

}


