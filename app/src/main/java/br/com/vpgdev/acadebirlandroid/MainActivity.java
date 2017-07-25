package br.com.vpgdev.acadebirlandroid;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.vpgdev.acadebirlandroid.activities.LoginActivity;
import br.com.vpgdev.acadebirlandroid.utils.Utils;

import static br.com.vpgdev.acadebirlandroid.activities.LoginActivity.isLogged;
import static br.com.vpgdev.acadebirlandroid.activities.LoginActivity.loggedUser;


/**
 * <b>MainActivity</b><br>
 *     Activity principal do aplicativo
 */
public class MainActivity extends AppCompatActivity {
    private Utils util = new Utils();

    /**
     * <b>onCreate</b><br>
     *     Método principal da classe, através dele o app
     *     é iniciado.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toFullscreen();

        // layout - Retorna o layout pelo id, para poder atualizá-lo
        View lay = findViewById(R.id.mainLayout);
        lay.setMinimumHeight((int) getScreenHeight()); // seta a altura do layout para total da tela
        lay.setMinimumWidth((int) getScreenWidth()); // seta a largura do laytout para total da tela

        TextView headerMain = (TextView) findViewById(R.id.headerMain);
        Configuration config = util.getScreenConfig(getContext());
        // SE RETRATO, CASO CONTRARIO SE PAISAGEM
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT){
            headerMain.setHeight((int) (getScreenHeight()));
            headerMain.setPadding(0,300,0,0);
            String html = "AcadeBirl!!!" +
                    "<br><br>" +
                    "O App de quem é monstro!!!" +
                    "<br><br><br><br><br><br><br><br><br>" +
                    "Delize para cima";
            headerMain.setText(Html.fromHtml(html));
        } else if (config.orientation == Configuration.ORIENTATION_LANDSCAPE){
            headerMain.setHeight((int) (getScreenHeight()));
            headerMain.setPadding(0,350,0,0);
            String html = "AcadeBirl!!!<br><br><br><br>" +
                    "Deslize para cima";
            headerMain.setText(Html.fromHtml(html));
        }


        // Tratando o botão de login
        Button btnLogin = (Button) findViewById(R.id.btnLoginMain);
        // chama o método passando o id
        if (isLogged) {
            btnLogin.setText("Volte à sua Sessão");
            btnLogin.setOnClickListener(onClickView(2));
        } else {
            btnLogin.setOnClickListener(onClickView(1));
        }


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
                        isLogged = false;
                        intent = new Intent(getContext(), MainActivity.class);
                        finish();
                        startActivity(intent);
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
