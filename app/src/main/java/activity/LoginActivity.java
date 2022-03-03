package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import config.ConfiguracaoFirebase;
import model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button btnEntrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciaComponentes();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validandoEsalvando(view);

            }
        });

    }

    private void iniciaComponentes(){
        campoEmail = findViewById(R.id.editEmail2);
        campoSenha = findViewById(R.id.editSenha2);
        btnEntrar = findViewById(R.id.buttonEntrar);
    }

    public void validandoEsalvando(View view){

        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        if( !textoEmail.isEmpty() ){
            if( !textoSenha.isEmpty() ){

            //Configurando o Objeto USUARIO
            usuario = new Usuario();
            usuario.setEmail(textoEmail);
            usuario.setSenha(textoSenha);
            validarLogin();

            }else{
                Toast.makeText(LoginActivity.this, "Preencha a campo Senha ", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(LoginActivity.this,"Preencha o campo Email", Toast.LENGTH_SHORT).show();
        }
    }

    public void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()

        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if( task.isSuccessful() ){

                    startActivity(new Intent(LoginActivity.this,PrincipalActivity.class));

                    Toast.makeText(LoginActivity.this,"Sucesso Fazer o Login!", Toast.LENGTH_SHORT).show();

                }else{
                    String excecao = "";

                    try {
                        throw task.getException();

                    }catch ( FirebaseAuthInvalidUserException e ) {
                        excecao = "Usuário não está Cadastrado.";

                    }catch ( FirebaseAuthInvalidCredentialsException e ){
                        excecao = "E-mail e Senha não correspondem a um usuário cadastrado";

                    }catch (Exception e){
                        excecao = "Erro ao Logar Usuário!" + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this,excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}