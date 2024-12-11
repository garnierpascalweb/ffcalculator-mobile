package com.gpwsofts.ffcalculator.mobile.model;

/**
 * @since 1.0.0 Classe Logo
 */
public class Logo {
    private int color;
    private String text;

    public Logo() {

    }

    public Logo(int color, String text) {
        this.color = color;
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
