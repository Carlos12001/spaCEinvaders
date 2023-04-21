//
// Created by Diablo on 4/19/2023.
//

#include "item.h"


int currentx = 5;
int currenty = 5;
int hCount = 0;


/**
* Initializes an SDL_Texture from an image file.
* @param file_name The name of the image file to load.
* @param renderer A pointer to the SDL_Renderer to use for creating the texture.
* @return A pointer to the created SDL_Texture.
*/
SDL_Texture * init_image_from_file(const char* file_name, SDL_Renderer *renderer){
    SDL_Surface *image = IMG_Load(file_name);
    if (!image) {
        printf("Failed to load image: %s\n", IMG_GetError());
        // handle error here
    }
    SDL_Texture *image_texture = SDL_CreateTextureFromSurface(renderer, image);
    SDL_FreeSurface(image);
    return image_texture;
}

/**
* Gets an SDL_Texture for a specified image file based on a given number.
* @param renderer A pointer to the SDL_Renderer to use for creating the texture.
* @param number The number to use for generating the filename of the image file.
* @return A pointer to the created SDL_Texture.
*/
SDL_Texture * getImage(SDL_Renderer *renderer, int number){
    char filename[256];
    sprintf(filename, "./icons/%d.png", number);
    return init_image_from_file(filename, renderer);
}


/**
* Displays an image on the SDL_Renderer at the specified coordinates.
* @param number The number of the image to display.
* @param renderer A pointer to the SDL_Renderer to use for displaying the image.
* @param x The x-coordinate of the top-left corner of the image's destination rectangle.
* @param y The y-coordinate of the top-left corner of the image's destination rectangle.
*/
void showPicture(int number, SDL_Renderer *renderer, int x, int y){
    SDL_Texture *image_texture = getImage(renderer, number);

    SDL_Rect texture_destination;
    texture_destination.x = x;
    texture_destination.y = y;
    texture_destination.w = 30;
    texture_destination.h = 30;

    // Create a source rectangle that covers the entire texture
    SDL_Rect source_rect;
    source_rect.x = 0;
    source_rect.y = 0;
    SDL_QueryTexture(image_texture, NULL, NULL, &source_rect.w, &source_rect.h);

    // Copies the image into the renderer.
    SDL_RenderCopy(renderer, image_texture, &source_rect, &texture_destination);

    // Shows what was copied.
    SDL_DestroyTexture(image_texture);

}


/**
* Manages the positioning of elements in a space-limited environment.
* If the horizontal count exceeds the limit, the function resets it to 0 and increases
* the vertical count by 1 while resetting the horizontal position to the starting value.
*/
void spaceManager(){
    if(hCount > 29){
        hCount = 0;
        currenty += 35;
        currentx = 5;
    }
}


/**
* @Brief Renders an image based on the input character and displays it on the given renderer.
* @param inputChar the input character containing comma-separated integer values
* @param renderer the renderer on which to display the images
*/
void renderChar(char* inputChar, SDL_Renderer *renderer) {
    char* token;
    int i = 0;

    // get the first token
    token = strtok(inputChar, ",");
    //int hCount = 0;
    // process each token
    while(token != NULL) {
        if (i < 3) {
            //printf("%d ", atoi(token)); // print the first 3 elements as integers
        }
        else {
            int value = atoi(token);
            if (value == 0) {
                //printf("v ");
            }
            else if (value == 1) {
                //printf("en1 ");
                //printf("%d ", currentx);
                showPicture(value, renderer, currentx, currenty);
            }
            else if (value == 2) {
                //printf("en2 ");
                showPicture(value, renderer, currentx, currenty);
            }
            else if (value == 3) {
                //printf("en3 ");
                showPicture(value, renderer, currentx, currenty);
            }
            else if (value == 4) {
                //printf("en3 ");
                showPicture(value, renderer, currentx, currenty);
            }
            else if (value == 7) {
                //printf("en3 ");
                showPicture(value, renderer, currentx, currenty);
            }
            else if (value == 10) {
                //printf("al ");
                showPicture(value, renderer, currentx, currenty);
            }
            else if (value == 14) {
                //printf("for ");
                showPicture(value, renderer, currentx, currenty);
            }
            else if (value == 17) {
                //printf("for ");
                showPicture(value, renderer, currentx, currenty);
            }
            else if (value == 18) {
                //printf("for ");
                showPicture(value, renderer, currentx, currenty);
            }
            else if (value == 19) {
                //printf("for ");
                showPicture(value, renderer, currentx, currenty);
            }
            else if (value == 20) {
                //printf("for ");
                showPicture(value, renderer, currentx, currenty);
            }
            else {
                //printf("unknown ");
                showPicture(75, renderer, currentx, currenty);
            }
        }

        if(i >= 3){
            hCount++;
            currentx += 35;
            spaceManager();
        }
        // get the next token
        token = strtok(NULL, ",");
        i++;
    }
}


/**
* @brief Resets the position and count for rendering.
* @return void
*/
void setRenderAgain(){
    currentx = 5;
    currenty = 5;
    hCount = 0;
}


/**
 * SDL_Rect texture_destination;
 *  texture_destination.x = x;
 *  texture_destination.y = y;
 *  texture_destination.w = w;
 *  texture_destination.h = h;
 * SDL_RenderClear(renderer);
 * SDL_RenderCopy(renderer, image_texture, NULL, &texture_destination);
 * SDL_RenderPresent(renderer);
 */

