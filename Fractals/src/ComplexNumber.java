/**
 * Комплексное число x + yi;
 * две переменные типа дабл + геттер и сеттер.
 */
public class ComplexNumber {
    private double x;//оьъявление переменных
    private double y;

    public ComplexNumber (){//конструктор по умолчанию
        x = 0;
        y = 0;
    }
    public  ComplexNumber (double x, double y){//конструктор с передачей данных
        this.x = x;
        this.y = y;
    }
//геттеры и сеттеры
    public double getX (){
        return x;
    }
    public double getY (){
        return y;
    }
    public void setX (double x){
        this.x = x;
    }
    public void setY (double y){
        this.y = y;
    }
}
