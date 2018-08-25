package Classes;

import java.io.Serializable;

public class Mensagem implements Serializable {
    Pessoa pessoa;
    String acao;

    public Mensagem(Pessoa pessoa, String acao) {
        this.pessoa = pessoa;
        this.acao = acao;
    }

    public Mensagem() {
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    @Override
    public String toString() {
        return "Mensagem{" + "pessoa=" + pessoa + ", acao=" + acao + '}';
    }
}
