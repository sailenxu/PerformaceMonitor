package com.test.page;

import com.test.log.LogDemo;
import com.test.util.AdbUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

public class DropTargetFrame extends JFrame {
    public DropTargetFrame() {
        new DropTarget(this, new TextDropTargetListener());
    }
}
class TextDropTargetListener implements DropTargetListener {
    private final static Logger logger = Logger.getLogger(LogDemo.class);
    /**
     * Constructs a listener.
     *
     *            the text area in which to display the properties of the
     *            dropped object.
     */

    public void dragEnter(DropTargetDragEvent event) {
        int a = event.getDropAction();
        if ((a & DnDConstants.ACTION_COPY) != 0)
            System.out.println("ACTION_COPY\n");
        if ((a & DnDConstants.ACTION_MOVE) != 0)
            System.out.println("ACTION_MOVE\n");
        if ((a & DnDConstants.ACTION_LINK) != 0)
            System.out.println("ACTION_LINK\n");

        if (!isDragAcceptable(event)) {
            event.rejectDrag();
            return;
        }
    }

    public void dragExit(DropTargetEvent event) {
    }

    public void dragOver(DropTargetDragEvent event) {
        // you can provide visual feedback here
    }

    public void dropActionChanged(DropTargetDragEvent event) {
        if (!isDragAcceptable(event)) {
            event.rejectDrag();
            return;
        }
    }

    public void drop(DropTargetDropEvent event) {
        if (!isDropAcceptable(event)) {
            //拒绝 Drop。
            event.rejectDrop();
            return;
        }

        event.acceptDrop(DnDConstants.ACTION_COPY);

        Transferable transferable = event.getTransferable();

        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        for (int i = 0; i < flavors.length; i++) {
            DataFlavor d = flavors[i];
            try {
                if (d.equals(DataFlavor.javaFileListFlavor)) {
                    List<File> fileList = (List<File>) transferable.getTransferData(d);
                    for (File f : fileList) {
                        //f有可能是文件或文件夹
                        if (f.isFile()){
                            logger.info("path:"+f.getAbsolutePath());
                            if (f.getAbsolutePath().contains("apk")){
                                logger.info("安装："+f);
                                new AdbUtil().installAPK(f.getAbsolutePath());
                            }
                        }else if (f.isDirectory()){
                            logger.info("文件夹："+f);
                        }
                    }
                } else if (d.equals(DataFlavor.stringFlavor)) {
                    String s = (String) transferable.getTransferData(d);
                    System.out.println("2222222222222");
                }
            } catch (Exception e) {
            }
        }
        event.dropComplete(true);
    }

    public boolean isDragAcceptable(DropTargetDragEvent event) {
        // usually, you check the available data flavors here
        // in this program, we accept all flavors
        return (event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }

    public boolean isDropAcceptable(DropTargetDropEvent event) {
        // usually, you check the available data flavors here
        // in this program, we accept all flavors
        return (event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }

}

