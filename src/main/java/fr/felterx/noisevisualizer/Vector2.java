package fr.felterx.noisevisualizer;

import java.io.Serializable;

public class Vector2 implements Serializable {

    public static final Vector2 ZERO = new Vector2(0, 0);

    public float x;
    public float y;

    public Vector2() {
        this(0);
    }

    public Vector2(float value) {
        this(value, value);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 v) {
        this(v.x, v.y);
    }

    public static Vector2 add(Vector2 vectorA, Vector2 vectorB) {
        return new Vector2(vectorA.x + vectorB.x, vectorA.y + vectorB.y);
    }

    public Vector2 add(Vector2 v) {
        if (v == null)
            return this;

        x += v.x;
        y += v.y;
        return this;
    }

    public static Vector2 sub(Vector2 vectorA, Vector2 vectorB) {
        return new Vector2(vectorA.x - vectorB.x, vectorA.y - vectorB.y);
    }

    public Vector2 sub(Vector2 v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    public Vector2 sub(double x, double y) {
        x -= x;
        y -= y;
        return this;
    }

    public final void normalize() {
        double norm;

        norm = (double) (1.0 / Math.sqrt(this.x * this.x + this.y * this.y));
        this.x *= norm;
        this.y *= norm;
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public static float distance(Vector2 a, Vector2 b) {
        return (float) Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y));
    }

    public static Vector2 mul(Vector2 vector, float v) {
        return new Vector2(vector.x * v, vector.y * v);
    }

    public Vector2 mul(float v) {
        this.x *= v;
        this.y *= v;
        return this;
    }

    public Vector2 mul(Vector2 v) {
        this.x *= v.x;
        this.y *= v.y;
        return this;
    }

    public Vector2 div(float v) {
        this.x /= v;
        this.y /= v;
        return this;
    }


    @Override
    public String toString() {
        return "x=" + String.valueOf(x) + ", y=" + String.valueOf(y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj instanceof Vector2) {
            Vector2 o = (Vector2) obj;
            return o.x == x && o.y == y;
        }

        return false;
    }
}
