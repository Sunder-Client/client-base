package wtf.base.client.util.io;

import wtf.base.client.Client;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A file system utility
 * Should work on all operating systems as far as I'm aware
 */
public class FileUtil {
    public static final Path ROOT;
    public static final Path CONFIGS;
    public static final Path SCRIPTS;

    public static void createFile(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void createDirectory(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static File[] getFilesFromDirectory(Path path) {
        if (Files.isDirectory(path)) {
            return path.toFile().listFiles();
        }

        return null;
    }

    public static String readFile(Path path) {
        if (Files.isRegularFile(path) && Files.isReadable(path)) {

            StringBuilder content = new StringBuilder();
            try (InputStream stream = Files.newInputStream(path.toFile().toPath())) {
                int i;
                while ((i = stream.read()) != -1) {
                    content.append((char) i);
                }

                stream.close();

                return content.toString();
            } catch (IOException ignored) {

            }
        }

        return null;
    }

    static {
        Path d = null;

        String userDir = System.getProperty("user.home");
        if (userDir == null) {
            // .minecraft folder
            d = Paths.get("");
        } else {
            // user directory
            d = new File(userDir).toPath();
        }

        if (d == null) {
            throw new IllegalStateException("Could not resolve directory!");
        }

        // set the user directory
        ROOT = d.resolve(Client.NAME);
        CONFIGS = ROOT.resolve("configs");
        SCRIPTS = ROOT.resolve("scripts");

        createDirectory(ROOT);
        createDirectory(CONFIGS);
        createDirectory(SCRIPTS);
    }
}
