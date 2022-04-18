package com.example;

public class TextAreaFactory {
    public static MyTextArea createTextArea(int idx, int activeParagraphIdx) {
        if (idx != activeParagraphIdx) {
            return new ReadTextArea(idx, activeParagraphIdx);
        } else {
            return new WriteTextArea(idx, activeParagraphIdx);
        }
    }
}
