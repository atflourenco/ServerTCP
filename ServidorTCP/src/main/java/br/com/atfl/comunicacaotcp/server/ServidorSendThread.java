
package br.com.atfl.comunicacaotcp.server;

import br.com.atfl.comunicacaotcp.eventos.SendMessageServerCliente;
import com.google.common.eventbus.EventBus;
import java.util.Scanner;

public class ServidorSendThread extends Thread{
    private EventBus eventBus;

    public ServidorSendThread(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }
 
    @Override
    public void run(){
        String dados = "";
        Scanner r = new Scanner(System.in);
        while(!isInterrupted()){
            System.out.print("Digite os dados: " );  
            dados = r.nextLine();
            if(dados.toUpperCase().equals("FIM")){
                break;
            }
            eventBus.post(new SendMessageServerCliente(dados));
        }
    }    
}
