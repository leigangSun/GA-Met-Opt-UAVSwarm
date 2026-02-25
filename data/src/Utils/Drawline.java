package Utils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.List;

public class Drawline extends Application {

    // 这个字段会在 JavaFX 应用程序启动时使用
    private static List<Double> dataToPlot;

    @Override
    public void start(Stage stage) {
        // 在 JavaFX 应用中启动时，绘制折线图
        drawLineChart(stage, dataToPlot);
    }

    /**
     * 绘制折线图的方法
     * 
     * @param stage JavaFX 的 Stage 对象
     * @param data 需要绘制的数据（List<Double>）
     */
    public static void drawLineChart(Stage stage, List<Double> data) {
        // 创建 X 轴和 Y 轴
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("generation");
        yAxis.setLabel("distance");

        // 创建 LineChart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("MeanBestFitness");

        // 创建 XYChart.Series 并添加数据
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("MeanBestFitness");
       
        // 将数据添加到 series 中
        for (int i = 0; i < data.size(); i++) {
            // 获取 X 和 Y 坐标
        	double x = 10 * i;
            double y = data.get(i);

            // 将坐标对添加到 series 中
            series.getData().add(new XYChart.Data<>(x, y));
        }

        // 将 series 添加到 lineChart 中
        lineChart.getData().add(series);

        // 创建一个 Scene 并将其放入 Stage 中
        Scene scene = new Scene(lineChart, 800, 600);
        stage.setScene(scene);
        stage.setTitle("MeanBestFitness");
        stage.show();
    }


 
    /**
     * 启动 JavaFX 应用程序的 main 方法
     */
    public static void startJavaFXApp(List<Double> averagedHistories) {
        // 将数据传递给静态字段
        dataToPlot = averagedHistories;
        launch();  // 启动 JavaFX 应用程序
    }

    public static void main(String[] args) {
        // 这里不再包含任何硬编码数据
    }
}
