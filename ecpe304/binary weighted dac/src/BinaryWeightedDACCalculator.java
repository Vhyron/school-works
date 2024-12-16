import javax.swing.*;
import java.awt.*;

public class BinaryWeightedDACCalculator extends JFrame {
    private JTextField vrefField, rField, rfField, digitalField;
    private JButton calculateButton;
    private JLabel voLabel;
    private JTable tablePanel;
    private String[] columns = {"Binary", "Analog"};

    // calculate vo and show table of binary and analog values
    private void calculateAndUpdate() {
        try {
            double Vref = Double.parseDouble(vrefField.getText());
            double R = Double.parseDouble(rField.getText());
            double Rf = Double.parseDouble(rfField.getText());
            String digital = digitalField.getText();

            if (!digital.matches("[01]+")) {
                throw new NumberFormatException("Binary Input Error");
            }

            double Vo = 0.0;
            for (int i = 0; i < digital.length(); i++) {
                int bit = digital.charAt(i) - '0';
                double weight = 1.0 / (Math.pow(2, i) * R);
                Vo += weight * Vref * bit;
            }

            Vo = -Rf * Vo;
            Vo = Math.round(Vo * 100.0) / 100.0; // round to 2 decimal places

            voLabel.setText(String.format("%.2f V", Vo));

            // Update the table
            int numBits = digital.length();
            int maxIndex = (int) Math.pow(2, numBits) - 1;
            Object[][] data = new Object[maxIndex + 1][2];

            for (int i = 0; i <= maxIndex; i++) {
                String binaryInput = String.format("%" + numBits + "s", Integer.toBinaryString(i)).replace(' ', '0');
                double analogOutput = 0.0;

                for (int j = 0; j < numBits; j++) {
                    int bit = binaryInput.charAt(j) - '0';
                    double weight = 1.0 / (Math.pow(2, j) * R);
                    analogOutput += weight * Vref * bit;
                }

                analogOutput = -Rf * analogOutput;
                analogOutput = Math.round(analogOutput * 100.0) / 100.0;

                data[i][0] = binaryInput;
                data[i][1] = String.format("%.2f", analogOutput);
            }

            tablePanel.setModel(new javax.swing.table.DefaultTableModel(data, columns));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numerical values for Vref, R, and Rf, and a binary string for the digital input.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addComponent(Component component, int x, int y, int w, int h) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = w;
        c.gridheight = h;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        add(component, c);
    }

    public BinaryWeightedDACCalculator() {
        setLayout(new GridBagLayout());

        addComponent(new JLabel("Vref (V):"), 0, 0, 1, 1);
        addComponent(vrefField = new JTextField(10), 1, 0, 1, 1);
        addComponent(new JLabel("RΩ:"), 0, 1, 1, 1);
        addComponent(rField = new JTextField(10), 1, 1, 1, 1);
        addComponent(new JLabel("RfΩ:"), 0, 2, 1, 1);
        addComponent(rfField = new JTextField(10), 1, 2, 1, 1);
        addComponent(new JLabel("Digital Input:"), 0, 3, 1, 1);
        addComponent(digitalField = new JTextField(10), 1, 3, 1, 1);
        addComponent(calculateButton = new JButton("Calculate"), 0, 4, 2, 1);
        calculateButton.addActionListener(e -> calculateAndUpdate());

        addComponent(new JLabel("Output Voltage (Vo):"), 0, 5, 1, 1);
        addComponent(voLabel = new JLabel(), 1, 5, 1, 1);

        addComponent(new JScrollPane(tablePanel = new JTable(new Object[0][0], columns)), 2, 0, 8, 7);
        tablePanel.setPreferredSize(new Dimension(400, 400));

        setTitle("Binary Weighted DAC Calculator");
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new BinaryWeightedDACCalculator();
    }
}