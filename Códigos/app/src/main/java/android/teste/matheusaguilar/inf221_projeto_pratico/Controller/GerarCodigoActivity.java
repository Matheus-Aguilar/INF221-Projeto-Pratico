package android.teste.matheusaguilar.inf221_projeto_pratico.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.teste.matheusaguilar.inf221_projeto_pratico.Model.ControladoraFachadaSingleton;
import android.teste.matheusaguilar.inf221_projeto_pratico.R;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class GerarCodigoActivity extends AppCompatActivity {

    private ControladoraFachadaSingleton controladora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerar_codigo);

        controladora = ControladoraFachadaSingleton.getInstance();

        ImageView fotoPerfil = (ImageView) findViewById(R.id.fotoPerfil);

        try {
            fotoPerfil.setImageResource(controladora.getEmpresaLogada().getFoto());
        }
        catch(Exception e){
            Log.e("ERRO", e.getMessage());
        }
    }

    public void clickGerarCodigo(View v){
        EditText pontosInput = (EditText) findViewById(R.id.numeroPontos);
        EditText valorInput = (EditText) findViewById(R.id.valorCompra);

        int numeroPontos = Integer.parseInt(pontosInput.getText().toString());
        double valorCompra = Double.parseDouble(valorInput.getText().toString());

        String codigo = controladora.criarCodigoPontos(numeroPontos, valorCompra);

        Bundle bundle = new Bundle();

        bundle.putString("codigo", codigo);
        bundle.putInt("pontos", numeroPontos);
        bundle.putDouble("valor", valorCompra);

        Intent it = new Intent(this, ResultadosCodigoActivity.class);
        it.putExtras(bundle);

        startActivity(it);
    }

}