package form;

import dw.MetricsSetup;
import dw.Util;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Created by ernst on 4-7-17.
 */
public class Form {
    private MetricsSetup metricsSetup;
    private JPanel panel1;
    private JTextField dropwizardMetricsDemoTextField;
    private JButton klikKlikButton;
    private JSlider gaugeSlider;
    private JTextField urlField;
    private JButton goButton;
    private JTextPane eenMeterMeetAlleenTextPane;
    private JTextPane eenGaugeMeetEenTextPane;
    private JTextPane eenTimerMeetHoelangTextPane;


    private Form() {
        metricsSetup = new MetricsSetup();

        klikKlikButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                metricsSetup.clickMeter();
            }
        });
        gaugeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                metricsSetup.setGaugeValue(source.getValue());
            }
        });
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                metricsSetup.runTimed(()-> ofNullable(urlField.getText()).ifPresent(Util::readUrl));
            }
        });

        urlField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                metricsSetup.setUrl(urlField.getText());
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Form");
        frame.setSize(400, 300);
        frame.setContentPane(new Form().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


}
