package carvalho.android.com.br.zapzap.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

import carvalho.android.com.br.zapzap.R;
import carvalho.android.com.br.zapzap.helper.Permissao;
import carvalho.android.com.br.zapzap.helper.Preferencias;

public class LoginActivity extends AppCompatActivity {

    private EditText telefone, ddd, pais, nome;
    private Button cadastrar;
    private String[] permissoesNecessarias= new String[]{Manifest.permission.SEND_SMS, Manifest.permission.INTERNET};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissoes(1,this,permissoesNecessarias);

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
                String mensagemEnvio = "ZapZap Código de Confirmação: " + token;

                //Salvar os dados para Validação
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                HashMap<String,String> usuario = preferencias.getDadosUsuario();

                //Envio de SMS
                boolean enviadoSMS = enviaSMS("+" + telefoneSemFormatacao, mensagemEnvio);
            }
        });
    }

    private boolean enviaSMS(String telefone, String mensagem){
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int resultado : grantResults){
            if (resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar esse app, é necessário aceitar as permissões");
        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
