package com.menotyou.JC.NIOBiblioteka;

import java.io.IOException;

/**
 * Tai nuosavas iššimties tipas, kuris naudojams jei yra pažeidžiamas
 * programos protokolas.
 */
public class ProtokoloPazeidimoIsimtis extends IOException {

    private static final long serialVersionUID = 8408611638856099196L;

    public ProtokoloPazeidimoIsimtis(String pranesimas) {
        super(pranesimas);
    }
}
