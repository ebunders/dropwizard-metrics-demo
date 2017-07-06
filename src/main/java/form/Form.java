package form;

import dw.MetricsSetup;
import dw.Util;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Set;

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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        setDefaultSize(24);


        JFrame frame = new JFrame("Form");
        frame.setSize(800, 600);
        frame.setContentPane(new Form().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static void setDefaultSize(int size) {

        Set<Object> keySet = UIManager.getLookAndFeelDefaults().keySet();
        Object[] keys = keySet.toArray(new Object[keySet.size()]);

        for (Object key : keys) {

            if (key != null && key.toString().toLowerCase().contains("font")) {

                System.out.println(key);
                Font font = UIManager.getDefaults().getFont(key);
                if (font != null) {
                    font = font.deriveFont((float)size);
                    UIManager.put(key, font);
                }

            }

        }

    }


}
