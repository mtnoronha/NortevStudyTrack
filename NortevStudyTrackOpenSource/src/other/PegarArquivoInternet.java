package other;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.UnknownHostException;


public class PegarArquivoInternet {


    public static File gravaArquivoDeURL(String stringUrl, String pathLocal) {
        try {

            //Encapsula a URL num objeto java.net.URL
            URL url = new URL(stringUrl);


            //Cria streams de leitura (este metodo ja faz a conexao)...
            InputStream is = url.openStream();

            //... e de escrita.
            FileOutputStream fos = new FileOutputStream(pathLocal);

            //Le e grava byte a byte. Voce pode (e deve) usar buffers para
            //melhor performance (BufferedReader).
            int umByte = 0;
            while ((umByte = is.read()) != -1) {
                fos.write(umByte);
            }

            //Nao se esqueca de sempre fechar as streams apos seu uso!
            is.close();
            fos.close();

            //apos criar o arquivo fisico, retorna referencia para o mesmo
            return new File(pathLocal);

        } catch (UnknownHostException e) {
            //Lembre-se de tratar bem suas excecoes, ou elas tambem lhe tratarão mal!
            //Aqui so vamos mostrar o stack no stderr.
            System.out.println("Conexão com a internet... Erro: "+e);
        } catch ( IOException e){
            System.out.println("Erro com arquivo... Erro: "+e);
        }

        return null;
    }

}
