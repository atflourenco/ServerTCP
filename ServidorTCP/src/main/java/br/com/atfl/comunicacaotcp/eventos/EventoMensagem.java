/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atfl.comunicacaotcp.eventos;

import br.com.atfl.comunicacaotcp.model.Mensagem;

/**
 *
 * @author Anderson
 */
public class EventoMensagem {
    
    private String ip;
    private Mensagem mensagem;

    public EventoMensagem(String ip, Mensagem mensagem) {
        this.ip = ip;
        this.mensagem = mensagem;
    }

    
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Mensagem getMensagem() {
        return mensagem;
    }

    public void setMensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
    }
    
    
    
}
