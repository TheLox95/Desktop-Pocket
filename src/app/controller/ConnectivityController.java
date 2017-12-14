package app.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Leonardo on 11/12/2017.
 */
public class ConnectivityController {

    public boolean thereIsInternetConnection(){
        Socket sock = new Socket();
        InetSocketAddress addr = new InetSocketAddress("www.google.com",80);
        try {
            sock.connect(addr,3000);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {sock.close();}
            catch (IOException e) {}
        }
    }
}
