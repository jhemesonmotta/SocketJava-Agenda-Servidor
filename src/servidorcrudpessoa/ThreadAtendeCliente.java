package servidorcrudpessoa;
import Classes.Mensagem;
import Classes.Pessoa;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadAtendeCliente extends Thread{
    Socket cSocket; //socket conectado com clientes
    String opcao;
    PrintStream EnviaClient;       
    BufferedReader RecebClient;
    static ArrayList<Pessoa> pessoas = new ArrayList();
    
    public ThreadAtendeCliente(Socket cSocket){
        this.cSocket = cSocket;
    }
    
    public void run(){
        try {
            System.err.println("Trhead criada!!!!");
            ObjectOutputStream envia = null;
            ObjectInputStream recebe = null;
            
            System.err.println("ch 1");
            recebe = new ObjectInputStream(cSocket.getInputStream());
            System.err.println("ch 2");
                Mensagem msg = (Mensagem) recebe.readObject();
                System.err.println("Recebido!!!!");
                opcao = msg.getAcao();
                System.err.println("opcao: " + opcao);

                if(opcao.equalsIgnoreCase("Inserir")){
                    if(cadastrar(msg.getPessoa())){
                        envia = new ObjectOutputStream(cSocket.getOutputStream());
                        envia.writeObject(msg);
                        //manda ao cliente a pessoa e a acao que chegaram
                    }
                    else{
                        Mensagem deuRuim = new Mensagem();
                        deuRuim.setPessoa(new Pessoa());
                        deuRuim.setAcao("Erro");
                        
                        envia = new ObjectOutputStream(cSocket.getOutputStream());
                        envia.writeObject(deuRuim);
                    }
                }
                else if(opcao.equalsIgnoreCase("Pesquisar")){
                    Pessoa pessoaRetornada = pesquisar(msg.getPessoa());
                    if(!pessoaRetornada.getNome().equalsIgnoreCase("erro")){
                        msg.setPessoa(pessoaRetornada);
                        envia = new ObjectOutputStream(cSocket.getOutputStream());
                        envia.writeObject(msg);
                        //manda ao cliente a pessoa retornada e a acao que chegou
                    }
                    else{
                        Mensagem deuRuim = new Mensagem();
                        deuRuim.setPessoa(new Pessoa());
                        deuRuim.setAcao("Erro");
                        
                        envia = new ObjectOutputStream(cSocket.getOutputStream());
                        envia.writeObject(deuRuim);
                        
                    }
                }
                else if(opcao.equalsIgnoreCase("Alterar")){
                    Pessoa pessoaRetornada = alterar(msg.getPessoa());
                    if(!pessoaRetornada.getNome().equalsIgnoreCase("erro")){
                        msg.setPessoa(pessoaRetornada);
                        envia = new ObjectOutputStream(cSocket.getOutputStream());
                        envia.writeObject(msg);
                        //manda ao cliente a pessoa retornada e a acao que chegou
                    }
                    else{
                        Mensagem deuRuim = new Mensagem();
                        deuRuim.setPessoa(new Pessoa());
                        deuRuim.setAcao("Erro");
                        
                        envia = new ObjectOutputStream(cSocket.getOutputStream());
                        envia.writeObject(deuRuim);
                    }
                }
                else if(opcao.equalsIgnoreCase("Excluir")){
                    if(excluir(msg.getPessoa())){
                        envia = new ObjectOutputStream(cSocket.getOutputStream());
                        envia.writeObject(msg);
                        //manda ao cliente a pessoa e a acao que chegaram
                    }
                    else{
                        Mensagem deuRuim = new Mensagem();
                        deuRuim.setPessoa(new Pessoa());
                        deuRuim.setAcao("Erro");
                        
                        envia = new ObjectOutputStream(cSocket.getOutputStream());
                        envia.writeObject(deuRuim);
                    }
                }
                else{
                    Mensagem deuRuim = new Mensagem();
                    deuRuim.setPessoa(new Pessoa());
                    deuRuim.setAcao("Erro");

                    envia = new ObjectOutputStream(cSocket.getOutputStream());
                    envia.writeObject(deuRuim);
                    //manda ao cliente msg com ação "ação inválida"
                }
                envia.close();
                recebe.close();
                cSocket.close();
                
        } catch (Exception ex) {
            System.err.println("erro:" + ex.getMessage());
        }
    }
    
    public boolean cadastrar(Pessoa p){
        try{
            synchronized(pessoas){
                pessoas.add(p);
            }
            System.out.println("lista apos cadastro:" + pessoas.toString());
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
    
    public boolean excluir(Pessoa pessoa){
        for(Pessoa p : pessoas){
            if(p.getNome().equalsIgnoreCase(pessoa.getNome())){
                synchronized(pessoas){
                    pessoas.remove(p);
                }
                return true;
            }
        }
        return false;
    }
    
     public Pessoa pesquisar(Pessoa pessoa){
         try{
            for(Pessoa p : pessoas){
               if(p.getNome().equalsIgnoreCase(pessoa.getNome())){
                   pessoa.setIdade(p.getIdade());
                   pessoa.setSexo(p.getSexo());
                   return pessoa;
               }
           }
           return new Pessoa("erro", 0, "erro");
         }
         catch(Exception ex){
            return new Pessoa("erro", 0, "erro");
         }
    }
     
     public Pessoa alterar(Pessoa pessoa){
        for(Pessoa p : pessoas){
            if(p.getNome().equalsIgnoreCase(pessoa.getNome())){
                synchronized(pessoas){
                    p.setIdade(pessoa.getIdade());
                    p.setSexo(pessoa.getSexo());
                }
                return pessoa;
            }
        }
        return new Pessoa("erro", 0, "erro");
    }
}
