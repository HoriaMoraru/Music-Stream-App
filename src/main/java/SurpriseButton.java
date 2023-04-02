import javax.swing.*;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class SurpriseButton extends JButton {
    public SurpriseButton(UserGUI frame) {
        GUIHelper.createStyledButton(GUIHelper.BUTON_TEXT_SURPRISE, this);
        setBounds(500, 420, 200, 40);
        addActionListener(e -> {
            Map<Stream, Boolean> userStreams = frame.getUser().getStreams();
            Queue<Stream> recommendedQueue = new PriorityQueue<>((s1, s2) ->
                    (int) (s2.getNoOfListenings() - s1.getNoOfListenings()));
            String[] recommendedStreams = new String[3];
            userStreams.keySet().forEach(stream -> {
                if (Boolean.FALSE.equals(userStreams.get(stream))) {
                    recommendedQueue.add(stream);
                }
            });
            for (int i = 0; i < 3; i++) {
                if (recommendedQueue.peek() != null)
                    recommendedStreams[i] = recommendedQueue.poll().getName();
            }
            frame.updateStreamList(recommendedStreams);
            frame.getScrollPane().setVisible(true);
        });
    }
}
