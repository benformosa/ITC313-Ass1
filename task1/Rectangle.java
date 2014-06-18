public class Rectangle {                                                                                                                                                            
    private int base;
    private int height;

    public Rectangle() {
        this.base = 0;
        this.height = 0;
    }

    public void setBase(int base) {
         this.base = base;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea() {
        return base * height;
    }
}
