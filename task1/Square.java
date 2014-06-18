public class Square extends Rectangle {
    
    /*
     * If you would set the base, set the height to the same value.
     */
    public void setBase(int base) {
        super.setBase(base);
        super.setHeight(base);
    }

    /*
     * If you would set the height, set the base to the same value.
     */
    public void setHeight(int height) {
        super.setBase(height);
        super.setHeight(height);
    }
}
