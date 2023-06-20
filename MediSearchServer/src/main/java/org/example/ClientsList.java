package org.example;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ClientsList
{
    private ArrayList<ObjectOutputStream> cliList;

    public ClientsList(){
        cliList = new ArrayList<ObjectOutputStream>();
    }

    public synchronized void addClient(ObjectOutputStream oos) {
        cliList.add(oos);
        System.out.println("this is addClient site");
    }

    public synchronized void removeClient(BufferedWriter bw) {
        cliList.remove(bw);
    }

    public synchronized void sendDataToTheClient(ObjectOutputStream fromOos, Object msg){
        try{
            for(ObjectOutputStream oos : cliList){
                if(oos == fromOos){
                    System.out.println("sendData : "+msg);
                    oos.writeObject(msg);
                    System.out.println("sendData under of OOS writeObject");
                }
            }
        }catch(IOException e){
            System.err.println(e);
        }
    }
}
