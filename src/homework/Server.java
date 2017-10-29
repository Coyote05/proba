package homework;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
            String errorMsg = null;

            @Override
            public void run() {
                try {

                    OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

                    String line = reader.readLine();

                    JSONObject containerObject = new JSONObject(line);


                    //FIXME A hashmap-nek van olyan metódusa, hogy containsKey(), ezzel meg tudod nézni, hogy tartalmazza
                    // ha tartalmazza, akkor pedig feltételezem el akarod kérni az adott kulcshoz tartozó értéket. de ez tipp, mert nem írtad
                    // ahhoz van a get() metódus, ami key alapján visszaadja a value-t

                    if(containerObject.toMap().containsKey("kiscica")){

                    }
                    for (Object key : containerObject.keySet()) {

                        if (key.equals("json_object")) {

                            jsonObject++;
                        }

                        if (key.equals("json_string")) {

                            jsonString++;
                        }

                        if (key.equals("json_number")) {

                            jsonNumber++;
                        }

                        if (key.equals("json_array")) {

                            jsonArray++;
                        }

                        if (key.equals("json_bool")) {

                            jsonBool++;
                        }

                        if (key.equals("json_null")) {

                            jsonNull++;
                        }
                    }

                    JSONObject jSONObject = new JSONObject();
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
