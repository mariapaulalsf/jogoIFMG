import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.lang.String;


/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    public String description;
    private HashMap <String, Room> saidas;
    private List<Item> itens;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        this.saidas = new HashMap<>();
        this.itens = new ArrayList<>();
        
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(String direction, Room neighbor) {
        saidas.put(direction,neighbor);
    }
    public void addItem(Item item) {
        itens.add(item);
    }
    /**
    * @return The description of the room.
    */
    public String getDescription()
       {
            return description;
        }    
     public String getLongDescription() {
        String description = "Você está no(a) " + this.description + ".\n" + getExitString();
        
        if (!itens.isEmpty()) {
            description += "\nItens presentes:"; 
            for (Item item : itens) {
                description += " " + item.getDescription() + " (Peso: " + item.getWeight() + ")";
            }
        } else {
            description += "";
        }

        return description;
    }
    public Room getExit(String direction)
    {
        return saidas.get(direction);
    }
    /**
    * Retorna uma descrição das saídas deste Room,
    * por exemplo, "Exits: north west".
    * @return Uma descrição das saídas disponíveis.
    */
    public String getExitString() {
        String exitss = "Caminhos:";
        Set<String> keys = saidas.keySet();
        for (String exit : keys) {
            exitss += " " + exit;
        }
        return exitss;
    }
    public void listItens() {
        if (itens.isEmpty()) {
            System.out.println("Não há itens nessa sala");
        } else {
            System.out.println("Itens presentes:");
            for (Item item : itens) {
                System.out.println(item); 
            }
        }
    }
    public Item getItem(String itemName) {
        
        for (Item item : itens) {
            if (item.getDescription().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }
    public void removeItem(Item item) {
        itens.remove(item);
    }
}
