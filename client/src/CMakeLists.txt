cmake_minimum_required(VERSION 3.25)

# Set C standard to C23
set(CMAKE_C_STANDARD 23)

# Set output directory for executables
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY  ${CMAKE_BINARY_DIR}/bin)

# Create output directory if it doesn't exist
file(MAKE_DIRECTORY ${CMAKE_RUNTIME_OUTPUT_DIRECTORY})


# Copy SDL2 and SDL2_image dll to output directory
file(COPY ${CMAKE_SOURCE_DIR}/src/lib/SDL2/x86_64-w64-mingw32/bin/SDL2.dll
        DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY})
file(COPY ${CMAKE_SOURCE_DIR}/src/lib/SDL2_image/x86_64-w64-mingw32/bin/SDL2_image.dll
        DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY})
file(COPY ${CMAKE_SOURCE_DIR}/src/icons/1.png
        DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/icons/)
file(COPY ${CMAKE_SOURCE_DIR}/src/icons/2.png
        DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/icons/)
file(COPY ${CMAKE_SOURCE_DIR}/src/icons/3.png
        DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/icons/)
file(COPY ${CMAKE_SOURCE_DIR}/src/icons/4.png
        DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/icons/)
file(COPY ${CMAKE_SOURCE_DIR}/src/icons/7.png
        DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/icons/)
file(COPY ${CMAKE_SOURCE_DIR}/src/icons/10.png
        DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/icons/)
file(COPY ${CMAKE_SOURCE_DIR}/src/icons/14.png
        DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/icons/)
file(COPY ${CMAKE_SOURCE_DIR}/src/icons/17.png
        DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/icons/)
file(COPY ${CMAKE_SOURCE_DIR}/src/icons/18.png
        DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/icons/)
file(COPY ${CMAKE_SOURCE_DIR}/src/icons/19.png
        DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/icons/)
file(COPY ${CMAKE_SOURCE_DIR}/src/icons/20.png
        DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/icons/)
file(COPY ${CMAKE_SOURCE_DIR}/src/icons/75.png
        DESTINATION ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/icons/)


# Include SDL2 and SDL2_image headers
find_path(SDL2_INCLUDE_DIR SDL.h HINTS
        ${CMAKE_SOURCE_DIR}/src/lib/SDL2/x86_64-w64-mingw32/include/SDL2)
find_path(SDL2_INCLUDE_DIR SDL.h HINTS
        ${CMAKE_SOURCE_DIR}/src/lib/SDL2_image/x86_64-w64-mingw32/include/SDL2)

include_directories(${SDL2_INCLUDE_DIR}
                    ${SDL2_IMAGE_INCLUDE_DIR}
        )

# Link SDL2 library
find_library(SDL2_LIB SDL2 HINTS
        ${CMAKE_SOURCE_DIR}/src/lib/SDL2/x86_64-w64-mingw32/lib)
if (NOT SDL2_LIB)
    message(FATAL_ERROR "SDL2 library not found")
endif()

# Link SDL2_image library
find_library(SDL2_IMAGE_LIB SDL2_image HINTS
        ${CMAKE_SOURCE_DIR}/src/lib/SDL2_image/x86_64-w64-mingw32/lib)
if (NOT SDL2_IMAGE_LIB)
    message(FATAL_ERROR "SDL2_image library not found")
endif()

include_directories(
        ${PROJECT_SOURCE_DIR}/connections
)

# Create executable
add_executable(${PROJECT_NAME} main.c connections/csocket.c gui/item.c gui/item.h lib/SDL2_image/x86_64-w64-mingw32/include/SDL2/SDL_image.h)

# Link libraries
target_link_libraries(${PROJECT_NAME}
        ${SDL2_LIB}
        ${SDL2_IMAGE_LIB}
        ws2_32
        )
