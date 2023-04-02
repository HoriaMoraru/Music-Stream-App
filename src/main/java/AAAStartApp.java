public class AAAStartApp {
    public static void main(String[] args) {
        GUIHelper.setWrapper(new LoginGUI());
        GUIHelper.getWrapper().display();
    }
}
