package org.example;

import java.net.*;
import java.io.*;
public class Main
{
    public static final int PORT = 5000;

    public static void main(String args[]){
        ServerSocket listenSocket = null;
        Socket commSocket = null;
        ClientsList cliList = new ClientsList();

        try{
            listenSocket = new ServerSocket(PORT);
            System.out.println("Waiting for connection...");

            while(true){
                commSocket = listenSocket.accept();

                System.out.println("Connection received from " + commSocket.getInetAddress().getHostName() + " : " + commSocket.getPort());

                ClientHandler cliHandler = new ClientHandler(commSocket, cliList);
                System.out.println("under of cliHandler start()");
                cliHandler.start();
                System.out.println("under of cliHandler start()");
            }
        }catch(IOException e){
            System.out.println("catch IOException in main");
            System.err.println(e);
        }finally{
            if(listenSocket != null){
                try{
                    listenSocket.close();
                }catch(IOException e){
                    System.out.println(e);
                }
            }
        }
    }
}
