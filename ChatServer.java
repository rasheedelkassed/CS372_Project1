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
	
	private String userName;


    private void startServer(int port){
        try{
            this.serverSocket = new ServerSocket(port);
            System.out.println("Server started");
        }catch (IOException e){
            System.out.println(e.getMessage());
            return;
        }
    }


    private void waitForResponse(){
        try{
            System.out.println("Waiting for client...");
            this.clientSocket = this.serverSocket.accept();
            System.out.println("Client connected");
        }catch (IOException e){
            System.out.println(e.getMessage());
            return;
        }
    }

    private void initializeVariables(){
        try{
            this.dataIn = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.dataOut = new PrintWriter(clientSocket.getOutputStream(), true);
            this.messageToSend = new Scanner(System.in);

        }catch (IOException e){
            System.out.println(e.getMessage());
            return;
        }
    }
	
	private void getUserName(){
		System.out.print("Enter your username: ");
		userName = messageToSend.nextLine();
	}

	
    public ChatServer(int port) {
        startServer(port);
        waitForResponse();
        initializeVariables();
		getUserName();
		
		String receivedData = "";
		String sentData = "";
        while(true){
		receivedData = "";
		sentData = "";
            try{
                receivedData = dataIn.readLine();
                if(receivedData == null){
					System.out.println("Client disconnected");
					dataOut.close();
                    dataIn.close();
                    clientSocket.close();
                    break;
				}else{
					System.out.println(receivedData);
				}
				sentData = messageToSend.nextLine();
				if(sentData.equals("\\quit")){
					System.out.println("Quitting...");
					dataOut.close();
                    dataIn.close();
                    clientSocket.close();
                    break;
				}
				sentData = userName + ">" + sentData;
				dataOut.println(sentData);


            }catch (IOException e){
                System.out.println(e.getMessage());
                return;
            }
        }

        try {
            clientSocket.close();
            dataIn.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
            return;
        }

    }

    public static void main(String args[]){
		try{
			ChatServer server = new ChatServer(Integer.parseInt(args[0]));
		}catch (NumberFormatException e){
			System.out.println(e.getMessage());
		}
    }
}