package android.teste.matheusaguilar.inf221_projeto_pratico.Model;

public class PontosCliente {
    private Cliente cliente;
    private Empresa empresa;
    private int total;

    public PontosCliente(Cliente c, Empresa e, Integer t){
        cliente = c;
        empresa = e;
        total = t;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Empresa getEmpresa(){
        return empresa;
    }

    public int getTotal(){
        return total;
    }

    public void adicionarPontos(int valor){
        ControladoraFachadaSingleton controladora = ControladoraFachadaSingleton.getInstance();
        controladora.adicionarPontos(cliente, empresa, valor);
    }

    public void removerPontos(int valor){
        ControladoraFachadaSingleton controladora = ControladoraFachadaSingleton.getInstance();
        controladora.removerPontos(cliente, empresa, valor);
    }
}
