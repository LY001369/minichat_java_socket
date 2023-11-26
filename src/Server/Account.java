package Server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Account {
    public static void main(String[] args) {
        loadJson();
    }

    public static Account login(String username, String password){
        
        
        
        Account acc = new Account();
        return acc;
    }

    private static JSONObject loadJson(){
        String fileName = ".\\resource\\json\\account.json";

        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(fileName)) {

            Object obj = parser.parse(reader);

            JSONObject jsonObject = (JSONObject) obj;

            System.out.println(obj); 

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return ;
    }
}


public class JSONFileLoader {
    public static void main(String[] args) {
        
}
