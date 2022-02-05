/*
    HECHO POR:
    SANDOVAL MILLAN
    TEPEZANO COTA
    ARENAS CEBREROS
* */
import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
public class pishing {
    public static void main(String[] args) throws IOException {
        //String target_dir = "Nombre_Directorio";
        File dir = new File(args[0]);
        File[] files = dir.listFiles();
        //COnjunto de palabras clave

        Hashtable contenedor = new Hashtable();
        contenedor.put("notificacion", 2);
        contenedor.put("cancelacion", 1);
        contenedor.put("expira", 1);
        contenedor.put("inmediatamente", 2);
        contenedor.put("reembolso", 3);
        contenedor.put("orden", 2);
        contenedor.put("no autorizado", 3);
        contenedor.put("hackeada", 2);
        contenedor.put("remitente", 3);
        contenedor.put("login", 1);
        contenedor.put("banco", 3);
        contenedor.put("recibo", 2);
        contenedor.put("entrega", 1);
        contenedor.put("documento", 2);
        contenedor.put("copia", 1);
        contenedor.put("factura", 2);
        contenedor.put("verificacion", 3);
        contenedor.put("movimiento", 2);
        contenedor.put("importante", 3);
        contenedor.put("email", 2);
        contenedor.put("cuenta", 3);
        contenedor.put("deposito", 1);
        contenedor.put("iniciar sesion", 3);
        contenedor.put("envio", 2);
        contenedor.put("informacion", 3);
        contenedor.put("banamex", 2);
        contenedor.put("apple", 3);
        contenedor.put("google", 3);
        contenedor.put("amazon", 3);
        contenedor.put("paypal", 3);


        //palabras
        String[] palabraSpam = {
                "notificacion",
                "cancelacion",
                "expira",
                "inmediatamente",
                "reembolso",
                "orden",
                "no autorizado",
                "hackeada",
                "remitente",
                "login",
                "banco",
                "recibo",
                "entrega",
                "documento",
                "copia",
                "factura",
                "verificacion",
                "movimiento",
                "importante",
                "email",
                "contraseÃ±a",
                "deposito",
                "iniciar" ,
                "sesion",
                "envio",
                "informacion",
                "banamex",
                "apple",
                "google",
                "amazon",
                "paypal"};

        for (File f : files) {
            if (args.length == 0) {
                System.out.println("Falta el nombre de archivo");
                System.exit(0);
            }

            FileReader fi = null;
            try {
                fi = new FileReader(f);
            } catch (FileNotFoundException ex) {
                System.out.println( ex.getMessage());
                System.exit(-1);
            }

            //Usar para leer linea x linea el archivo
            BufferedReader inputFile = new BufferedReader(fi);

            String textLine = null;

            int lineCount = 0;
            int wordCount = 0;
            int numberCount = 0;
            int puntaje = 0;

            String delimiters = "\\s+|,\\s*|\\.\\s*|\\;\\s*|\\:\\s*|\\!\\s*|\\¡\\s*|\\¿\\s*|\\?\\s*|\\-\\s*"
                    + "|\\[\\s*|\\]\\s*|\\(\\s*|\\)\\s*|\\\"\\s*|\\_\\s*|\\%\\s*|\\+\\s*|\\/\\s*|\\#\\s*|\\$\\s*";


            // Lista con todas las palabras diferentes
            HashSet<String> wordsSet = new HashSet<String>();
            // Tiempo inicial
            long startTime = System.currentTimeMillis();
            try {
                String[] words = new String[0];
                while ((textLine = inputFile.readLine()) != null) {
                    lineCount++;

                    if (textLine.trim().length() == 0) {
                        continue; // la linea esta vacia, continuar
                    }

                    // separar las palabras en cada linea
                    words = textLine.split(delimiters);
                    wordCount += words.length;

                    for (String theWord : words) {

                        theWord = theWord.toLowerCase().trim();
                        wordsSet.add(theWord);

                        // si la palabra no esta en la lista, agregar a la lista
                        if ( !wordsSet.contains(theWord) ) {
                        wordsSet.add(theWord);
                        }
                    }
                }
                //Contador de palabras vacias
                // Obtener tiempo de ejecución
                long tiempoEjecucion = System.currentTimeMillis() - startTime;
                inputFile.close();
                fi.close();

                //Filtrar palabras
                HashSet<String> wordWithStopWord = new HashSet<String>(wordsSet);
                HashSet<String> wordsSetComplete = new HashSet<String>(wordsSet);
                HashSet<String> StopWordsSet = new HashSet<>(Arrays.asList(palabraSpam));
                wordWithStopWord.removeAll(StopWordsSet);
                wordsSetComplete.removeAll(wordWithStopWord);
                int palabras = 0;
                for(String Word : wordsSetComplete){
                    puntaje += (int)contenedor.get(Word);
                    palabras++;
                    System.out.println(Word+": "+contenedor.get(Word));
                }
                System.out.println("Palabras con Spam del archivo: "+ palabras);
                System.out.println("Puntaje palabras Spam: "+puntaje);
                if(puntaje>15){
                    System.out.println("Este correo es un posible spam");
                }if(puntaje>30){
                    System.out.println("Altamente probable de ser un correo Spam");
                }

            } catch (IOException ex) {
                System.out.println( ex.getMessage() );
            }
        }
    }
}