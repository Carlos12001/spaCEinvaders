package game;

public class MatrizGame {

    private final int rowNum = 15;

    private final int colNum = 30;

    private int colNumAliens = 5;

    public int[][] matriz;

    public int moveDirection = -1;

    public MatrizGame(){
        this.matriz = new int[rowNum][colNum];
        initializeGameMatriz();
    }

    public MatrizGame(int colNumAliens){
        this.colNumAliens = 1 < colNumAliens && colNumAliens < 10
                ? colNumAliens : 5;
        this.matriz = new int[rowNum][colNum];
        initializeGameMatriz();
    }

    public void initializeGameMatriz(){
        for (int i = 0; i <  6; i++) {
            for (int j = colNum-1; j > colNum-1-colNumAliens; j--) {
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

    public void updateAliensPosition() {
        boolean moveDown = false;
        int colToCheck = moveDirection == -1 ? 0 : colNum-1;
        for (int i = 0; i < rowNum; i++) {
            if(0 < matriz[i][colToCheck] && matriz[i][colToCheck] < 4 ){
                moveDown = true;
                moveDirection *= -1;
                break;
            }
        }

        int[][] temp = new int[rowNum][colNum];

        if (moveDown) {
            for (int i = 0; i < rowNum; i++) {
                for (int j = 0; j < colNum; j++) {
                    if(0 < matriz[i][j] && matriz[i][j] < 4){
                        temp[i+1][j] = matriz[i][j];
                    }
                }
            }

        } else {
            for (int i = 0; i < rowNum; i++) {
                for (int j = 0; j < colNum; j++) {
                    if(0 < matriz[i][j] && matriz[i][j] < 4) {
                        temp[i][j + moveDirection] = matriz[i][j];
                    }
                }
            }
        }
        matriz = temp;
    }

    public void printMatriz() {
        for (int [] row: matriz) {
            for(int element: row){
                System.out.print(element + "\t");
            }
            System.out.println();
        }
        System.out.println("------------------------------------------------");
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

}
