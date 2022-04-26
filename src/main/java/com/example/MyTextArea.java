package com.example;
import java.awt.Font;
import java.awt.event.*;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class MyTextArea extends JTextArea implements FocusListener {
    private int idx;
    private String messagesExchangeName;
    private String readingConsumerTag;
    private SendingDocumentListener sendingDocumentListener;
    public MyTextArea(int idx) {
        super("write something here...");
        this.idx = idx;
        this.messagesExchangeName = RabbitMQHelpers.getExchangeName(idx);
        setFont(new Font("Monaco", Font.PLAIN, 20));
        addFocusListener(this);

        sendingDocumentListener =
            new SendingDocumentListener(messagesExchangeName);

        readingConsumerTag = startReadingMessages();
    }

    private String startReadingMessages() {
        Consumer<String> messageConsumer = message -> {
            try {
                // remove content from document
                Document doc = getDocument();
                doc.remove(0, doc.getLength());
                // insert new content
                doc.insertString(0, message, null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        };
        return RabbitMQHelpers.readMessage(
            RabbitMQHelpers.createQueue(messagesExchangeName), messageConsumer);
    }

    private void stopReadingMessages() {
        RabbitMQHelpers.cancelReading(readingConsumerTag);
    }

    private void startSendingMessages() {
        getDocument().addDocumentListener(sendingDocumentListener);
    }

    private void stopSendingMessages() {
        getDocument().removeDocumentListener(sendingDocumentListener);
    }

    public String getExchangeName() { return messagesExchangeName; }

    public void focusGained(FocusEvent e) {
        System.out.println("focusGained " + this.idx);
        stopReadingMessages();
        startSendingMessages();
    }

    public void focusLost(FocusEvent e) {
        System.out.println("focusLost " + this.idx);
        // start reading again
        readingConsumerTag = startReadingMessages();
        stopSendingMessages();
    }
}

class SendingDocumentListener implements DocumentListener {
    private String exchangeName;
    public SendingDocumentListener(String exchangeName) {
        this.exchangeName = exchangeName;
    }
    private void sendMessage(Document doc) {
        String text;
        try {
            text = doc.getText(0, doc.getLength());
            RabbitMQHelpers.sendMessage(text, exchangeName);
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void insertUpdate(DocumentEvent e) {
        sendMessage((Document)e.getDocument());
    }

    public void removeUpdate(DocumentEvent e) {
        sendMessage((Document)e.getDocument());
    }

    public void changedUpdate(DocumentEvent e) {
        System.out.println("removeUpdate");
    }
}