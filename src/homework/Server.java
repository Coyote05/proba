package homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Norman
 */
public class Server {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(6060);

        try {
            while (true) {

                Socket socket = serverSocket.accept();
                start(socket);
            }
        } finally {
            serverSocket.close();
        }
    }

    private static void start(final Socket socket) throws IOException {

        Thread thread = new Thread() {

            int jsonObject = 0;
            int jsonString = 0;
            int jsonNumber = 0;
            int jsonArray = 0;
            int jsonBool = 0;
            int jsonNull = 0;
            int errorCode = 0;
            int jsonObjectMax = 0;
            int jsonStringMax = 0;
            int jsonNumberMax = 0;
            int jsonArrayMax = 0;
            int jsonBoolMax = 0;
            int jsonNullMax = 0;
            int errorCodeMax = 0;
            String errorMsg = null;

            @Override
            public void run() {
                try {

                    OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

                    String line = reader.readLine();

                    JSONObject jSONObject = new JSONObject(line);
                    
                    for (Map.Entry<String, Object> key : jSONObject.toMap().entrySet()) {

                        if (key.getKey().equals("json_object")) {

                            jsonObject++;
                        }

                        if (key.getKey().equals("json_string")) {

                            jsonString++;
                        }

                        if (key.getKey().equals("json_number")) {

                            jsonNumber++;
                        }

                        if (key.getKey().equals("json_array")) {

                            jsonArray++;
                        }

                        if (key.getKey().equals("json_bool")) {

                            jsonBool++;
                        }

                        if (key.getKey().equals("json_null")) {

                            jsonNull++;
                        }
                    }

                    jSONObject.put("json_object", jsonObject);
                    jSONObject.put("json_string", jsonString);
                    jSONObject.put("json_number", jsonNumber);
                    jSONObject.put("json_array", jsonArray);
                    jSONObject.put("json_bool", jsonBool);
                    jSONObject.put("json_null", jsonNull);
                    jSONObject.put("error_code", errorCode);
                    jSONObject.put("error_msg", errorMsg);

                    writer.write(jSONObject.toString());
                    writer.flush();
                    
                    jsonObjectMax += jsonObject;
                    jsonStringMax += jsonString;
                    jsonNumberMax += jsonNumber;
                    jsonArrayMax += jsonArray;
                    jsonBoolMax += jsonBool;
                    jsonNullMax += jsonNull;
                    errorCodeMax += errorCode;

                } catch (IOException ex) {
                    errorCode++;
                    errorMsg = ex.toString();
                } finally {
                    closeSocket();
                }
            }

            private void closeSocket() {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        };
        thread.start();
    }
;
}
