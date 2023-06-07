import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Database {
    
    public static final ArrayList<String[]> getArticles() {
        try {
            try (FileInputStream file = new FileInputStream(DataOperator.projectPath+"data.txt")) {
                String[] cnt = new String(file.readAllBytes()).split("///");
                ArrayList<String[]> res = new ArrayList<String[]>();
                for (String el : cnt) {
                    res.add(el.split("<>"));
                }
                // res.remove(res.size()-1);
                return res;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final void addArticle(String title, String content) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        try {
            try (FileOutputStream file = new FileOutputStream(DataOperator.projectPath+"data.txt", true)) {
                file.write((title+"<>"+content+"<>"+dtf.format(LocalDateTime.now())+"///").getBytes());
            }
        } catch (IOException e) {e.printStackTrace();}
    }

}
