import java.io.*;

public class InputOutputOperations {

    String outputFileName;

    //Creating input file
    public String specificInputFile(String fileName) {

        fileName = FileConstant.FILE_PATH.concat(fileName).concat(FileConstant.FILE_EXTENSION_TXT);
        return fileName;
    }

    //Creating output file
    public String specificOutputFile(String fileName) {

        this.outputFileName = FileConstant.FILE_PATH.concat(fileName).concat(FileConstant.FILE_EXTENSION_TXT);
        return this.outputFileName;
    }

    //Parsing a input file
    public void fileParse(String readFileName, String key, String writeFileName, String process) throws IOException {

        File file = new File(readFileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(writeFileName, true)); // true for append mode
        try {
            // Open the file in read-write mode
            // Read and print file contents
            String line;
            InputOutputOperations inputOutputOperations = new InputOutputOperations();
            Cypher cypher = new Cypher(key);
            StringBuilder stringBuilder = new StringBuilder();
            //For encryption process
            if (process.equals(FileConstant.ENCRYPTION)) {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String finalString = stringBuilder.toString();
                //Remove all the character except A-Z , a-z, 0-9
                finalString = finalString.trim().replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
                //Calling encryption method and get  encrypted String
                String encryption = cypher.encryption(finalString);
                //Write into a file
                inputOutputOperations.processOutput(encryption, writer);
                //For decryption process
            } else if (process.equals(FileConstant.DECRYPTION)) {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String finalString = stringBuilder.toString();
                //Decryption is calling and get decryption string
                String decryption = cypher.decryption(finalString);
                //write decryption string into file
                inputOutputOperations.processOutput(decryption, writer);

            } else {
                System.out.println("Bad Process");
                throw new RuntimeException("Bad process");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Closing Reader resource
            reader.close();
            //Closing Writer resource
            writer.close();
        }

    }

    private void processOutput(String encryptedText, BufferedWriter writer) {
        try {
            //Writing into output file
            writer.write(encryptedText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
