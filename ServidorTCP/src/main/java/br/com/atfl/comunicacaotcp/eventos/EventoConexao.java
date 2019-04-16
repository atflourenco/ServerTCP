/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atfl.comunicacaotcp.eventos;

/**
 *
 * @author Anderson
 */
public class EventoConexao {
    
    String ip;
    String nome;
    boolean conectado;

    public EventoConexao(String ip, String nome, boolean conectado) {
        this.ip = ip;
        this.nome = nome;
        this.conectado = conectado;
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }
    
}
