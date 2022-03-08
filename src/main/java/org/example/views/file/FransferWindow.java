package org.example.views.file;

import org.example.views.BaseWindow;

/**
 * 传输
 */
public class FransferWindow extends BaseWindow {
    public FransferWindow(String title) {
        super(title);
    }

    @Override
    protected void windowClose() {
        frame.setSize(500,100);
        clean();
        frame.validate();
    }
}
