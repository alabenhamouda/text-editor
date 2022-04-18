package com.example;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class WriteTextArea extends MyTextArea {
    WriteTextArea(int idx, int activeParagraphIdx) {
        super(idx, activeParagraphIdx);
    }
    @Override
    protected void attachListener() {
        getDocument().addDocumentListener(
            new SendingDocumentListener(getExchangeName()));
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
