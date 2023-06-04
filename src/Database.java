import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Database {
    
    public static final ArrayList<String[]> getArticles() throws IOException {
        try (FileInputStream file = new FileInputStream(new File("data.txt"))) {
            String[] cnt = new String(file.readAllBytes()).split("///");
            ArrayList<String[]> res = new ArrayList<String[]>();
            Arrays.asList(cnt).stream().map( el -> res.add(el.split("<>")) );
            return res;
        }
    }

    public static final void addArticle(String title, String content) throws IOException {
        try (FileOutputStream file = new FileOutputStream(new File("data.txt"), true)) {
            
        }
    }

}
