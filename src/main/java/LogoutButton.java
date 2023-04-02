import javax.swing.*;

public class LogoutButton extends JButton {

    public LogoutButton(UserGUI frame) {
        GUIHelper.createStyledButton(GUIHelper.BUTON_TEXT_LOGOUT, this);
        setBounds(20, 250, 200, 40);
        addActionListener(e -> {
            frame.dispose();
            GUIHelper.setWrapper(new LoginGUI());
            GUIHelper.getWrapper().display();
        });
    }

    public LogoutButton(StreamerGUI frame) {
        GUIHelper.createStyledButton(GUIHelper.BUTON_TEXT_LOGOUT, this);

        setBounds(20, 260, 200, 40);
        addActionListener(e -> {
            frame.dispose();
            GUIHelper.setWrapper(new LoginGUI());
            GUIHelper.getWrapper().display();
        });
    }
}