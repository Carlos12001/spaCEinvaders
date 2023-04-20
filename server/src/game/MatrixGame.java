package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MatrixGame class
 * This class is the matrix of the game
 */
public class MatrixGame {

    /*
     * This is the number of rows of the matrix
     */
    private Integer rowNum = 15;

    /*
     * This is the number of columns of the matrix
     */
    private Integer colNum = 30;

    /*
     * This is the matrix of the game
     */
    private Integer[][] matrix;

    /*
     * This is the list of the aliens
     */
    private Integer colNumAliens = 5;

    /*
     * This is the direction of the aliens
     */
    private Integer moveDirectionAliens = -1;

    /*
     * Check is the ufo is present
     */
    public Boolean isUFOPresent = false;

    /*
     * The constructor of the class
     */
    public MatrixGame(){
        this.matrix = new Integer[rowNum][colNum];
        initializeGameMatriz();
    }

    /*
     * Initialize the matrix of the game
     */
    public void initializeGameMatriz(){
        setMatrixZero(matrix);
        for (Integer i = 1; i <  7; i++) {
            for (Integer j = colNum-1; j > colNum-1-colNumAliens; j--) {
                if (i <= 1)
                    matrix[i][j] = 1;
                else if (i <= 3)
                    matrix[i][j] = 2;
                else if (i <= 5)
                    matrix[i][j] = 3;
            }
        }

        // create the player
        matrix[rowNum-1][colNum/2 -1] = 10;

        // create bunkers
        Integer numBunkers = 4;
        Integer bunkerWidth = 3;
        Integer totalBunkerWidth = numBunkers * bunkerWidth;
        Integer remainingSpace = colNum - totalBunkerWidth;
        Integer spaceBetweenBunkers = remainingSpace / (numBunkers + 1);
        Integer extraSpace = remainingSpace % (numBunkers + 1);
        Integer startingColumn = spaceBetweenBunkers + extraSpace / 2;

        for (Integer k = 0; k < numBunkers; k++) {
            for (Integer i = rowNum - 2; i < rowNum - 1; i++) {
                for (Integer j = startingColumn; j < startingColumn + bunkerWidth; j++) {
                    matrix[i][j] = 20;
                }
            }
            startingColumn += bunkerWidth + spaceBetweenBunkers;
        }
    }

