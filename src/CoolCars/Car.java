package CoolCars;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author mytar
 */
public class Car {
    private final SimpleStringProperty Vin;
    private final SimpleStringProperty Condition;
    private final SimpleStringProperty Style;
    private final SimpleStringProperty Make;
    private final SimpleStringProperty Model;
    private final SimpleStringProperty Year;
    private final SimpleStringProperty Color;
    private final SimpleStringProperty Price;
    
    public Car(String condition, String style, String make, 
            String model, String year, String color, String price){
        this.Vin = new SimpleStringProperty();
        this.Condition = new SimpleStringProperty(condition);
        this.Style = new SimpleStringProperty(style);
        this.Make = new SimpleStringProperty(make);
        this.Model = new SimpleStringProperty(model);
        this.Year = new SimpleStringProperty(year);
        this.Color = new SimpleStringProperty(color);
        this.Price = new SimpleStringProperty(price);
    }
    
    public Car(String vin, String condition, String style, String make, 
            String model, String year, String color, String price){
        this.Vin = new SimpleStringProperty(vin);
        this.Condition = new SimpleStringProperty(condition);
        this.Style = new SimpleStringProperty(style);
        this.Make = new SimpleStringProperty(make);
        this.Model = new SimpleStringProperty(model);
        this.Year = new SimpleStringProperty(year);
        this.Color = new SimpleStringProperty(color);
        this.Price = new SimpleStringProperty(price);
    }
    
    //Getters
    public String getVin(){
        return Vin.get();  
    }
    
    public String getCondition(){
        return Condition.get();  
    }
    
    public String getStyle(){
        return Style.get();  
    }
    
    public String getMake(){
        return Make.get();  
    }
    
    public String getModel(){
        return Model.get();  
    }
    
    public String getYear(){
        return Year.get();  
    }
    
    public String getColor(){
        return Color.get();  
    }
    
    public String getPrice(){
        return Price.get();  
    }
    
    //Setters
    public void setVin(String inputVin){
        Vin.set(inputVin);
    }
    
    public void setCondition(String inputCondition){
        Condition.set(inputCondition);
    }
    
    public void setStyle(String inputStyle){
        Style.set(inputStyle);
    }
    
    public void setMake(String inputMake){
        Make.set(inputMake);
    }
    
    public void setModel(String inputModel){
        Model.set(inputModel);
    }
    
    public void setYear(String inputYear){
        Year.set(inputYear);
    }
    
    public void setColor(String inputColor){
        Color.set(inputColor);
    }
    
    public void setPrice(String inputPrice){
        Price.set(inputPrice);
    }
}
