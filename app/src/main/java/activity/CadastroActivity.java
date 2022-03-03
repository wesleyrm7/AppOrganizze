package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import config.ConfiguracaoFirebase;
import model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome,campoEmail,campoSenha;
    private Button botaoCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        iniciaComponentes();

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vericaEcadastra();

            }
        });

    }

    private void iniciaComponentes(){
        campoNome = findViewById(R.id.editNome);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoCadastrar = findViewById(R.id.button);

    }

    private void vericaEcadastra(){

        String textoNome = campoNome.getText().toString();
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        if( !textoNome.isEmpty() ){
            if( !textoEmail.isEmpty() ){
                if( !textoSenha.isEmpty() ){

                    usuario = new Usuario();
                    usuario.setNome(textoNome);
                    usuario.setEmail(textoEmail);
                    usuario.setSenha(textoSenha);

                    cadastrarUsuario();

                }else{
                    Toast.makeText(this, "Preencha a campo Senha ", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"Preencha o campo Email", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Preencha o Nome",Toast.LENGTH_SHORT).show();
        }
    }

    public void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(  usuario.getEmail(), usuario.getSenha()

        ).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful() ){
                            Toast.makeText(CadastroActivity.this,"Sucesso Ao Cadastrar",Toast.LENGTH_SHORT).show();

                        }else {

                            String excecao = "";
                            try {
                                throw task.getException();//throw LANÇA A ESCESSAO
                            }catch ( FirebaseAuthWeakPasswordException e ){
                                excecao = "Digite uma senha mais Forte!";
                            }catch ( FirebaseAuthInvalidCredentialsException e){
                                excecao = "Por Favor. Digite um E-mail Valido!";
                            }catch ( FirebaseAuthUserCollisionException e){
                                excecao = "Esta conta já foi Cadastrada";

                            }catch (Exception e){
                                excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace(); //Printa a excecao no Log

                            }

                            Toast.makeText(CadastroActivity.this,excecao,Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }
}