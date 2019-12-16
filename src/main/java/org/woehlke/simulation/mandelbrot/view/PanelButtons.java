package org.woehlke.simulation.mandelbrot.view;

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

    private volatile JRadioButton radioButtonsSwitch;
    private volatile JRadioButton radioButtonsZoom;
    private volatile ButtonGroup radioButtonsGroup;
    private volatile ApplicationFrame frame;

    public PanelButtons(ApplicationFrame frame) {
        this.frame = frame;
        JLabel buttonsLabel = new JLabel(frame.getConfig().getButtonsLabel());
        this.radioButtonsSwitch = new JRadioButton(frame.getConfig().getButtonsSwitch());
        this.radioButtonsSwitch.setMnemonic(RADIO_BUTTONS_SWITCH.ordinal());
        this.radioButtonsSwitch.setSelected(true);
        this.radioButtonsSwitch.addActionListener(this);
        this.radioButtonsZoom = new JRadioButton(frame.getConfig().getButtonsZoom());
        this.radioButtonsZoom.setMnemonic(RADIO_BUTTONS_ZOOM.ordinal());
        this.radioButtonsZoom.addActionListener(this);
        this.radioButtonsGroup = new ButtonGroup();
        this.radioButtonsGroup.add(radioButtonsSwitch);
        this.radioButtonsGroup.add(radioButtonsZoom);
        FlowLayout layout = new FlowLayout();
        this.setLayout(layout);
        this.add(buttonsLabel);
        this.add(radioButtonsSwitch);
        this.add(radioButtonsZoom);
    }

    /**
     * TODO write doc.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.radioButtonsSwitch) {
            this.frame.setModeSwitch();
        } else if(ae.getSource() == this.radioButtonsZoom) {
            this.frame.setModeZoom();
        }
    }
}