    /*
     * This method set the matrix to zero
     * @param matrix is the matrix to set to zero
     */
    static public void setMatrixZero(Integer[][] matrix) {
        for (Integer i = 0; i < matrix.length; i++) {
            for (Integer j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    /*
     * This check is aliens arrived to earth
     * @return true if aliens arrived to earth
     */
    public Boolean aliensArrived(){
        for (Integer j = 0; j < rowNum; j++) {
            if(0 < matrix[rowNum-1][j] && matrix[rowNum-1][j] < 4)
                return true;
        }

        return false;
    }

    /*
     * Check is all aliens died
     * @return true if all aliens died
     */
    public Boolean aliensDied(){
        for (Integer i = 0; i < rowNum; i++) {
            for (Integer j = 0; j < colNum; j++) {
                if(0 < matrix[i][j] && matrix[i][j] < 4){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method create the UFO
     * @param row is the row to create the UFO
     */
    public void createUFO(Integer row) {
        // Verificar si no existe una UFO previamente
        if (!isUFOPresent) {
            // Revisar si en la fila proporcionada hay
            // otros elementos diferentes a 0
            boolean emptyRow = true;
            for (int col = 0; col < colNum; col++) {
                if (matrix[row][col] != 0) {
                    emptyRow = false;
                    break;
                }
            }

            // Si la fila esta vacia, crear la UFO
            if (emptyRow) {
                matrix[row][0] = 14;
                isUFOPresent = true;
            }
        }
    }

    /*
     * This method move the UFO
     * @return return 14 if UFO was killed and return 0 if UFO was not killed
     */
    public Integer moveUFO() {
        if (!isUFOPresent) {
            return 0;
        }

        Integer ufoRow = -1;
        Integer ufoCol = -1;

        // Encuentra la posicion de la UFO
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                if (matrix[row][col] == 14) {
                    ufoRow = row;
                    ufoCol = col;
                    break;
                }
            }
            if (ufoRow != -1) {
                break;
            }
        }

        // Si la UFO no se encontro, establece isUFOPresent en false y devuelve 0
        if (ufoRow == -1) {
            isUFOPresent = false;
            return 0;
        }

        // Si la UFO esta en el borde derecho, eliminar la UFO y devolver 0
        if (ufoCol == colNum - 1) {
            matrix[ufoRow][ufoCol] = 0;
            isUFOPresent = false;
            return 0;
        }

        // Comprueba si hay un disparo del jugador debajo de la UFO
        if (matrix[ufoRow + 1][ufoCol + 1] == 7) {
            matrix[ufoRow][ufoCol] = 0;
            matrix[ufoRow + 1][ufoCol + 1] = 0;
            isUFOPresent = false;
            return 14;
        }
        // 0 14 ... 0
        if (ufoCol>0){
            if (matrix[ufoRow][ufoCol - 1] == 7){
                matrix[ufoRow][ufoCol] = 0;
                matrix[ufoRow][ufoCol - 1] = 0;
                isUFOPresent = false;
                return 14;
            }
        }
        
        // 0 0 14 ... 0
        if (ufoCol>1){
            if (matrix[ufoRow][ufoCol - 2] == 7){
                matrix[ufoRow][ufoCol] = 0;
                matrix[ufoRow][ufoCol - 2] = 0;
                isUFOPresent = false;
                return 14;
            }
        }
        
        // 0 ... 14 0
        if (ufoCol<colNum-1){
            if (matrix[ufoRow][ufoCol + 1] == 7){
                matrix[ufoRow][ufoCol] = 0;
                matrix[ufoRow][ufoCol + 1] = 0;
                isUFOPresent = false;
                return 14;
            }
        }

        // 0 ... 14 0 0
        if (ufoCol<colNum-2){
            if (matrix[ufoRow][ufoCol + 2] == 7){
                matrix[ufoRow][ufoCol] = 0;
                matrix[ufoRow][ufoCol + 2] = 0;
                isUFOPresent = false;
                return 14;
            }
        }
        
        // Mueve la UFO hacia la derecha
        matrix[ufoRow][ufoCol] = 0;
        matrix[ufoRow][ufoCol + 1] = 14;

        return 0;
    }

    /*
     * This method move the shoots of the player
     * @return return the aliens and ufo hit, 0 if no aliens were hit
     */
    public Integer[] moveShootsPlayer() {
        List<Integer> aliensHit = new ArrayList<>();

        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                if (matrix[row][col] == 7) {
                    // Si el disparo esta en la fila 0, eliminarlo
                    if (row == 0) {
                        matrix[row][col] = 0;
                    } else {
                        int aboveElement = matrix[row - 1][col];

                        // Si hay un espacio vacio arriba del disparo
                        if (aboveElement == 0) {
                            matrix[row][col] = 0;
                            matrix[row - 1][col] = 7;
                        }
                        // Si hay otro disparo arriba del disparo, no hacer nada
                        else if (aboveElement == 7) {
                            continue;
                        }
                        // Si hay un bunker arriba del disparo
                        else if (aboveElement >= 17 && aboveElement <= 20) {
                            matrix[row][col] = 0;
                            if (aboveElement - 1 == 16) {
                                matrix[row - 1][col] = 0;
                            } else {
                                matrix[row - 1][col] = aboveElement - 1;
                            }
                        }
                        // Si hay un alien o un ufo arriba del disparo
                        else if ((aboveElement >= 1 && aboveElement <= 3) ||
                                aboveElement == 14) {
                            matrix[row][col] = 0;
                            matrix[row - 1][col] = 0;
                            aliensHit.add(aboveElement);
                        }
                        // Si hay un disparo de un alien arriba del disparo
                        else if (aboveElement == 4) {
                            matrix[row][col] = 0;
                            matrix[row - 1][col] = 0;
                        }
                    }
                }
            }
        }

        return aliensHit.toArray(new Integer[0]);
    }

    /*
     * This method move the shoots of the aliens
     * @return return the player hit -1, 0 if no player was hit
     */
    public Integer[] moveShootsAliens() {
        List<Integer> results = new ArrayList<>();
        for (int row = rowNum - 2; row >= 0; row--) {
            for (int col = 0; col < colNum; col++) {
                if (matrix[row][col] == 4) {
                    int belowElement = matrix[row + 1][col];

                    if (belowElement == 0) {
                        matrix[row + 1][col] = 4;
                        matrix[row][col] = 0;
                    } else if (belowElement == 7) {
                        matrix[row + 1][col] = 0;
                        matrix[row][col] = 0;
                    } else if (belowElement >= 17 && belowElement <= 20) {
                        matrix[row][col] = 0;
                        matrix[row + 1][col] -= 1;
                        if (matrix[row + 1][col] == 16) {
                            matrix[row + 1][col] = 0;
                        }
                    } else if (belowElement == 10) {
                        matrix[row][col] = 0;
                        results.add(-1);
                    } else if (belowElement == 4) {
                        // Do nothing and move to the next column
                    }

                }
            }
        }

        // Remove alien shots at the last row
        for (int col = 0; col < colNum; col++) {
            if (matrix[rowNum - 1][col] == 4) {
                matrix[rowNum - 1][col] = 0;
            }
        }

        return results.toArray(new Integer[0]);
    }

    /*
     * This method move the player
     */
    public void movePlayer(Integer direction){

        Integer playerRow = rowNum - 1;
        Integer playerCol = -1;

        // Find the current position of the player
        for (Integer col = 0; col < colNum; col++) {
            if (matrix[playerRow][col] == 10) {
                playerCol = col;
                break;
            }
        }

        // Determine the new position of the player based on the direction
        Integer newPlayerCol = playerCol + direction;

        // Check if the new position is within the boundaries of the matrix
        if (newPlayerCol >= 0 && newPlayerCol < colNum) {
            // Move the player to the new position
            if (matrix[playerRow][newPlayerCol] == 0) {
                matrix[playerRow][playerCol] = 0;
                matrix[playerRow][newPlayerCol] = 10;
            }
        }
    }

    /*
     * This method shoots a player
     * @return return the alien if was hit, 0 if nothing
     */
    public Integer shootPlayer() {
        Integer playerRow = rowNum - 1;
        Integer playerCol = 0;

        // Encontrar la posicion actual del jugador
        for (Integer col = 0; col < colNum; col++) {
            if (matrix[playerRow][col] == 10) {
                playerCol = col;
                break;
            }
        }

        Integer aboveRow = playerRow - 1;
        Integer aboveElement = matrix[aboveRow][playerCol];

        // Si el elemento arriba del jugador es un 0
        if (aboveElement == 0) {
            matrix[aboveRow][playerCol] = 7;
            return 0;
        }
        // Si el elemento arriba del jugador es un 7
        else if (aboveElement == 7) {
            return 0;
        }
        // Si el elemento arriba del jugador es 20-17 (un bunker)
        else if (aboveElement >= 17 && aboveElement <= 20) {
            if (aboveElement == 17) {
                matrix[aboveRow][playerCol] = 0;
            } else {
                matrix[aboveRow][playerCol] -= 1;
            }
            return 0;
        }
        // Si el elemento arriba del jugador es 1-3 o 14
        else if ((aboveElement >= 1 && aboveElement <= 3) || aboveElement == 14) {
            matrix[aboveRow][playerCol] = 0;
            return aboveElement;
        }
        // Si el elemento arriba del jugador es 4 (es un disparo de alien)
        else if (aboveElement == 4) {
            matrix[aboveRow][playerCol] = 0;
            return 0;
        }
        return 0;
    }

    /*
     * Create shoot of the alien
     */
    public void shootAliens() {
        List<Integer> alienColumns = new ArrayList<>();
        Integer playerRow = rowNum - 1;

        // Encuentra las columnas con aliens
        for (Integer col = 0; col < colNum; col++) {
            for (Integer row = 0; row < playerRow; row++) {
                if (matrix[row][col] >= 1 && matrix[row][col] <= 3) {
                    alienColumns.add(col);
                    break;
                }
            }
        }

        if (alienColumns.isEmpty()) {
            return;
        }

        // Escoge una columna de alien aleatoria
        Random random = new Random();
        Integer selectedColumn = alienColumns.get(random.nextInt(alienColumns.size()));

        // Encuentra el alien mas abajo en la columna seleccionada
        Integer lowestAlienRow = -1;
        for (Integer row = 0; row < playerRow; row++) {
            if (matrix[row][selectedColumn] >= 1 && matrix[row][selectedColumn] <= 3) {
                lowestAlienRow = row;
            }
        }

        if (lowestAlienRow == -1) {
            return;
        }

        // Verifica que la fila de abajo este vacia antes de poner el disparo del alien
        if (matrix[lowestAlienRow + 1][selectedColumn] == 0) {
            // Verifica si hay espacio suficiente (3 filas) para el disparo desde la fila del jugador
            if (playerRow - lowestAlienRow >= 4) {
                matrix[lowestAlienRow + 1][selectedColumn] = 4;
            }
        }
    }

    /*
     * This method move the aliens
     */
    public void moveAliens() {
        Boolean moveDown = false;
        Integer colToCheck = moveDirectionAliens == -1 ? 0 : colNum-1;
        for (Integer i = 0; i < rowNum; i++) {
            if(0 < matrix[i][colToCheck] && matrix[i][colToCheck] < 4 ){
                moveDown = true;
                moveDirectionAliens *= -1;
                break;
            }
        }

        Integer[][] temp = new Integer[rowNum][colNum];
        setMatrixZero(temp);
        if (moveDown) {
            for (Integer i = 0; i < rowNum; i++) {
                for (Integer j = 0; j < colNum; j++) {
                    if(0 < matrix[i][j] && matrix[i][j] < 4){
                        temp[i+1][j] = matrix[i][j];
                        continue;
                    }
                    if(matrix[i][j] != 0)
                        temp[i][j] = temp[i][j] == 0 ?
                                matrix[i][j] : temp[i][j];
                }
            }

        } else {
            for (Integer i = 0; i < rowNum; i++) {
                for (Integer j = 0; j < colNum; j++) {
                    if(0 < matrix[i][j] && matrix[i][j] < 4) {
                        temp[i][j + moveDirectionAliens] = matrix[i][j];
                        continue;
                    }
                    if(matrix[i][j] != 0)
                        temp[i][j] = temp[i][j] == 0 ?
                                matrix[i][j] : temp[i][j];
                }
            }
        }
        matrix = temp;
    }

    /*
     * This method kill all aliens
     */
    public void killAllAliens(){
        Integer[][] temp = new Integer[rowNum][colNum];
        setMatrixZero(temp);
        for (Integer i = 0; i < rowNum; i++) {
                for (Integer j = 0; j < colNum; j++) {
                    if(0 < matrix[i][j] && matrix[i][j] < 4){
                        temp[i][j] = 0;
                        continue;
                    }
                    if(matrix[i][j] != 0)
                        temp[i][j] = temp[i][j] == 0 ?
                                matrix[i][j] : temp[i][j];
                }
            }
        matrix = temp;
    }

    /*
     * Get the matrix as a string
     */
    public String getMatrizString() {
        StringBuilder result = new StringBuilder();
        for (Integer i = 0; i < rowNum; i++) {
            for (Integer j = 0; j < colNum; j++) {
                result.append(matrix[i][j]);
                if (i != rowNum-1)
                    result.append(",");
                else if (j != colNum-1)
                    result.append(",");
            }
        }
        return result.toString();
    }

    /*
     * Print the matrix
     */
    public void printMatriz() {
        StringBuilder result = new StringBuilder();
        for (Integer i = 0; i < rowNum; i++) {
            for (Integer j = 0; j < colNum; j++) {
                result.append(matrix[i][j]).append("\t");
            }
            result.append("\n");
        }
        System.out.print(result);
        System.out.println("------------------------------------------------");
    }

    /*
        * Set the matrix
     */
    private void setMatrix(Integer[][] matrix) {
        this.matrix = matrix;
        rowNum = matrix.length;
        colNum = matrix[0].length;
    }

    /*
     * Get the row number
     */
    public Integer getRowNum() {
        return rowNum;
    }

    /*
     * Get the column number
     */
    public Integer getColNum() {
        return colNum;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (Integer i = 0; i < rowNum; i++) {
            if (i > 0)
                result.append(",");
            result.append("[");
            for (Integer j = 0; j < colNum; j++) {
                if (j > 0)
                    result.append(",");
                result.append(matrix[i][j]);
            }
            result.append("]");
        }
        result.append("]");
        return result.toString();
    }

    /*
     * Get the matrix
     */
    public Integer[][] getMatrix() {
        return matrix;
    }
}

