package carvalho.android.com.br.zapzap.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

import carvalho.android.com.br.zapzap.R;
import carvalho.android.com.br.zapzap.helper.Preferencias;

public class LoginActivity extends AppCompatActivity {

    private EditText telefone, ddd, pais, nome;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        telefone = (EditText)findViewById(R.id.editTextTelefone);
        ddd = (EditText)findViewById(R.id.editTextDDD);
        pais = (EditText)findViewById(R.id.editTextPais);
        nome = (EditText) findViewById(R.id.editTextNome);
        cadastrar = (Button)findViewById(R.id.buttonCadastro);

        // criando as mascaras
        SimpleMaskFormatter smfPais = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter smfDDD = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("N NNNN-NNNN");

        // formatando as mascaras
        MaskTextWatcher maskPais = new MaskTextWatcher(pais, smfPais);
        MaskTextWatcher maskDDD = new MaskTextWatcher(ddd, smfDDD);
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone, smfTelefone);

        // adicionando as mascara
        pais.addTextChangedListener(maskPais);
        ddd.addTextChangedListener(maskDDD);
        telefone.addTextChangedListener(maskTelefone);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto = pais.getText().toString() + ddd.getText().toString() + telefone.getText().toString();
                String telefoneSemFormatacao = telefoneCompleto.replace("+", "");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-","");

                //Gerar token
                Random random = new Random();
                int numeroRandomico = random.nextInt(9999-1000)+1000;

                String token = String.valueOf(numeroRandomico);

                //Salvar os dados para Validação
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                HashMap<String,String> usuario = preferencias.getDadosUsuario();

            }
        });
    }
}
