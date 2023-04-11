# Set C standard to C23
set(CMAKE_C_STANDARD 23)

# Set output directory for executables
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY  ${CMAKE_BINARY_DIR}/bin)

# Create output directory if it doesn't exist
file(MAKE_DIRECTORY ${CMAKE_RUNTIME_OUTPUT_DIRECTORY})

# Create executable
add_executable(${PROJECT_NAME} main.c connection.c)
