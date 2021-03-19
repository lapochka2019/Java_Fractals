import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {
    @Override
    public void getInitialRange (Rectangle2D.Double range){
        //координаты прямоугольника
        range.x = -2.0;
        range.y = -1.5;
        //размер прямоугольника
        range.width =  3;
        range.height = 3;
    }
//глобальная переменная количества иттераций
    public static final int MAX_ITERATIONS = 2000;
    //перепишем метод подсчета количества иттераций для данного класса
    @Override
    public int numIterations (double x, double y){

        ComplexNumber c = new ComplexNumber(x,y);//комплексные числа
        ComplexNumber z = new ComplexNumber();

        int iterations = 0;//номер иттераций


        while (z.getX() * z.getX() + z.getY() * z.getY() <= 4 &&//условие повторений
                iterations < MAX_ITERATIONS){

            double tempZ = z.getX(); // временное сохранение х
            //(Zx^2 + Cx -Zy^2)
            z.setX(z.getX() * z.getX() + c.getX() - z.getY() * z.getY());//изменяем х
            //(2*Zy*Zx + Cy)
            z.setY(2 * tempZ * z.getY() + c.getY());//изменяем у

            iterations++;//увеличиваем количество иттераций
        }
        if (iterations >= MAX_ITERATIONS) return -1;//если больше дозволенного
        return iterations;//возвращаем количесвто повторений

    }
//возвращаем название фрактала для комбобокса
    @Override
    public String toString() {
        return "Mandelbrot";
    }
}
