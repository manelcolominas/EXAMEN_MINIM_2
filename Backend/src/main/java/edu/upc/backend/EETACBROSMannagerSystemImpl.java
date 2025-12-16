package edu.upc.backend;

import edu.upc.backend.classes.*;
import edu.upc.backend.exceptions.InsufficientMoneyException;
import edu.upc.backend.exceptions.UserNotFoundException;
import edu.upc.backend.exceptions.UserOrPasswordInvalidException;
import edu.upc.backend.exceptions.UsernameAlreadyExistsException;

import java.util.*;

public class EETACBROSMannagerSystemImpl implements EETACBROSMannagerSystem {

    private static EETACBROSMannagerSystemImpl instance;

    private final List<User> users;
    private final List<Item> items;
    private final List<Team> teams;
    private final Map<Integer, Player> players;
    private final Map<Integer, Game> games;

    private EETACBROSMannagerSystemImpl() {
        this.users = new ArrayList<>();
        this.items = new ArrayList<>();
        this.teams = new ArrayList<>();

        this.players = new HashMap<>();
        this.games = new HashMap<>();

        // Add some users for testing
        User agente007 = new User("agente007", "Manel Colominas", "manel@gmail.com", "1234567Ab");
        agente007.setId(1);
        agente007.setCoins(100000000);
        users.add(agente007);


        User agente008 = new User("agente008", "Marc Colominas", "marc@gmail.com", "1234567Ab");
        agente008.setId(2);
        agente008.setCoins(100000000);
        users.add(agente008);

        User agente009 = new User("agente009", "Manel Colominas", "manel@gmail.com", "1234567Ab");
        agente009.setId(1);
        agente009.setCoins(100000000);
        users.add(agente009);


        User agente010 = new User("agente008", "Marc Colominas", "marc@gmail.com", "1234567Ab");
        agente010.setId(2);
        agente010.setCoins(100000000);
        users.add(agente010);

        // Add some items for testing
        Item calculator = new Item("Calculator", 200, 200, "📱", "Solve tricky math problems with ease.", 0);
        calculator.setId(1);
        items.add(calculator);

        Item laptop = new Item("Laptop", 200, 200, "💻", "Complete reports and projects efficiently.", 0);
        laptop.setId(2);
        items.add(laptop);

        Item notebook = new Item("Notebook", 150, 150, "📓", "Keep track of class notes and ideas.", 0);
        notebook.setId(3);
        items.add(notebook);

        Item pen = new Item("Pen", 100, 100, "🖊️", "Write down important formulas and reminders.", 0);
        pen.setId(4);
        items.add(pen);

        Item oldMobile = new Item("Old Mobile", 180, 180, "☎️", "Check messages and stay connected the old-school way.", 0);
        oldMobile.setId(5);
        items.add(oldMobile);

        Item energyDrink = new Item("Energy Drink", 120, 120, "🥤", "Boost your focus and stay awake during long study sessions.", 0);
        energyDrink.setId(6);
        items.add(energyDrink);

        Item headphones = new Item("Headphones", 160, 160, "🎧", "Concentrate on work and block out distractions.", 0);
        headphones.setId(7);
        items.add(headphones);

        Item backpack = new Item("Backpack", 200, 200, "🎒", "Carry all your items and tools wherever you go.", 0);
        backpack.setId(8);
        items.add(backpack);

        Item usbDrive = new Item("USB Drive", 100, 100, "💾", "Store and transport your important files easily.", 0);
        usbDrive.setId(9);
        items.add(usbDrive);

        Item coffee = new Item("Coffee", 100, 100, "☕", "Recharge your energy and stay productive.", 0);
        coffee.setId(10);
        items.add(coffee);

        // EXAMEN_MINIM_2

        Team team1 = new Team("Porxinos","https://cdn.pixabay.com/photo/2017/07/11/15/51/kermit-2493979_1280.png",  250);
        teams.add(team1);

        Team team2 = new Team("rey/reina de xxx’","https://cdn.pixabay.com/photo/2017/07/11/15/51/kermit-2493979_1280.png",200);
        teams.add(team2);

        Team team3 = new Team("Los Tiburones","https://cdn.pixabay.com/photo/2017/07/11/15/51/kermit-2493979_1280.png",180);
        teams.add(team3);

        Team team4 = new Team("Las Panteras","https://cdn.pixabay.com/photo/2017/07/11/15/51/kermit-2493979_1280.png",300);
        teams.add(team4);

        Team team5 = new Team("Dragones del Norte","https://cdn.pixabay.com/photo/2017/07/11/15/51/kermit-2493979_1280.png",275);
        teams.add(team5);

    }

    public static EETACBROSMannagerSystemImpl getInstance() {
        if (instance == null) {
            instance = new EETACBROSMannagerSystemImpl();
        }
        return instance;
    }

    @Override
    public List<Team> getTeamsRanking() {
        List<Team> teamsSorted = new ArrayList<>(teams); // copy
        teamsSorted.sort(Comparator.comparingInt(Team::getPoints).reversed());
        return teamsSorted;
    }



    @Override
    public void registerUser(User user) throws UsernameAlreadyExistsException {
        if (getUserByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        users.add(user);
    }

    @Override
    public List<User> getUsersListDatabase() {
        return new ArrayList<>(users);
    }

    @Override
    public List<Item> getItemList() {
        return new ArrayList<>(items);
    }

    @Override
    public User logIn(User user) throws UserOrPasswordInvalidException {
        User u = getUserByUsername(user.getUsername());
        if (u == null || !u.getPassword().equals(user.getPassword())) {
            throw new UserOrPasswordInvalidException("Invalid username or password");
        }
        return u;
    }

    @Override
    public void clear() {
        users.clear();
        items.clear();
        players.clear();
        games.clear();
    }

    @Override
    public void registerPurchase(BuyRequest buyRequest) throws UserNotFoundException, InsufficientMoneyException {
        User user = getUserById(buyRequest.getUserId());
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        // This is a simplified implementation. A real implementation would check item existence and price.
        // For now, we assume the client sends valid data.
        // We also assume the user has enough money.
    }

    @Override
    public List<Item> getUserItems(int userId) {
        // This needs a proper implementation based on how items are associated with users.
        // For now, returning an empty list.
        return new ArrayList<>();
    }

    @Override
    public User updateUserData(User user) throws UserNotFoundException {
        User existingUser = getUserById(user.getId());
        if (existingUser == null) {
            throw new UserNotFoundException("User not found");
        }
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        return existingUser;
    }

    @Override
    public void deleteUserData(int id) throws UserNotFoundException {
        User user = getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        users.remove(user);
    }

    @Override
    public void addPlayer(Player player) {
        players.put(player.getId(), player);
    }

    @Override
    public PlayerList getPlayerList() {
        PlayerList playerList = new PlayerList();
        for (Player player : players.values()) {
            playerList.add(player);
        }
        return playerList;
    }

    @Override
    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Player getPlayerById(int id) {
        return players.get(id);
    }

    @Override
    public User getUserById(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void createGame(int playerId) {
        // Simplified implementation
        games.put(playerId, new Game(playerId, 0));
    }

    @Override
    public Game findGame(int playerId) {
        return games.get(playerId);
    }

    @Override
    public void updateGame(Game game) {
        games.put(game.getId(), game);
    }

    @Override
    public void removeGame(int playerId) {
        games.remove(playerId);
    }
}
