import java.math.BigInteger;
import java.util.ArrayList;


public class Hill {

    int claveCifrado[][];
    int claveDescifrado[][];
    String alfabeto;


    public Hill(String alfabeto) {
        this.claveCifrado = claveCifrado;
        this.claveDescifrado = claveDescifrado;
        this.alfabeto = alfabeto;
    }

    public int[][] getClaveCifrado() {
        return claveCifrado;
    }

    public void setClaveCifrado(int[][] claveCifrado) {
        this.claveCifrado = claveCifrado;
    }

    public int[][] getClaveDescifrado() {
        return claveDescifrado;
    }

    public void setClaveDescifrado(int[][] claveDescifrado) {
        this.claveDescifrado = claveDescifrado;
    }

    public String getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(String alfabeto) {
        this.alfabeto = alfabeto;
    }

    public String cifrar(String mensaje, boolean cifrado){

        String resultado = "";
        int contador1 = 0;
        int [][] cifrar = new int[2][2];
        int[][] resultadoArrays = new int[2][2];
        // Separamos el mensaje en 4
        int contador = 0;

        for(int i = 0; i<mensaje.length(); i++){
            contador++;
            if(contador == 2){
                for(int k = 0; k < 2; k++ ){
                    for(int j = 0; j < 2; j++){

                        cifrar[i][j] = letraToNum(mensaje.charAt(contador1), this.alfabeto);
                        contador1++;

                    }

                }
                contador = 0;
            }

            if(cifrado)
                resultadoArrays = multiply(cifrar, this.claveCifrado);
            else
                resultadoArrays = multiply(cifrar, this.claveDescifrado);

            for(int k = 0; k < 2; k++ ){
                for(int j = 0; j < 2; j++) {

                    resultado = resultado + resultadoArrays[k][j];

                }

            }
        }
        return resultado;
    }
    public static int letraToNum(char letra, String alfabeto){

        String res = "";
        for(int j = 0; j<alfabeto.length(); j++) {
            if (letra == alfabeto.charAt(j)) {
                res = res + j;
            }
        }

        return Integer.valueOf(res);
    }

    public static char numToLetra(int num, String alf){

        for(int i = 0; i<alf.length(); i++){
            if(num == i)
                return alf.charAt(i);
        }
        return 'x';
    }

    public static int[][] multiply(int[][] a, int[][] b) {
        int[][] c = new int[a.length][b[0].length];
        // se comprueba si las matrices se pueden multiplicar
        if (a[0].length == b.length) {
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < b[0].length; j++) {
                    for (int k = 0; k < a[0].length; k++) {
                        // aquí se multiplica la matriz
                        c[i][j] += a[i][k] * b[k][j];
                    }
                }
            }
        }
        /**
         * si no se cumple la condición se retorna una matriz vacía
         */
        return c;
    }
   }




