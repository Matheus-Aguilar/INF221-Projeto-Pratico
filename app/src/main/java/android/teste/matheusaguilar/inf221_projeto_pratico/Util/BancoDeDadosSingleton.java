package android.teste.matheusaguilar.inf221_projeto_pratico.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.teste.matheusaguilar.inf221_projeto_pratico.R;
import android.util.Log;

public final class BancoDeDadosSingleton {

    protected SQLiteDatabase db;
    private static final String NOME_BANCO = "cartoes_fidelidade_bd";
    private static BancoDeDadosSingleton INSTANCE;

    private final String[] SCRIPT_DATABASE_CREATE = new String[]{
            "CREATE TABLE cliente(" +
                    "idCliente INTEGER PRIMARY KEY, " +
                    "nome TEXT, " +
                    "email TEXT UNIQUE, " +
                    "CPF INTEGER UNIQUE, " +
                    "senha TEXT, " +
                    "telefone INTEGER, " +
                    "endereco TEXT, " +
                    "foto INTEGER, " +
                    "dataDeNascimento TEXT" +
                    "); ",
            "CREATE TABLE empresa(" +
                    "idEmpresa INTEGER PRIMARY KEY, " +
                    "nome TEXT, " +
                    "email TEXT UNIQUE, " +
                    "CPF INTEGER, " +
                    "CNPJ TEXT UNIQUE, " +
                    "senha TEXT, " +
                    "telefone INTEGER, " +
                    "endereco TEXT, " +
                    "redesSociais TEXT, " +
                    "foto INTEGER, " +
                    "pontosCompra INTEGER DEFAULT 0, " +
                    "pontosValor INTEGER DEFAULT 0, " +
                    "precoPonto INTEGER, " +
                    "inadimplente INTEGER DEFAULT 0" +
                    "); ",
            "CREATE TABLE codigopontos(" +
                    "codigo TEXT PRIMARY KEY, " +
                    "numeroDePontos INTEGER, " +
                    "validado INTEGER DEFAULT 0, " +
                    "valorDaCompra REAL, " +
                    "dataGeracao TEXT, " +
                    "idEmpresa INTEGER, " +
                    "CONSTRAINT fk_codigopontos_empresa FOREIGN KEY (idEmpresa) REFERENCES empresa(idEmpresa) " +
                    "); ",
            "CREATE TABLE pontoscliente(" +
                    "idCliente INTEGER, " +
                    "idEmpresa INTEGER, " +
                    "totalPontos INTEGER DEFAULT 0, " +
                    "PRIMARY KEY(idCliente, idEmpresa), " +
                    "CONSTRAINT fk_pontoscliente_cliente FOREIGN KEY (idCliente) REFERENCES cliente(id), " +
                    "CONSTRAINT fk_pontoscliente_empresa FOREIGN KEY (idEmpresa) REFERENCES empresa(id) " +
                    "); ",
            "INSERT INTO cliente(idCliente, nome, email, CPF, senha, telefone, endereco, foto, dataDeNascimento) VALUES " +
                    "(1, 'Arnaldo', 'arnaldo@gmail.com', 12345678900, 'a123', 33988782323, 'Rua Euclides Dutra, 37'," + R.drawable.foto_pessoa + ", '12-12-1990');",
            "INSERT INTO empresa(idEmpresa, nome, email, CPF, CNPJ, senha, telefone, endereco, redesSociais, foto, pontosCompra, pontosValor, precoPonto, inadimplente) VALUES " +
                    "(1, 'Lojas Laranja', 'lojaslaranja@gmail.com', '98765432100', '13579246801234', '1abc', '33988613490', 'Rua Manoel Saraiva, 42', NULL, " + R.drawable.foto_empresa + ", 0, 1, 1, 0);",
    };

    private BancoDeDadosSingleton(){
        Context ctx = MyApp.getContext();

        if(ctx == null){
            Log.e("NULL", "NOME");
        }

        db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);

        Cursor c = buscar("sqlite_master", null, "type = 'table'", "");

        if(c.getCount() == 1){
            for(int i = 0; i < SCRIPT_DATABASE_CREATE.length; i++){
                db.execSQL(SCRIPT_DATABASE_CREATE[i]);
            }
        }

        c.close();
    }

    public static BancoDeDadosSingleton getInstance(){
        if(INSTANCE == null){
            INSTANCE = new BancoDeDadosSingleton();
        }
        return INSTANCE;
    }

    public long inserir(String tabela, ContentValues valores){
        long id = db.insert(tabela, null, valores);
        return id;
    }

    public int atualizar(String tabela, ContentValues valores, String where){
        int cnt = db.update(tabela, valores, where, null);
        return cnt;
    }

    public int deletar(String tabela, String where){
        int cnt = db.delete(tabela, where, null);
        return cnt;
    }

    public Cursor buscar(String tabela, String colunas[], String where, String orderBy){
        Cursor c;

        if(where.equals("")){
            c = db.query(tabela, colunas, null, null, null, null, orderBy);
        }
        else{
            c = db.query(tabela, colunas, where, null, null, null, orderBy);
        }

        return c;
    }

    public void abrir(){
        Context ctx = MyApp.getContext();
        db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
    }

    public void fechar(){
        if(db != null){
            db.close();
        }
    }
}
