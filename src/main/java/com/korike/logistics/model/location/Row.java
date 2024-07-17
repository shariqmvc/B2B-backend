package com.korike.logistics.model.location;

import java.util.ArrayList;
import com.korike.logistics.model.location.Element;

public class Row{
    public ArrayList<Element> elements;

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }
}