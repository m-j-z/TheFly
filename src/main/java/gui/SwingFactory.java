package gui;

import java.awt.*;
import javax.swing.*;

/**
 * A class that creates JLabels
 */
public class SwingFactory {
    private Font labelFont;

    /**
     * Creates a SwingFactory object
     * @param font a Font object specifying the font
     * @see Font
     */
    public SwingFactory(Font font){
        this.labelFont = font;
    }

    /**
     * Creates a new JLabel with a specified text and color.
     * @param text the String to display on the label.
     * @param color the color of the text.
     * @return JLabel
     */
    public JLabel createLabel(String text, Color color){
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setOpaque(false);
        label.setFont(labelFont);
        label.setForeground(color);
        return label;
    }
}
