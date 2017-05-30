package com.remotecontrol.remotecontrol;

/**
 * Created by maypo on 21/05/2017.
 */

public class myCursor
{
    private float x;
    private float y;
    private float z;

    public myCursor(float X, float Y, float Z)
    {
        x = X;
        y = Y;
        z = Z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public void setX(float new_x) {
        x = new_x;
    }

    public void setY(float new_y) {
        y = new_y;
    }

    public void setZ(float new_z) {
        z = new_z;
    }
}
