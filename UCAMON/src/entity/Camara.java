package entity;

public class Camara {
    private float camaraX = 0f;
    private float camaraY = 0f;
    private float lerpFactor = 0.1f;

    public void update(float newCanX, float newCamY){
        this.camaraX += (newCanX - this.camaraX) * lerpFactor;
        this.camaraY += (newCamY - this.camaraY) * lerpFactor;
    }

    public float getCamaraX() {
        return camaraX;
    }

    public float getCamaraY() {
        return camaraY;
    }
}
