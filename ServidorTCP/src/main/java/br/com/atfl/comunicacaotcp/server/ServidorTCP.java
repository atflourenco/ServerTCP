package br.com.atfl.comunicacaotcp.server;

import br.com.atfl.comunicacaotcp.eventos.EventoConexao;
import br.com.atfl.comunicacaotcp.eventos.EventoMensagem;
import br.com.atfl.comunicacaotcp.eventos.SendMessageServerCliente;
import br.com.atfl.comunicacaotcp.model.Mensagem;
import br.com.atfl.comunicacaotcp.model.Usuario;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServidorTCP implements Runnable {

    private int porta;
    private ServerSocket serverSocket;
    private Socket socket;
    private Thread threadServer;
    private List<ClienteThread> clientes = new ArrayList<>();
    private EventBus eventBus;
    private ServidorSendThread servidorSendThread;
    private static Map<String, Usuario> usuariosOnLine = new HashMap<String, Usuario>();
    private static Map<String, List<Mensagem>> mensagensUsuario = new HashMap<String, List<Mensagem>>();
    private boolean running  = false;

    public ServidorTCP(int porta, EventBus eventBus) {
        this.porta = porta;
        this.eventBus = eventBus;
        this.eventBus.register(this);
        servidorSendThread = new ServidorSendThread(eventBus);
    }

    public static Map<String, Usuario> getUsuariosOnLine() {
        return usuariosOnLine;
    }

    public static Map<String, List<Mensagem>> getMensagensUsuario() {
        return mensagensUsuario;
    }

    
    
    public void start() {
        threadServer = new Thread(this);
        threadServer.start();
        running = true;
        System.out.println("Server is runnning.....");
    }

    public void stop() {
        System.out.println("Server is stopping.....");
        try {
            for (ClienteThread cliente : clientes) {
                if (cliente.isAlive()) {
                    cliente.interrupt();
                    cliente.close();
                }
            }
            threadServer.interrupt();
            serverSocket.close();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(porta);
            servidorSendThread.start();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        while (running) {
            try {
                socket = serverSocket.accept();
                socket.setSoTimeout(5000);
                socket.setKeepAlive(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ClienteThread clienteThread = new ClienteThread(socket,this.eventBus);
            clienteThread.start();
            clientes.add(clienteThread);
        }
    }

    @Subscribe
    public void sendMessageCliente(SendMessageServerCliente message){     
        for(ClienteThread cliente: clientes){
            cliente.sendMessage(message.getMensagem());
        }
    } 
    
    
    @Subscribe
    public void updateListOnline(EventoConexao evento){
        if(evento.isConectado()){
            usuariosOnLine.put(evento.getIp(), new Usuario(evento.getNome(), true, evento.getIp()));
        }else{
            usuariosOnLine.get(evento.getIp()).setConectado(false);
            usuariosOnLine.remove(evento.getIp());
        }
        System.out.println("Online: " + usuariosOnLine.size());
    }

    @Subscribe
    public void updateMensagens(EventoMensagem evento){
        if(mensagensUsuario.containsKey(evento.getIp())){ 
            mensagensUsuario.get(evento.getIp()).add(evento.getMensagem());
        }else{
            List<Mensagem> ms = new ArrayList<>();
            ms.add(evento.getMensagem());
            mensagensUsuario.put(evento.getIp(), ms);
        }
        System.out.println("Mensagens: " + mensagensUsuario.get(evento.getIp()).size());
    }
}
