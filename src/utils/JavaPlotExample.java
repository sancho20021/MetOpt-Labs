package utils;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotColor;
import com.panayotis.gnuplot.style.Style;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;

public class JavaPlotExample {
    public final static PlotColor[] COLORS = {
            NamedPlotColor.AQUAMARINE,
            NamedPlotColor.RED,
            NamedPlotColor.DARK_VIOLET,
            NamedPlotColor.GREEN,
            NamedPlotColor.BROWN,
            NamedPlotColor.DARK_MAGENTA,
            NamedPlotColor.BLACK
    };

    public static DataSetPlot getPointsGraph(final List<Point2D.Double> points, final String name) {
        double[][] pointsArray = new double[points.size()][2];
        for (int i = 0; i < points.size(); i++) {
            pointsArray[i] = new double[]{points.get(i).x, points.get(i).y};
        }
        var plot = new DataSetPlot(pointsArray);
        plot.setTitle(name);
        return plot;
    }

    public static JavaPlot getPlot(
            final String name,
            final String xAxisName,
            final String yAxisName,
            final List<DataSetPlot> dataSets
    ) {
        JavaPlot p = new JavaPlot();
        p.setTitle(name);
        p.getAxis("x").setLabel(xAxisName);
        p.getAxis("y").setLabel(yAxisName);
        for (int i = 0; i < dataSets.size(); i++) {
            var dataSetPlot = dataSets.get(i);
            dataSetPlot.getPlotStyle().setStyle(Style.LINESPOINTS);
            dataSetPlot.getPlotStyle().setLineType(COLORS[i]);
            p.addPlot(dataSetPlot);
        }
        return p;
    }

    public static List<Point2D.Double> getSetOfPoints(double from, double to, double step, DoubleUnaryOperator f) {
        var points = new ArrayList<Point2D.Double>();
        for (double x = from; x < to; x += step) {
            points.add(new Point2D.Double(x, f.applyAsDouble(x)));
        }
        return points;
    }

    public static void main(String[] args) {
        var square = getPointsGraph(getSetOfPoints(0, 3.1, 1, x -> x * x), "x^2");
        var root = getPointsGraph(getSetOfPoints(0, 3.1, 1, Math::sqrt), "sqrt(x)");
        var graphics = List.of(square, root);
        var plot = getPlot("Very good plot", "X-Axis", "Y-Axis", graphics);
        plot.plot();
    }
}
