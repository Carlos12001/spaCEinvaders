//
// Created by Diablo on 4/9/2023.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <winsock2.h>
#include <pthread.h>
#include <ws2tcpip.h>
#include "csocket.h"

#define MAX_MSG_LEN 1024

// Helper function to create a socket and connect to the server
int create_socket(const char *server_ip, int port) {
    int sock_fd;
    struct sockaddr_in server_addr;
    WSADATA wsa_data;

    // Initialize Winsock
    if (WSAStartup(MAKEWORD(2, 2), &wsa_data) != 0) {
        printf("WSAStartup failed with error code: %d\n", WSAGetLastError());
        exit(EXIT_FAILURE);
    }

    // Create socket
    sock_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (sock_fd < 0) {
        printf("Failed to create socket\n");
        exit(EXIT_FAILURE);
    }

    // Set server address
    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(port);
    if (inet_pton(AF_INET, server_ip, &server_addr.sin_addr) <= 0) {
        printf("Invalid address/ Address not supported\n");
        exit(EXIT_FAILURE);
    }

    Sleep(100);
    // Connect to server
    if (connect(sock_fd, (struct sockaddr *)&server_addr, sizeof(server_addr)) < 0) {
        printf("Connection failed\n");
        exit(EXIT_FAILURE);
    }

    return sock_fd;
}

// Thread function to listen for incoming messages
void *listen_thread(void *arg) {
    int sock_fd = *(int *)arg;
    char buffer[MAX_MSG_LEN];
    int bytes_received;

    while (1) {
        memset(buffer, 0, MAX_MSG_LEN);
        bytes_received = recv(sock_fd, buffer, MAX_MSG_LEN, 0);
        if (bytes_received <= 0) {
            // Error or connection closed
            printf("Error or connection closed. Trying to reconnect...\n");
            close(sock_fd);
            sock_fd = init_socket("127.0.0.1", 25565);
            if (sock_fd < 0) {
                printf("Failed to reconnect. Exiting listen thread.\n");
                break;
            }
            printf("Reconnected to server.\n");
            continue;
        }
        printf("Received message: %s\n", buffer);
    }

    printf("Listening thread exited\n");
    pthread_exit(NULL);
}

// Initializes the socket and connects to the server
int init_socket(const char *server_ip, int port) {
    return create_socket(server_ip, port);
}

// Sends a message through the socket
int send_message(int socket_fd, const char *message) {
    int bytes_sent;

    bytes_sent = send(socket_fd, message, strlen(message), 0);
    if (bytes_sent <= 0) {
        printf("Failed to send message\n");
        return -1;
    }

    return 0;
}

// Starts a thread to listen for incoming messages
void start_listening(int socket_fd) {
    pthread_t thread_id;

    if (pthread_create(&thread_id, NULL, listen_thread, &socket_fd) != 0) {
        printf("Failed to create listening thread\n");
        exit(EXIT_FAILURE);
    }
}
