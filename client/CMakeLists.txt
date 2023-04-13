cmake_minimum_required(VERSION 3.25)

# set the project name
project(client)

list(APPEND CMAKE_MODULE_PATH ${CMAKE_CURRENT_SOURCE_DIR}/src/lib/cmake/sdl2)

# add the source directory to the build
add_subdirectory(src)