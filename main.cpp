#include <string.h>
#include <sys/types.h>
#include <iostream>
#include <sys/socket.h>
#include <sys/socket.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <netinet/in.h>

#define PORT "5000"

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
	getaddrinfo("flip2.engr.oregonstate.edu", "5000", &hints, &res);
	// make a socket:
	sockfd = socket(res->ai_family, res->ai_socktype, res->ai_protocol);
	// bind
	bind(sockfd, res->ai_addr, res->ai_addrlen);
	// connect!
	connect(sockfd, res->ai_addr, res->ai_addrlen);
	
	int send(int sockfd, const void *msg, int len, int flags);

}