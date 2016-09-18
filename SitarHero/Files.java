import java.io.File;
public class Files {
    public static File getFile(Object object, String path) {
        try {
            return new File(object.getClass().getResource(path).toURI());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
