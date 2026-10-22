package com.example.ejemplo2.Pojo;

import java.util.ArrayList;

public class DataManager {
    private ArrayList<Lenguaje> listaLenguajes = new ArrayList<>();
    private int index;

    public DataManager(){
        listaLenguajes.add(new Lenguaje("Java","Lenguaje de programación orientado a objetos"));
        listaLenguajes.add(new Lenguaje("Python", "Lenguaje de programación interpretado"));
        listaLenguajes.add(new Lenguaje("C++", "Lenguaje de programación con el que te podés quedar calvo"));
        listaLenguajes.add(new Lenguaje("JavaScript", "Lenguaje de programacion para web"));

        index = 0;
    }

    public ArrayList<Lenguaje> getListaLenguajes() {
        return listaLenguajes;
    }

    public void siguienteLenguaje(){
        index = (index + 1) % listaLenguajes.size();
        // java es puto
    }

    public Lenguaje getLenguaje(){
        return listaLenguajes.get(index);
    }
}
