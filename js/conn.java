package Nico_Perez_Projecte_UF1;

import java.util.*;

/**
 *
 * @author nico
 */
public class Projecte_UF1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int N, M, fila, filaCalcular=0, jug, contadorFilCol, filaInici,columnaInici,filaFinal,columnaFinal, contador,numeroPartida=0;   
        String guions,jug1,jug2;
        boolean validat, ganador=false, empat=false, jugadorExistent=false;
        char seguentPartida='s';
        
        DadesJugadors[] jugador= new DadesJugadors[50]; //com a màxim emmagatzemaré 50 jugadors (que han guanyat).        
        DadesJugadors numAuxiliar;
        
        //En primero lloc, tots els jugadors tindrán el nom (*) i les seves partides guanyades serà 0.
        for (int i = 0; i < 50; i++) {
            jugador[i]=new DadesJugadors();
            jugador[i].nom="*";
            jugador[i].partidesGuanyades=0;
        }                                
        System.out.println("Connecta 4\n=========="); //Imprimeoixo el títol del joc
        do{                                                                            
            if (seguentPartida=='s') {  //si la següent partida es s, llavors començarà la partida (ho farà automàticament la primera vegada ja que la variable l'hem iniciat a 's'):
                
                ganador = false; empat=false; contador = 0; contadorFilCol=0;                               
                
                do {     //Demano el número de files i comprovo que aquest valor sigui un número enter i positiu.           
                    System.out.print("Introdueix el número de files que tindrà el taulell (mínim 4): ");
                    while (!in.hasNextInt()) {
                        System.out.print("Introdueix el número de files que tindrà el taulell (mínim 4): ");
                        in.next();
                    }
                    N = in.nextInt();
                    fila = N;  //com N anirà canviant (per decrementar-se), guardaré el seu valor inicial en aquesta variable.             
                    validat = N >= 4;
                } while (!validat);

                do {        //Demano el número de columnes i comprovo que aquest valor sigui un número enter i positiu.   
                    System.out.print("Introdueix el número de columnes que tindrà el taulell (mínim 4): ");
                    while (!in.hasNextInt()) {
                        System.out.print("Introdueix el número de columnes que tindrà el taulell (mínim 4): ");
                        in.next();
                    }
                    M = in.nextInt();
                    validat = M >= 4 ;
                } while (!validat);
                
                //Demano el nom del jugador 1 (jug1) i comprovo que no sigui un número (enter de forma individual) i trec els caràcters que no siguin lletres i els espais al principi i final de la paraula.                
                System.out.printf("Nom del jugador 'X': "); 
                while(in.hasNextInt()){
                    System.out.printf("Nom del jugador 'X': "); 
                    in.next();
                }jug1=in.next().replaceAll("\\W","").trim();
                
                //Demano el nom del jugador 2 (jug2) i comprovo que no sigui un número (enter de forma individual)  i trec els caràcters que no siguin lletres i els espais al principi i final de la paraula.                 
                System.out.printf("Nom del jugador 'O': "); 
                while(in.hasNextInt()){
                    System.out.printf("Nom del jugador 'O': "); 
                    in.next();
                }jug2=in.next().replaceAll("\\W","").trim();
                                
                String taulell[][] = new String[N][M];  // El taulell serà de N files i M columnes que haurà introduït l'usuari.
                guions = "-----";
                for (int k = 0; k < taulell.length; k++) {  //mostro la taula amb les seves característiques (espais, guions, "separadors"--> (|), i número per fila i per columna:
                    if (k==0) {
                        System.out.printf("Comença la partida %d!\n",numeroPartida+1);
                        for (int col = 1; col < M; col++) {
                        guions += "----";       //calculo la mida total dels guions depenen del número de columnes.
                        }
                    }
                    System.out.printf("%2s%s\n%2d", "", guions, N);  //mostro els guions, faig un salt en línia i mostro les files (que ocupen dos espais) i decremento aquest valor.
                    N--;
                    for (int col = 0; col < taulell[k].length; col++) {
                        taulell[k][col] = "";       //inicialment totes les caselles estaràn buides.
                        System.out.printf("|%3s", taulell[k][col]);  //mostro els separadors i deixo 3 espais
                        if (col == taulell[k].length - 1) {
                            System.out.print("|");  //si estem a la última columna, llavors mostraré els últims separadors.
                        }
                    }System.out.println();
                    if (k==taulell.length-1) {
                        for (int col = 1; col <= M; col++) {
                        if (col==1) {
                            System.out.printf("%2s%s\n%2s", "", guions, " ");   //mostro els guions finals
                        }
                        System.out.printf("%3d%s", col, " "); //mostro el número de columnes de forma incrementada.
                        }System.out.println();
                    }
                }                

                do {
                    do {     //Demano al jugador (1) la columna on es vol col·locar la "fitxa" i comprovo que aquest valor sigui un número enter i positiu:           
                        System.out.printf("Jugador 'X' (%s), introdueix el teu moviment (columna [1-%d]): ",jug1, M);
                        while (!in.hasNextInt()) {
                            System.out.printf("Jugador 'X' (%s), introdueix el teu moviment (columna [1-%d]): ", jug1,M);
                            in.next();
                        }
                        jug = in.nextInt();
                        if (jug>0  && jug<=M) {     //ara valido que aquest número sigui major que 0 y menor o igual al nombre del columnes totals
                            validat=true;
                            if (!taulell[0][jug-1].equals("")) {    //si la casella de més a dalt (la fila 0 amb la columna indicada) no està buida, llavors mostro per pantalla el següent missatge i torno a demanar la columna:
                               System.out.println("Aquesta columna està plena!"); 
                               validat=false;
                            }                    
                        }
                        else{
                            validat=false;
                        }                
                    } while (!validat);
                    N = fila;

                    //Recorrerà la columna introduïda d'avall cap amunt i si la casella està buida, llavors la pintarà, sinó, seguirà "pujant" fins a la fila 0 (la de més a sobre).
                    for (int fil = taulell.length - 1; fil >= 0; fil--) {
                        if (taulell[fil][jug-1].equals("")) {
                            taulell[fil][jug-1] = "X"; filaCalcular=fil; 
                            contador++;
                            break;              //si la casella està "buida" llavors es "pintarà" amb la fitxa i pararem el bucle.
                        }
                    }

                    for (int fil = 0; fil < taulell.length; fil++) {
                        System.out.printf("%2s%s\n%2d", "", guions, N);N--;                
                        for (int col = 0; col < taulell[fil].length; col++) {
                            System.out.printf("|%2s%1s", taulell[fil][col], "");  //es tornarà a pintar el taulell però aquesta vegada amb la fitxa col·locada
                            if (col == taulell[fil].length - 1) {
                                System.out.print("|");
                            }
                        }System.out.println();                
                        if (fil==taulell.length-1) {
                            System.out.printf("%2s%s\n%2s", "", guions, " ");
                            for (int col = 1; col <= M; col++) {
                                System.out.printf("%3d%s", col, " ");
                            }
                            System.out.println();
                        }                                
                    }

                    //bucle per calcular si guanya la X de forma vertical.
                    contadorFilCol=0;                                           
                    for (int fil = taulell.length - 1; fil >= 0; fil--) {
                        if (taulell[fil][jug-1].equals("X")) {            //si en una columna hi ha una X, llavors sumem 1 al contador
                            contadorFilCol++;    
                            if (contadorFilCol==4) {                            //si el contador hi arriba a 4 (X seguides), llavors parem els bucles i mostrem el següent missatge:
                            System.out.printf("Ha guanyat el %s (jugador 'X').\n",jug1);
                            ganador=true;   
                            break;
                            }
                        }
                        if (taulell[fil][jug-1].equals("O")) {        //si en una columna hi troba una O, llavors el contador es reinicia
                            contadorFilCol=0;
                        }
                    }contadorFilCol=0;

                    //bucle per calcular si guanya la X de forma horitzontal.
                    contadorFilCol=0; 
                    for (int col = 0; col < M; col++) {                
                        if (taulell[filaCalcular][col].equals("X")) {        
                            contadorFilCol++;                               //si en una fila hi ha una X, llavors sumem 1 al contador 
                            if (contadorFilCol==4) {                        //si el contador hi arriba a 4 (X seguides), llavors parem els bucles.
                            System.out.printf("Ha guanyat el %s (jugador 'X').\n",jug1);
                            ganador=true;   
                            break;
                            }  
                        }                                                   //si en una fila hi troba una O o està buida, llavors el contador es reinicia
                        else if (taulell[filaCalcular][col].equals("O")|| taulell[filaCalcular][col].equals("")  ) {        
                            contadorFilCol=0;
                        }               
                    }contadorFilCol=0;
                    
                    //bucle per calcular si guanya la X de forma diagonal (en aquest sentit) --> \.                    
                    
                    filaInici = filaCalcular;  columnaInici = jug-1;    filaFinal =filaCalcular;  columnaFinal = jug-1; 
                                        
                    while(filaInici!=0  &&  columnaInici!=0 ){ //calculem la casella inicial per recorre la diagonal
                        filaInici--; columnaInici--;
                    }                                                                                                                     
                    while(filaFinal!=fila-1  &&  columnaFinal!=M-1 ){ //calculem la casella final per recorre la diagonal
                        filaFinal++; columnaFinal++;
                    }
                    
                    while(filaInici <=filaFinal&& columnaInici<=columnaFinal){   //recorrem desde la casella inicial a la final i comprovem:
                        if (taulell[filaInici][columnaInici].equals("X")) {        
                            contadorFilCol++;                               //si en la diagonal hi ha una X, llavors sumem 1 al contador 
                            if (contadorFilCol==4) {                        //si el contador hi arriba a 4 (X seguides), llavors parem els bucles.
                                System.out.printf("Ha guanyat el %s (jugador 'X').\n",jug1);
                                ganador=true;   
                                break;
                            }  
                        }                                                   //si en la diagonal hi troba una O o està buida, llavors el contador es reinicia
                        else if (taulell[filaInici][columnaInici].equals("O")|| taulell[filaInici][columnaInici].equals("")  ) {        
                            contadorFilCol=0;
                        }filaInici++; columnaInici++;                                                                                                                 
                    }contadorFilCol=0;                                                                                
                    
                    //bucle per calcular si guanya la X de forma diagonal (en aquest sentit) --> /. 
                    filaInici = filaCalcular;  columnaInici = jug-1;    filaFinal=filaCalcular;  columnaFinal = jug-1; 
                                        
                    while(filaInici!=fila-1  &&  columnaInici!=0 ){     //calculo la casella inicial de la diagonal per posteriorment recorre-la
                        filaInici++; columnaInici--;
                    }                                                                                                         
                    while(filaFinal!=0  &&  columnaFinal!=M-1 ){        //calculo la casella final de la diagonal per posteriorment recorre-la
                        filaFinal--; columnaFinal++;
                    }
                    
                    while(filaInici >=  filaFinal&& columnaInici<=columnaFinal){        //recorro la diagonal desde la casella inicial a la final
                        if (taulell[filaInici][columnaInici].equals("X")) {        
                            contadorFilCol++;                               //si en la diagonal hi ha una X, llavors sumem 1 al contador 
                            if (contadorFilCol==4) {                        //si el contador hi arriba a 4 (X seguides), llavors parem els bucles.
                                System.out.printf("Ha guanyat el %s (jugador 'X').\n",jug1);
                                ganador=true;  
                                break;
                            }  
                        }                                                   //si en la diagonal hi troba una O o està buida, llavors el contador es reinicia
                        else if (taulell[filaInici][columnaInici].equals("O")|| taulell[filaInici][columnaInici].equals("")  ) {        
                            contadorFilCol=0;
                        }filaInici--; columnaInici++;                                                                                                                 
                    } contadorFilCol=0; 
                    //si no ha guanyat un dels dos jugadors però hi ha un empat, llavors la partida s'ha acabat.
                    if (!ganador && contador==fila*M) {     //si el contador (que s'incrementa al col·locar una fitxa) arriba al número total de caselles llavors hi ha empat:
                        empat=true;
                        System.out.println("Empat (partida nula).");
                    }                                        
                    //si ganador es true significa que ha guanyat el jugador 1 amb el nom de la variable jug1.
                                                            
                    //comprovar si aquest nom ja ha existit recorrent de 0 al número de partida actual:
                    jugadorExistent=false;
                    if (ganador) {  
                        for (int i = 0; i <= numeroPartida; i++) {
                            if (jug1.equals(jugador[i].nom)) {  // si aquest nom ja ha existit en una partida anterior, llavors asignarem aquesta victoria al jugador existent.
                                jugador[i].partidesGuanyades++; 
                                jugadorExistent=true;
                            }                            
                        }                                               // si no ha existit cap jugador amb el nom del guanyador actual, crearem un nou jugador a la posició de la partida actual:  
                        if (!jugadorExistent) {
                            jugador[numeroPartida]= new DadesJugadors();    
                            jugador[numeroPartida].nom=jug1;
                            jugador[numeroPartida].partidesGuanyades++;                            
                        }
                    }                                                                                                    
                    
                    if (!ganador &&!empat) {     //si encara el jugador 1 no ha guanyat i no ha hagut empat, pasarà el torn al jugador 2:                                                                          
                        do {     //Demano al jugador (2) la columna on es vol col·locar la "fitxa" i comprovo que aquest valor sigui un número enter i positiu:           
                            System.out.printf("Jugador 'O' (%s), introdueix el teu moviment (columna [1-%d]): ",jug2, M);
                            while (!in.hasNextInt()) {
                                System.out.printf("Jugador 'O' (%s), introdueix el teu moviment (columna [1-%d]): ", jug2,M);
                                in.next();
                            }
                            jug = in.nextInt();
                            if (jug>0  && jug<=M) {
                                validat=true;
                                if (!taulell[0][jug-1].equals("")) {
                                   System.out.println("Aquesta columna està plena!"); 
                                   validat=false;
                                }                    
                            }
                            else{
                                validat=false;
                            } 
                        } while (!validat);
                        N = fila;
                        for (int fil = taulell.length - 1; fil >= 0; fil--) {
                            if (taulell[fil][jug-1].equals("")) {
                                taulell[fil][jug-1] = "O"; filaCalcular=fil; 
                                contador++;
                                break;              //si la casella està "buida" llavors es "pintarà" amb la fitxa i pararem el bucle.
                            }
                        }
                        for (int fil = 0; fil < taulell.length; fil++) {
                            System.out.printf("%2s%s\n%2d", "", guions, N);
                            N--;
                            for (int col = 0; col < taulell[fil].length; col++) {
                                System.out.printf("|%2s%1s", taulell[fil][col], "");
                                if (col == taulell[fil].length - 1) {
                                    System.out.print("|");
                                }
                            }
                            System.out.println();
                            if (fil==taulell.length-1) {
                                System.out.printf("%2s%s\n%2s", "", guions, " ");
                                for (int l = 1; l <= M; l++) {
                                    System.out.printf("%3d%s", l, " ");
                                }
                                System.out.println();
                            }
                        }                                    
                        //bucle per calcular si guanya la O de forma vertical.                                               
                        for (int fil = taulell.length - 1; fil >= 0; fil--) {  
                            if (taulell[fil][jug-1].equals("O")) {     //si en la columna indicada hi ha una O, llavors sumem 1 al contador
                                contadorFilCol++;    
                                if (contadorFilCol==4) {                                //si el contador hi arriba a 4 (O seguides), llavors parem els bucles.
                                System.out.printf("Ha guanyat el %s (jugador 'O').\n",jug2);
                                ganador=true;  
                                break;
                                }
                            }
                            else if (taulell[fil][jug-1].equals("X")  ) {   //si en una casella hi troba una X, llavors el contador es reinicia
                                contadorFilCol=0;                                    
                            }                
                        }contadorFilCol=0;

                        //bucle per calcular si guanya la O de forma horitzontal.  
                        for (int col = 0; col < M; col++) {   
                            if (taulell[filaCalcular][col].equals("O")) {        
                                contadorFilCol++;                               //si en una fila hi ha una O, llavors sumem 1 al contador 
                                if (contadorFilCol==4) {                        //si el contador hi arriba a 4 (O seguides), llavors parem els bucles.
                                System.out.printf("Ha guanyat el %s (jugador 'O').\n",jug2);
                                ganador=true; 
                                break;
                                }  
                            }                                                   //si en una fila hi troba una X o està buida, llavors el contador es reinicia
                            else if (taulell[filaCalcular][col].equals("X")|| taulell[filaCalcular][col].equals("")  ) {        
                                contadorFilCol=0;
                            }               
                        } contadorFilCol=0;              
                        
                        //bucle per calcular si guanya la O de forma diagonal (en aquest sentit) --> \.
                        filaInici = filaCalcular;  columnaInici = jug-1;    filaFinal =filaCalcular;  columnaFinal = jug-1;                         
                        while(filaInici!=0  &&  columnaInici!=0 ){
                            filaInici--; columnaInici--;
                        }                                                                                                                     
                        while(filaFinal!=fila-1  &&  columnaFinal!=M-1 ){
                            filaFinal++; columnaFinal++;
                        }
                        while(filaInici <=filaFinal && columnaInici<=columnaFinal){
                            if (taulell[filaInici][columnaInici].equals("O")) {        
                                contadorFilCol++;                               //si en la diagonal hi ha una O, llavors sumem 1 al contador 
                                if (contadorFilCol==4) {                        //si el contador hi arriba a 4 (O seguides), llavors parem els bucles.
                                    System.out.printf("Ha guanyat el %s (jugador 'O').\n",jug2);
                                    ganador=true;   
                                    break;
                                }  
                            }                                                   //si en la diagonal hi troba una X o està buida, llavors el contador es reinicia
                            else if (taulell[filaInici][columnaInici].equals("X")|| taulell[filaInici][columnaInici].equals("")  ) {        
                                contadorFilCol=0;
                            }filaInici++; columnaInici++;                                                                                                                 
                        } contadorFilCol=0;           
                        
                        //bucle per calcular si guanya la O de forma diagonal (en aquest sentit) --> /.
                        filaInici = filaCalcular;  columnaInici = jug-1;    filaFinal = filaCalcular;  columnaFinal = jug-1;                         
                        while(filaInici!=fila-1  &&  columnaInici!=0 ){
                            filaInici++; columnaInici--;
                        }                                                                                                         
                        while(filaFinal!=0  &&  columnaFinal!=M-1 ){
                            filaFinal--; columnaFinal++;
                        }
                        while(filaInici >=  filaFinal && columnaInici<=columnaFinal){
                            if (taulell[filaInici][columnaInici].equals("O")) {        
                                contadorFilCol++;                               //si en la diagonal hi ha una O, llavors sumem 1 al contador 
                                if (contadorFilCol==4) {                        //si el contador hi arriba a 4 (O seguides), llavors parem els bucles.
                                    System.out.printf("Ha guanyat el %s (jugador 'O').\n",jug2);
                                    ganador=true;   
                                    break;
                                }  
                            }                                                   //si en la diagonal hi troba una X o està buida, llavors el contador es reinicia
                            else if (taulell[filaInici][columnaInici].equals("X")|| taulell[filaInici][columnaInici].equals("")  ) {        
                                contadorFilCol=0;
                            }filaInici--; columnaInici++;                                                                                                                 
                        }  contadorFilCol=0; 
                        jugadorExistent=false;
                        if (ganador) {              //si ha guanyat el jugador 2, utilitzarem el mateix mètode que al jugador 1 (línia 232):
                            for (int i = 0; i <= numeroPartida; i++) {
                                if (jug2.equals(jugador[i].nom)) {
                                    jugador[i].partidesGuanyades++; 
                                    jugadorExistent=true;
                                }                            
                            }     
                            if (!jugadorExistent) {
                                jugador[numeroPartida]= new DadesJugadors();
                                jugador[numeroPartida].nom=jug2;
                                jugador[numeroPartida].partidesGuanyades++;                            
                            }
                        }
                    }                                                                                                                                                                
                    if (!ganador && contador==fila*M) {
                        empat=true;
                        System.out.println("Empat (partida nula).");
                    }
                } while (!ganador&&!empat);              
            } numeroPartida++;     //quan s'acaba la partida: incremento 1 al contador del número de la partida.
            do {                
                System.out.print("Vols tornar a jugar (s/n)? ");        //pregunto si es vol tornar a jugar i comprovo que el caràcter introduït és una lletra
                while (!in.hasNext()) {
                    System.out.print("Vols tornar a jugar (s/n)? ");
                    in.next();
                }
                seguentPartida = in.next().charAt(0);  
                seguentPartida = Character.toLowerCase(seguentPartida);  //en el cas que sigui en majúscules la lletra, la converteixo en minúscules per comparar-la:
                validat = seguentPartida == 's'|| seguentPartida == 'n';
            } while (!validat);                                               //tornaré a demanar la lletra en el cas que aquesta no sigui ni una 's' ni una 'n'.                                                             
        }while(seguentPartida=='s');     
        
        System.out.println("Podium:\n======");        
        //ordeno els resultat descendentment amb el mètode de la bombolla:        
        numAuxiliar = new DadesJugadors();          //creo una variable auxiliar per posteriorment utilitzar-la per ordenar els resultats:
        for (int i = 0; i < numeroPartida; i++) {   //aquest bucle farà tantes voltes com número de partides hi hagi
            for (int j = 0; j < jugador.length-1; j++) {  
                if (jugador[j].partidesGuanyades < jugador[j+1].partidesGuanyades) {  
                    numAuxiliar = jugador[j]; 
                    jugador[j]=jugador[j+1];   
                    jugador[j+1]=numAuxiliar; 
                }
            }
        }                         
        //mostro els 5 guanyadors amb les partides que ha guanyat cadascú:
        for (int i = 0; i < 5; i++) {
            if (!jugador[i].nom.equals("*")) {
                System.out.printf("%d. %s: %d\n",i+1,jugador[i].nom, jugador[i].partidesGuanyades);
            }            
        }               
    }
}