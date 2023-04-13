#include <stdio.h>
#include "connection.h"
#include <SDL.h>
#include <windows.h>

#define WINDOW_WIDTH 640
#define WINDOW_HEIGHT 480

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance,
                   LPSTR lpCmdLine, int nCmdShow){
    printf("Initalize the Client\n");
    print_example_coneection();

    // Initialize SDL
    if (SDL_Init(SDL_INIT_VIDEO) < 0) {
        printf("Failed to initialize SDL! SDL_Error: %s\n",
               SDL_GetError());
        return -1;
    }

    // Create window
    SDL_Window* window = SDL_CreateWindow("Hello World", SDL_WINDOWPOS_UNDEFINED,
                                          SDL_WINDOWPOS_UNDEFINED, WINDOW_WIDTH,
                                          WINDOW_HEIGHT, SDL_WINDOW_SHOWN);
    if (!window) {
        printf("Failed to create window! SDL_Error: %s\n", SDL_GetError());
        return -1;
    }

    // Get window surface
    SDL_Surface* surface = SDL_GetWindowSurface(window);
    if (!surface) {
        printf("Failed to get the surface from the window! SDL_Error: %s\n",
               SDL_GetError());
        return -1;
    }

    // Fill surface with white color
    SDL_Rect rect = {0, 0, WINDOW_WIDTH, WINDOW_HEIGHT};
    Uint32 white = SDL_MapRGB(surface->format, 255, 255, 255);
    SDL_FillRect(surface, &rect, white);

    // Update window surface
    SDL_UpdateWindowSurface(window);

    // Wait for user exit
    SDL_Event event;
    while (SDL_WaitEvent(&event)) {
        if (event.type == SDL_QUIT) {
            break;
        }
    }

    // Clean up
    SDL_DestroyWindow(window);
    SDL_Quit();

    return 0;
}
