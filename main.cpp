#include <string.h>
#include <sys/types.h>
#include <iostream>
#include <sys/socket.h>
#include <sys/socket.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <netinet/in.h>

#define PORT "5423"

#define MAXDATASIZE 500


int main() {
    struct addrinfo hints, *res;
	int sockfd;
	
	char *msg = "This might work";
	int len = strlen(msg);

	// first, load up address structs with getaddrinfo():
	memset(&hints, 0, sizeof hints);
	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_STREAM;
	getaddrinfo("flip2.engr.oregonstate.edu", "5423", &hints, &res);
	// make a socket:
	sockfd = socket(res->ai_family, res->ai_socktype, res->ai_protocol);
	// connect!
	connect(sockfd, res->ai_addr, res->ai_addrlen);
	while(int recv(int sockfd, void *buf, int len, int flags) <= 0){
		int send(int sockfd, const void *msg, int len, int flags);
	}
	close(sockfd);
}