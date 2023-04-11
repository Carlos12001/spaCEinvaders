# spaCEinvaders


1. Objetivo General

- Desarrollar una aplicación que permita reafirmar el conocimiento de
- los paradigmas de
programación imperativo y orientado a objetos.

2. Objetivos Específicos

- Desarrollar una aplicación en el lenguaje de programación C y java.
- Aplicar los conceptos de programación imperativa y orientada a objetos.
- Crear y manipular listas como estructuras de datos.

3.Datos Generales

- El valor de la tarea: 10% (5% C y 5% Java)
- Nombre código: spaCEinvaders.
- La tarea debe ser implementada en grupos de no más de 3 personas.
- La fecha de entrega es 19/Abril/2023.
- Cualquier indicio de copia será calificado con una nota de 0 y será
- procesado de acuerdo al
reglamento.

4. Descripción del juego.

Space Invaders es uno de los videojuegos más importantes de la historia.
Su objetivo es eliminar oleadas
de alienígenas con un cañón láser y obtener la mayor cantidad de puntos posible.

El jugador controla un cañón que puede moverse a la derecha o izquierda
y un botón de disparo. Tiene
que ir destruyendo los extraterrestres invasores (de los cuales ha
y tres tipos: con forma de calamar otorga
10 pts en caso de ser derribado, de cangrejo otorga 20pts en caso de
ser derribado y de pulpo otorga 40
pts en case de ser derribado) que van acercándose a la tierra cada
vez más rápidamente a medida que el
jugador va destruyendo a los enemigos. Este ciclo se puede repetir
en forma indefinida. Si los invasores
llegan al cañón controlado por el jugador, el juego termina (Wikipedia, 2023).

Cada cierto tiempo aparece en la pantalla, por encima de los invasores,
un platillo volador que se mueve
aleatoriamente de derecha a izquierda o de izquierda a derecha y que
no agrega una puntuación definida,
sino puntos extras en cantidades aleatorias. Además se tienen cuatros
escudos de protección terrestre
(más parecidos a bunkers) que cubren al jugador del fuego alienígena,
pero que son destruidos
gradualmente por los disparos de los invasores y el cañón
del jugador (Wikipedia, 2023).

Pueden jugarlo en línea para que se den una mejor idea en (Pacxon, 2023).

4.1. El servidor: Deberá ser implementado en Java. Mantendrá la lógica
del juego. Será el
encargado de crear y destruir los extraterrestres en las posiciones
que el administrador lo
decida, llevar la puntuación, las vidas restantes del jugador y crear
los ovnis con el valor
aleatorio (introducido por el usuario) velocidad de desplazamiento
de los extraterrestres y el
estado de los bunkers.

Cada vez que el jugador elimine todos los extraterrestres se le asignará 
una vida al jugador.

4.2. El cliente Jugador se refiere a la interfaz gráfica del juego debe 
realizarse en C, controla el rayo
láser que utiliza el usuario, interpretara la estructura enviada por el
servidor y le informa al
servidor cuando un extraterrestre es eliminado.

Para revisión como mínimo el servidor debe soportar al menos 2 clientes jugadores.

4.3. El cliente espectador: Se podrá unir a una partida existente pero 
sólo podrá observar lo que
sucede.

4.4. Conexión cliente servidor: La conexión entre el servidor y los
clientes debe realizarse utilizando
Sockets.

4.5. Aspectos de implementación en C: Recuerden se evaluará que todas
las constantes estén en
un archivo aparte. Se evaluará el uso de strutcs.
Se requiere un ejecutable para la revisión.
4.6. Aspectos de implementación en Java: Recuerden se evaluará que
todo este programado según
los conceptos de OO (Clases, paquetes, patrones al menos deben implementar
2 patrones -
Singleton no cuenta como patrón para revisión-).

Se requiere un ejecutable para la revisión.

5. Entregables

5.1 Código fuente comentado.
5.2 Manual de usuario.

6. Documentación

1.2.Se deberá entregar un documento que contenga:

1.1. Manual de usuario: cómo ejecutar el programa.

1.2. Descripción de la utilización de las estructuras de datos 
desarrolladas (por ejemplo
descripción del nodo de una lista).

1.3. Descripción detallada de los algoritmos desarrollados.

1.4. Problemas sin solución: En esta sección se detalla cualquier
problema que no se ha
podido solucionar en el trabajo.

1.5. Plan de Actividades realizadas por estudiante: Este es un
planeamiento de las actividades
que se realizaran para completar la tarea, este debe incluir 
descripción de la tarea,
tiempo estimado de completitud, responsable a cargo y fecha de entrega.

1.6. Problemas encontrados: descripción detallada, intentos de
solución sin éxito, soluciones
encontradas con su descripción detallada, recomendaciones, conclusiones
y bibliografía
consultada para este problema específico.

1.7. Conclusiones del proyecto.

1.8. Recomendaciones del proyecto.

1.9. Bibliografía consultada en todo el proyecto
Bitácora en digital, donde se describen las actividades realizadas,
desde reuniones con el
compañero de trabajo, investigaciones, consultas, etc. Está se puede
encontrar hecha a mano,
se debe describir todo por más insignificante que sea, esto demostrará
si ustedes están
trabajando en realidad. Este es su diario de trabajo
, llevan seguimiento de todo en el tiempo,
imaginen que si un compañero los releva en su trabajo, 
le bastaría con leer sus bitácoras para
seguir el trabajo.

