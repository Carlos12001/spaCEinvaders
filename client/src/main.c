#include <stdio.h>
#include "connections/csocket.h"
#include "gui/item.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <synchapi.h>
#include "stdbool.h"
#include "lib/SDL2_image/x86_64-w64-mingw32/include/SDL2/SDL_image.h"
#include <SDL.h>
#include <windows.h>

#define MAX_MSG_LEN 4096

//int sock_fd;

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance,
                   LPSTR lpCmdLine, int nCmdShow){

    char inputChar[] = "0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,20,20,20,0,0,0,20,20,20,0,0,0,20,20,20,0,0,0,20,20,20,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
    //char inputChar[] = "1,3,1000,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1";
    printf("Client Functioning\n");
    // /**

//    char buffer[MAX_MSG_LEN];

    // Connect to server
    init_socket("127.0.0.1", 25565);
    printf("Connected to server\n");


    // Start listening for incoming messages
    start_listening();

    // Send messages to server
     /**
    while (1) {
        printf("Enter message: \n");
        char buffer[MAX_MSG_LEN] = "pito\n";
        //fgets(buffer, MAX_MSG_LEN, stdin);
        printf("%s", buffer);
        if (send_message(buffer) < 0) {
            printf("Failed to send message\n");
            break;
        }
    }
    // */
    // /**
    //SDL_Init(SDL_INIT_EVERYTHING);
    SDL_Init(SDL_INIT_VIDEO);
    SDL_Window * win = SDL_CreateWindow("SpaceInviders", SDL_WINDOWPOS_UNDEFINED,SDL_WINDOWPOS_UNDEFINED,1200,675, SDL_WINDOW_OPENGL);
    SDL_Renderer *renderer = SDL_CreateRenderer(win, -1, SDL_RENDERER_ACCELERATED | SDL_RENDERER_PRESENTVSYNC);
    SDL_SetRenderDrawColor(renderer, 30, 31, 34, 255);
    IMG_Init(IMG_INIT_JPG | IMG_INIT_PNG);
    //SDL_Delay(2000);
    bool run = true;

    //strcpy(buffer, "j");
    //send_message(sock_fd, buffer);


    int abc= 0;
    while(run){
        SDL_Event ev;
        while(SDL_PollEvent(&ev))
        {
            switch (ev.type)
            {
                case SDL_QUIT:
                    run = false;
                    break;
                case SDL_KEYUP:
                    if(SDLK_j == ev.key.keysym.sym)
                    {
                        send_message( "j\n");
                    }

            }
        }
        renderChar(get_received_message(), renderer);
    }


    IMG_Quit();
    SDL_DestroyRenderer(renderer);
    SDL_DestroyWindow(win);
    SDL_Quit();

    // */
    return EXIT_SUCCESS;
}
