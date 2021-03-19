import java.awt.geom.Rectangle2D;

public class Tricorn extends FractalGenerator {

    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        //коорлинаты начала
        range.x = -2.0;
        range.y = -2.0;
//размер области
        range.width =  4;
        range.height = 4;
    }
//количество повторов
    public static final int MAX_ITERATIONS = 2000;
    @Override
    public int numIterations(double x, double y) {
        ComplexNumber c = new ComplexNumber(x,-y);//комплексные числа
        ComplexNumber z = new ComplexNumber();

        int iterations = 0;

        //(x^2 + y^2)
        while (z.getX() * z.getX() + z.getY() * z.getY() <= 4 &&
                iterations < MAX_ITERATIONS){

            double tempZ = z.getX(); // saving old material part of z for z.setY();

            z.setX(z.getX() * z.getX() + c.getX() - z.getY() * z.getY());//пересчет по формуле

            z.setY(-1 * (2 * tempZ * z.getY() + c.getY()));//пересчет по формуле

            iterations++;
        }
        if (iterations >= MAX_ITERATIONS) return -1;
        return iterations;
    }
    @Override//название для комбобокса
    public String toString() {
        return "Tricorn";
    }
}
//zn = zn-12 + c,
//zn = zn-12 + c