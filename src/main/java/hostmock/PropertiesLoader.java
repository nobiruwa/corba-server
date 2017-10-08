package hostmock;

import java.lang.ClassLoader;

public class PropertiesLoader {
    public String searchClassLoader(String path) {
        return ClassLoader.getSystemResource(path).getFile();
    }
}
