import javax.swing.*;
import java.util.Random;

public class AddStreamButton extends JButton {
    public AddStreamButton(StreamerGUI frame) {
        GUIHelper.createStyledButton(GUIHelper.BUTON_TEXT_ADD_STREAM, this);
        setBounds(20, 210, 200, 40);
        addActionListener(e -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Database data = Database.getInstance();
            String streamName = JOptionPane.showInputDialog(frame, "Enter stream name:", "Add Stream", JOptionPane.QUESTION_MESSAGE);
            if (streamName != null && !streamName.isEmpty() && !data.checkDBforStreams(streamName)) {
                StreamBuilder streamBuilder = new StreamBuilder();
                Streamer streamer = frame.getStreamer();
                Stream.streamType type = getTheType(streamer);
                Stream stream = streamBuilder
                        .withName(streamName)
                        .withNoOfStreams(0)
                        .withLength(new Random().nextInt((240-120) + 1) + 120)
                        .withStreamerId(streamer.getId())
                        .withGUId()
                        .withDateAdded(System.currentTimeMillis())
                        .withType(type)
                        .build();
                data.addStream(stream);
                streamer.getStreams().add(stream);
                frame.updateStreamList();
            }
        });
    }

    private Stream.streamType getTheType(Streamer streamer) {
        switch (streamer.getType()) {
            case MUSICIAN:
                return Stream.streamType.SONG;
            case PODCASTER:
                return Stream.streamType.PODCAST;
            case AUDIOBOOK_AUTHOR:
                return Stream.streamType.AUDIOBOOK;
            default:
                throw new IllegalStateException("Unexpected value: " + streamer.getType());
        }
    }
}
