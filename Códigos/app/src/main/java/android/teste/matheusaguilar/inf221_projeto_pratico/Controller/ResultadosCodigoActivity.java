package android.teste.matheusaguilar.inf221_projeto_pratico.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.teste.matheusaguilar.inf221_projeto_pratico.Model.ControladoraFachadaSingleton;
import android.teste.matheusaguilar.inf221_projeto_pratico.R;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultadosCodigoActivity extends AppCompatActivity {

    private ControladoraFachadaSingleton controladora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_codigo);

        controladora = ControladoraFachadaSingleton.getInstance();

        Intent it = getIntent();
        Bundle bundle = it.getExtras();

        String codigo = bundle.getString("codigo");
        int pontos = bundle.getInt("pontos");
        double valor = bundle.getDouble("valor");

        ImageView fotoPerfil = (ImageView) findViewById(R.id.fotoPerfil);
        TextView codigoText = (TextView) findViewById(R.id.codigoText);
        TextView pontosText = (TextView) findViewById(R.id.pontosText);
        TextView valorText = (TextView) findViewById(R.id.valorText);

        try {
            fotoPerfil.setImageResource(controladora.getEmpresaLogada().getFoto());
            codigoText.setText(codigo);
            pontosText.setText(Integer.toString(pontos));
            valorText.setText("R$ " + String.format("%.2f", valor));
        }
        catch(Exception e){
            Log.e("ERRO", e.getMessage());
        }
    }

    public void voltar(View v){
        finish();
    }
}