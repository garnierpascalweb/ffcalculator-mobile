package com.gpwsofts.ffcalculator.mobile.model.logo;

/**
 * @since 1.0.0 Classe Logo
 */
public class Logo implements ILogo {
    private int color;
    private String text;
    public Logo(int color, String text) {
        this.color = color;
        this.text = text;
    }
    @Override
    public int getColor() {
        return color;
    }
    @Override
    public void setColor(int color) {
        this.color = color;
    }
    @Override
    public String getText() {
        return text;
    }
}
