import javax.swing.*;

public class ClearButton extends JButton {
    public ClearButton(UserGUI frame) {
        GUIHelper.createStyledButton(GUIHelper.BUTON_TEXT_CLEAR_STREAMS, this);
        setBounds(20, 200, 200, 40);
        addActionListener(e -> {
            frame.getScrollPane().setVisible(false);
        });
    }
}
