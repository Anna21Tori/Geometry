package UI.dialog;

import struct.Line;
import struct.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DialogOneLine extends JPanel {

    private ArrayList<Point> points;

    private JButton btnDraw;
    private boolean ok;
    private JDialog dialog;
    private DrawField panel;
    public DialogOneLine(){
        setLayout(new BorderLayout());
        points = new ArrayList<>();
        panel  = new DrawField();
        panel.setLayout(new GridLayout(2,5));

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                System.out.println("ok");
                if (points.size() < 2) {
                    if(points.size()== 0){
                        points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
                        panel.addRect(new Point(mouseEvent.getX(), mouseEvent.getY()));
                        repaint();
                    }else {
                        for (Point p : points) {
                            if (p.getX() != mouseEvent.getX() || p.getY() != mouseEvent.getY()) {
                                points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
                                panel.addRect(new Point(mouseEvent.getX(), mouseEvent.getY()));
                                repaint();
                                break;
                            } else
                                JOptionPane.showMessageDialog(null, "The same points. Change co-ordinates.");
                        }
                    }
                }else
                    JOptionPane.showMessageDialog(null, "too many points");
            }
        });


        btnDraw = new JButton("Draw");
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e-> dialog.setVisible(false));
        btnDraw.addActionListener(e->{
            if(points.size() == 2){
                ok = true;
                dialog.setVisible(false);
            }else
                JOptionPane.showMessageDialog(null,"too little points");
        });


        JPanel btnPanel = new JPanel();
        btnPanel.add(btnCancel);
        btnPanel.add(btnDraw);
        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

    }



    public Line getLine(){
        return new Line(points.get(0), points.get(1),0,298);
    }


    public boolean showDialog(Component parent){
        ok = false;
        Frame owner = null;

        //location parent frame
        if(parent instanceof Frame)
            owner = (Frame) parent;
        else
            owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);

        //first or change user
        if(dialog == null || dialog.getOwner() !=owner){
            dialog = new JDialog(owner, true);
            dialog.add(this);
            dialog.getRootPane().setDefaultButton(btnDraw);
            dialog.pack();
        }

        dialog.setTitle("Draw");
        dialog.setVisible(true);
        return ok;
    }
}
