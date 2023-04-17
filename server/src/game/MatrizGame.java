package game;


public class MatrizGame {

    private final Integer rowNum = 15;

    private final Integer colNum = 30;

    private Integer[][] matriz;

    private Integer colNumAliens = 5;

    private Integer moveDirectionAliens = -1;

    public Boolean isUFOPresent = false;

    private Integer moveDirectionUFO = -1;

    public MatrizGame(){
        this.matriz = new Integer[rowNum][colNum];
        initializeGameMatriz();
    }

    public void initializeGameMatriz(){
        setMatrixZero(matriz);
        for (Integer i = 1; i <  7; i++) {
            for (Integer j = colNum-1; j > colNum-1-colNumAliens; j--) {
                if (i <= 1)
                    matriz[i][j] = 1;
                else if (i <= 3)
                    matriz[i][j] = 2;
                else if (i <= 5)
                    matriz[i][j] = 3;
            }
        }

        // create the player
        matriz[rowNum-1][colNum/2 -1] = 10;

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
                    matriz[i][j] = 20;
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
            if(0 < matriz[rowNum-1][j] && matriz[rowNum-1][j] < 4)
                return true;
        }

        return false;
    }

    public Boolean aliensDied(){
        for (Integer i = 0; i < rowNum; i++) {
            for (Integer j = 0; j < colNum; j++) {
                if(0 < matriz[i][j] && matriz[i][j] < 4){
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

    public void moveShoots(){

    }

    public Boolean playerWillHit(){
        return false;
    }

    public void bunkerWillHit(){
    }

    public Boolean alienWillDie(){
        return false;
    }

    public void movePlayer(Integer direction){

        Integer playerRow = rowNum - 1;
        Integer playerCol = -1;

        // Find the current position of the player
        for (Integer col = 0; col < colNum; col++) {
            if (matriz[playerRow][col] == 10) {
                playerCol = col;
                break;
            }
        }

        // Determine the new position of the player based on the direction
        Integer newPlayerCol = playerCol + direction;

        // Check if the new position is within the boundaries of the matrix
        if (newPlayerCol >= 0 && newPlayerCol < colNum) {
            // Move the player to the new position
            matriz[playerRow][playerCol] = 0;
            matriz[playerRow][newPlayerCol] = 10;
        }
    }

    public void shootPlayer(){
    }

    public void shotAliens(){
    }

    public void moveAliens() {
        Boolean moveDown = false;
        Integer colToCheck = moveDirectionAliens == -1 ? 0 : colNum-1;
        for (Integer i = 0; i < rowNum; i++) {
            if(0 < matriz[i][colToCheck] && matriz[i][colToCheck] < 4 ){
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
                    if(0 < matriz[i][j] && matriz[i][j] < 4){
                        temp[i+1][j] = matriz[i][j];
                        continue;
                    }
                    if(matriz[i][j] != 0)
                        temp[i][j] = temp[i][j] == 0 ?
                                matriz[i][j] : temp[i][j];
                }
            }

        } else {
            for (Integer i = 0; i < rowNum; i++) {
                for (Integer j = 0; j < colNum; j++) {
                    if(0 < matriz[i][j] && matriz[i][j] < 4) {
                        temp[i][j + moveDirectionAliens] = matriz[i][j];
                        continue;
                    }
                    if(matriz[i][j] != 0)
                        temp[i][j] = temp[i][j] == 0 ?
                                matriz[i][j] : temp[i][j];
                }
            }
        }
        matriz = temp;
    }
    
    public void killAllAliens(){
        Integer[][] temp = new Integer[rowNum][colNum];
        setMatrixZero(temp);
        for (Integer i = 0; i < rowNum; i++) {
                for (Integer j = 0; j < colNum; j++) {
                    if(0 < matriz[i][j] && matriz[i][j] < 4){
                        temp[i][j] = 0;
                        continue;
                    }
                    if(matriz[i][j] != 0)
                        temp[i][j] = temp[i][j] == 0 ?
                                matriz[i][j] : temp[i][j];
                }
            }
        matriz = temp;
    }

    public String getMatrizString() {
        StringBuilder result = new StringBuilder();
        for (Integer i = 0; i < rowNum; i++) {
            for (Integer j = 0; j < colNum; j++) {
                result.append(matriz[i][j]);
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
                result.append(matriz[i][j]).append("\t");
            }
            result.append("\n");
        }
        System.out.print(result);
        System.out.println("------------------------------------------------");
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
                result.append(matriz[i][j]);
            }
            result.append("]");
        }
        result.append("]");
        return result.toString();
    }

    public Integer[][] getMatriz() {
        return matriz;
    }
}
