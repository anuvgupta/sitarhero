import java.io.*;
import java.net.URL;
import java.nio.file.Paths;

public class Files {

    public static File getFile(String path) {
        try {
            URL resource = Files.class.getResource(path);
            return Paths.get(resource.toURI()).toFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static InputStream getInputStream(String path) {
        try {
            return Files.class.getResourceAsStream(path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;

        // read bytes from the input stream and store them in buffer
        while ((len = in.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }

        return os.toByteArray();
    }

}
