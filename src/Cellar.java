import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Cellar {

    private final Path path_cellar_dir = Paths.get("./cellar");
    private final Path path_cellar_csv = Paths.get("./cellar/mycellar.csv");

    public Cellar(){

        if(!path_cellar_csv.toFile().exists()){
            try {
                Files.createDirectories(path_cellar_dir);
                path_cellar_csv.toFile().createNewFile();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
