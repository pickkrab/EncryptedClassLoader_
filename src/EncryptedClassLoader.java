import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by alexander on 12.08.16.
 */
public class EncryptedClassLoader extends ClassLoader {

    private final String key;
    private final File dir;

    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {

        super(parent);
        this.key = key;
        this.dir = dir;

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Path file = Paths.get(dir.getPath() + "/" + name.replace('.', '/').concat(".class"));
        byte[] shifr_byte = new byte[0];
        try {
            shifr_byte = Files.readAllBytes(file);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        int value = key.hashCode();
        for (int i = 0; i < shifr_byte.length; i++) {
            shifr_byte[i] = (byte) (shifr_byte[i] + value);
        }
        return defineClass(name, shifr_byte, 0, shifr_byte.length);
    }
}
