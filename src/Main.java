import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Recorder recorder = new Recorder();
        Thread recorderThread = new Thread(recorder);
        recorderThread.start();
    }
}
