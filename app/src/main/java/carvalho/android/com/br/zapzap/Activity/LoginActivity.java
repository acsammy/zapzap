package carvalho.android.com.br.zapzap.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import carvalho.android.com.br.zapzap.R;

public class LoginActivity extends AppCompatActivity {

    private EditText telefone, ddd, pais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        telefone = (EditText)findViewById(R.id.editTextTelefone);
        ddd = (EditText)findViewById(R.id.editTextDDD);
        pais = (EditText)findViewById(R.id.editTextPais);

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
    }
}
