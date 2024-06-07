import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class NewtonRaphsonMethodCalculator extends JFrame{
    private JTextField equationField, x0Field, eaField;
    private JButton calculateButton;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private String[] columns = {"Iteration", "x0", "f(x0)", "Ea"};

    public NewtonRaphsonMethodCalculator() {
        setTitle("Newton-Raphson Method Calculator");
        setLayout(new GridBagLayout());

        add(new JLabel("Enter Equation: "), 0, 0, 1, 1);
        add(equationField = new JTextField(10), 1, 0, 1, 1);
        add(new JLabel("Enter x0: "), 2, 0, 1, 1);
        add(x0Field = new JTextField(10), 3, 0, 1, 1);
        add(new JLabel("Enter ea: "), 4, 0, 1, 1);
        add(eaField = new JTextField(10), 5, 0, 1, 1);
        add(calculateButton = new JButton("Calculate"), 6, 0, 1, 1);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String polynomial = equationField.getText();
                double initialGuess = Double.parseDouble(x0Field.getText());
                double tolerance = Double.parseDouble(eaField.getText());

                double[] coefficients = parsePolynomial(polynomial);
                performNewtonRaphson(coefficients, initialGuess, tolerance, tableModel);
            }
        });
        tableModel = new DefaultTableModel();
        for (String column : columns) {
            tableModel.addColumn(column);
        }
        add(scrollPane = new JScrollPane(table = new JTable(tableModel)), 0, 1, 11, 1);
        scrollPane.setPreferredSize(new Dimension(660, 200));

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

    public static double f(double x, double[] coefficients) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, coefficients.length - 1 - i);
        }
        return result;
    }

    public static double fPrime(double x, double[] coefficients) {
        double result = 0;
        for (int i = 0; i < coefficients.length - 1; i++) {
            result += coefficients[i] * (coefficients.length - 1 - i) * Math.pow(x, coefficients.length - 2 - i);
        }
        return result;
    }

    public static double[] parsePolynomial(String polynomial) {
        polynomial = polynomial.replace(" ", "");
        String[] parts = polynomial.split("=");
        String left = parts[0];
        double right = parts.length > 1 ? Double.parseDouble(parts[1]) : 0;

        if (right != 0) {
            left += String.format("%+f", -right);
        }

        String[] terms = left.split("(?=[+-])");
        double[] coefficients = new double[terms.length];

        for (int i = 0; i < terms.length; i++) {
            String term = terms[i];
            int degree = 0;

            if (term.contains("x^")) {
                degree = Integer.parseInt(term.substring(term.indexOf("^") + 1));
                term = term.substring(0, term.indexOf("x^"));
            } else if (term.contains("x")) {
                degree = 1;
                term = term.substring(0, term.indexOf("x"));
            } else {
                degree = 0;
            }

            if (term.equals("") || term.equals("+")) {
                coefficients[coefficients.length - 1 - degree] = 1;
            } else if (term.equals("-")) {
                coefficients[coefficients.length - 1 - degree] = -1;
            } else {
                coefficients[coefficients.length - 1 - degree] = Double.parseDouble(term);
            }
        }

        return coefficients;
    }

    public static void performNewtonRaphson(double[] coefficients, double x0, double tolerance, DefaultTableModel tableModel) {
        int maxIterations = 1000;
        double x1 = x0;
        int iteration = 1;
        double error;

        DecimalFormat df = new DecimalFormat("#.####");

        tableModel.setRowCount(0);

        do {
            double fx = f(x0, coefficients);
            double fpx = fPrime(x0, coefficients);

            x1 = x0 - fx / fpx;
            error = Math.abs(x1 - x0);

            tableModel.addRow(new Object[]{iteration, df.format(x0), df.format(fx), df.format(error)});

            x0 = x1;
            iteration++;

            if (iteration > maxIterations) {
                JOptionPane.showMessageDialog(null, "The method did not converge within the maximum number of iterations.");
                break;
            }
        } while (error > tolerance);

        if (iteration <= maxIterations) {
            JOptionPane.showMessageDialog(null, "The root is approximately: " + df.format(x1));
        }
    }

    public static void main(String[] args) {
        new NewtonRaphsonMethodCalculator();
    }
}