package br.com.vpgdev.acadebirlandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.Window;
import android.view.WindowManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Fernando on 08/07/2017.
 */

public class Utils {

    /**
     * <b>getUrlWebService</b><br>
     *     Método que retorna a url usado para acessar a webservice
     * @return
     */
    public String getUrlWebService() {
        final String IP = "192.168.1.107";
        final String PORT = "8080";
        final String WEBSERVICE_NAME = "AcadeBirlWebService/rest";
        String url = "http://"+IP+":"+PORT+"/"+WEBSERVICE_NAME;
        return url;
    }

    /**
     * <b>getScreenConfig</b><br>
     * Retorna a configuração da tela.
     * @param ctx Recebe o contexto da activity como parâmetro
     * @return Configuration
     */
    public Configuration getScreenConfig(Context ctx) {
        return ctx.getResources().getConfiguration();
    }

    /**
     * <b>dateToString</b><br>
     *     Método que converte uma data para String.
     *     Retorna uma String no formato dd/mm/aaaa
     * @param date
     * @return String
     */
    public String dateToString(Calendar date) {
        String stringDate = new SimpleDateFormat("dd/MM/yyyy").format(date.getTime());
        return stringDate;
    }

    /**
     * <b>stringToDate</b><br>
     *     Método que converte uma String para data. Aceita o format dd/mm/aaaa
     * @param date
     * @return Calendar
     */
    public Calendar stringToDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(date));
            return cal;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
