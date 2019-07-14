import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Cellar {

    private final Path path_cellar_dir = Paths.get("./cellar");
    private final Path path_cellar_db = Paths.get("./cellar/mycellar");
    private final String first_line = "Year | Color | Name | Appelation | Price | Quantity | Comment";

    private FileWriter cellarWriter;
    private BufferedWriter cellerBuffWriter;

    private FileReader cellarReader;
    private BufferedReader cellarBuffReader;

    public Cellar(){

        if(!path_cellar_db.toFile().exists()) {
            try {
                System.out.println("No cellar detected initialisation");
                Files.createDirectories(path_cellar_dir);
                path_cellar_db.toFile().createNewFile();

                cellarReader = new FileReader(path_cellar_db.toString());
                cellarBuffReader = new BufferedReader(cellarReader);

                cellarWriter = new FileWriter(path_cellar_db.toString());
                cellerBuffWriter = new BufferedWriter(cellarWriter);

                cellerBuffWriter.write(first_line);
                cellerBuffWriter.newLine();
                cellerBuffWriter.flush();

            } catch (Exception e) {
                System.out.println(e);
            }
        }else{
            System.out.println("Cellar detected opening");
            try {
                cellarReader = new FileReader(path_cellar_db.toString());
                cellarBuffReader = new BufferedReader(cellarReader);

                cellarWriter = new FileWriter(path_cellar_db.toString());
                cellerBuffWriter = new BufferedWriter(cellarWriter);
            }catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
