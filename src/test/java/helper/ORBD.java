package helper;

import java.io.IOException;
import java.util.stream.Collectors;
import java.lang.Process;
import java.lang.ProcessBuilder;

public class ORBD {
    private Process orbd;

    public ORBD(String orbInitialHost, String orbInitialPort) throws IOException {
        this.orbd = new ProcessBuilder(
            "orbd",
            "-ORBInitialHost", orbInitialHost,
            "-ORBInitialPort", orbInitialPort
            ).start();
    }

    public void stop() {
        this.orbd.destroy();
    }
}
