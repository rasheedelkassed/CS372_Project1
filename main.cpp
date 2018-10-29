#include <string.h>
#include <sys/types.h>
#include <iostream>
#include <sys/socket.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>

#define PORT "5423"

#define MAXDATASIZE 500


struct addrinfo* createAddressInfo(char *input_addr, char *port){
	struct addrinfo hints;
	struct addrinfo *res;
	
	memset(&hints, 0, sizeof hints);
	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_STREAM;
	
	int status = getaddrinfo(input_addr, port, &hints, &res);
	if(status != 0){
		exit(1);
	}
	
	return res;
}

int createSocket(struct addrinfo *res){
	int sockfd = socket(res->ai_family, res->ai_socktype, res->ai_protocol);
	if (sockfd == -1){
		exit(1);
	}
	return sockfd;
}

void connectSocket(int sockfd, struct addrinfo *res){
	int status;
	status = connect(sockfd, res->ai_addr, res->ai_addrlen);
	if(status == -1){
		exit(1);
	}
}

void getUserName(char *name){
	fgets(name, 10, stdin);
}

int main() {
	struct addrinfo *res = createAddressInfo("flip2.engr.oregonstate.edu", "5423");
	int sockfd = createSocket(res);
	connectSocket(sockfd, res);
	printf("Server Connected\n");
	
	int status;
	char output[500];
	char input[500];
	char userName[10];
	char toSend[510];
	
	memset(input,0,sizeof(input));
	memset(output,0,sizeof(output));
	memset(userName,0,sizeof(userName));
	memset(toSend,0,sizeof(toSend));
	
	printf("Enter your username: ");
	getUserName(userName);
	
	
	
	int i = 0;
	while(i < 5){
		
		fgets(input, 500, stdin);
		
		strncpy(toSend, userName, sizeof(userName));
		strncat(toSend, input, sizeof(input));
		
		send(sockfd, toSend, sizeof(input), 0);
		status = recv(sockfd, output, 500, 0);		
		
		if (status == -1){
			fprintf(stderr, "Error when receiving data from host\n");
			exit(1);
		}
		else if (status == 0){ 
			printf("Connection closed by server\n");
			break;
		}
		else{
			printf("%s", output);
		}
		
		
		memset(input,0,sizeof(input));
		memset(output,0,sizeof(output));
		memset(toSend,0,sizeof(toSend));
		i++;
	}
	close(sockfd);
	freeaddrinfo(res);
	
	return 0;
}