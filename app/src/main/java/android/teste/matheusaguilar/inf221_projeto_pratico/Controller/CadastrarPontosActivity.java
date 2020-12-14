package android.teste.matheusaguilar.inf221_projeto_pratico.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.teste.matheusaguilar.inf221_projeto_pratico.Model.AlreadyValidatedCodeException;
import android.teste.matheusaguilar.inf221_projeto_pratico.Model.CodigoPontos;
import android.teste.matheusaguilar.inf221_projeto_pratico.Model.ControladoraFachadaSingleton;
import android.teste.matheusaguilar.inf221_projeto_pratico.Model.Empresa;
import android.teste.matheusaguilar.inf221_projeto_pratico.Model.InvalidCodeException;
import android.teste.matheusaguilar.inf221_projeto_pratico.Model.PontosCliente;

import android.teste.matheusaguilar.inf221_projeto_pratico.R;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CadastrarPontosActivity extends AppCompatActivity {

    private Empresa empresa;
    private PontosCliente pontos;
    private ControladoraFachadaSingleton controladora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_pontos);

        controladora = ControladoraFachadaSingleton.getInstance();

        //Usamos o mock para buscar os dados da empresa
        empresa = controladora.mockDadosEmpresa();
        pontos = controladora.getPontosCliente(controladora.getClienteLogado(), empresa);

        ImageView fotoPerfil = (ImageView) findViewById(R.id.fotoPerfil);
        ImageView fotoEmpresa = (ImageView) findViewById(R.id.fotoEmpresa);
        TextView nomeEmpresa = (TextView) findViewById(R.id.nomeEmpresa);
        TextView pontosCliente = (TextView) findViewById(R.id.pontosCliente);

        try {
            fotoPerfil.setImageResource(controladora.getClienteLogado().getFoto());
            fotoEmpresa.setImageResource(empresa.getFoto());
            nomeEmpresa.setText(empresa.getNome());
            pontosCliente.setText(Integer.toString(pontos.getTotal()));
        }
        catch(Exception e){
            Log.e("ERRO", e.getMessage());
        }
    }

    public void resgatarCodigo(View v){
        EditText codigoText = (EditText) findViewById(R.id.codigoPromocional);
        String codigo = codigoText.getText().toString();

        Log.e("ERRO", codigo);

        try{
            CodigoPontos cp = controladora.validarCodigoPontos(codigo, controladora.getClienteLogado());

            pontos = controladora.getPontosCliente(controladora.getClienteLogado(), empresa);

            TextView pontosCliente = (TextView) findViewById(R.id.pontosCliente);
            pontosCliente.setText(Integer.toString(pontos.getTotal()));

            Toast.makeText(this, "Parabéns! Você ganhou " + cp.getPontos() + " pontos!", Toast.LENGTH_LONG).show();
        }
        catch(InvalidCodeException e){
            Toast.makeText(this, "Esse código não é válido", Toast.LENGTH_LONG).show();
        }
        catch(AlreadyValidatedCodeException e){
            Toast.makeText(this, "Esse código já foi validado", Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            Log.e("ERRO", e.getMessage());
        }
    }
}