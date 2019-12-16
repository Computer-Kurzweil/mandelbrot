package org.woehlke.simulation.mandelbrot.view;

import org.woehlke.simulation.mandelbrot.model.constant.ApplicationStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.woehlke.simulation.mandelbrot.view.RradioButtons.RADIO_BUTTONS_SWITCH;
import static org.woehlke.simulation.mandelbrot.view.RradioButtons.RADIO_BUTTONS_ZOOM;


/**
 * TODO write doc.
 */
public class PanelButtons extends JPanel implements ActionListener {

    private volatile JButton buttonSetMode;
    private volatile JRadioButton radioButtonsSwitch;
    private volatile JRadioButton radioButtonsZoom;
    private volatile ButtonGroup radioButtonsGroup;
    private volatile FrameApplication frame;

    public PanelButtons(FrameApplication frame) {
        this.frame = frame;
        JLabel buttonsLabel = new JLabel(frame.getConfig().getButtonsLabel());
        this.radioButtonsSwitch = new JRadioButton(frame.getConfig().getButtonsSwitch());
        this.radioButtonsSwitch.setMnemonic(RADIO_BUTTONS_SWITCH.ordinal());
        this.radioButtonsSwitch.setSelected(true);
        this.radioButtonsZoom = new JRadioButton(frame.getConfig().getButtonsZoom());
        this.radioButtonsZoom.setMnemonic(RADIO_BUTTONS_ZOOM.ordinal());
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
            final int selectedMnemonic = this.radioButtonsGroup.getSelection().getMnemonic();
            if (radioButtonsSwitch.getMnemonic() == selectedMnemonic){
                this.frame.setModeSwitch();
            } else if (radioButtonsZoom.getMnemonic() == selectedMnemonic) {
                this.frame.setModeZoom();
            }
        }
    }
}
