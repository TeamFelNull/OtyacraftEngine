package dev.felnull.otyacraftengine.client.debug.socket;

import java.io.Serializable;

public class TextSocketObject implements Serializable {
    private final String dataText;

    public TextSocketObject(String dataText) {
        this.dataText = dataText;
    }

    public String getDataText() {
        return dataText;
    }
}
