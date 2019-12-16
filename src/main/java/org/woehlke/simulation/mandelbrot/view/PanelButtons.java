package org.woehlke.simulation.mandelbrot.view;

import org.woehlke.simulation.mandelbrot.model.cost.ApplicationStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * TODO write doc.
 */
public class PanelButtons extends JPanel implements ActionListener {

    private volatile JButton buttonSetMode;
    private volatile JRadioButton radioButtonsSwitch;
    private volatile JRadioButton radioButtonsZoom;
    private volatile ButtonGroup radioButtonsGroup;
    private volatile FrameMandelbrotSetApplication frame;

    public PanelButtons(FrameMandelbrotSetApplication frame) {
        this.frame = frame;
        JLabel buttonsLabel = new JLabel(frame.getConfig().getButtonsLabel());
        this.radioButtonsSwitch = new JRadioButton(frame.getConfig().getButtonsSwitch(),true);
        this.radioButtonsZoom = new JRadioButton(frame.getConfig().getButtonsZoom());
        this.radioButtonsGroup = new ButtonGroup();
        this.radioButtonsGroup.add(radioButtonsSwitch);
        this.radioButtonsGroup.add(radioButtonsZoom);
        this.buttonSetMode = new JButton(frame.getConfig().getButtonsSetMode());
        FlowLayout layout = new FlowLayout();
        this.setLayout(layout);
        this.add(buttonsLabel);
        this.add(radioButtonsSwitch);
        this.add(radioButtonsZoom);
        this.add(this.buttonSetMode);
        this.buttonSetMode.addActionListener(this);
    }

    /**
     * TODO write doc.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.buttonSetMode) {
            this.frame.setMode(ApplicationStatus.MANDELBROT);
        }
    }
}
