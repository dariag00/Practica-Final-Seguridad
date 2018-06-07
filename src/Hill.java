import java.util.ArrayList;


public class Hill {

    int claveCifrado[][];
    int claveDescifrado[][];
    String alfabeto;



    public Hill(String alfabeto) {
        this.claveCifrado = claveCifrado;
        this.alfabeto = alfabeto;
        this.claveDescifrado = claveDescifrado;
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
        char[][] resultALetra = new char[2][2];
        // Separamos el mensaje en 4
        int contador = 0;
        int determinante = 0;
       /* int[][] adjunta = new int[2][2];
        adjunta[0][0] = this.claveCifrado[1][1];
        adjunta[1][1] = this.claveCifrado[0][0];
        adjunta[0][1] = -this.claveCifrado[0][1];
        adjunta[1][0] = -this.claveCifrado[1][0];
        */ArrayList<String> palabros = new ArrayList<>();
        int inverso = 0;

        for(int i = 0; i<mensaje.length(); i++) {
            contador++;
            if (contador == 4) {
                palabros.add(mensaje.substring(i - 4 + 1, i + 1));
                contador = 0;
            }
        }
        if(contador != 0) {
            String resto = mensaje.substring(mensaje.length() - contador, mensaje.length());

            System.out.println(resto + " Antes");


            for (int i = resto.length(); i < 4; i++) {

                resto = resto + this.alfabeto.charAt(0);

            }

            palabros.add(resto);


            System.out.println(resto + " Después");
        }

        for(String palabro : palabros){
                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < 2; j++) {

                        cifrar[k][j] = letraToNum(palabro.charAt(contador1), this.alfabeto);
                        contador1++;

                    }

            }
            contador1 = 0;

            if(cifrado) {
                resultadoArrays = multiply(cifrar, this.claveCifrado);
                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < 2; j++) {
                        resultadoArrays[k][j] = (resultadoArrays[k][j])%this.alfabeto.length();
                        resultALetra[k][j] = numToLetra((resultadoArrays[k][j]), this.alfabeto);
                    }
                }
            }else {
               /* determinante = determinant(this.claveCifrado, 2);

                inverso = euclidesExtendido(determinante, 27);
                System.out.println("Determinante " + determinante);
                */System.out.println("Inverso " + inverso);

                resultadoArrays = multiply(cifrar, this.claveDescifrado);

                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < 2; j++) {
                       // resultadoArrays[k][j] = (resultadoArrays[k][j] * inverso) % this.alfabeto.length();
                        resultALetra[k][j] = numToLetra((resultadoArrays[k][j]), this.alfabeto);
                    }
                }
            }

            for(int k = 0; k < 2; k++ ){
                for(int j = 0; j < 2; j++) {

                    resultado = resultado + resultALetra[k][j];

                }

            }
        }
        System.out.println("Resultado "+resultado);
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


    public int determinant(int A[][],int N)
    {
        int det=0;
        if(N == 1)
        {
            det = A[0][0];
        }
        else if (N == 2)
        {
            det = A[0][0]*A[1][1] - A[1][0]*A[0][1];
        }
        else
        {
            det=0;
            for(int j1=0;j1<N;j1++)
            {
                int[][] m = new int[N-1][];
                for(int k=0;k<(N-1);k++)
                {
                    m[k] = new int[N-1];
                }
                for(int i=1;i<N;i++)
                {
                    int j2=0;
                    for(int j=0;j<N;j++)
                    {
                        if(j == j1)
                            continue;
                        m[i-1][j2] = A[i][j];
                        j2++;
                    }
                }
                det += Math.pow(-1.0,1.0+j1+1.0)* A[0][j1] * determinant(m,N-1);
            }
        }
        return det;
    }

    public int euclidesExtendido(int a, int b)
    {
        int[] resp = new int[3];
        int x=0,y=0,d=0;
        if(b==0)
        {
            resp[0] = a; resp[1] = 1; resp[2] = 0;
        }
        else
        {
            int x2 = 1, x1 = 0, y2 = 0, y1 = 1;
            int q = 0, r = 0;
            while(b>0)
            {
                q = (a/b);
                r = a - q*b;
                x = x2-q*x1;
                y = y2 - q*y1;
                a = b;
                b = r;
                x2 = x1;
                x1 = x;
                y2 = y1;
                y1 = y;
            }
            resp[0] = a;
            resp[1] = x2;
            resp[2] = y2;
        }
        return resp[1];
    }
}
