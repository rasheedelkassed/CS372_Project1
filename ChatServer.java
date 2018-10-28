// A Java program for a Server
import java.net.*;
import java.io.*;

public class ChatServer{

    private Socket clientSocket;
    private ServerSocket serverSocket;

    private BufferedReader dataIn;          //Data being received from the client
    private BufferedReader commandLineIn;   //Data being received from the command line
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

    private void getClientData(){
        try{
            this.dataIn = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void getCommandLineData(){
        this.commandLineIn = new BufferedReader(new InputStreamReader(System.in));
    }

    private void sendDataToClient(){
        try{
            this.dataOut = new PrintWriter(clientSocket.getOutputStream(), true);
            dataOut.println(commandLineIn.readLine());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


    private String testReceivedData = "";
    public ChatServer(int port) {
        startServer(port);
        waitForResponse();
        getClientData();

        while(testReceivedData != "\\quit"){
            try{
                testReceivedData = dataIn.readLine();
                System.out.println(testReceivedData);
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