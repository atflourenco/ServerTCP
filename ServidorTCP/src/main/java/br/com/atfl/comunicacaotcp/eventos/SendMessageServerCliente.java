
package br.com.atfl.comunicacaotcp.eventos;

public class SendMessageServerCliente {
    private String mensagem;

    public SendMessageServerCliente(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
