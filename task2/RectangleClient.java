public class RectangleClient {                                                                                                                                                      
    public static void main(String[] args) {                                                                                                                                        
        System.out.println("Create Rectangle r");                                                                                                                                   
        Rectangle r = new Rectangle();                                                                                                                                              
                                                                                                                                                                                    
        System.out.println("Set r's base to 8 and height to 6");                                                                                                                    
        r.setBase(4);                                                                                                                                                               
        r.setHeight(5);                                                                                                                                                             
                                                                                                                                                                                    
        System.out.format("r has an area of %d", r.getArea());                                                                                                                      
        System.out.println();                                                                                                                                                       
                                                                                                                                                                                    
        System.out.println("Create Square s");                                                                                                                                      
        Square s = new Square();                                                                                                                                                    
                                                                                                                                                                                    
        System.out.println("Set s's base to 8");                                                                                                                                    
        s.setBase(8);                                                                                                                                                               
                                                                                                                                                                                    
        System.out.format("s has an area of %d", s.getArea());                                                                                                                      
        System.out.println();                                                                                                                                                       
    }                                                                                                                                                                               
}  
