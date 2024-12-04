import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MoveButton extends JButton implements ActionListener {
    private UndoManager undoManager;
    private JPanel drawingPanel;
    private int startX, startY;

    public MoveButton(UndoManager undoManager, JPanel drawingPanel) {
        super("Move");
        this.undoManager = undoManager;
        this.drawingPanel = drawingPanel;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        drawingPanel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int endX = e.getX();
                int endY = e.getY();
                int dx = endX - startX;
                int dy = endY - startY;

                if (dx != 0 || dy != 0) {
                    MoveCommand moveCommand = new MoveCommand(dx, dy);
                    undoManager.beginCommand(moveCommand);
                    undoManager.endCommand(moveCommand);
                }

                drawingPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
}