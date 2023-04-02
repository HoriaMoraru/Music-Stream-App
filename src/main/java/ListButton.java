import javax.swing.*;
import java.util.Map;

public class ListButton extends JButton {
    public ListButton(UserGUI frame) {
        GUIHelper.createStyledButton(GUIHelper.BUTON_TEXT_LIST_STREAMS, this);
        setBounds(20, 420, 200, 40);
        addActionListener(e -> {
            Map<Stream, Boolean> userStreams = frame.getUser().getStreams();
            String[] streamNames = userStreams.keySet().stream()
                    .map(Stream::getName)
                    .toArray(String[]::new);
            frame.updateStreamList(streamNames);
            frame.getScrollPane().setVisible(true);
        });
    }
}
