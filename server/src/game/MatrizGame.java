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
        setMatrixZero(matriz);
        initializeGameMatriz();
    }

    public void initializeGameMatriz(){
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
        matriz[rowNum-1][colNum/2 -1] = 10;
        System.out.println("Matriz inicializada");
    }

    static public void setMatrixZero(Integer[][] matrix) {
        for (Integer i = 0; i < matrix.length; i++) {
            for (Integer j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public Boolean aliensArrived(){
        return false;
    }

    public Boolean aliensDied(){
        return false;
    }

    public void createUFO(){

    }

    public void moveUFO(){

    }

    public void moveShoots(){

    }

    public Boolean playerWillHit(){
        return false;
    }

    public Boolean alienWillDie(){
        return false;
    }

    public void movePlayer(Integer direction){
    }

    public void createShoot(){
    }

    public void updateAliensPosition() {
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
                    }
                }
            }

        } else {
            for (Integer i = 0; i < rowNum; i++) {
                for (Integer j = 0; j < colNum; j++) {
                    if(0 < matriz[i][j] && matriz[i][j] < 4) {
                        temp[i][j + moveDirectionAliens] = matriz[i][j];
                    }
                    else {
                        temp[i][j] = matriz[i][j];
                    }
                }
            }
        }
        matriz = temp;
    }

    public void printMatriz() {
        for (Integer [] row: matriz) {
            for(Integer element: row){
                System.out.print(element + "\t");
            }
            System.out.println();
        }
        System.out.println("------------------------------------------------");
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public Integer getColNum() {
        return colNum;
    }

}
