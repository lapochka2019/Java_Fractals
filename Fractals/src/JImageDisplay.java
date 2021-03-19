
import javax.swing.JComponent;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent{//наследование от класса интерфейса

    private BufferedImage bufferedImage;//создаем переменную класса из подкласса аймадж

    public JImageDisplay (int x, int y){
//размер изображения и цветовая палитра
        bufferedImage = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);
//
        setPreferredSize(new Dimension(x,y));//вызываем метод для отображения изображений
    }

    //перепишем метод отрисовки компонента
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage (bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
    }

    /**
     * just passes variables into setRGB() of BuggeredImage
     *///закрашивает пиксель в нужный цвет
    public void drawPixel (int x, int y, int rgbColor){
        bufferedImage.setRGB(x,y,rgbColor);
    }
//
    public BufferedImage getBufferedImage () {
        return bufferedImage;
    }//возвращает элемент изображения
}
