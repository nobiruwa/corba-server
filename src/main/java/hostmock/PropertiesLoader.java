package hostmock;

import java.lang.ClassLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    public Properties load(String path) {
        Properties properties = new Properties();
        // jarファイルのルートからプロパティファイルを取得する
        // すなわち、このクラスをロードしたクラスローダーからリソースを取得する
        try (InputStream baseProperties = this.getClass().getClassLoader().getResourceAsStream(path)) {
            properties.load(baseProperties);
        } catch (IOException e) {
        }
        // カレントディレクトリからプロパティファイルを取得する
        if ((new File(path)).exists()) {
            try (InputStream overwriteProperties = new FileInputStream(path)) {
                properties.load(overwriteProperties);
            } catch (IOException e) {
            }
        }
        return properties;
    }
}
