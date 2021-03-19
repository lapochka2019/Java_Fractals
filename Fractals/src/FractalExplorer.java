import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Logger;

public class FractalExplorer {

    /**
     * length of fractal in pixels
     *///объявление  переменных
    private int length;

    private JImageDisplay jImageDisplay;

    private FractalGenerator fractalGenerator;

    private Rectangle2D.Double aDouble;
//элементы управления
    private JComboBox<FractalGenerator> jComboBox;
    private Button btnSave;
    private Button btnReset;
//конструктор размера окна с мандельбротом по умолчанию
    public FractalExplorer (int l){
        length = l;//размер
//новое поле
        aDouble = new Rectangle2D.Double();
//новый фрактал в поле
        new Mandelbrot().getInitialRange(aDouble);

        fractalGenerator = new Mandelbrot();
    }

    /**
     * Creates: JFrame with JImageDispaly in which fractal is painted,
     * Buttons ResetDisplay and save in file, JPanel and ActionListener-s for them,
     * JComboBox containing 3 FractalGenerator-s.
     */
    public void createAndShowGUI(){
        JFrame frame = new JFrame("Fractals");//создание рамки с названием
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//кнопка закрыть

        jImageDisplay = new JImageDisplay(length,length);//дисплей с квадратным размером
        jImageDisplay.addMouseListener(new MyMouseListener().mouseListener);//считываение комманд мыши

        JPanel jPanelForComboBox = new JPanel();//создаем комбобокс
//добавляем несколько пунктов в комбобокс
        jComboBox = new JComboBox<>();
        jComboBox.addItem(new Mandelbrot());
        jComboBox.addItem(new Tricorn());
        jComboBox.addItem(new BurningShip());
//прописываем действия для выбора пункта комбобокса
        jComboBox.addActionListener(e -> {
            fractalGenerator = (FractalGenerator) jComboBox.getSelectedItem();//генерируем фрактал

                                            // на всякий случай (getInitialRange говорит о возможности NullPointException).
            if (fractalGenerator != null) { // В теории такое может быть только, если jComboBox.getSelectedItem() вернёт null
                fractalGenerator.getInitialRange(aDouble);
                drawFractal();
                jImageDisplay.repaint();
            }
        });

        jPanelForComboBox.add(new JLabel("Fractal"));//название комбобокса
        jPanelForComboBox.add(jComboBox);//добавляем комбобокс на интерфейс


        JPanel jPanelForButtons = new JPanel();//панель для кнопок
//даем название кнопки ресет
        btnReset = new Button("Reset Display");
        //прописываем децствия для нее
        ActionListener actionListenerForBtnReset = e -> {
            fractalGenerator.getInitialRange(aDouble);
            drawFractal();//рисуем новый фрактал
            jImageDisplay.repaint();
        };
        btnReset.addActionListener(actionListenerForBtnReset);
//кнопка сохранить
        //выбор местоположения, названия
        btnSave = new Button("Save Image");
        ActionListener actionListenerForBtnSave = e -> {

            JFileChooser jFileChooser = new JFileChooser();
//фиксируем формат изображения
            FileFilter fileFilter = new FileNameExtensionFilter("PNG Images", "png");
            jFileChooser.setFileFilter(fileFilter);//сохранение в формате пнг
            jFileChooser.setAcceptAllFileFilterUsed(false);//запрет на выбор другого формата
//диалоговое окно для выбора файла и сохранения
            if (jFileChooser.showDialog(frame, "Save") == JFileChooser.APPROVE_OPTION){
//обработка исключения ошибки для метода врайт
                try {
                    ImageIO.write(jImageDisplay.getBufferedImage(), "png", jFileChooser.getSelectedFile());
//вывод об ошибке сохранения
                } catch (IOException ioException){
                    JOptionPane.showMessageDialog(frame, ioException.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        btnSave.addActionListener(actionListenerForBtnSave);
//добавление кнопок на панель с кнопками
        jPanelForButtons.add(btnSave);
        jPanelForButtons.add(btnReset);
//расположение элементов
        frame.add(jImageDisplay, BorderLayout.CENTER);
        frame.add(jPanelForButtons, BorderLayout.SOUTH);

        frame.add(jPanelForComboBox, BorderLayout.NORTH);
        //кусочек из инструкции к лабораторной работе
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);



    }


    private int rowsRemaining;//новачя переменная
    private void drawFractal (){//рисование фрактала

        enableUI(false);
        rowsRemaining = length;
        for (int i = 0; i < length; i++){
            FractalWorker fractalWorker = new FractalWorker(i);
            fractalWorker.execute();
        }
    }

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(800);//размер поля
        fractalExplorer.createAndShowGUI();//создать интерфейс

        fractalExplorer.drawFractal();//нарисовать фрактал
    }

        class MyMouseListener extends MouseAdapter {//считывание нажатий мыши
        public  MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
//перемещение окошка по рабочему экрану
                if (rowsRemaining <= 0) {
                    double xCord;
                    double yCord;

                    xCord = FractalGenerator.getCoord(aDouble.x, aDouble.x + aDouble.width, length, e.getX());
                    yCord = FractalGenerator.getCoord(aDouble.y, aDouble.y + aDouble.height, length, e.getY());

                    FractalGenerator.recenterAndZoomRange(aDouble, xCord, yCord, 0.5);

                    drawFractal();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        };


    }

    private void enableUI (boolean val){//включать или отключать кнопки с выпадающим списком в пользовательском
       // интерфейсе на основе указанного параметра
        if (val){
            btnReset.setEnabled(true);
            btnSave.setEnabled(true);
            jComboBox.setEnabled(true);
        } else {
            btnReset.setEnabled(false);
            btnSave.setEnabled(false);
            jComboBox.setEnabled(false);
        }
    }

    private class FractalWorker extends SwingWorker<Object, Object>{//отвечает за вычисление звета строки фаркатала
//заполняет строку цветами построчно
        private int y;
        private int[] pixelsRGB;

        private FractalWorker (int y){
            this.y = y;
        }
//метод, который фактически выполняет фоновые
//операции
        //вычисляет фрактал в фоновом рещиме
        @Override
        protected Object doInBackground() throws Exception {
            pixelsRGB = new int[length + 1];//создает массив цветовых значнеий


            double xCoord;
            double yCoord;

            int numIters;

            yCoord = FractalGenerator.getCoord(aDouble.y, aDouble.y + aDouble.height, length, y);//считает фрактал для каждоый строки

                for (int x = 0; x < length; x++){

                    xCoord = FractalGenerator.getCoord(aDouble.x, aDouble.x + aDouble.width, length, x);//считает фрактал для каждоый строки

                    numIters = fractalGenerator.numIterations(xCoord,yCoord);

                    if (numIters != -1){//кладет значения цветов
                        float hue = 0.7f + (float) numIters / 200f;
                        pixelsRGB[x] = Color.HSBtoRGB(hue, 0.74f, 0.74f);
                    }
                    else pixelsRGB[x] = 0;
                }
            return null;
        }

        @Override//перепишем метод
     //этот метод вызывается, когда фоновая задача завершена. Он
       // вызывается в потоке обработки событий, поэтому данному методу разрешено
        //взаимодействовать с пользовательским интерфейсом.

        protected void done() {//работает после doInBackground
            super.done();
            for (int x = 0; x < length; x++){
                jImageDisplay.drawPixel(x, y, pixelsRGB[x]);
            }
            jImageDisplay.repaint(1,y, length, 1);

            rowsRemaining--;//для выключения кнопок
            if (rowsRemaining <= 0) enableUI(true);
        }
    }
}
