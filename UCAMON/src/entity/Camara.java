package entity;

public class Camara {
    private float camaraX = 0f;
    private float camaraY = 0f;

    public void update(float newCanX, float newCamY){
        this.camaraX = newCanX;
        this.camaraY = newCamY;
    }

    public float getCamaraX() {
        return camaraX;
    }

    public float getCamaraY() {
        return camaraY;
    }
}
