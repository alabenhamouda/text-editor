package com.example;
import constants.SharedConstants;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TextEditorFrame extends JFrame {
    private MyTextArea[] textAreas;
    TextEditorFrame(int activeParagraphIdx) {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);

        textAreas = new MyTextArea[SharedConstants.NUMBER_OF_PARAGRAPHS];

        for (int i = 0; i < SharedConstants.NUMBER_OF_PARAGRAPHS; i++) {
            textAreas[i] =
                TextAreaFactory.createTextArea(i, activeParagraphIdx);
        }

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(50, 30, 50, 30));

        GridLayout layout =
            new GridLayout(SharedConstants.NUMBER_OF_PARAGRAPHS, 1);
        layout.setVgap(50);
        panel.setLayout(layout);

        for (MyTextArea textArea : textAreas) {
            panel.add(textArea);
        }

        add(panel);

        setVisible(true);
    }
}
