package br.com.atfl.comunicacaotcp.beans;

import javafx.beans.property.SimpleStringProperty;

public class Usuario {

    private SimpleStringProperty ip;
    private SimpleStringProperty nome;

    public Usuario () {
    }

	public Usuario (String s1, String s2) {

        ip = new SimpleStringProperty(s1);
        nome = new SimpleStringProperty(s2);
    }

    public String getIp() {
	
        return ip.get();
    }
    public void setIp(String s) {
        ip.set(s);
    }
	
    public String getNome() {
	
        return nome.get();
    }
    public void setNome(String s) {
	
        nome.set(s);
    }
	
    @Override
    public String toString() {
        return ("Usuario: " + nome.get() + " - Endereço:" + ip.get());
    }
}