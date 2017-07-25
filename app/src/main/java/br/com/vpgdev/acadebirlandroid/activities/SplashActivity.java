package br.com.vpgdev.acadebirlandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import br.com.vpgdev.acadebirlandroid.MainActivity;
import br.com.vpgdev.acadebirlandroid.R;

/**
 * Created by Fernando on 11/07/2017.
 */

/**
 * <b>SplashActivity</b><br>
 *     Classe usada como pré-carregamento antes de iniciar a MainActivity
 */
public class SplashActivity extends AppCompatActivity {

    private final static int SPLASH_TIME = 5000;

    /**
     * <b>onCreate</b><br>
     *     Método principal da classe, através dele o app
     *     é iniciado.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toFullscreen();

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            // Carrega a imagem logo
            @Override
            public void run() {
                // Este método será executado por 5 segundos
                // Antes de iniciar a MainActivity
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
                // fecha a Activity atual
                finish();
            }
        }, SPLASH_TIME);


    }



    /**
     * <b>onBackPressed</b><br>
     *     Controla a ação do botão back do android
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * <b>onClickView</b><br>
     *     Método que controla a ação de após o clique em qualquer botão da tela
     * @param id Recebe como parâmetro um id para direcionar a ação correta
     * @return View.OnClickListener
     */
    private View.OnClickListener onClickView(final int id) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (id) {
                    case 1:
                        intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        break;
                    case 2:

                        break;
                }
            }
        };
    }

    /**
     * <b>toast</b><br>
     *     Método que facilita a exibição de mensagens toast.
     * @param msg Recebe a mensagem a ser exibida.
     */
    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * <b>getContext</b><br>
     *     Método que retorna o contexto da activity atual.
     * @return
     */
    private Context getContext() {
        return this;
    }

    /**
     * <b>toFullScreen</b><br>
     *     Muda a exibição da tela para o modo fullscreen,
     * escondendo a área de notificação.
     */
    private void toFullscreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }

    /**
     * <b>getScreenWidth</b><br>
     *     Retorna a largura da tela
     * @return double
     */
    private double getScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * <b>getScreenHeight</b><br>
     *     Retorna a altura da tela
     * @return double
     */
    private double getScreenHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }


}
