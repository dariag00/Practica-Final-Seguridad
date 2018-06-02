import java.math.BigInteger;
import java.util.ArrayList;

public class main {

    public static void main (String[] Args) {
        BigInteger a = new BigInteger("410338673");
        BigInteger b = new BigInteger("6956072540376423616884");

        BigInteger res = new BigInteger("0");

        res = euclidesExtendido(a, b);

        System.out.println(res);

        BigInteger res1 = new BigInteger("0");
        res1 = res.mod(b);
        System.out.println("Inverso: " + res1);

        rsaBloques(new BigInteger("5244938048376303456108649"), new BigInteger("114340249"), "qÉÑusynXúp3h7ké2 M.S¿NYiá:c0ÍhS39X.8Y0¿tbÚñÍWDaJ8 x.", 84, new BigInteger("2290182972661"), new BigInteger("2290182972709"));

    }
    static String alf2 = "abcdefghijklmnñopqrstuvwxyz";

    public static BigInteger euclidesExtendido(BigInteger a, BigInteger b){

        BigInteger[] resp = new BigInteger[3];

        BigInteger x = new BigInteger("0"), y = new BigInteger("0"), d = new BigInteger("0");

        if(b.equals(BigInteger.ZERO)){

            resp[0] = a; resp[1] = new BigInteger("1"); resp[2] = new BigInteger("0");

        }else{

            BigInteger x2 = new BigInteger("1"), x1 = new BigInteger("0"), y2 = new BigInteger("0"), y1 = new BigInteger("1");

            BigInteger q = new BigInteger("0"), r = new BigInteger("0");

            while(b.compareTo(BigInteger.ZERO)>0){

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
    public static BigInteger RSA(BigInteger n, BigInteger e, BigInteger mensaje, BigInteger primo1, BigInteger primo2){

        //multiplos de los numeros
        BigInteger phi = phi(primo1, primo2);
        System.out.println("phi : " + phi);

        BigInteger d = euclidesExtendido(e, phi);
        d = d.mod(phi);
        System.out.println("d: " + d);
        System.out.println(d.multiply(e).mod(phi));

        return apm(mensaje, d, n);

    }

    public static String RSACifrado(BigInteger n, BigInteger e, BigInteger mensaje){

        String nd = String.valueOf(apm(mensaje, e, n));

        return nd;
    }



    public static BigInteger gcd(BigInteger a, BigInteger b) {
        if (a.compareTo(new BigInteger("0")) == 0)
            return b;
        return gcd(b.mod(a), a);
    }


    public static BigInteger phi(BigInteger a, BigInteger b){

        a = a.subtract(BigInteger.ONE);
        b = b.subtract(BigInteger.ONE);
        return a.multiply(b);

    }

    public static ArrayList<BigInteger> cambioDeBase84(BigInteger b){

        ArrayList<BigInteger> intss = new ArrayList<>();
        BigInteger coc = new BigInteger("84");
        while(b.compareTo(new BigInteger("84")) == 0 || b.compareTo(new BigInteger("84")) == 1){
            coc =  b.divide(new BigInteger("84"));
            BigInteger mod = b.mod(new BigInteger("84"));
            intss.add(mod);
            b = coc;
            System.out.println("xd" + coc);
        }

        if(coc.compareTo(BigInteger.ZERO) == 1){
            intss.add(coc);
        }

        return intss;
    }
    public static String alfabeto = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPKRSTUVWXYZáéíóúÁÉÍÓÚ0123456789 ,.:;-¿?()";
    public static int[] CambioDeBase27(BigInteger b) {
        String alfabeto = "0123456789abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPKRSTUVWXYZáéíóúÁÉÍÓÚ ,.:;-¿?()";
        String result = "";
        result = b.toString(84);
        int letraValue[] = new int[result.length()];

        for(int i = 0; i < result.length(); i++){
            char letra = result.charAt(i);
            letraValue[i] = letraToNum(letra, alfabeto);
        }

        return letraValue;
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

    public static String rsaBloques(BigInteger n, BigInteger e, String m, int z, BigInteger primo1, BigInteger primo2){

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
        for(String palabro : palabros){
            String total = "";
            BigInteger num = new BigInteger("0");
            int multiplo = k-1;

            for(int i = 0; i<palabro.length(); i++){
                total = total + String.valueOf(letraToNum(palabro.charAt(i), alfabeto));
                System.out.println(letraToNum(palabro.charAt(i), alfabeto) + " " +  Math.pow(84,multiplo));
                BigInteger letra = new BigInteger(String.valueOf(letraToNum(palabro.charAt(i), alfabeto)));
                BigInteger zz = new BigInteger("84");
                BigInteger mult = zz.pow(multiplo);
                num = letra.multiply(mult).add(num);
                multiplo = multiplo - 1;
            }
            System.out.println("num: " + num);
            BigInteger codif = RSA(n, e, num, primo1, primo2);
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
                String s = "" + numToLetra(cocientes.get(i).intValue(), alfabeto);
                resultado.add(s);
            }

            System.out.println(resultado);

        }

        ArrayList<BigInteger> numeross =  cambioDeBase84(new BigInteger("9100"));
        System.out.println("xd " +numeross.toString());
        return "xd";
    }




    public static char numToLetra(int num, String alf){

        for(int i = 0; i<alf.length(); i++){
            if(num == i)
                return alf.charAt(i);
        }
        return 'x';
    }

}
