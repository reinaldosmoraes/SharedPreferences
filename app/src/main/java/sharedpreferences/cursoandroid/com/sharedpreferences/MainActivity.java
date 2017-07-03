package sharedpreferences.cursoandroid.com.sharedpreferences;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText campoNome;
    private Button botaoSalvar;
    private TextView texto;

    private RadioGroup radioGroup;
    private RadioButton radioButtonSelecionado;
    private TextView textoCor;

    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campoNome = (EditText) findViewById(R.id.campoNomeId);
        botaoSalvar = (Button) findViewById(R.id.botaoSalvarId);
        texto = (TextView) findViewById(R.id.textoId);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupId);
        textoCor = (TextView) findViewById(R.id.textoCorId);

        layout = (RelativeLayout) findViewById(R.id.layoutId);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //instanciando o sharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);// 0 é a permissao privada
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //tratando o radioButton
                int idRadioButtonEscolhido = radioGroup.getCheckedRadioButtonId();

                //testando se a String está vazia
                if (campoNome.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Insira um nome", Toast.LENGTH_SHORT).show();
                }
                else if (idRadioButtonEscolhido <= 0){
                    Toast.makeText(MainActivity.this, "Selecione uma cor", Toast.LENGTH_SHORT).show();
                }
                else{
                    //radioButton
                    radioButtonSelecionado = (RadioButton) findViewById(idRadioButtonEscolhido);
                    editor.putString("cor", radioButtonSelecionado.getText().toString());
                    textoCor.setText("Cor: " + radioButtonSelecionado.getText().toString());

                    //função para setar backgroud com as cores q eu gerei
                    setBackgroud(radioButtonSelecionado.getText().toString());

                    //salvando nome
                    editor.putString("nome", campoNome.getText().toString()); // nome é a chave para recuperar depois
                    texto.setText("Olá, " + campoNome.getText().toString());

                    //Commit
                    editor.commit();
                }

            }
        });

        //Recuperando dados ao abrir o app
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);

        //testando se tem alguma string com a chave "nome" salva
        if (sharedPreferences.contains("nome")){
            String nomeUsuario = sharedPreferences.getString("nome", "Usuário não encontrado"); //segundo parametro vai ser exibido caso por algum motivo a String do primeiro parametro nao possa ser recuperada
            texto.setText("Olá, " + nomeUsuario);
        }

        if (sharedPreferences.contains("cor")){
            String corEscolhida = sharedPreferences.getString("cor", "Nenhuma cor escolhida");
            textoCor.setText("Cor: " + corEscolhida);
        }

        //setando o fundo com a ultima alteração antes de fechar o app
        SharedPreferences sharedPreferences2 = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        if (sharedPreferences.contains("cor")){
            String corRecuperada = sharedPreferences2.getString("cor", "Nenhuma cor escolhida");
            setBackgroud(corRecuperada);
        }

    }

    //função para setar as cores do fundo
    public void setBackgroud(String cor){

        if (cor.equals("Branco")){
            layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        else if (cor.equals("Azul")){
            layout.setBackgroundColor(Color.parseColor("#FF3AC1FF"));
        }
        else if (cor.equals("Verde")){
            layout.setBackgroundColor(Color.parseColor("#FF00DF7B"));
        }
    }
}
