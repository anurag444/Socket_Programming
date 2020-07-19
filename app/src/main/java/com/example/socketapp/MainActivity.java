package com.example.socketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Thread myThread = new Thread(new MyServerThread());
        myThread.start();

//        Thread clientThread = new Thread(new MyClientThread());
//        clientThread.start();


        callClient();


    }

    private void callClient() {
        WebView webView = findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/index.html");
    }

    class MyClientThread implements Runnable {

        @Override
        public void run() {
            final WebView webView = findViewById(R.id.webview);
            webView.post(new Runnable() {
            @Override
            public void run() {
                webView.getSettings().setJavaScriptEnabled(true);
                //webview.loadDataWithBaseURL(null, yourhtmlpage, "text/html", "UTF-8", null);
                webView.loadUrl("file:///android_asset/index.html");
              }
        });


        }
    }

    class MyServerThread implements Runnable {

        @Override
        public void run() {
            try {

                ServerSocket serverSocket = new ServerSocket(60010);

                Log.v("MAIN", "Server started");
                Log.v("MAIN", "Waiting for a client ...");

                Socket clientSocket = serverSocket.accept();
                Log.v("MAIN", "Client Accepted");

                String line = "";
                java.util.Date date = new java.util.Date();
                InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

                while (true) {
                        //if (reader.readLine() == null) continue;
                        line = reader.readLine();
                        //Log.v("MAIN", "Echoing:" + line);


                        //Printing Header of TCP payload
                        StringBuilder raw = new StringBuilder();
                        //raw.append("").append(line);

                        int contentLength = 0;

                        while (!(line = reader.readLine()).isEmpty()) {
                            //raw.append('\n').append(line);
                            final String contentHeader = "Content-Length: ";

                            if (line.startsWith(contentHeader)) {
                                contentLength = Integer.parseInt(line.substring(contentHeader.length()));
                            }

                        }


                        //Printing Body of TCP payload
                        final StringBuilder body = new StringBuilder();

                        int c = 0;
                        for (int i = 0; i < contentLength; i++) {
                            c = reader.read();
                            body.append((char) c);
                            //System.out.println("POST: " + ((char) c) + " " + c);
                        }


                    final int finalContentLength = contentLength;
                    runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (finalContentLength >120){
                                    Toast.makeText(MainActivity.this, body.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        raw.append(body.toString());

                        Log.v("MAIN", raw.toString());

                        //Sending response back to client

                    Log.v("MAIN", "Sending Response to Client");
                    out.writeUTF("HTTP/1.1 200 OK\r\nAccess-Control-Allow-Origin: null\r\nVary: Origin, Access-Control-Request-Headers Access-Control-Allow-Credentials: true\r\nAccess-Control-Allow-Methods: GET,HEAD,PUT,PATCH,POST,DELETE\r\nAccess-Control-Allow-Headers: authorization,content-type,x-experience-api-version\r\nAccess-Control-Expose-Headers: ETag\r\nX-DNS-Prefetch-Control: off\r\nStrict-Transport-Security: max-age=15552000; includeSubDomains\r\nX-Download-Options: noopen X-Content-Type-Options: nosniff\r\nX-XSS-Protection: 1; mode=block\r\nAllow: DELETE,GET,HEAD,PUT,POST\r\nContent-Type: text/html; charset=utf-8\r\nContent-Length: 24\r\nETag: W/\"11-xQE0LAHGfCdBHtmWJ7avxA+rFIg\"\r\nDate: " + date + "\r\nConnection: keep-alive\r\n\r\nDELETE,GET,HEAD,PUT,POST");
                    Log.v("MAIN", "Response Sent");

//                    } while (true);

                }//End of while loop

//                clientSocket.close ();
//
//                serverSocket.close ();

            } catch (IOException e) {
                Log.e("MAIN", e.getMessage());
            }

        }
    }

}

