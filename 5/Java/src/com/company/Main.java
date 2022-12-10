package com.company;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Main {


    public static void importData(String data) throws IOException {
        URL url = new URL("http://localhost:9200/tweets/_bulk");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = data.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        con.getInputStream();
    }

    public static void main(String[] args) {
        BufferedReader reader;
        StringBuilder bulk_string = new StringBuilder();
        var counter = 0;

        try {
            reader = new BufferedReader(new FileReader("D:\\Skola\\7.semester\\PDT\\Zadanie5\\data.json"));
            String line = reader.readLine();

            while (line != null) {
                counter += 1;
                bulk_string.append("{\"index\":{\"_id\": \"").append(counter).append("\" }}\n").append(line).append("\n");
                if ((counter % 10000) == 0) {
                    importData(bulk_string.toString());
                    bulk_string = new StringBuilder();
                    System.out.print(counter + " ");
                }
                line = reader.readLine();
            }

            if (!bulk_string.toString().equals("")) {
                importData(bulk_string.toString());
                System.out.print(counter + " ");
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
