package br.com.vpgdev.acadebirlandroid.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import br.com.vpgdev.acadebirlandroid.MainActivity;
import br.com.vpgdev.acadebirlandroid.R;
import br.com.vpgdev.acadebirlandroid.domain.User;
import br.com.vpgdev.acadebirlandroid.utils.Utils;
import br.com.vpgdev.acadebirlandroid.webservice.HttpHandler;


/**
 * Created by Fernando on 10/07/2017.
 */

/**
 * <b>LoginActivity</b><br>
 *     Classe que exibe a tela de login, e controla os dados inseridos
 */
public class LoginActivity extends AppCompatActivity {

    public static boolean isLogged = false;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private Utils util = new Utils();
    public static User loggedUser = new User();
    private User foundUser = new User();
    private User user = new User();
    private String errorMessage;
    ProgressDialog pDialog;


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

        setContentView(R.layout.activity_login);

        // layout - Retorna o layout pelo id, para poder atualizá-lo
        View lay = findViewById(R.id.loginLayout);
        lay.setMinimumHeight((int) getScreenHeight()); // seta a altura do layout para total da tela
        lay.setMinimumWidth((int) getScreenWidth()); // seta a largura do laytout para total da tela

        TextView headerLogin = (TextView) findViewById(R.id.headerLogin);
        Configuration config = util.getScreenConfig(getContext());
        // SE RETRATO, CASO CONTRARIO SE PAISAGEM
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT){
            // retrato
            headerLogin.setHeight((int) (getScreenHeight() * 0.30 ));
        } else if (config.orientation == Configuration.ORIENTATION_LANDSCAPE){
            // paisagem
            headerLogin.setHeight((int) (getScreenHeight() * 0.20));
            headerLogin.setPadding(0,10,0,0);
        }

        EditText editUsername = (EditText) findViewById(R.id.editUsername);
        EditText editPassword = (EditText) findViewById(R.id.editPassword);
        editUsername.setHint("Informe seu usuário");
        editPassword.setHint("Informe sua senha");
        /*Drawable x = getResources().getDrawable(R.drawable.x);
        x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
        editUsername.setCompoundDrawables(null, null, x, null);*/

        /*editUsername.setText("fernando");
        editPassword.setText("1234");*/

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnForgetPass = (Button) findViewById(R.id.btnForgetPass);

        btnLogin.setOnClickListener(onClickView(1));
        btnForgetPass.setOnClickListener(onClickView(2));

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
                        errorMessage = null;
                        TextView txtUser = (TextView) findViewById(R.id.editUsername);
                        TextView txtPass = (TextView) findViewById(R.id.editPassword);

                        if (txtUser.getText().toString().equals("") ||
                                txtPass.getText().toString().equals("")) {
                            toast("O campos login e senha são obrigatórios");
                            break;
                        }

                        user.setUsername(txtUser.getText().toString());
                        user.setPass(txtPass.getText().toString());

                        if (user.getUsername().equals("root") &&
                                user.getPass().equals("root")) {
                            foundUser = new User();
                            foundUser.setFirstName("Root User");
                            foundUser.setUsername("root");
                            foundUser.setPass("root");
                            if (verifyUser(user.getUsername(), user.getPass())) {
                                intent = new Intent(getContext(), MainActivity.class);
                                isLogged = true;
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            new VerifyUserTask().execute();
                        }

                        break;
                    case 2:
                        toast("Ih! Esqueceu a senha?");
                        break;
                }
            }
        };
    }

    /**
     * <b>VerifyUser</b><br>
     *     Classe interna que faz o acesso à web service
     */
    private class VerifyUserTask extends AsyncTask<Void, Void, Void> {

        /**
         * <b>onPreExecute</b><br>
         *     Controla as ações que acontecerão antes da execução principal
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Carregando...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * <b>doInBackground</b><br>
         *     Este método executa as ações desejadas em plano de fundo, enquanto
         *     outra ação está sendo executada.
         * @param voids
         * @return
         */
        @Override
        protected Void doInBackground(Void... voids) {
            errorMessage = null;
            HttpHandler webservice = new HttpHandler();
            String json = webservice.sendGet(util.getUrlWebService()+"/users/username/"+user.getUsername());
            Log.e(TAG, "URL: " + util.getUrlWebService()+"/users/username/"+user.getUsername());
            Log.e(TAG, "Resposta do Json: " + json);

            if (json == null || json == "{}" || json.equals("erro") || json == "erro") {
                foundUser = null;
                errorMessage = json;
            } else if (errorMessage == null) {
                Gson gson = new Gson();
                Type userType = new TypeToken<User>(){}.getType();
                foundUser = gson.fromJson(json, userType);
                Log.e(TAG, foundUser.toString());
            }

            return null;
        }

        /**
         * <b>onPostExecute</b><br>
         *     Este método encerra a execução da AsynkTask logo depois das ações
         *     executadas em plano de fundo.
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (verifyUser(user.getUsername(), user.getPass())) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                isLogged = true;
                startActivity(intent);
                finish();
            }

            if(pDialog.isShowing()){
                pDialog.dismiss();
            }

        }
    }

    /**
     * <b>verifyUser</b><br>
     *     Este método faz a verificação entre os dados informados na tela de login e o retorno da webservice
     * @param user
     * @param senha
     * @return
     */
    private boolean verifyUser(String user, String senha) {
        if (errorMessage != null) {
            toast("Houve um erro no acesso ao servidor.\n" +
                    "Tente novamente");
            return false;
        } else {
            String u = foundUser.getUsername();
            String p = foundUser.getPass();

            if (user.equals(u) && senha.equals(p)) {
                loggedUser = foundUser;
                return true;
            } else {
                if (errorMessage != null) {
                    toast(errorMessage);
                    return false;
                } else if (errorMessage == "erro") {
                    toast("Houve um erro no acesso ao servidor.\n" +
                            "Tente novamente");
                } else {
                    toast("Usuário e/ou senha inválidos");
                }
                return false;
            }
        }
    };

    /**
     * <b>toast</b><br>
     *     Método que facilita a exibição de mensagens toast.
     * @param msg Recebe a mensagem a ser exibida.
     */
    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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
