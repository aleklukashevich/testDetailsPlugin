package forms;

import javafx.collections.*;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ScrollPane;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CenterPanel extends JPanel {
    private final static ObservableList<PieChart.Data> details = FXCollections.observableArrayList();
    private static PieChart pieChart;

    public CenterPanel(List<PieChart.Data> data) {
        setLayout(new GridLayout(2, 3, 10, 10));

        final JFXPanel dataPaneel = new JFXPanel();

        ScrollPane sp = new ScrollPane();

        details.addAll(data);
        pieChart = new PieChart();
        pieChart.setData(details);
        pieChart.setTitle("Details");
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart.setLabelsVisible(true);
        sp.setContent(pieChart);

        Scene scene = new Scene(sp, 600, 500);
        dataPaneel.setScene(scene);
        add(dataPaneel);
    }
}
