public class Dot {
    private double x;
    private double y;
    private double r;
    private boolean popadanie;


    public Dot(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        setP();
    }

    public Dot(double x, double y, double r, boolean popadanie) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.popadanie = popadanie;
    }

    public Dot(double x, String y1, String r1) {
        try {
            this.x = x;
            this.y = Double.parseDouble(y1);
            this.r = Double.parseDouble(r1.replace(",", "."));
            setP();
        }catch (NumberFormatException ignored){
        }
    }

    private void setP() {
        if (x <= 0 && y >= 0) popadanie = x * x + y * y <= r * r;
        else if (x < 0 && y < 0) popadanie = false;
        else if (x > 0 && y < 0) popadanie = y >= x - r;
        else if (x >= 0 && y >= 0) popadanie = x <= r && y <= r;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isPopadanie() {
        return popadanie;
    }

    public void setPopadanie(boolean popadanie) {
        this.popadanie = popadanie;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Dot{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", r=").append(r);
        sb.append(", popadanie=").append(popadanie);
        sb.append('}');
        return sb.toString();
    }
}
