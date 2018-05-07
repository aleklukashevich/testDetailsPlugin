package actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.ui.*;
import com.intellij.openapi.vfs.VirtualFile;
import forms.ReportDataForm;

import javax.annotation.Nullable;
import javax.swing.*;
import java.util.*;

public class DetailsAction extends AnAction {

    public DetailsAction() {
        super();
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        VirtualFile[] vFile = event.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
       // Messages.showTextAreaDialog(new ReportDataForm(vFile), "Details", "");
        DialogWrapper dialog = new DialogWrapper(event.getProject()) {

            protected void init() {
                super.init();
            }

            protected JComponent createCenterPanel() {
                return new ReportDataForm(vFile);
            }
        };
        dialog.show();
        if(dialog.isOK()){
            dialog.close(0);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        VirtualFile[] vFile = e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
        if (vFile == null) {
            return;
        }

        boolean isJavaFile = Arrays.stream(vFile).anyMatch(file -> !file.isDirectory() && file.getName().endsWith(".java"));
        boolean hasDirectory = Arrays.stream(vFile).anyMatch(VirtualFile::isDirectory);

        if (vFile.length == 1) {
            if (!isJavaFile) {
                e.getPresentation().setEnabledAndVisible(false);
            }
            if (hasDirectory) {
                e.getPresentation().setEnabledAndVisible(true);
            }
        } else if (vFile.length > 1) {
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }
        return;
    }
}
