import java.math.BigInteger;
import java.util.ArrayList;

public class RSA {


    private static BigInteger e;
    private static BigInteger n;
    private static BigInteger d;
    private static String alfabeto;
    private static int z;

    public RSA(BigInteger e, BigInteger n, String alfabeto) {
        this.e = e;
        this.n = n;
        this.alfabeto = alfabeto;
        this.z = alfabeto.length();
    }


    public void setE(BigInteger e) {
        this.e = e;
    }

    public BigInteger getE() {
        return e;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getN(BigInteger n) {
        return n;
    }

    public static String descifradoRSABloques(String m, BigInteger primo1, BigInteger primo2){

        //Hallar K
        boolean hallado = false;
        int k = 0;
        int contador = 0;
        while(hallado == false){
            int pot = z;
            double menorD = Math.pow(z,contador);
            BigInteger pow = new BigInteger("84");
            BigInteger menor = pow.pow(contador);
            BigInteger mayor = pow.pow(contador+1);

            if(menor.compareTo(n) == -1 || menor.compareTo(n) == 0){
                if(mayor.compareTo(n) == 1){
                    hallado = true;
                    k = contador + 1;
                }
            }

            contador++ ;
        }
        System.out.println("k: " + k);
        //Dividimos en k

        ArrayList<String> palabros = new ArrayList<>();
        contador = 0;
        for(int i = 0; i<m.length(); i++){
            contador++;
            if(contador == k){
                palabros.add(m.substring(i-k+1,i+1));
                contador = 0;
            }
        }

        System.out.println(palabros.toString());
        //Calculo de la clave de descifrado
        BigInteger phi = phi(primo1, primo2);
        System.out.println("phi: " + phi);

        BigInteger d = euclidesExtendido(e, phi);
        d = d.mod(phi);

        //
        ArrayList<String> resultado =  new ArrayList<>();
        String finalResult = "";
        for(String palabro : palabros){
            String total = "";
            BigInteger num = new BigInteger("0");
            int multiplo = k-1;

            for(int i = 0; i<palabro.length(); i++){
                total = total + String.valueOf(letraToNum(palabro.charAt(i)));
                System.out.println(letraToNum(palabro.charAt(i)) + " " +  Math.pow(84,multiplo));
                BigInteger letra = new BigInteger(String.valueOf(letraToNum(palabro.charAt(i))));
                BigInteger zz = new BigInteger("84");
                BigInteger mult = zz.pow(multiplo);
                num = letra.multiply(mult).add(num);
                multiplo = multiplo - 1;
            }
            System.out.println("num: " + num);
            BigInteger codif = descifradoRSASimple(num, primo1, primo2);
            System.out.println("codif: " + codif);

            BigInteger cociente = codif;
            ArrayList<BigInteger> cocientes = new ArrayList<>();
            System.out.println("----");
            while(cociente.compareTo(new BigInteger("84")) == 1){
                BigInteger resto = cociente.mod(new BigInteger("84"));
                System.out.println("resto: " + resto);
                cociente = cociente.divide(new BigInteger("84"));
                cocientes.add(resto);
            }
            cocientes.add(cociente);

            for(int i = cocientes.size()-1; i>=0; i--){
                String s = "" + numToLetra(cocientes.get(i).intValue());
                resultado.add(s);
                finalResult = finalResult + s;
            }

            System.out.println(resultado);

        }
        return finalResult;
    }

    public String cifradoRSABloques(String m) {

        //Hallar K
        boolean hallado = false;
        int k = 0;
        int contador = 0;
        while (hallado == false) {
            int pot = z;
            double menorD = Math.pow(z, contador);
            BigInteger pow = new BigInteger("84");
            BigInteger menor = pow.pow(contador);
            BigInteger mayor = pow.pow(contador + 1);

            if (menor.compareTo(n) == -1 || menor.compareTo(n) == 0) {
                if (mayor.compareTo(n) == 1) {
                    hallado = true;
                    k = contador;
                }
            }

            contador++;
        }
        System.out.println("k: " + k);
        //Dividimos en k

        ArrayList<String> palabros = new ArrayList<>();
        contador = 0;
        for (int i = 0; i < m.length(); i++) {
            contador++;
            if (contador == k) {
                palabros.add(m.substring(i - k + 1, i + 1));
                contador = 0;
            }
        }
        String finalResult = "";
        for (String palabro : palabros) {
            String total = "";
            BigInteger num = new BigInteger("0");
            int multiplo = k - 1;

            for (int i = 0; i < palabro.length(); i++) {
                total = total + String.valueOf(letraToNum(palabro.charAt(i)));
                System.out.println(letraToNum(palabro.charAt(i)) + " " + Math.pow(z, multiplo));
                BigInteger letra = new BigInteger(String.valueOf(letraToNum(palabro.charAt(i))));
                BigInteger zz = new BigInteger(String.valueOf(z));
                BigInteger mult = zz.pow(multiplo);
                num = letra.multiply(mult).add(num);
                multiplo = multiplo - 1;
            }

            BigInteger codificacion = cifradoRSASimple(num);

            BigInteger cociente = codificacion;
            ArrayList<BigInteger> cocientes = new ArrayList<>();

            while (cociente.compareTo(new BigInteger("84")) == 1) {
                BigInteger resto = cociente.mod(new BigInteger("84"));
                System.out.println("resto: " + resto);
                cociente = cociente.divide(new BigInteger("84"));
                cocientes.add(resto);
            }
            cocientes.add(cociente);

            for (int i = cocientes.size() - 1; i >= 0; i--) {
                String s = "" + numToLetra(cocientes.get(i).intValue());
                finalResult = finalResult + s;
            }
        }
        return finalResult;
    }



    public static BigInteger apm(BigInteger a, BigInteger n, BigInteger m){
        BigInteger exp = new BigInteger("1");
        BigInteger x= a;
        while (n.compareTo(BigInteger.ZERO) > 0) {
            if(n.mod(new BigInteger("2")).compareTo(new BigInteger("0")) != 0){
                exp =(exp.multiply(x)).mod(m);
            }
            x=(x.multiply(x)).mod(m);
            n=n.divide(new BigInteger("2"));
        }
        return exp;
    }

    public static BigInteger phi(BigInteger a, BigInteger b){

        a = a.subtract(BigInteger.ONE);
        b = b.subtract(BigInteger.ONE);
        return a.multiply(b);
    }


    public static BigInteger euclidesExtendido(BigInteger a, BigInteger b) {

        BigInteger[] resp = new BigInteger[3];

        BigInteger x = new BigInteger("0"), y = new BigInteger("0"), d = new BigInteger("0");

        if (b.equals(BigInteger.ZERO)) {

            resp[0] = a;
            resp[1] = new BigInteger("1");
            resp[2] = new BigInteger("0");

        } else {

            BigInteger x2 = new BigInteger("1"), x1 = new BigInteger("0"), y2 = new BigInteger("0"), y1 = new BigInteger("1");

            BigInteger q = new BigInteger("0"), r = new BigInteger("0");

            while (b.compareTo(BigInteger.ZERO) > 0) {

                q = (a.divide(b));

                r = a.subtract(q.multiply(b));

                x = x2.subtract(q.multiply(x1));

                y = y2.subtract(q.multiply(y1));

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

    public static int letraToNum(char letra){

        String res = "";
        for(int j = 0; j<alfabeto.length(); j++) {
            if (letra == alfabeto.charAt(j)) {
                res = res + j;
            }
        }

        return Integer.valueOf(res);
    }

    public static BigInteger cifradoRSASimple(BigInteger mensaje){
        return apm(mensaje, e, n);
    }

    public static BigInteger descifradoRSASimple(BigInteger mensaje, BigInteger primo1, BigInteger primo2){

        //multiplos de los numeros
        BigInteger phi = phi(primo1, primo2);
        System.out.println("phi : " + phi);

        BigInteger d = euclidesExtendido(e, phi);
        d = d.mod(phi);
        System.out.println("d: " + d);
        System.out.println(d.multiply(e).mod(phi));

        return apm(mensaje, d, n);

    }



    public static char numToLetra(int num){

        for(int i = 0; i<alfabeto.length(); i++){
            if(num == i)
                return alfabeto.charAt(i);
        }
        return 'x';
    }
}
