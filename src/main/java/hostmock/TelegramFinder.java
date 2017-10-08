package hostmock;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TelegramFinder {
    private String dir;
    public TelegramFinder(String dir) {
        this.dir = dir;
    }
    private Path makePath(String exName) {
        String fileName = exName + ".txt";
        return Paths.get(this.dir, fileName);
    }
    public Boolean exists(String exName) {
        return this.makePath(exName).toFile().exists();
    }
    public String read(String exName) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (FileReader fr = new FileReader(this.makePath(exName).toFile())) {
            try (BufferedReader br = new BufferedReader(fr)) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return builder.toString();
    }
}
