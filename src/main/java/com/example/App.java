package com.example;

import constants.SharedConstants;

public class App {
    public static void main(String[] args) {
        String usage = "Usage: java App <active paragraph index | -1>";
        if (args.length != 1) {
            System.out.println(usage);
            throw new RuntimeException("Invalid argument");
        }
        int activeParagraphIdx = Integer.parseInt(args[0]);
        if (activeParagraphIdx != -1 &&
            (activeParagraphIdx < 0 ||
             activeParagraphIdx >= SharedConstants.NUMBER_OF_PARAGRAPHS)) {
            System.out.println(usage);
            throw new RuntimeException("Invalid argument");
        }
        new TextEditorFrame(activeParagraphIdx);
    }
}
