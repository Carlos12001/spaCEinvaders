//
// Created by Diablo on 4/9/2023.
//

#ifndef CSOCKET_H
#define CSOCKET_H

// Initializes the socket and connects to the server
int init_socket(const char *server_ip, int port);

// Sends a message through the socket
int send_message(const char *message);

// Starts a thread to listen for incoming messages
void start_listening();

int getSocket();

#endif
