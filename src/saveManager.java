import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner; 
/*
 * Handles all file writing for player save data.
 * Kept separate from MenuService to give file I/O its own responsibility,
 * following the principle that each class should have a single purpose.
 */
public class SaveManager {

    /*
     * Writes a list of strings to the given file, one per line.
     * Overwrites the file completely — the caller is responsible for
     * including all players that should be retained.
     * Parameters:
     *   file  - the target save file to write to
     *   lines - the list of player data strings to write
     * Throws IllegalArgumentException if either parameter is null.
     */
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
    /*
 * Removes a player's entry from the save file by matching their id.
 * Reads all lines, skips the matching one, and writes the rest back.
 * Parameters:
 *   file     - the target save file
 *   playerId - the id of the player to remove
 */
public static void delete(File file, int playerId) throws IOException {
    List<String> remaining = new ArrayList<>();

    try (Scanner fileScanner = new Scanner(file)) {
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            if (line.isEmpty()) continue;
            if (!line.startsWith(playerId + ",")) {
                remaining.add(line);
            }
        }
    }

    save(file, remaining);
}
}