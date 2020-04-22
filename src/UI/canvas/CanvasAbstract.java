package UI.canvas;

import javax.swing.*;
import java.text.DecimalFormat;

abstract public class CanvasAbstract extends JComponent {
    public DecimalFormat df2 = new DecimalFormat("#.##");
    abstract public void random();
    abstract public void setParameters();
}
