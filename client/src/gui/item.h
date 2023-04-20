//
// Created by Diablo on 4/19/2023.
//

#ifndef CLIENT_ITEM_H
#define CLIENT_ITEM_H

#include <SDL.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../lib/SDL2_image/x86_64-w64-mingw32/include/SDL2/SDL_image.h"

void showPicture(int number, SDL_Renderer *renderer, int x, int y);
void renderChar(char* inputChar, SDL_Renderer *renderer);

#endif //CLIENT_ITEM_H
