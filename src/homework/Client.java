package homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Norman
 */
public class Client {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 6060);
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

        JSONObject jSONObject = new JSONObject();
        
        jSONObject.put("json_object", "{'name':'John'}");
        jSONObject.put("json_string", "John");
        jSONObject.put("json_number", 2);
        jSONObject.put("json_array", "['John','Anna','Peter']");
        jSONObject.put("json_bool", "true");
        jSONObject.put("json_string", "Anna");
        jSONObject.put("json_number", 1);
        jSONObject.put("json_null", "null");
        jSONObject.put("json_number", 3);

        writer.write(jSONObject.toString() + "\n");
        writer.flush();

        String line = reader.readLine();
        jSONObject = new JSONObject(line);
        
        System.out.println("{");
        System.out.println("'response': {");
        
        for (Map.Entry<String, Object> entry : jSONObject.toMap().entrySet()) {
        
            System.out.println("\t\t'" + entry.getKey() + "': " + entry.getValue() + ",");
        }

        System.out.println("\t\t}");
        System.out.println("}");

        socket.close();
    }
}
