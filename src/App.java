import java.util.ArrayList;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {

        final ArrayList<String> IMAGES_EXTENSIONS = new ArrayList<String>(Arrays.asList("webm", "png", "jpeg", "jpg"));

        Server server = new Server(7070);
        DataOperator.projectPath+="resources/";

        // Main page
        server.addRequestHandler("", false, (String res, String ip, String req) -> {
            server.sendResponse(DataOperator.buildPage("pages/main"));

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
                        server.sendResponseAsync(contentType+"/"+ext, filepath);
                    }
                } else {
                    server.send404Response(res, ip, request);
                }

            return true;
        });

        server.start();
    }
}
