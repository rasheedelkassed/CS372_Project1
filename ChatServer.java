// A Java program for a Server
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ChatServer{

    private Socket clientSocket;
    private ServerSocket serverSocket;

    private Scanner messageToSend;
    private BufferedReader dataIn;          //Data being received from the client
    private PrintWriter dataOut;            //Data to be sent to the client


    private void startServer(int port){
        try{
            this.serverSocket = new ServerSocket(port);
            System.out.println("Server started");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


    private void waitForResponse(){
        try{
            System.out.println("Waiting for client...");
            this.clientSocket = this.serverSocket.accept();
            System.out.println("Client connected");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void initializeVariables(){
        try{
            this.dataIn = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.dataOut = new PrintWriter(clientSocket.getOutputStream(), true);
            this.messageToSend = new Scanner(System.in);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


    private String receivedData = "";
    public ChatServer(int port) {
        startServer(port);
        waitForResponse();
        initializeVariables();
        while(true){
            try{
                receivedData = dataIn.readLine();
                if((receivedData) == "\\quit"){
                    dataOut.close();
                    dataIn.close();
                    clientSocket.close();
                    break;
                }
                System.out.println(receivedData);


            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }

        try {
            clientSocket.close();
            dataIn.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    public static void main(String args[]){
        ChatServer server = new ChatServer(5423);
    }
}