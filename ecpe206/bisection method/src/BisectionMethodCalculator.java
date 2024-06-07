import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BisectionMethodCalculator extends JFrame {

    private JTextField equationField, x0Field, x1Field, eaField;
    private JButton calculateButton;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private String[] columns = {"Iteration", "x0", "x1", "x2", "f(x2)", "Ea"};

    public BisectionMethodCalculator() {
        setTitle("Bisection Method Calculator");
        setLayout(new GridBagLayout());

        add(new JLabel("Enter Equation: "), 0, 0, 1, 1);
        add(equationField = new JTextField(10), 1, 0, 1, 1);
        add(new JLabel("Enter x0: "), 2, 0, 1, 1);
        add(x0Field = new JTextField(10), 3, 0, 1, 1);
        add(new JLabel("Enter x1: "), 4, 0, 1, 1);
        add(x1Field = new JTextField(10), 5, 0, 1, 1);
        add(new JLabel("Enter ea: "), 6, 0, 1, 1);
        add(eaField = new JTextField(10), 7, 0, 1, 1);
        add(calculateButton = new JButton("Calculate"), 8, 0, 1, 1);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String equation = equationField.getText();
                double[] coefficients = parsePolynomial(equation);

                double x0 = Double.parseDouble(x0Field.getText());
                double x1 = Double.parseDouble(x1Field.getText());
                double tolerance = Double.parseDouble(eaField.getText());

                performBisectionMethod(coefficients, x0, x1, tolerance);
            }
        });
        tableModel = new DefaultTableModel();
        for (String column : columns) {
            tableModel.addColumn(column);
        }
        add(scrollPane = new JScrollPane(table = new JTable(tableModel)), 0, 1, 11, 1);
        scrollPane.setPreferredSize(new Dimension(837, 300));

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void add(Component component, int x, int y, int w, int h) {
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

    private double[] parsePolynomial(String polynomial) {
        Pattern pattern = Pattern.compile("([+-]?[^-+]+)");
        Matcher matcher = pattern.matcher(polynomial.replaceAll("\\s", ""));

        int maxDegree = 0;
        while (matcher.find()) {
            String term = matcher.group();
            int degree = getDegree(term);
            if (degree > maxDegree) {
                maxDegree = degree;
            }
        }

        double[] coefficients = new double[maxDegree + 1];
        matcher.reset();
        while (matcher.find()) {
            String term = matcher.group();
            int degree = getDegree(term);
            double coefficient = getCoefficient(term);
            coefficients[maxDegree - degree] = coefficient;
        }

        return coefficients;
    }

    private int getDegree(String term) {
        if (term.contains("x^")) {
            return Integer.parseInt(term.split("\\^")[1]);
        } else if (term.contains("x")) {
            return 1;
        } else {
            return 0;
        }
    }

    private double getCoefficient(String term) {
        if (term.contains("x")) {
            String coeff = term.split("x")[0];
            if (coeff.isEmpty() || coeff.equals("+")) {
                return 1.0;
            } else if (coeff.equals("-")) {
                return -1.0;
            } else {
                return Double.parseDouble(coeff);
            }
        } else {
            return Double.parseDouble(term);
        }
    }

    private double evaluatePolynomial(double[] coefficients, double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, coefficients.length - 1 - i);
        }
        return result;
    }

    private void performBisectionMethod(double[] coefficients, double x0, double x1, double tolerance) {
        double f0 = evaluatePolynomial(coefficients, x0);
        double f1 = evaluatePolynomial(coefficients, x1);

        if (f0 * f1 > 0) {
            JOptionPane.showMessageDialog(this, "The function has the same signs at x0 and x1. Please choose different initial values.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int iteration = 1;
        double x2;
        double currentTolerance;
        while ((x1 - x0) / 2 > tolerance) {
            x2 = (x0 + x1) / 2;
            double f2 = evaluatePolynomial(coefficients, x2);
            currentTolerance = Math.abs(x1 - x0) / 2;

            tableModel.addRow(new Object[]{
                    iteration,
                    roundToFourDecimals(x0),
                    roundToFourDecimals(x1),
                    roundToFourDecimals(x2),
                    roundToFourDecimals(f2),
                    roundToFourDecimals(currentTolerance)
            });

            if (f2 == 0) {
                break;
            } else if (f2 * f0 < 0) {
                x1 = x2;
            } else {
                x0 = x2;
                f0 = f2;
            }
            iteration++;
        }
    }

    private double roundToFourDecimals(double value) {
        return Math.round(value * 10000.0) / 10000.0;
    }

    public static void main(String[] args) {
        new BisectionMethodCalculator().setVisible(true);
    }
}