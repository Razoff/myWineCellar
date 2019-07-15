import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Cellar {

    private List<Bottle> bottles;

    private final Path path_cellar_dir = Paths.get("./cellar");
    private final Path path_cellar_db = Paths.get("./cellar/mycellar");
    private final Path path_cellar_db_bak = Paths.get("./cellar/mycellar.bak");

    private FileWriter cellarWriter;
    private BufferedWriter cellarBuffWriter;

    private FileReader cellarReader;
    private BufferedReader cellarBuffReader;

    public Cellar(){

        bottles = new ArrayList<Bottle>();

        if(!path_cellar_db.toFile().exists()) {
            try {
                System.out.println("No cellar detected initialisation");
                Files.createDirectories(path_cellar_dir);
                path_cellar_db.toFile().createNewFile();
            } catch (Exception e) {
                System.out.println("Error while creating cellar");
                System.out.println(e);
            }
        }else{
            try{
                System.out.println("Cellar detected initialisation");
                cellarReader = new FileReader(path_cellar_db.toString());
                cellarBuffReader = new BufferedReader(cellarReader);
                while (cellarBuffReader.ready()) {
                    bottles.add(new Bottle(cellarBuffReader.readLine()));
                }
                cellarBuffReader.close();


            }catch (Exception e){
                System.out.println("Error while loading cellar");
                System.out.println(e);
            }
        }
    }

    public void addBottle(Bottle bottle){
        bottles.add(bottle);
    }

    public void delBottle(Bottle bottle){
        bottles.remove(bottle);
    }

    public void modifyBottle(Bottle curr, Bottle newval){
        bottles.remove(curr);
        bottles.add(newval);
    }

    public String toString(){
        String ret = "";
        for(Bottle bottle : bottles){ ret += bottle.toString() + " \n";}
        return ret;
    }

    public Object[][] arrayification(){
        Object[][] ret = new Object[bottles.size()][Bottle.getHeaders().length];
        for(int i = 0; i < bottles.size(); i++){
            ret[i] = bottles.get(i).arrayification();
        }
        return ret;
    }


    protected Boolean backup(){
        File bak = path_cellar_db_bak.toFile();
        File curr = path_cellar_db.toFile();

        bak.delete(); // Delete old backup
        curr.renameTo(bak); // Create new one

        try {
            curr.createNewFile();
            cellarWriter = new FileWriter(path_cellar_db.toString());
            cellarBuffWriter = new BufferedWriter(cellarWriter);
            cellarBuffWriter.write(toString());
            cellarBuffWriter.close();
            return true;

        }catch (Exception e){
            System.out.println("Error during backup");
            System.out.println(e);
            curr.delete();
            bak.renameTo(curr); // Put old backup in place
            return false;
        }
    }

}
