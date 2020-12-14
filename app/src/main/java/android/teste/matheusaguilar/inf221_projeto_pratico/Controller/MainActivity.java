package android.teste.matheusaguilar.inf221_projeto_pratico.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.teste.matheusaguilar.inf221_projeto_pratico.Model.ControladoraFachadaSingleton;
import android.teste.matheusaguilar.inf221_projeto_pratico.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    ControladoraFachadaSingleton controladora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controladora = ControladoraFachadaSingleton.getInstance();
    }

    public void clickCadastrarPontos(View v){

        controladora.mockLoginCliente();

        Intent it = new Intent(this, CadastrarPontosActivity.class);
        startActivity(it);
    }

    public void clickGerarCodigo(View v){

        controladora.mockLoginEmpresa();

        Intent it = new Intent(this, GerarCodigoActivity.class);
        startActivity(it);
    }
}