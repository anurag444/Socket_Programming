package com.example.socketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        Thread myThread= new Thread(new MyServerThread());
        myThread.start();

    }

    private void initViews() {
        webView = findViewById(R.id.webview);
    }

    class MyServerThread implements Runnable{

        @Override
        public void run() {
            try{

                ServerSocket serverSocket = new ServerSocket (5000);

            Log.e("MAIN","Server started");


            Log.e("MAIN","Waiting for a client ...");


                Socket clientSocket =  serverSocket.accept();

                Log.e("MAIN","Client Accepted");



                while(true){

                    java.util.Date date=new java.util.Date();
                    InputStreamReader isr =  new InputStreamReader(clientSocket.getInputStream());
                    BufferedReader reader = new BufferedReader(isr);
                    DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());


                    do {
                        String line;
                        line = reader.readLine();
                        //Log.e("MAIN", "Echoing:" + line);


                        //Printing Header of TCP payload
                        StringBuilder raw = new StringBuilder();
                        //raw.append("" + line);

                        int contentLength = 0;
                        while (!(line = reader.readLine()).equals("")) {
                           // raw.append('\n' + line);
                            final String contentHeader = "Content-Length: ";

                            if (line.startsWith(contentHeader)) {
                                contentLength = Integer.parseInt(line.substring(contentHeader.length()));
                            }

                        }

                        //Printing Body of TCP payload
                        StringBuilder body = new StringBuilder();

                        int c = 0;
                        for (int i = 0; i < contentLength; i++) {
                            c = reader.read();
                            body.append((char) c);
                            // System.out.println("POST: " + ((char) c) + " " + c);
                        }

                        raw.append(body.toString());


                        Log.e("MAIN", raw.toString());


                        //Sending response back to client
                        out.writeUTF("HTTP/1.1 200 OK\r\nAccess-Control-Allow-Origin: null\r\nVary: Origin, Access-Control-Request-Headers Access-Control-Allow-Credentials: true\r\nAccess-Control-Allow-Methods: GET,HEAD,PUT,PATCH,POST,DELETE\r\nAccess-Control-Allow-Headers: authorization,content-type,x-experience-api-version\r\nAccess-Control-Expose-Headers: ETag\r\nX-DNS-Prefetch-Control: off\r\nStrict-Transport-Security: max-age=15552000; includeSubDomains\r\nX-Download-Options: noopen X-Content-Type-Options: nosniff\r\nX-XSS-Protection: 1; mode=block\r\nAllow: DELETE,GET,HEAD,PUT,POST\r\nContent-Type: text/html; charset=utf-8\r\nContent-Length: 24\r\nETag: W/\"11-xQE0LAHGfCdBHtmWJ7avxA+rFIg\"\r\nDate: " + date + "\r\nConnection: keep-alive\r\n\r\nDELETE,GET,HEAD,PUT,POST");


                    } while (true);

                }//End of while loop

//                clientSocket.close ();
//
//                serverSocket.close ();

            }catch (IOException e){
                Log.e("MAIN", e.getMessage());
            }

        }
    }
}

