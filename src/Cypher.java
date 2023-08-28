public class Cypher {
    private char[][] grid = {
            {' ', 'A', 'D', 'F', 'G', 'V', 'X'},
            {'A', 'P', 'H', '0', 'Q', 'G', '6'},
            {'D', '4', 'M', 'E', 'A', '1', 'Y'},
            {'F', 'L', '2', 'N', 'O', 'F', 'D'},
            {'G', 'X', 'K', 'R', '3', 'C', 'V'},
            {'V', 'S', '5', 'Z', 'W', '7', 'B'},
            {'X', 'J', '9', 'U', 'T', 'I', '8'}
    };

    private String key;

    public Cypher(String key) {
        this.key = key;
    }

    private String charToSymbol(char c) {
        c = Character.toUpperCase(c);
        for (int row = 1; row < 7; row++) {
            for (int col = 1; col < 7; col++) {
                if (grid[row][col] == c) {
                    return (String.valueOf(grid[row][0]).concat(String.valueOf(grid[0][col])));
                }
            }
        }
        return "";
    }

    public String encode(String plaintext) {
        StringBuilder encodedText = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                encodedText.append(charToSymbol(c));
            } else {
                encodedText.append(c);
            }
        }
        return encodedText.toString();
    }

    public String decode(String ciphertext) {
        StringBuilder decodedText = new StringBuilder();
        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                decodedText.append(charToSymbol(c));
            } else {
                decodedText.append(c);
            }
        }
        return decodedText.toString();
    }

    public String[][] createMatrix(String plaintext) {
        plaintext = this.key.concat(plaintext);
        int numRows = (int) Math.ceil((double) plaintext.length() / key.length());
        String[][] matrix = new String[numRows][key.length()];

        int index = 0;
        for (int col = 0; col < numRows; col++) {
            for (int row = 0; row < key.length(); row++) {
                if (index < plaintext.length()) {
                    char c = plaintext.charAt(index++);
                    matrix[col][row] = String.valueOf(c);
                }
            }
        }

        return matrix;
    }

    public String[][] sortTransposeMatrix(String[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Sort the first row and keep track of the indices
        String[] firstRow = new String[cols];
        int[] indices = new int[cols];
        for (int i = 0; i < cols; i++) {
            firstRow[i] = matrix[0][i];
            indices[i] = i;
        }
        for (int i = 0; i < cols - 1; i++) {
            for (int j = 0; j < cols - i - 1; j++) {
                if (firstRow[j].compareTo(firstRow[j + 1]) > 0) {
                    String temp = firstRow[j];
                    firstRow[j] = firstRow[j + 1];
                    firstRow[j + 1] = temp;

                    int tempIndex = indices[j];
                    indices[j] = indices[j + 1];
                    indices[j + 1] = tempIndex;
                }
            }
        }

        // Transpose the matrix using the sorted indices
        String[][] transposedMatrix = new String[rows][cols];
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                transposedMatrix[row][col] = matrix[row][indices[col]];
            }
        }

        return transposedMatrix;
    }

    public static String cipherCode(String arr[][]) {
        String text = "";
        for (int col = 0; col < arr[0].length; col++) {
            for (int row = 1; row < arr.length; row++) {
                text = text.concat(String.valueOf(arr[row][col]));
            }
        }

        return text;
    }


    public String encryption(String plaintext) {
        if (plaintext.isEmpty() || plaintext == null) {
            System.out.println("String is Empty/Null");
            return null;
        }
        Cypher cps = new Cypher(key);
        String encoded = cps.encode(plaintext);

        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encoded: " + encoded);

        String[][] matrix = cps.createMatrix(encoded);
        System.out.println("Matrix with Enciphered Codes:");


        for (String[] row : matrix) {
            for (String symbol : row) {
                System.out.print(symbol + " ");
            }
            System.out.println();
        }
        String[][] strings = cps.sortTransposeMatrix(matrix);


        System.out.println("                    ");

        for (String[] row : strings) {
            for (String symbol : row) {
                System.out.print(symbol + " ");
            }
            System.out.println();
        }

        System.out.println("                    ");


        return cipherCode(strings);

    }

    public String decryption(String input) {
        Cypher cps = new Cypher(key);
        char[] charArray = input.toCharArray();

        int noClipChars = ((input.length() * 2) % key.length() / 2);
        input = input.substring(0, input.length() - noClipChars);

        int numRows = input.length() / key.length();
        int numCols = this.key.length();
        char[][] matrix = new char[numRows][numCols];

        int index = 0; // Start from 1 to skip the '{' character
        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                matrix[row][col] = charArray[index++];
            }
        }

        char arr[] = newMatrix(this.key, matrix);
        String decryptedString = cps.decryptUsingPolybius(new String(arr));
        return decryptedString;

    }

    public char[] newMatrix(String key, char[][] matrix) {

        String sortKey = sort(key);

        int numRows = matrix.length;
        int numCols = matrix[0].length;

        char[][] newMatrix = new char[numRows + 1][numCols];

        // Shift rows down
        for (int row = numRows; row > 0; row--) {
            for (int col = 0; col < numCols; col++) {
                newMatrix[row][col] = matrix[row - 1][col];
            }
        }

        for (int i = 0; i < sortKey.length(); i++) {
            newMatrix[0][i] = sortKey.charAt(i);
        }

        // Print the new matrix
        for (int row = 0; row < numRows + 1; row++) {
            for (int col = 0; col < numCols; col++) {
                System.out.print(newMatrix[row][col] + " ");
            }
            System.out.println();
        }

        return convertMatrix(newMatrix, key);

    }

    public static String sort(String str) {
        char[] arr = str.toCharArray();
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] and arr[j+1]
                    char temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        return new String(arr);
    }


    public char[] convertMatrix(char[][] matrix, String key) {


        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }

        char[] sortOrder = key.toCharArray();

        // Rearrange the matrix based on the pattern
        int numRows = matrix.length;
        int numCols = matrix[0].length;

        for (int col = 0; col < numCols; col++) {
            int originalIndex = col;

            // Find the corresponding index in the sortOrder
            for (int i = 0; i < sortOrder.length; i++) {
                if (sortOrder[i] == matrix[0][col]) {
                    originalIndex = i;
                    break;
                }
            }

            // Swap the entire column with the column at the new index
            for (int row = 0; row < numRows; row++) {
                char temp = matrix[row][col];
                matrix[row][col] = matrix[row][originalIndex];
                matrix[row][originalIndex] = temp;
            }
        }

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }

        char[] chars = readMatrixHorizontally(matrix);

        for (char c : chars) {
            System.out.print(c);
        }

        return chars;

    }

    public static char[] readMatrixHorizontally(char[][] matrix) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;

        char[] rowData = new char[(numRows - 1) * numCols]; // Total characters in rows 2 to last

        int index = 0;
        for (int row = 1; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                rowData[index++] = matrix[row][col];
            }
        }
        String str = new String(rowData);

        return rowData;
    }

    public String decryptUsingPolybius(String ciphertext) {
//        char[][] polybiusSquare = createPolybiusSquare();
        StringBuilder decryptedMessage = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 2) {
            String pair = ciphertext.substring(i, i + 2);
            String decryptedChar = findCharFromPair(grid, pair);
            decryptedMessage.append(decryptedChar);
        }

        return decryptedMessage.toString();
    }

    public static String findCharFromPair(char[][] polybiusSquare, String pair) {

        int x = findX(polybiusSquare[0], pair.charAt(0));
        int y = findY(polybiusSquare[0], pair.charAt(1));

        return String.valueOf(polybiusSquare[x][y]);
    }

    public static int findX(char[] row, char x) {

        for (int i = 0; i < row.length; i++) {
            if (row[i] == x) {
                return i;
            }
        }

        return 0;


    }

    public static int findY(char[] row, char x) {

        for (int i = 0; i < row.length; i++) {
            if (row[i] == x) {
                return i;
            }
        }

        return 0;

    }

}
