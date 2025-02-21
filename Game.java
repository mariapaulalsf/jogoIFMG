import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private CommandWords commandWords;
    private Stack<Room> roomHistory;
    private Player player;
    private Player jogador;
    private Room invisivel;
    private Room professor;
    private Room invisivel2;
    private Room invisivel3;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        commandWords = new CommandWords();
        roomHistory = new Stack<>();
        player = new Player("Jogador", currentRoom);  
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room ladofora, terreo, primeiroa, segundoa, biblioteca, cantina, corredor, sala1, sala2, sala3, sala4;
        
        // create the rooms
        ladofora = new Room("Lado de fora da faculdade em frente a entrada");
        terreo = new Room("Térreo da faculdade");
        primeiroa  = new Room("Primeiro andar");
        segundoa = new Room("Segundo andar");
        biblioteca = new Room("Biblioteca");
        cantina = new Room ("Cantina");
        corredor = new Room ("Corredor (elevador em manutenção)");
        sala1 = new Room ("Sala de aula");
        sala2 = new Room ("Sala de aula");
        sala3 = new Room ("");
        sala4 = new Room ("");
        invisivel = sala3;
        professor = sala2;
        invisivel2 = sala4;
        invisivel3 = terreo;
        
        biblioteca.addItem(new Item("papeis", 2));//papéis para proposta
        cantina.addItem(new Item("coxinha", 1));
        sala3.addItem(new Item("papel", 5));// papel assinado pelos alunos
        sala1.addItem(new Item("caneta", 1));
        sala4.addItem(new Item("documento", 10));//papel assinado pelo professor
        
        ladofora.setExits("frente", biblioteca);
        ladofora.setExits("esquerda", cantina);
        cantina.setExits ("tras", ladofora);
        biblioteca.setExits("direita", terreo);
        biblioteca.setExits("tras", ladofora);
        terreo.setExits ("frente", corredor);
        terreo.setExits ("direita", primeiroa);
        terreo.setExits ("tras", biblioteca);
        corredor.setExits ("tras", terreo);
        primeiroa.setExits ("frente", sala1);
        primeiroa.setExits ("direita", segundoa);
        primeiroa.setExits ("esquerda", terreo);
        sala1.setExits ("tras", primeiroa);
        segundoa.setExits ("frente", sala2);
        segundoa.setExits ("esquerda", primeiroa);
        sala2.setExits ("tras", segundoa);
        
        currentRoom = ladofora;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Obrigado por jogar!");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("!Você é um aluno que está na tentativa de fundar uma atletica em sua faculdade, para isso você deve reunir os documentos necessários!");
        System.out.println("Mais um dia de aula, e hoje é o dia perfeito para você finalmente conseguir as assinaturas para finalmente fundar a atletica");
        System.out.println("e para isso é preciso conseguir as assinaturas dos alunos e de um professor.");
        System.out.println("O que fazer: ");
        System.out.println("Pegue os papeis na biblioteca e consiga as assinaturas (Alunos e professor), e devolva na secretaria;");
        System.out.println();
        System.out.println("Digite 'ajuda' se você precisar.\nPara andar apenas digite andar e a direção.");
        System.out.println();
        printLocationInfo();
        
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("Não sei o que isso significa...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("ajuda")) {
            printHelp();
        }
        else if (commandWord.equals("andar")) {
            goRoom(command);
        }
        else if (commandWord.equals("sair")) {
            wantToQuit = quit(command);
        }
         else if (commandWord.equals("look")) {
            look(command);
        } else if (commandWord.equals("eat")) {
            eat(command);
        } else if (commandWord.equals("voltar")) {
            back(command);
        } else if (commandWord.equals("pegar")) {
            pegar(command);
        } else if (commandWord.equals("soltar")) {
            soltar(command);
        } else if (commandWord.equals("inventario")){
            inventario(command);
        }
        else if (commandWord.equals("usar")){
            usar(command);
        }
        return wantToQuit;
    }
    // implementations of user commands:
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Se perdeu né");
        System.out.println("e olha que nem é tão grande a faculdade...");
        System.out.println();
        System.out.println("Seus comandos são:");
        for(String command : CommandWords.getCommands()){
            System.out.println(command);
        }
        //
    }
    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("andar onde?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("Tem nada ai!");
        }
        else {
            roomHistory.push(currentRoom);
            currentRoom = nextRoom;
            player.setCurrentRoom(nextRoom);
            printLocationInfo();
        }
    }
    private void look(Command command){
        System.out.println(currentRoom.getDescription());
        currentRoom.listItens();
    }
    private void eat(Command command){
        if (!command.hasSecondWord()) {
            System.out.println("Comer o quê?");
            return;
        }
        String itemName = command.getSecondWord();
        Item item = player.getPesquisa(itemName);
        if (item == null){
            System.out.println("O que devo comer?");
        }
        else{
           System.out.println("Você já comeu e não está com fome mais");
           player.soltar(item);
        }
    }
     private void back(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Voltar o que?");
            return;
        }
        
        if (roomHistory.isEmpty()) {
            System.out.println("Não da para voltar, não há mais locais anteriores.");
        } else {
            currentRoom = roomHistory.pop(); 
            printLocationInfo();
        }
    }
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Sair o que?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    private void pegar(Command command){
        if (!command.hasSecondWord()) {
            System.out.println("Pegar o quê?");
            return;
        }
        String itemName = command.getSecondWord();
        Room currentRoom = player.getCurrentRoom();
        Item item = currentRoom.getItem(itemName);
        if (item == null) {
            System.out.println("Não há nenhum item com esse nome aqui.");
        } else {
            player.pegar(item);
            currentRoom.removeItem(item);
            System.out.println("Você pegou " + item.getDescription());
        }
    }
    private void soltar(Command command){
         if (!command.hasSecondWord()) {
            System.out.println("Soltar o quê?");
        }
        String itemName = command.getSecondWord();
        Room currentRoom = player.getCurrentRoom();
        Item item = player.getPesquisa(itemName);
        if(item == null) {
            System.out.print ("Esse item não está no seu inventário");
        }else{
            System.out.println ("Você Soltou " + item.getDescription());
            player.soltar(item);
            currentRoom.addItem(item);
        }
        }
    private void usar (Command command){
         if (!command.hasSecondWord()) {
            System.out.println("Usar o quê?");
        }
        String itemName = command.getSecondWord();
        Item item = player.getPesquisa(itemName);
        Room room = invisivel; 
        Room porta = invisivel2;
        Room currentRoom = player.getCurrentRoom();
        if (item == null){
            System.out.print ("Esse item não está no seu inventário");
        }else {
            if(itemName.equals("caneta")){
                Item verificar = player.getPesquisa ("papeis");
                if (verificar == null){
                    System.out.println ("Não há nada para usar a caneta!");
                }else{
                    player.soltar(item);
                    player.soltar(verificar);
                    String novoitem = "papel";
                    item = room.getItem(novoitem);
                    player.pegar(item);
                }
            }
            else if (itemName.equals("coxinha")){
                System.out.println ("Esse item não se usa, é um alimento. Tente outro comando!");
            }
            else if (itemName.equals ("papel") && currentRoom == professor){
                player.soltar(item);
                String novoitem = "documento";
                item = porta.getItem (novoitem);
                player.pegar(item);
            }
            else{
                System.out.println ("Esse item não possivel ser utilizado!");
            }
        }
    }
    private void inventario(Command command){
        System.out.println ("Inventário: ");
        player.listInventory();
    }
    public void printLocationInfo (){
        System.out.println(currentRoom.getLongDescription());
        if (player.getCurrentRoom() == professor){
            System.out.println ("Você deu de cara com o Professor Conrado, por um momento você se lembra que precisa de algo...");
        }
        String itemName = "documento";
        Item item = player.getPesquisa(itemName);
        boolean secret= false;
        if (item == null){
            secret = false;
        }
        else {
            secret = true;
        }
        if(player.getCurrentRoom() == invisivel3 && secret){
            player.soltar (item);
            System.out.println ("A secretária pegou os papeis e te liberou, agora pode ir para casa tranquilo(a)!\nSua atletica foi fundada, Seja Bem Vinda Computaria!");
        }
    }
}