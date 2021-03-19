import java.awt.geom.Rectangle2D;//библиотека подключающая класс, описывающий прямоугольник,
// определенный располодением (х,у) и размерностью (высота*ширину)
//

public class BurningShip extends FractalGenerator{
    @Override
    public void getInitialRange(Rectangle2D.Double range) {
//координаты прямоугольника
        range.x = -2.0;
        range.y = -2.5;
//размер прямоугольника
        range.width =  4;
        range.height = 4;
    }

//глобальная переменная максимального количества иттераций
    public static final int MAX_ITERATIONS = 2000;
//переписываем метод подсчета количества иттераций для данного класса
    @Override
    public int numIterations(double x, double y) {
        ComplexNumber c = new ComplexNumber(x,y);//создаем и инициалиируем комплексные числа
        ComplexNumber z = new ComplexNumber();

        int iterations = 0;//счетчик

        //(x^2 + y^2) формула
        while (z.getX() * z.getX() + z.getY() * z.getY() <= 4 &&//пока количество иттераий меньше или
                iterations < MAX_ITERATIONS){//пока значение формулы меньше 4

            double tempZ = z.getX(); // сохраняем старое значение z.setY();

            z.setX(z.getX() * z.getX() + c.getX() - z.getY() * z.getY());//меняем значение х

            z.setY(Math.abs(2 * z.getY() * tempZ) + c.getY());//меняем значение у

            iterations++;//добавляем иттераию
        }
        if (iterations >= MAX_ITERATIONS) return -1;//если количество иттерауий превысило максимальное
        return iterations;//иначе
    }
//перепишем метод, который возвращает название фрактала для комбобокс
    @Override
    public String toString() {
        return "Burning ship";
    }
}
