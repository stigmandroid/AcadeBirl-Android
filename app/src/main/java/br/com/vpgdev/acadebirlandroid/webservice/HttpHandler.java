package br.com.vpgdev.acadebirlandroid.webservice;

/**
 * Created by Fernando on 11/07/2017.
 */

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * <b>HttpHandler</b><br>
 *     Classe que manipula o acesso à webservice
 */
public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    /**
     * <b>HttpHandler</b><br>
     *     Método construtor da classe
     */
    public HttpHandler(){

    }

    /**
     * <b>sendGet</b><br>
     *     Método que envia uma requisição do tipo GET para a webservice
     *     através da URL
     * @param reqUrl
     * @return String
     */
    public String sendGet(String reqUrl) {
        String response = "erro";
        try{
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);

            //leitura
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);

        }catch (MalformedURLException e){
            response = "erro";
            Log.e(TAG, "MalformedURLException " +e.getMessage());
        }catch (ProtocolException e){
            response = "erro";
            Log.e(TAG, "ProtocolException " +e.getMessage());
        }catch (IOException e){
            response = "erro";
            Log.e(TAG, "IOException " +e.getMessage());
        }catch (Exception e){
            response = "erro";
            Log.e(TAG, "Exception " +e.getMessage());
        }

        return response;
    }

    /**
     * <b>sendPost</b><br>
     *     Método que envia uma requisição do tipo POST para a webservice
     *     através da URL, enviando um arquivo JSON
     * @param reqUrl
     * @param json
     * @return String
     * @throws MinhaException
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String sendPost(String reqUrl, String json) throws MinhaException{

        try {
            // Cria um objeto HttpURLConnection:
            HttpURLConnection request = (HttpURLConnection) new URL(reqUrl).openConnection();

            try {
                // Define que a conexão pode enviar informações e obtê-las de volta:
                request.setDoOutput(true);
                request.setDoInput(true);

                // Define o content-type:
                request.setRequestProperty("Content-Type", "application/json");

                // Define o método da requisição:
                request.setRequestMethod("POST");

                // Conecta na URL:
                request.connect();

                // Escreve o objeto JSON usando o OutputStream da requisição:
                try (OutputStream outputStream = request.getOutputStream()) {
                    outputStream.write(json.getBytes("UTF-8"));
                }

                // Caso você queira usar o código HTTP para fazer alguma coisa, descomente esta linha.
                //int response = request.getResponseCode();

                return readResponse(request);
            } finally {
                request.disconnect();
            }
        } catch (IOException ex) {
            throw new MinhaException(ex);
        }
    }

    /**
     * <b>sendPut</b><br>
     *     Método que envia uma requisição do tipo PUT para a webservice
     *     através da URL, enviando um arquivo JSON
     * @param reqUrl
     * @param json
     * @return String
     * @throws MinhaException
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String sendPut(String reqUrl, String json) throws MinhaException{

        try {
            // Cria um objeto HttpURLConnection:
            HttpURLConnection request = (HttpURLConnection) new URL(reqUrl).openConnection();

            try {
                // Define que a conexão pode enviar informações e obtê-las de volta:
                request.setDoOutput(true);
                request.setDoInput(true);

                // Define o content-type:
                request.setRequestProperty("Content-Type", "application/json");

                // Define o método da requisição:
                request.setRequestMethod("PUT");

                // Conecta na URL:
                request.connect();

                // Escreve o objeto JSON usando o OutputStream da requisição:
                try (OutputStream outputStream = request.getOutputStream()) {
                    outputStream.write(json.getBytes("UTF-8"));
                }

                // Caso você queira usar o código HTTP para fazer alguma coisa, descomente esta linha.
                //int response = request.getResponseCode();

                return readResponse(request);
            } finally {
                request.disconnect();
            }
        } catch (IOException ex) {
            throw new MinhaException(ex);
        }
    }

    /**
     * <b>sendDelete</b><br>
     *     Método que envia uma requisição do tipo DELETE para a webservice
     *     através da URL
     * @param reqUrl
     * @return String
     * @throws MinhaException
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String sendDelete(String reqUrl) throws MinhaException{
        try {
            // Cria um objeto HttpURLConnection:
            HttpURLConnection request = (HttpURLConnection) new URL(reqUrl).openConnection();

            try {
                // Define que a conexão pode enviar informações e obtê-las de volta:
                request.setDoOutput(true);
                request.setDoInput(true);

                // Define o content-type:
                request.setRequestProperty("Content-Type", "application/json");

                // Define o método da requisição:
                request.setRequestMethod("DELETE");

                // Conecta na URL:
                request.connect();


                // Caso você queira usar o código HTTP para fazer alguma coisa, descomente esta linha.
                //int response = request.getResponseCode();

                return readResponse(request);
            } finally {
                request.disconnect();
            }
        } catch (IOException ex) {
            throw new MinhaException(ex);
        }
    }

    /**
     * <b>readResponse</b><br>
     *     Método que transforma a resposta da WebService em String
     * @param request
     * @return String
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String readResponse(HttpURLConnection request) throws IOException {
        ByteArrayOutputStream os;
        try (InputStream is = request.getInputStream()) {
            os = new ByteArrayOutputStream();
            int b;
            while ((b = is.read()) != -1) {
                os.write(b);
            }
        }
        return new String(os.toByteArray());
    }
    private String convertStreamToString(InputStream is){

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try{
            while((line = reader.readLine()) !=null){
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static class MinhaException extends Exception {
        private static final long serialVersionUID = 1L;

        public MinhaException(Throwable cause) {
            super(cause);
        }
    }
}