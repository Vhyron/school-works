import javax.swing.*;
import java.awt.*;

public class QuantizationAndEncodingMethod extends JFrame {
    private JTextField nBitsField, VmaxField, VminField, voltageInputField;
    private JButton calculateButton, findDigitalValueButton, clearButton;
    private JLabel qLabel, digitalValueLabel;
    private JTable tablePanel;

    private void calculateAndUpdate() {
        try {
            int n = Integer.parseInt(nBitsField.getText());
            double Vmax = Double.parseDouble(VmaxField.getText());
            double Vmin = Double.parseDouble(VminField.getText());

            double stepSize = calculateStepSize(n, Vmax, Vmin);
            qLabel.setText(String.format("%.2f V", stepSize));

            Object[][] data = generateTableData(n, Vmin, Vmax, stepSize);
            updateTable(data);

        } catch (NumberFormatException e) {
            showErrorDialog("Please enter valid numerical values for the number of bits, Vmax, and Vmin.");
        }
    }

    private double calculateStepSize(int n, double Vmax, double Vmin) {
        int L = (int) Math.pow(2, n);
        return Math.round((Vmax - Vmin) / L * 100.0) / 100.0;
    }

    private Object[][] generateTableData(int n, double Vmin, double Vmax, double stepSize) {
        int L = (int) Math.pow(2, n);
        Object[][] data = new Object[L][2];
        double analogValue = Vmin;

        for (int i = 0; i < L; i++) {
            String binaryInput = String.format("%" + n + "s", Integer.toBinaryString(i)).replace(' ', '0');
            data[i][0] = binaryInput;
            data[i][1] = String.format("%.2f", analogValue);
            analogValue += stepSize;
            if (analogValue > Vmax) {
                analogValue = Vmax;
            }
        }

        return data;
    }

    private void updateTable(Object[][] data) {
        tablePanel.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"Binary", "Analog"}));
    }

    private void findDigitalValue() {
        try {
            double voltage = Double.parseDouble(voltageInputField.getText());
            int n = Integer.parseInt(nBitsField.getText());
            double Vmax = Double.parseDouble(VmaxField.getText());
            double Vmin = Double.parseDouble(VminField.getText());

            double stepSize = calculateStepSize(n, Vmax, Vmin);
            String binaryValue = getDigitalValueForVoltage(voltage, Vmin, stepSize, n);

            digitalValueLabel.setText("Digital Value: " + binaryValue);
        } catch (NumberFormatException e) {
            showErrorDialog("Please enter a valid voltage.");
        }
    }

    private String getDigitalValueForVoltage(double voltage, double Vmin, double stepSize, int n) {
        int L = (int) Math.pow(2, n);
        int closestIndex = 0;
        double minDifference = Double.MAX_VALUE;

        for (int i = 0; i < L; i++) {
            double currentAnalogValue = Vmin + i * stepSize;
            double difference = Math.abs(currentAnalogValue - voltage);

            if (difference < minDifference) {
                minDifference = difference;
                closestIndex = i;
            }
        }

        return String.format("%" + n + "s", Integer.toBinaryString(closestIndex)).replace(' ', '0');
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    private void clearFields() {
        voltageInputField.setText("");
        digitalValueLabel.setText("");
    }

    private void add(Component component, int x, int y, int w, int h) {
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

    public QuantizationAndEncodingMethod() {
        setLayout(new GridBagLayout());

        add(new JLabel("Number of Bits (n):"), 0, 0, 1, 1);
        add(nBitsField = new JTextField(10), 1, 0, 1, 1);
        add(new JLabel("Vmax:"), 0, 1, 1, 1);
        add(VmaxField = new JTextField(10), 1, 1, 1, 1);
        add(new JLabel("Vmin:"), 0, 2, 1, 1);
        add(VminField = new JTextField(10), 1, 2, 1, 1);
        add(calculateButton = new JButton("Calculate"), 0, 3, 2, 1);
        calculateButton.addActionListener(e -> calculateAndUpdate());

        add(new JLabel("Step Size (q):"), 0, 4, 1, 1);
        add(qLabel = new JLabel(), 1, 4, 1, 1);

        tablePanel = new JTable(0, 2);
        tablePanel.getColumnModel().getColumn(0).setHeaderValue("Binary");
        tablePanel.getColumnModel().getColumn(1).setHeaderValue("Analog");
        add(new JScrollPane(tablePanel), 2, 0, 8, 9);

        add(new JLabel("Quantized Value (Xq):"), 0, 5, 1, 1);
        add(voltageInputField = new JTextField(10), 1, 5, 1, 1);

        add(findDigitalValueButton = new JButton("Find Digital Value"), 0, 6, 1, 1);
        findDigitalValueButton.addActionListener(e -> findDigitalValue());
        add(clearButton = new JButton("Clear"), 1, 6, 1, 1);
        clearButton.addActionListener(e -> clearFields());

        add(digitalValueLabel = new JLabel(), 0, 7, 1, 1);

        setTitle("Quantization and Encoding Method");
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new QuantizationAndEncodingMethod();
    }
}
