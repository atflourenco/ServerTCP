/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atfl.comunicacaotcp.model;

/**
 *
 * @author Anderson
 */
public class Usuario {
    private String nome;
    private boolean conectado;
    private String ip;

    public Usuario(String nome, boolean conectado, String ip) {
        this.nome = nome;
        this.conectado = conectado;
        this.ip = ip;
    }

    public Usuario() {
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
