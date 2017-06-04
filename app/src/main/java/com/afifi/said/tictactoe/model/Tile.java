package com.afifi.said.tictactoe.model;

import com.afifi.said.tictactoe.R;

public enum Tile {
    X(R.color.colorGreen),
    O(R.color.colorRed),
    NONE(R.color.colorTransparent);

    int colorId;

    Tile(int colorId) {
        this.colorId = colorId;
    }

    public int getColorId() {
        return colorId;
    }
}
