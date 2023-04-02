import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;


public class UserGUI extends JFrame implements GUI {

    private final User user;
    private JButton logoutButton;
    private JButton listButton;
    private JButton clearButton;
    private JButton recommendButton;
    private JButton surpriseButton;
    private JPanel panel;
    private JList<String> streamList;
    private JScrollPane scrollPane;
    private JLabel overviewLabel;

    public UserGUI(User user) {
        super("User");
        this.user = user;

        createView();
        fit(this);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JList<String> getStreamList() {
        return streamList;
    }

    public User getUser() {
        return user;
    }

    public void display() {
        setVisible(true);
    }

    private void createView() {

        panel = createBackground(1000, 500, this);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setForeground(GUIHelper.COLOR_OUTLINE);
        panel.add(separator);
        separator.setBounds(400, 70, 1, 300);

        JLabel titleLabel = new JLabel("Welcome back, " + user.getName());
        titleLabel.setFont(GUIHelper.FONT_USER_TITLE);
        titleLabel.setForeground(Color.white);
        panel.add(titleLabel);
        titleLabel.setBounds(20, 20, 500, 40);

        JLabel idLabel = new JLabel("ID: " + user.getId());
        idLabel.setFont(GUIHelper.FONT_GENERAL_UI);
        idLabel.setForeground(Color.white);
        panel.add(idLabel);
        idLabel.setBounds(20, 80, 200, 40);

        JLabel nameLabel = new JLabel("Name: " + user.getName());
        nameLabel.setFont(GUIHelper.FONT_GENERAL_UI);
        nameLabel.setForeground(Color.white);
        panel.add(nameLabel);
        nameLabel.setBounds(20, 120, 200, 40);

        Map<Integer, Stream> streams = Database.getInstance().getStreams();
        Map<Stream, Boolean> userStreams = user.getStreams();
        for(Stream stream : streams.values()) {
            userStreams.put(stream, false);
        }
        String[] streamNames = userStreams.keySet().stream()
                                      .map(Stream::getName)
                                      .toArray(String[]::new);
        streamList = new JList<>(streamNames);
        streamList.setFont(GUIHelper.FONT_GENERAL_UI);
        streamList.setForeground(Color.white);
        streamList.setBackground(GUIHelper.COLOR_BACKGROUND);
        streamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        streamList.setLayoutOrientation(JList.VERTICAL);
        streamList.setVisibleRowCount(-1);
        streamList.setSelectionBackground(GUIHelper.COLOR_OUTLINE);
        streamList.setSelectionForeground(Color.white);

        scrollPane = new JScrollPane(streamList);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(5, 5));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(GUIHelper.COLOR_BACKGROUND);

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem listenItem = new JMenuItem("Listen");
        listenItem.addActionListener(e -> {
            Database data = Database.getInstance();
            Stream stream = data.getStream(data.getStreamIdByName(streamList.getSelectedValue()));
            stream.incrementNoOfStreams();
            user.addFavorite(data.getStreamer(stream.getStreamerId()));
            userStreams.put(stream, true);
            panel.revalidate();
            panel.repaint();
        });
        popupMenu.add(listenItem);

        streamList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger() && streamList.getSelectedIndex() != -1) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        panel.add(scrollPane);
        scrollPane.setBounds(440, 80, 200, 240);
        scrollPane.setVisible(false);


        overviewLabel = new JLabel();
        overviewLabel.setFont(GUIHelper.FONT_GENERAL_UI);
        overviewLabel.setForeground(Color.white);
        overviewLabel.setBackground(GUIHelper.COLOR_BACKGROUND);

        streamList.addListSelectionListener(e -> {
            if (streamList.getSelectedIndex() != -1) {
                Database data = Database.getInstance();
                Stream stream = data.getStream(data.getStreamIdByName(streamList.getSelectedValue()));
                boolean listened = user.getStreams().get(stream);
                overviewLabel.setText("<html>Stream name: " + stream.getName()
                        + "<br> State: " + (listened ? "Listened" : "Not listened")
                        + "<br> Type: " + stream.typeForGUI()
                        + "<br>NoOfListenings: " + stream.getNoOfListenings()
                        + "<br>Length: " + stream.getLength()
                        + "<br>Creator: " + stream.getStreamerName()
                        + "</html>");
                overviewLabel.setVisible(true);
            }
        });
        panel.add(overviewLabel);
        overviewLabel.setBounds(750, 80, 200, 240);
        overviewLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                overviewLabel.setVisible(false);
            }
        });

        logoutButton = ButtonFactory.createButton("LOGOUT", this);
        listButton = ButtonFactory.createButton("LIST", this);
        clearButton = ButtonFactory.createButton("CLEAR", this);
        recommendButton = ButtonFactory.createButton("RECOMMEND", this);
        surpriseButton = ButtonFactory.createButton("SURPRISE", this);


        panel.add(logoutButton);
        panel.add(listButton);
        panel.add(clearButton);
        panel.add(recommendButton);
        panel.add(surpriseButton);
        add(panel);
    }

    public void updateStreamList(String[] streamNames) {
        streamList.setListData(streamNames);
        panel.revalidate();
        panel.repaint();
    }
}
