package android.teste.matheusaguilar.inf221_projeto_pratico.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.teste.matheusaguilar.inf221_projeto_pratico.Util.BancoDeDadosSingleton;
import android.util.Log;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public final class ControladoraFachadaSingleton {

    private final static int TAMANHO_CODIGO = 10;
    private final static String ALPHABET = "0123456789" +
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static Cliente usuarioCliente;
    private static Empresa usuarioEmpresa;
    private BancoDeDadosSingleton bd;
    private static ControladoraFachadaSingleton INSTANCE = new ControladoraFachadaSingleton();

    private ControladoraFachadaSingleton(){
        bd = BancoDeDadosSingleton.getInstance();
    }

    public static ControladoraFachadaSingleton getInstance(){
        return ControladoraFachadaSingleton.INSTANCE;
    }

    /*Esse método é apenas um modelo (mock), que simula o login
    * de um cliente*/
    public void mockLoginCliente(){
        Cursor c = bd.buscar("cliente", new String[]{"idCliente", "nome", "foto"}, "", "idCliente");
        usuarioCliente = new Cliente(c.getInt(0), c.getString(1), c.getInt(2));
        usuarioEmpresa = null;
        c.close();
    }

    /*Esse método é apenas um modelo (mock), que simula o login
     * de uma empresa*/
    public void mockLoginEmpresa(){
        Cursor c = bd.buscar("empresa", new String[]{"idEmpresa", "nome", "foto"}, "", "idCliente");
        usuarioEmpresa = new Empresa(c.getInt(0), c.getString(1), c.getInt(2));
        usuarioCliente = null;
        c.close();
    }

    public Cliente getCliente(Integer id){
        String where = "WHERE idCliente = " + id;
        Cursor c = bd.buscar("cliente", new String[]{"idCliente", "nome", "foto"}, where, "");

        if(c != null && c.getCount() > 0) {

            Cliente cliente = new Cliente(c.getInt(0), c.getString(1), c.getInt(2));
            c.close();

            return cliente;
        }
        else {
            c.close();
            return null;
        }
    }

    public Empresa getEmpresa(Integer id){
        String where = "WHERE idEmpresa = " + id;
        Cursor c = bd.buscar("empresa", new String[]{"idEmpresa", "nome", "foto"}, where, "");

        if(c != null && c.getCount() > 0) {

            Empresa empresa = new Empresa(c.getInt(0), c.getString(1), c.getInt(2));
            c.close();

            return empresa;
        }
        else {
            c.close();
            return null;
        }
    }

    public PontosCliente getPontosCliente(Cliente c, Empresa e){
        String where = "WHERE idCliente = " + c.getId() + " AND idEmpresa = " + e.getId();
        Cursor cursor = bd.buscar("pontoscliente", new String[]{"idCliente", "idEmpresa", "totalPontos"}, where, "");

        Cliente cliente = getCliente(cursor.getInt(0));
        Empresa empresa = getEmpresa(cursor.getInt(1));
        int total = cursor.getInt(2);

        if(cursor != null && cursor.getCount() > 0) {
            PontosCliente pc = new PontosCliente(cliente, empresa, total);
            cursor.close();
            return pc;
        }
        else{
            cursor.close();
            return null;
        }
    }

    private void criarPontosCliente(Cliente c, Empresa e){
        ContentValues valores = new ContentValues();

        valores.put("idCliente", c.getId());
        valores.put("idEmpresa", e.getId());

        bd.inserir("pontoscliente", valores);
    }


    public void adicionarPontos(Cliente c, Empresa e, Integer valor){
        String where = "WHERE idCliente = " + c.getId() + " AND idEmpresa = " + e.getId();

        Cursor cursor = bd.buscar("pontoscliente", new String[]{"totalPontos"}, where, "");

        if(cursor == null || cursor.getCount() > 0){
            criarPontosCliente(c, e);
        }

        ContentValues valores = new ContentValues();
        valores.put("totalPontos", cursor.getInt(0) + valor);

        bd.atualizar("pontoscliente", valores, where);

        cursor.close();
    }

    public void removerPontos(Cliente c, Empresa e, Integer valor){
        String where = "WHERE idCliente = " + c.getId() + " AND idEmpresa = " + e.getId();

        Cursor cursor = bd.buscar("pontoscliente", new String[]{"totalPontos"}, where, "");

        if(valor > cursor.getInt(0)){
            cursor.close();
            Log.e("PontosCliente", "Falha ao remover pontos: o cliente nao tem pontos suficientes");
            return;
        }

        ContentValues valores = new ContentValues();
        valores.put("totalPontos", cursor.getInt(0) - valor);

        bd.atualizar("pontoscliente", valores, where);

        cursor.close();
    }

    public String randomString(int n){
        StringBuilder sb = new StringBuilder(n);
        Random rng = new Random();

        for(int i = 0; i < n; i++){
            int idx = rng.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(idx));
        }

        return sb.toString();
    }

    public CodigoPontos getCodigoPontos(String codigo){
        Cursor c = bd.buscar("codigodepontos", new String[]{"numeroDePontos", "valorDaCompra", "idEmpresa", "validado"}, "WHERE codigo=" + codigo, "");

        if(c != null && c.getCount() > 0){

            int pontos = c.getInt(0);
            int valor = c.getInt(1);
            Empresa empresa = getEmpresa(c.getInt(2));
            boolean validado = (c.getInt(3) == 1);

            c.close();

            CodigoPontos cd = new CodigoPontos(codigo, pontos, valor, empresa, validado);

            return cd;
        }
        else{
            c.close();
            return null;
        }
    }

    public void criarCodigoPontos(Integer pontos, Integer valor){
        String codigo = randomString(TAMANHO_CODIGO);
        CodigoPontos cd = getCodigoPontos(codigo);

        while(cd != null){
            codigo = randomString(TAMANHO_CODIGO);
            cd = getCodigoPontos(codigo);
        }

        Calendar calendar = Calendar.getInstance();
        String dataString = calendar.get(calendar.DAY_OF_MONTH) + "/" + calendar.get(calendar.MONTH) + "/" + calendar.get(calendar.YEAR);

        ContentValues valores = new ContentValues();
        valores.put("codigo", codigo);
        valores.put("numeroDePontos", pontos);
        valores.put("valorDaCompra", valor);
        valores.put("dataGeracao", dataString);
        valores.put("idEmpresa", usuarioEmpresa.getId());

        bd.inserir("codigopontos", valores);
    }

    public CodigoPontos validarCodigoPontos(String codigo){
        CodigoPontos cd = getCodigoPontos(codigo);

        if(cd == null || cd.foiValidado())
            return null;
        else {
            cd.validar();

            ContentValues valores = new ContentValues();
            valores.put("validado", 1);

            bd.atualizar("codigopontos", valores, "WHERE=" + codigo);

            return cd;
        }
    }
}
