import java.util.ArrayList;
import java.util.List;

public class Player {
    private Room currentRoom;
    private String nome;
    private ArrayList <Item> inventario;
    private int cont;

    public Player(String nome, Room currentRoom) {
        this.nome = nome;
        this.currentRoom = currentRoom;
        inventario = new ArrayList <>();
    }
    public void pegar(Item item){
        if (item.getWeight() < 11){
            inventario.add(item);
            String itens = item.getDescription();
            System.out.println (item.getDescription() + " foi adicionado ao seu inventário");
            if (itens == "papeis"){
                System.out.println ("Voce pegou os papeis necessários para as assinaturas dos alunos, ache a caneta e peça para assinarem");
            }
            else if (itens == "caneta"){
                System.out.println ("Você tem todos os itens necessários para as assinaturas, utilize-os com sabedoria");
            }
            else if(itens == "papel"){
                System.out.println();
                System.out.println("A sala em que você estava tinha alguns alunos que já foram o suficiente para conseguir as assinaturas.");
                System.out.println ("Com o papel asinado em mãos só resta o ultimo passo para ter toda a documentação.\nEncontre o professor e peça a assinatura");
            }
            else if (itens == "documento"){
                System.out.println ();
                System.out.println("Você conseguiu todos os itens necessários, entregue a documentação na secretaria que fica no terreo");
                
            }
        } else{
            System.out.println ("você está pesado, solte algum item para pegar esse");
        }
    }
    public Item getPesquisa (String itemName){
        for (Item item : inventario) {
            if (item.getDescription().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }
    public void soltar(Item item){
        System.out.println (item.getDescription() + " foi removido do seu inventário");
        cont = 0;
        for (Item itens : inventario){
            if (itens.equals(item)){
                break;
            }
            else{
                cont++;
            }
        }
        inventario.remove(cont);
    }
    public Room getCurrentRoom() {
        return currentRoom;
    }
    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }
    public String getNome() {
        return nome;
    }
    public String listInventory() {
        if (inventario.isEmpty()) {
            return "Seu inventário está vazio.";
        } else {
            System.out.println("Itens no seu inventário:");
            for (Item item : inventario) {
                System.out.println(item);
            }
        }   
        return inventario.toString();
    }
}
