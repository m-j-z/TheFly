package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Handles the creation of the instruction submenu.
 */
public class InstructionMenu {

    private final JFrame frame;
    private final MainMenu menu;
    private final String fontType = "Serif";

    /**
     * Construction method for a InstructionMenu object.
     * @param frame the JFrame to put the instructions on
     * @param menu  the parent menu
     */
    public InstructionMenu(JFrame frame, MainMenu menu) {
        this.frame = frame;
        this.menu = menu;
    }

    /**
     * Creates the instruction screen with instructions to play the game, the controls to do so and a back button to return to the main menu.
     */
    public void createInstructionsMenu() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        JPanel btnPanel = addLowerButtonPanel();
        mainPanel.setBackground(Color.BLACK);

        JPanel mainPanel1 = new JPanel(new GridBagLayout());
        JPanel mainPanel2 = new JPanel(new GridBagLayout());
        mainPanel1.setOpaque(false);
        mainPanel2.setOpaque(false);
        mainPanel.add(mainPanel1);
        mainPanel.add(mainPanel2);

        JTextArea gameplay = new JTextArea(2, 32);
        gameplay.setText("INSTRUCTIONS:\nTo win, you must rescue all Flies while also avoiding Spiders, that will eat you, and Webs, that will slow you down, to win the game. You can also rescue other bugs for bonus points.");
        gameplay.setWrapStyleWord(true);
        gameplay.setLineWrap(true);
        gameplay.setEditable(false);
        gameplay.setFocusable(false);
        gameplay.setBackground(Color.BLACK);
        gameplay.setForeground(Color.WHITE);

        JLabel instructions = new JLabel("");
        instructions.setText("<html>Controls:<br> 'W' to move up<br> 'A' to move left<br> 'D' to move right<br> 'S' to move down.</html>");

        Font labelFont = new Font(fontType, Font.PLAIN, 32);
        gameplay.setFont(labelFont);
        instructions.setFont(labelFont);
        instructions.setForeground(Color.WHITE);

        frame.add(mainPanel);
        mainPanel1.add(gameplay);
        mainPanel2.add(instructions);
        frame.add(btnPanel, BorderLayout.SOUTH);

        createBackButton(btnPanel);

        frame.setVisible(true);
    }

    /**
     * Creates a new JPanel for the bottom of the JFrame.
     * @return returns JPanel
     * @see JPanel
     */
    private JPanel addLowerButtonPanel() {
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.BLACK);
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
        return btnPanel;
    }

    /**
     * Creates a back button.
     * @param panel JPanel to add the button to
     * @see JPanel
     */
    private void createBackButton(JPanel panel) {
        Font btnFont = new Font(fontType, Font.PLAIN, 24);
        JButton backBtn = new JButton("<< BACK");
        menu.modifyButton(backBtn);
        backBtn.setBorder(new EmptyBorder(32, 32, 32, 32));
        backBtn.setFont(btnFont);
        panel.add(backBtn);
        backBtn.addActionListener(e -> {
            if(e.getSource() == backBtn) {
                frame.getContentPane().removeAll();
                frame.repaint();
                menu.drawMainMenu();
            }
        });
    }
}
