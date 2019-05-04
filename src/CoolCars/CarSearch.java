/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoolCars;

import java.util.HashMap;
/**
 *
 * @author koryo
 */
public class CarSearch {
    private int Year, Price;
    private String Make, Model, Color, Style;
    private HashMap<Integer, String> Condition;
    
    public CarSearch (String year, String make, String model, String price, String color, String style, String condition) {
        Year = Integer.parseInt(year);
        Make = make;
        Model = model;
        Price = Integer.parseInt(price);
        Color = color;
        Style = style;
        Condition = new HashMap<>();
        int conditioni = Integer.parseInt(condition);
        switch (conditioni) {
            case 1:
                Condition.put(conditioni, "New");
                break;
            case 2:
                Condition.put(conditioni, "Fair");
                break;
            case 3:
                Condition.put(conditioni, "Used");
                break;
            default:
                Condition.put(conditioni, "Unavailable");
                break;
        }
    }
    
    public int getYear(){ return Year; }
    public int getPrice(){ return Price; }
    public String getMake(){ return Make; }
    public String getModel() { return Model; }
    public String getColor() { return Color; }
    public String getStyle() { return Style; }
    public String getCondition(int condition) { return Condition.get(condition); }
}
