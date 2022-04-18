package com.example;
import java.awt.Font;
import javax.swing.*;

public abstract class MyTextArea extends JTextArea {
    private int idx;
    private String exchangeName;
    public MyTextArea(int idx, int activeParagraphIdx) {
        super("write something here...");
        this.idx = idx;
        this.exchangeName = RabbitMQHelpers.getExchangeName(idx);
        setEnabled(activeParagraphIdx == idx);
        setFont(new Font("Monaco", Font.PLAIN, 20));
        attachListener();
    }

    public String getExchangeName() { return exchangeName; }

    protected abstract void attachListener();
}