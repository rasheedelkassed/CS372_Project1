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
    int s;
    struct addrinfo hints, *res;


    memset(&hints, 0, sizeof hints);
    hints.ai_family = AF_UNSPEC;
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_flags = AI_PASSIVE;

    getaddrinfo("127.0.0.1", "3490", &hints, &res);
}