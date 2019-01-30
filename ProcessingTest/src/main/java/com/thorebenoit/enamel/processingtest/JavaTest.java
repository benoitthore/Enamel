package com.thorebenoit.enamel.processingtest;

import com.thorebenoit.enamel.kotlin.geometry.figures.BufferProvider;
import processing.core.PApplet;

import java.util.List;

public class JavaTest extends PApplet {

    @Override
    public void settings() {
        setSize(400, 400);
    }

    @Override
    public void draw() {
        background(255,0,0);
    }

    public static void main(String[] args) {
        PApplet.main(JavaTest.class);
    }
}
