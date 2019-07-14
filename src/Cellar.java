import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Cellar {

    private final Path path_cellar_dir = Paths.get("./cellar");
    private final Path path_cellar_db = Paths.get("./cellar/mycellar");
    private final String first_line = "Year | Color | Name | Appelation | Price | Quantity | Comment";

    private FileWriter cellarWriter;
    private BufferedWriter cellarBuffWriter;

    private FileReader cellarReader;
    private BufferedReader cellarBuffReader;

    public Cellar(){

        if(!path_cellar_db.toFile().exists()) {
            try {
                System.out.println("No cellar detected initialisation");
                Files.createDirectories(path_cellar_dir);
                path_cellar_db.toFile().createNewFile();

                cellarWriter = new FileWriter(path_cellar_db.toString());
                cellarBuffWriter = new BufferedWriter(cellarWriter);
                cellarBuffWriter.write(first_line);
                cellarBuffWriter.newLine();
                cellarBuffWriter.close();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void addBottle(Bottle bottle){
        addLine(bottle.dataification());
    }

    public String getHeader(){
        return first_line;
    }

    public List<String> bottles(){
        List<String> ret = new ArrayList<String>();

        try {
            cellarReader = new FileReader(path_cellar_db.toString());
            cellarBuffReader = new BufferedReader(cellarReader);
            while (cellarBuffReader.ready()) {
                ret.add(cellarBuffReader.readLine());
            }
            cellarBuffReader.close();

            ret.remove(0);

            return ret;

        }catch(Exception e){
            System.out.println(e);
        }

        return null;

    }

    private Boolean addLine(String line){
        try {
            cellarWriter = new FileWriter(path_cellar_db.toString(), true);
            cellarBuffWriter = new BufferedWriter(cellarWriter);
            cellarBuffWriter.write(line);
            cellarBuffWriter.newLine();
            cellarBuffWriter.close();
            return true;
        }catch (IOException e){
            System.out.println(e);
            return false;
        }
    }

    private Boolean dlLine(){
        return false;
    }

}
