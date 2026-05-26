import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class saveManager {

   
    public static void save(File file, List<String> lines) throws IOException {

        if (file == null || lines == null) {
            throw new IllegalArgumentException("File and lines cannot be null");
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
        }
    }
}