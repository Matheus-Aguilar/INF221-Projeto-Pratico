package android.teste.matheusaguilar.inf221_projeto_pratico.Model;

public class CodigoPontos {
    /*Os códigos possuem mais atributos, mas, para esse conjunto de testes
     * apenas esses são relevantes*/

    private String codigo;
    private int numeroDePontos, valorDaCompra;
    private boolean validado;
    private Empresa empresa;

    public CodigoPontos(String _codigo, int _numeroDePontos, int _valorDaCompra, Empresa _empresa, boolean _validado){
        codigo = _codigo;
        numeroDePontos = _numeroDePontos;
        valorDaCompra = _valorDaCompra;
        empresa = _empresa;
        validado = _validado;
    }

    public int getPontos(){
        return numeroDePontos;
    }

    public Empresa getEmpresa(){
        return empresa;
    }

    public boolean foiValidado(){
        return validado;
    }

    public void validar(){
        validado = true;
    }
}
