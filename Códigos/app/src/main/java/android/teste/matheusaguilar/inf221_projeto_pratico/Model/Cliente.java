package android.teste.matheusaguilar.inf221_projeto_pratico.Model;

public class Cliente {


    /*O cliente possui mais atributos, mas, para esse conjunto de testes
    * apenas esses são relevantes*/
    private int id, foto;
    private String nome;

    public Cliente(int _id, String _nome, int _foto){
        id = _id;
        nome = _nome;
        foto = _foto;
    }

    public int getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public int getFoto(){
        return foto;
    }
}
