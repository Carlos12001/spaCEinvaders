package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatrixGameTest {
    MatrixGame matrixGame;

    @BeforeEach
    void setUp() {
        matrixGame = new MatrixGame();
    }

    @Test
    void aliensDied() {
        matrixGame.printMatriz();
        for (Integer i = 0; i < 53; i++) {
            matrixGame.moveAliens();
            matrixGame.printMatriz();
        }
        System.out.println("Kill All Aliens");
        matrixGame.killAllAliens();
        matrixGame.printMatriz();
        assertEquals(true, matrixGame.aliensDied());
        matrixGame.initializeGameMatriz();
        matrixGame.printMatriz();
    }

    @Test
    void createUFO() {
    }

    @Test
    void moveUFO() {
    }

    @Test
    void moveShoots() {
    }

    @Test
    void movePlayer() {
        // Move player to the left edge
        for (Integer i = 0; i < 50; i++) {
            matrixGame.movePlayer(-1);
        }
        matrixGame.printMatriz();

        // Check if player is at the left edge
        assertEquals(10, (Integer) matrixGame.getMatrix()[matrixGame.getRowNum() - 1][0]);

        // Move player to the right edge
        for (Integer i = 0; i < 50; i++) {
            matrixGame.movePlayer(1);
        }
        matrixGame.printMatriz();


        // Check if player is at the right edge
        assertEquals(10, (Integer) matrixGame.getMatrix()[matrixGame.getRowNum() - 1][matrixGame.getColNum() - 1]);

        // Move player back to the center
        for (Integer i = 0; i < matrixGame.getColNum() / 2; i++) {
            matrixGame.movePlayer(-1);
        }
        matrixGame.printMatriz();


        // Check if player is at the center
        assertEquals(10, (Integer) matrixGame.getMatrix()[matrixGame.getRowNum() - 1][matrixGame.getColNum() / 2 - 1]);
    }

    @Test
    void createShoot() {
    }

    @Test
    void moveAliens() {
        matrixGame.printMatriz();
        for (Integer i = 0; i < 2*(matrixGame.getColNum()-5+1); i++) {
            System.out.print("Iteration:\t" + i + "\t");
            matrixGame.moveAliens();
            matrixGame.printMatriz();
        }
    }

    @Test
    public void testShootPlayer() throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        Method setMatrizMethod = MatrixGame.class.getDeclaredMethod(
                "setMatrix", Integer[][].class);
        setMatrizMethod.setAccessible(true);
        Integer[][] testMatrix;
        Integer[][] expectMatriz;
        Integer result;

        testMatrix = new Integer[][] {
                {   0,  0,  0,  0,  0   },
                {   0,  0,  1,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  17, 0,  0   },
                {   10, 0,  0,  0,  0   }
        };
        expectMatriz = new Integer[][] {
                    {   0,  0,  0,  0,  0   },
                    {   0,  0,  1,  0,  0   },
                    {   0,  0,  0,  0,  0   },
                    {   7,  0,  17, 0,  0   },
                    {   10, 0,  0,  0,  0   }
        };
        setMatrizMethod.invoke(matrixGame, (Object) testMatrix);
        matrixGame.shootPlayer();
        assertTrue(Arrays.deepEquals(expectMatriz, matrixGame.getMatrix()));



        testMatrix = new Integer[][] {
                {   0,  0,  0,  0,  0   },
                {   0,  0,  1,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  17, 0,  0   },
                {   0, 0,  10,  0,  0   }
        };
        expectMatriz = new Integer[][] {
                {   0,  0,  0,  0,  0   },
                {   0,  0,  1,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  0, 0,  0   },
                {   0, 0,  10,  0,  0   }
        };
        setMatrizMethod.invoke(matrixGame, (Object) testMatrix);
        matrixGame.shootPlayer();
        assertTrue(Arrays.deepEquals(expectMatriz, matrixGame.getMatrix()));

        testMatrix = new Integer[][] {
                {   0,  0,  0,  0,  0   },
                {   0,  0,  1,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  19, 0,  0   },
                {   0, 0,  10,  0,  0   }
        };
        expectMatriz = new Integer[][] {
                {   0,  0,  0,  0,  0   },
                {   0,  0,  1,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  18, 0,  0   },
                {   0, 0,  10,  0,  0   }
        };
        setMatrizMethod.invoke(matrixGame, (Object) testMatrix);
        matrixGame.shootPlayer();
        assertTrue(Arrays.deepEquals(expectMatriz, matrixGame.getMatrix()));

        testMatrix = new Integer[][] {
                {   0,  0,  0,  0,  0   },
                {   0,  0,  1,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  17, 0,  0   },
                {   0,  0,   10,  0,  0   }
        };
        expectMatriz = new Integer[][] {
                {   0,  0,  0,  0,  0   },
                {   0,  1,  0,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  7,  17, 0,  0   },
                {   0,  10, 0,  0,  0   }
        };
        setMatrizMethod.invoke(matrixGame, (Object) testMatrix);
        matrixGame.movePlayer(-1);
        matrixGame.moveAliens();
        matrixGame.shootPlayer();
        assertTrue(Arrays.deepEquals(expectMatriz, matrixGame.getMatrix()));


        testMatrix = new Integer[][] {
                {   0,  0,  0,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  3,  17, 0,  0   },
                {   0,  10,   0,  0,  0   }
        };
        expectMatriz = new Integer[][] {
                {   0,  0,  0,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  17, 0,  0   },
                {   0,  10, 0,  0,  0   }
        };
        setMatrizMethod.invoke(matrixGame, (Object) testMatrix);
        result = matrixGame.shootPlayer();
        assertTrue(Arrays.deepEquals(expectMatriz, matrixGame.getMatrix()));
        assertEquals(3, result);


        testMatrix = new Integer[][] {
                {   0,  0,  0,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  4,  17, 0,  0   },
                {   0,  10,   0,  0,  0   }
        };
        expectMatriz = new Integer[][] {
                {   0,  0,  0,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  17, 0,  0   },
                {   0,  10, 0,  0,  0   }
        };
        setMatrizMethod.invoke(matrixGame, (Object) testMatrix);
        result = matrixGame.shootPlayer();
        assertTrue(Arrays.deepEquals(expectMatriz, matrixGame.getMatrix()));
        assertEquals(0, result);

    }

    @Test
    void shotAliens() throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        Method setMatrizMethod = MatrixGame.class.getDeclaredMethod(
                "setMatrix", Integer[][].class);
        setMatrizMethod.setAccessible(true);
        Integer[][] testMatrix;
        Integer[][] expectMatriz;
        Integer result;

        testMatrix = new Integer[][] {
                {   0,  0,  1,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  17, 0,  0   },
                {   10, 0,  0,  0,  0   }
        };
        expectMatriz = new Integer[][] {
                {   0,  0,  1,  0,  0   },
                {   0,  0,  4,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  17, 0,  0   },
                {   10, 0,  0,  0,  0   }
        };
        setMatrizMethod.invoke(matrixGame, (Object) testMatrix);
        matrixGame.shotAliens();
        assertTrue(Arrays.deepEquals(expectMatriz, matrixGame.getMatrix()));

        testMatrix = new Integer[][] {
                {   0,  0,  1,  0,  0   },
                {   0,  0,  1,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  17, 0,  0   },
                {   10, 0,  0,  0,  0   }
        };
        expectMatriz = new Integer[][] {
                {   0,  0,  1,  0,  0   },
                {   0,  0,  1,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  17, 0,  0   },
                {   10, 0,  0,  0,  0   }
        };
        setMatrizMethod.invoke(matrixGame, (Object) testMatrix);
        matrixGame.shotAliens();
        assertTrue(Arrays.deepEquals(expectMatriz, matrixGame.getMatrix()));

        testMatrix = new Integer[][] {
                {   0,  0,  1,  0,  0   },
                {   0,  0,  3,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  17, 0,  0   },
                {   10, 0,  0,  0,  0   }
        };
        expectMatriz = new Integer[][] {
                {   0,  0,  1,  0,  0   },
                {   0,  0,  3,  0,  0   },
                {   0,  0,  4,  0,  0   },
                {   0,  0,  0,  0,  0   },
                {   0,  0,  17, 0,  0   },
                {   10, 0,  0,  0,  0   }
        };
        setMatrizMethod.invoke(matrixGame, (Object) testMatrix);
        matrixGame.shotAliens();
        assertTrue(Arrays.deepEquals(expectMatriz, matrixGame.getMatrix()));
    }
}