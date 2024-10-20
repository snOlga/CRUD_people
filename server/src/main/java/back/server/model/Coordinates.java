package back.server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "coordinates")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private float x;

    @Column
    private float y;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    // should be private i think but hibernate thinks different
    public Coordinates() {
        x = 0;
        y = 0;
    }

    public Coordinates(String xStr, String yStr) {
        this.x = Float.parseFloat(xStr);
        this.y = Float.parseFloat(yStr);
    }

    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // shouldn't be here
    public void setX(float x) {
        this.x = x;
    }
    
    public void setY(float y) {
        this.y = y;
    }
}
