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
/**
 * Main function
 * @param hInstance the instance of the program
 * @param hPrevInstance the previous instance of the program
 * @param lpCmdLine the command line
 * @param nCmdShow the command show
 * @return the exit code
 */
int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance,
                   LPSTR lpCmdLine, int nCmdShow){

    char inputChar[] = "0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,20,20,20,0,0,0,20,20,20,0,0,0,20,20,20,0,0,0,20,20,20,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
    //char inputChar[] = "1,3,1000,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1";
    printf("Client Functioning\n");
    boolean isStart = false;
    boolean isObserver = false;
    char previous[MAX_MSG_LEN];

    //char buffer[MAX_MSG_LEN];

    /**
     * @brief Starts the socket, and starts listening.
     */
    init_socket("127.0.0.1", 25565);
    printf("Connected to server\n");
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
    //SDL_Init(SDL_INIT_EVERYTHING);


    /**
     *@brief Defines SDL window elements.
     */
    SDL_Init(SDL_INIT_VIDEO);
    SDL_Window * win = SDL_CreateWindow("SpaceInviders", SDL_WINDOWPOS_UNDEFINED,SDL_WINDOWPOS_UNDEFINED,1200,675, SDL_WINDOW_OPENGL);
    SDL_Renderer *renderer = SDL_CreateRenderer(win, -1, SDL_RENDERER_ACCELERATED | SDL_RENDERER_PRESENTVSYNC);
    SDL_SetRenderDrawColor(renderer, 30, 31, 34, 255);
    IMG_Init(IMG_INIT_JPG | IMG_INIT_PNG);
    //SDL_Delay(2000);
    bool run = true;

    //strcpy(buffer, "j");
    //send_message(sock_fd, buffer);

    /*
     * Loop that draws frames every iteration.
     */
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
                    /*
                     * Commands to set player(j) or spectator(e)
                     */
                    if(!isStart) {

                        if (SDLK_j == ev.key.keysym.sym) {
                            send_message("j\n");
                            isStart = true;
                        }
                        if (SDLK_e == ev.key.keysym.sym) {
                            send_message("e\n");
                            isStart = true;
                            isObserver = true;
                        }
                    }
                    /*
                     * Commands for movements and actions in-game
                     */
                    if (!isObserver) {
                        if (SDLK_SPACE == ev.key.keysym.sym) {
                            send_message("s\n");
                        }
                        if (SDLK_RIGHT == ev.key.keysym.sym) {
                            send_message("r\n");
                        }
                        if (SDLK_LEFT == ev.key.keysym.sym) {
                            send_message("l\n");
                        }
                        if (SDLK_k == ev.key.keysym.sym) {
                            send_message("killall\n");
                        }
                        if (SDLK_u == ev.key.keysym.sym) {
                            send_message("u0\n");
                        }
                        if (SDLK_ESCAPE == ev.key.keysym.sym) {
                            send_message("killgame\n");
                        }
                        if (SDLK_i == ev.key.keysym.sym) {
                            send_message("u1\n");
                        }
                    }

            }
        }
        if (strcmp(get_received_message(), previous) != 0) {
            SDL_RenderClear(renderer);
            setRenderAgain();
            renderChar(get_received_message(), renderer);
            strcpy(previous, get_received_message());
        }
        SDL_RenderPresent(renderer);
    }

    /*
     * Closes every window element.
     */
    IMG_Quit();
    SDL_DestroyRenderer(renderer);
    SDL_DestroyWindow(win);
    SDL_Quit();
    return EXIT_SUCCESS;
}
