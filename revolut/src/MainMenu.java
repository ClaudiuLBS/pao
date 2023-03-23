public final class MainMenu {
    private static MainMenu instance;

    private MainMenu() {
    }

    public static MainMenu getInstance(){
        if(instance == null){
            instance = new MainMenu();
        }
        return instance;
    }

    public void Register(){

    }

    public void LogIn(){

    }

    public void Menu(){
        System.out.println("\n\n\n\n\n\tWelcome\n");
        System.out.println("\n\n\n\n\n\tLogin");
        System.out.println("\n\n\n\n\n\tRegister");
    }



}
