import java.util.ArrayList;
import java.util.Arrays;

public class App {
    public static void main(String[] arguments) throws Exception {

        final ArrayList<String> IMAGES_EXTENSIONS = new ArrayList<String>(Arrays.asList("webm", "png", "jpeg", "jpg"));
        final ArrayList<String> loginedClients = new ArrayList<String>();

        Server server = new Server(7070);
        DataOperator.projectPath+="resources/";

        // OVERWATCH
        server.addRequestHandler("OVERWATCH", false, (String res, String ip, String req) -> {
            if (loginedClients.contains(ip) || res.equals("login") || res.startsWith("CMD<>login<>") || res.equals("media/bg2.png")) {
                return true;
            }
            server.sendResponse("<script>window.location.replace('/login')</script>");
            return false;
        });

        // Main page
        server.addRequestHandler("", false, (String res, String ip, String req) -> {
            String page = DataOperator.buildPage("pages/main");
            String articles = "";

            for (String[] s : Database.getArticles()) {
                articles+="<div class='article'><h1>"+s[0]+"</h1><a>"+s[1]+"</a><span class='date'>"+s[2]+"</span></div>";
            }

            page = page.replace("<!-- ARTICLES HERE -->", articles);
            server.sendResponse(page);

            return true;
        });
        
        // Login page
        server.addRequestHandler("login", false, (String res, String ip, String req) -> {
            server.sendResponse(DataOperator.buildPage("pages/login"));
            return true;
        });

        // Media files
        server.addRequestHandler("media/", true, (String res, String ip, String request) -> {
            String filepath = "media/"+res.substring(5);
            String contentType = "text";
            String ext = filepath.substring(filepath.lastIndexOf(".")+1);
            if (ext.equals("mp4")) {contentType="video";}
            else if (IMAGES_EXTENSIONS.contains(ext)) {contentType="image";}
                if (DataOperator.isFileExist(filepath)) {
                    if (DataOperator.getFileSize(filepath)<10500000) {
                        server.sendResponse(contentType+"/"+ext, DataOperator.readFileAsBytes(filepath));
                    } else {
                        server.sendResponse(contentType+"/"+ext, filepath);
                    }
                } else {
                    server.send404Response(res, ip, request);
                }

            return true;
        });

        //##########################################################################################
        //------------------------------------ COMMANDS --------------------------------------------
        //##########################################################################################

        server.addRequestCMDHandler("login", (String ip, String[] args) -> {
            if (args[0]=="Ckyrer" && args[1]=="veni") {
                loginedClients.add(ip);
                server.sendResponse("1");
            } else {
                server.sendResponse("0");
            }
            return true;
        });

        server.addRequestCMDHandler("addArticle", (String ip, String[] args) -> {
            System.out.println(args[0]);
            Database.addArticle(args[0], args[1]);
            server.sendResponse("1");

            return true;
        });

        server.start();
    }
}
