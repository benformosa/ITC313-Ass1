public class Square extends Rectangle {
    public void setBase(int base) {
        super.setBase(base);
        super.setHeight(base);
    }

    public void setHeight(int height) {
        super.setBase(height);
        super.setHeight(height);
    }
}
