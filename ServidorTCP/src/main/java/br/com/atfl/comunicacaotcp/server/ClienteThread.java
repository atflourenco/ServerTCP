
package br.com.atfl.comunicacaotcp.server;

import br.com.atfl.comunicacaotcp.eventos.EventoConexao;
import br.com.atfl.comunicacaotcp.eventos.EventoMensagem;
import br.com.atfl.comunicacaotcp.model.Mensagem;
import com.google.common.eventbus.EventBus;
import com.google.gson.Gson;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClienteThread extends Thread{
    private Socket socket;
    private InputStream inputStream;
    private DataOutputStream out;
    private EventBus eventBus;
    private boolean running = false;
    
    public ClienteThread(Socket socketCliente, EventBus event) {
        this.socket = socketCliente;
        this.eventBus = event;
        this.eventBus.register(this);
        this.running = true;
        try {
            if(!socket.isClosed()){                
                inputStream = socket.getInputStream();
                out = new DataOutputStream(socket.getOutputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        byte dados[];
        String dadosRecebidos = null;
        while(running && !socket.isClosed()){
            try {
                int tam = inputStream.available();
                if(tam>0){
                    dados = new byte[tam];
                    inputStream.read(dados);
                    dadosRecebidos = new String(dados);
                    Gson gson = new Gson();
                    Mensagem m = gson.fromJson(dadosRecebidos, Mensagem.class);
                    if(m!=null){
                        sendMessage("ACK");
                        if(m.getMensagem().equals("CONNECT")){
                            eventBus.post(new EventoConexao(m.getIp(),m.getAlias(),true));
                        }else{                            
                            if(m.getMensagem().equals("DISCONNECT")){
                                eventBus.post(new EventoConexao(m.getIp(),m.getAlias(),false));
                            }
                            else{
                                eventBus.post(new EventoMensagem(m.getIp(), m));
                            }
                        }
                    }
                    System.out.println("Dados recebidos: " + m);                    
                }                    
            } catch (Exception e) {
                running = false;
                e.printStackTrace();
            }
        }
    }
    
    public void sendMessage(String message){
        try {
            out.write(message.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    public void close() {
        try {
            if(socket!=null){
                running = false;
                inputStream.close();
                out.close();
                socket.close();
            }
        } catch (IOException e) {
            running = false;
            e.printStackTrace();
        }
    }    
}
