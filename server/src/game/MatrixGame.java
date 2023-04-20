package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatrixGame {

    private Integer rowNum = 15;

    private Integer colNum = 30;

    private Integer[][] matrix;

    private Integer colNumAliens = 5;

    private Integer moveDirectionAliens = -1;

    public Boolean isUFOPresent = false;

    private Integer moveDirectionUFO = -1;

    public MatrixGame(){
        this.matrix = new Integer[rowNum][colNum];
        initializeGameMatriz();
    }

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

    static public void setMatrixZero(Integer[][] matrix) {
        for (Integer i = 0; i < matrix.length; i++) {
            for (Integer j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public Boolean aliensArrived(){
        for (Integer j = 0; j < rowNum; j++) {
            if(0 < matrix[rowNum-1][j] && matrix[rowNum-1][j] < 4)
                return true;
        }

        return false;
    }

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

    public void createUFO(Integer row){

    }

    public void moveUFO(){

    }

    public Integer[] moveShootsPlayer() {
        List<Integer> aliensHit = new ArrayList<>();

        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                if (matrix[row][col] == 7) {
                    // Si el disparo está en la fila 0, eliminarlo
                    if (row == 0) {
                        matrix[row][col] = 0;
                    } else {
                        int aboveElement = matrix[row - 1][col];

                        // Si hay un espacio vacío arriba del disparo
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
            matrix[playerRow][playerCol] = 0;
            matrix[playerRow][newPlayerCol] = 10;
        }
    }

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

    public void shotAliens() {
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

        // Encuentra el alien más abajo en la columna seleccionada
        Integer lowestAlienRow = -1;
        for (Integer row = 0; row < playerRow; row++) {
            if (matrix[row][selectedColumn] >= 1 && matrix[row][selectedColumn] <= 3) {
                lowestAlienRow = row;
            }
        }

        if (lowestAlienRow == -1) {
            return;
        }

        // Verifica que la fila de abajo esté vacía antes de poner el disparo del alien
        if (matrix[lowestAlienRow + 1][selectedColumn] == 0) {
            // Verifica si hay espacio suficiente (3 filas) para el disparo desde la fila del jugador
            if (playerRow - lowestAlienRow >= 4) {
                matrix[lowestAlienRow + 1][selectedColumn] = 4;
            }
        }
    }

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

    private void setMatrix(Integer[][] matrix) {
        this.matrix = matrix;
        rowNum = matrix.length;
        colNum = matrix[0].length;
    }

    public Integer getRowNum() {
        return rowNum;
    }

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

    public Integer[][] getMatrix() {
        return matrix;
    }
}

