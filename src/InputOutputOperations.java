import java.io.*;

public class InputOutputOperations {

    String outputFileName;

    public String specificInputFile(String fileName) {

        fileName = FileConstant.FILE_PATH.concat(fileName).concat(FileConstant.FILE_EXTENSION_TXT);
        return fileName;
    }

    public String specificOutputFile(String fileName) {

        this.outputFileName = FileConstant.FILE_PATH.concat(fileName).concat(FileConstant.FILE_EXTENSION_TXT);
        return this.outputFileName;
    }

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
            if (process.equals(FileConstant.ENCRYPTION)) {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String finalString = stringBuilder.toString();
                finalString = finalString.trim().replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
                String encryption = cypher.encryption(finalString);
                inputOutputOperations.processOutput(encryption, writer);
            } else if (process.equals(FileConstant.DECRYPTION)) {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String finalString = stringBuilder.toString();
                String decryption = cypher.decryption(finalString);
                inputOutputOperations.processOutput(decryption, writer);

            } else {
                System.out.println("Bad Process");
                throw new RuntimeException("Bad process");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            reader.close();
            writer.close();
        }

    }

    private void processOutput(String encryptedText, BufferedWriter writer) {
        try {
            writer.write(encryptedText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
