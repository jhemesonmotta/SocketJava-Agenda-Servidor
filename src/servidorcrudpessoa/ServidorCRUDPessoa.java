package servidorcrudpessoa;

import Classes.Pessoa;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorCRUDPessoa {
    public static void main(String[] args) {
        Socket cSocket; //socket conectado com clientes
        ServerSocket sSocket = null;
      	try {
            sSocket = new ServerSocket(7000);
      	}catch (IOException e) {
            System.err.println("Servidor nao pode ouvir a porta: 7000 ");
            System.exit(0);
      	}
      	
	while(true){
            try{
	        System.out.println("\n\nEsperando cliente:"+
                                    "Endereço IP do servidor: " + sSocket.getInetAddress());
	        cSocket = sSocket.accept();
                
                new ThreadAtendeCliente(cSocket).start();
                
            }catch(IOException e){
                System.err.println("Erro :"+e);
		System.exit(0);	  	
            }
        }  	
    }
}
