package forms;

import com.intellij.openapi.vfs.VirtualFile;
import javafx.scene.chart.PieChart;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;
import java.util.stream.Collectors;

public class ReportDataForm extends JPanel {
    private final List<File> a = new ArrayList<>();

    private final VirtualFile[] files;
    private final StringBuilder stringBuilder = new StringBuilder();
    private List<PieChart.Data> data = new ArrayList<>();

    public ReportDataForm(VirtualFile[] files) {
        this.files = files;
        this.setSize(500,600);
        this.setMinimumSize(new Dimension(500,600));
        draw();
        updateUI();
    }

    //region private
    private void draw() {
        Arrays.stream(files)
                .forEach(file -> {
                    File currentFile = new File(file.getPath());

                    if (currentFile.isDirectory()) {
                        ifDirectory(currentFile);
                    } else {
                        ifSingleFile(currentFile);
                    }

                });
        this.add(new CenterPanel(data));
    }

    private void ifSingleFile(File currentFile) {
        try {
            String content = FileUtils.readFileToString(currentFile);

            stringBuilder.append(findTextByPattern(content, "(?<=(groups = \\{)).*?(?=(\\}))"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ifDirectory(File currentFile) {
        final List<String> groups = new ArrayList<>();
        final Set<String> set = new HashSet<>();

        getAllJavaFiles(currentFile).forEach(filee -> {
            try {
                String content = FileUtils.readFileToString(filee);
                String[] groupz = findTextByPattern(content, "(?<=(groups = \\{)).*?(?=(\\}))").split(",");
                groups.addAll(Arrays.stream(groupz).collect(Collectors.toSet()));

            } catch (IOException e) {
                e.printStackTrace();
            }


        });
        groups.size();
        groups.forEach(gr -> set.add(gr));
        set.forEach(g -> data.add(new PieChart.Data(g + "(" + Collections.frequency(groups, g) + ")", (double) Collections.frequency(groups, g))));
    }

    private String findTextByPattern(String source, final String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);

        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    private List<File> getAllJavaFiles(File files) {
        for (File file : Objects.requireNonNull(files.listFiles())) {
            if (file.isDirectory()) {
                getAllJavaFiles(file);
            } else if (file.isFile() && file.getName().endsWith(".java")) {
                a.add(file);
            }
        }
        return a;
    }
    //endregion private


}
