package de.jquast.utils.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class CSVReader {

    private Path filePath;
    private String delimiter;

    public CSVReader(Path file, String delimiter) {
        this.filePath = file;
        this.delimiter = delimiter;
    }

    public Optional<String[][]> read() {
        try {
            List<String> lines = Files.readAllLines(filePath);

            String header = lines.get(0);
            int columns = header.split(delimiter).length;

            String[][] result = new String[lines.size() - 1][columns];
            for (int i = 1; i < lines.size(); ++i) {
                String[] splitted = lines.get(i).split(delimiter);
                result[i - 1] = splitted;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

}
