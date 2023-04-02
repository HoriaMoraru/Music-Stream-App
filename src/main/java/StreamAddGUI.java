import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class StreamAddGUI extends JFrame implements GUI {

    private StreamerGUI streamerGUI;
    private JButton okButton;
    private JButton cancelButton;
    private JLabel titleLabel;
    private MyTextField streamField;
    private JPanel panel;

    public StreamAddGUI(StreamerGUI streamerGUI) {
        super("Add Stream");
        this.streamerGUI = streamerGUI;
        createView();
        fit(this);
    }

    private void createView() {

        panel = createBackgroundPopUp(300, 100, this);
        MyTextField streamField = new MyTextField();
        streamField.setBounds(20, 80, 200, 40);
        panel.add(streamField);
        streamField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        add(panel);
    }

    public void display() {
        setVisible(true);
    }

    public static void main(String[] args) {
        StreamAddGUI streamAddGUI = new StreamAddGUI(null);
        streamAddGUI.display();
    }
}
