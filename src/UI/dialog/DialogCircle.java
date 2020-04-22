package UI.dialog;

import struct.Line;
import struct.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DialogCircle extends JPanel {

    private ArrayList<Point> points;

    private JButton btnDraw;
    private JSpinner x0;
    private JSpinner y0;
    private JSpinner r;

    private boolean ok;
    private JDialog dialog;
    private DrawField panel;

    public DialogCircle(){
        setLayout(new BorderLayout());
        points = new ArrayList<>();
        panel  = new DrawField();
        panel.setLayout(new GridLayout(2,5));

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
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
        JPanel panelCircle = new JPanel();
        panelCircle.setLayout(new GridLayout(1,6));
        panelCircle.add(new JLabel("x0: "));
        panelCircle.add(x0= new JSpinner(new SpinnerNumberModel(10,10,298,0.1)));
        panelCircle.add(new JLabel("   y0: "));
        panelCircle.add(y0= new JSpinner(new SpinnerNumberModel(10,10,298,0.1)));
        panelCircle.add(new JLabel("   radius: "));
        panelCircle.add(r= new JSpinner(new SpinnerNumberModel(10,10,298,0.1)));

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
        add(panel, BorderLayout.NORTH);
        add(panelCircle, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

    }



    public Line getLine(){
        return new Line(points.get(0), points.get(1),0,298);
    }

    public Point getPoint(){
        return new Point((double) x0.getValue(), (double) y0.getValue());
    }

    public double getRadius(){
        return (double) r.getValue();
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
