package Utils;

import GA.Components.Individual;
import GA.Components.Route;
import MDVRP.CrowdedDepot;
import MDVRP.Customer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.*;


public class Visualizer {
    private double scalingFactor;
    private int minimumX;
    private int minimumY;
    private int offset = 15;
    private GraphicsContext gc;


    public Visualizer(GraphicsContext gc) {
        this.gc = gc;
    }

    public void plotSolution(List<CrowdedDepot> depots, Individual individual) {
        // prepare data for plotting
        this.scaleTransform(depots);

        // plot data
        for (CrowdedDepot depot: depots) {
            this.drawCustomers(depot);
            this.gc.setLineWidth(1.2);
            this.drawRoutes(depot, individual);
        }
    }


    private void drawCustomers(CrowdedDepot depot) {     
        // draw depot
        this.gc.setFill(Color.BLACK);
        double depotSize = 10; 
        double x = ((depot.getX()-this.minimumX) * this.scalingFactor) - (depotSize / 2) + this.offset;
        double y = 470 - (((depot.getY()-this.minimumY) * this.scalingFactor) + (depotSize / 2));
        System.out.println("\ndepotx, depoty: " + x + "," + y);
        this.gc.fillRect(x, y, depotSize, depotSize);

        // 绘制属于仓库的所有客户
        for (Customer customer : depot.getCustomers()) {
            double csize = 6;
            // 使用 customer 的坐标而不是 depot 的坐标
            double xx = ((customer.getX()-this.minimumX) * this.scalingFactor) - (csize / 2) + this.offset;  // 计算客户的 X 坐标
            double yy = 470 - (((customer.getY()-this.minimumY) * this.scalingFactor) + (csize / 2));  // 计算客户的 Y 坐标
            System.out.println("\ncustomerx, customery: " + xx + "," + yy);
            // 绘制一个填充的圆形表示客户
            gc.fillOval(xx, yy, csize, csize);
        }
    }


    private void drawRoutes(CrowdedDepot depot, Individual individual) {
        // Possible colors for a route
        Color[] colors = new Color[]{
                Color.CADETBLUE,
                Color.CORAL,
                Color.DARKSLATEBLUE,
                Color.CYAN,
                Color.DARKGOLDENROD,
                Color.DARKMAGENTA,
                Color.BLUEVIOLET,
                Color.CRIMSON,
                Color.DEEPPINK,
                Color.DARKORANGE,
        };

        Random random = new Random();

        int colorIndex = random.nextInt(10);

        List<Route> routes = individual.getChromosome().get(depot.getId());

        for (Route route : routes) {
            double[] xPoints = new double[route.getRoute().size() + 1];
            double[] yPoints = new double[route.getRoute().size() + 1];

            xPoints[0] = (depot.getX()-this.minimumX) * this.scalingFactor+ this.offset;
            yPoints[0] = 470 - ((depot.getY()-this.minimumY) * this.scalingFactor);

            for (int i = 0; i < route.getRoute().size(); i++) {
                Customer customer = depot.getCustomer(route.getRoute().get(i));
                xPoints[i + 1] = (customer.getX()-this.minimumX) * this.scalingFactor+ this.offset;
                yPoints[i + 1] = 470 - ((customer.getY()-this.minimumY) * this.scalingFactor);
            }

            this.gc.setStroke(colors[colorIndex]);
            this.gc.strokePolygon(xPoints, yPoints, xPoints.length);
            colorIndex = (colorIndex + 1) % 10;
        }
    }


    private void scaleTransform(List<CrowdedDepot> depots) {
        int maxX = 0;
        int maxY = 0;
        int minX = 999999999;
        int minY = 999999999;
        for (CrowdedDepot depot : depots) {
            for (Customer customer : depot.getCustomers()) {
                if (customer.getX() > maxX) { maxX = customer.getX(); }
                if (customer.getY() > maxY) { maxY = customer.getY(); }
                if (customer.getX() < minX) { minX = customer.getX(); }
                if (customer.getY() < minY) { minY = customer.getY(); }
            }
        }
        this.minimumX = minX;
        this.minimumY = minY;
        //System.out.println("\nminimumx, minimumy: " + minX + "," + minY);
        // Compute appropriate scaling factor
        int diffX = maxX - minX;
        int diffY = maxY - minY;
        double size = Integer.max(diffX, diffY);
        //System.out.println("\ndiffx, diffy, size: " + diffX + "," + diffY + "," + size);

        this.scalingFactor = 470 / size;

        // Transform all coordinates to positive axes
        if (minX < 0 || minY < 0) {
            for (CrowdedDepot depot : depots) {
                if (minX < 0) {
                    depot.setX(depot.getX() - minX + 10);
                }
                if (minX < 0) {
                    depot.setY(depot.getY() - minY + 10);
                }
                for (Customer customer : depot.getCustomers()) {
                    if (minX < 0) {
                        customer.setX(customer.getX() - minX + 10);
                    }
                    if (minX < 0) {
                        customer.setY(customer.getY() - minY + 10);
                    }
                }
            }
        }
    }
}
