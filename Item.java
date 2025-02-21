/**
 * Escreva uma descrição da classe Itens aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class Item
{
    private String description;
    private int peso;
    public Item(String description, int peso) {
        this.description = description;
        this.peso = peso;
        
    }
    public String getDescription() {
        return description;
    }
    public int getWeight() {
        return peso;
    }
    public String toString() {
        return description + " (peso: " + peso + " kg)";
    }
}

