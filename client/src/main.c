#include <stdio.h>
#include "connections/csocket.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <synchapi.h>
#include <SDL.h>
#include <windows.h>

#define MAX_MSG_LEN 1024


int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance,
                   LPSTR lpCmdLine, int nCmdShow){
    printf("Initalize the Client\n");

    int sock_fd;
    char buffer[MAX_MSG_LEN];

    // Connect to server
    sock_fd = init_socket("127.0.0.1", 25565);
    printf("Connected to server\n");


    // Start listening for incoming messages
    start_listening(sock_fd);

    // Send messages to server
    while (1) {
        printf("Enter message: ");
        fgets(buffer, MAX_MSG_LEN, stdin);
        if (send_message(sock_fd, buffer) < 0) {
            printf("Failed to send message\n");
            break;
        }
    }

    return EXIT_SUCCESS;
}
