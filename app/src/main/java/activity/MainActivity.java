package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.organizze.R;

public class MainActivity extends AppCompatActivity {

    private Button cadastro;
    private TextView logar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciaComponentes();

      cadastro.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              //acao
              btCadastro(view);


          }
      });

      logar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              //acao
              btEntrar(view);


          }
      });
    }

    private void iniciaComponentes(){

        cadastro = findViewById(R.id.btnCadastro);
        logar = findViewById(R.id.txtEntrar);
    }

    public void btEntrar(View view){
        startActivity(new Intent(this,LoginActivity.class));

    }

    public void btCadastro(View view){
        startActivity(new Intent(this,CadastroActivity.class));
    }
}