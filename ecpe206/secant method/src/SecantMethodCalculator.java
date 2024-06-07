import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class SecantMethodCalculator extends JFrame {

    private JTextField equationField, x0Field, x1Field, eaField;
    private JButton calculateButton;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private String[] columns = {"Iteration", "x0", "x1", "x2", "f(x0)", "f(x1)", "f(x2)", "Ea"};

    public SecantMethodCalculator() {
        setTitle("Secant Method Calculator");
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
                String function = equationField.getText();
                double x0 = Double.parseDouble(x0Field.getText());
                double x1 = Double.parseDouble(x1Field.getText());
                double tolerance = Double.parseDouble(eaField.getText());

                performSecantMethod(function, x0, x1, tolerance, tableModel);
            }
        });
        tableModel = new DefaultTableModel();
        for (String column : columns) {
            tableModel.addColumn(column);
        }
        add(scrollPane = new JScrollPane(table = new JTable(tableModel)), 0, 1, 11, 1);
        scrollPane.setPreferredSize(new Dimension(837, 200));

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

    public static double f(double x, String function) {
        String expr = function.replace("x", "(" + x + ")");
        return eval(expr);
    }

    public static double eval(final String str) {
        class Parser {
            int pos = -1, c;

            void nextChar() {
                c = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (c == ' ') nextChar();
                if (c == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)c);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((c >= '0' && c <= '9') || c == '.') {
                    while ((c >= '0' && c <= '9') || c == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (c >= 'a' && c <= 'z') {
                    while (c >= 'a' && c <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)c);
                }

                if (eat('^')) x = Math.pow(x, parseFactor());
                return x;
            }
        }
        return new Parser().parse();
    }

    public static void performSecantMethod(String function, double x0, double x1, double tolerance, DefaultTableModel tableModel) {
        int iteration = 1;
        double x2, error;
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        df.applyPattern("#.####");

        tableModel.setRowCount(0);

        do {
            double fx0 = f(x0, function);
            double fx1 = f(x1, function);
            x2 = x1 - fx1 * (x1 - x0) / (fx1 - fx0);
            double fx2 = f(x2, function);
            error = Math.abs(x2 - x1);

            tableModel.addRow(new Object[]{
                    iteration, df.format(x0), df.format(x1), df.format(x2),
                    df.format(fx0), df.format(fx1), df.format(fx2), df.format(error)
            });

            x0 = x1;
            x1 = x2;
            iteration++;
        } while (error > tolerance);

        JOptionPane.showMessageDialog(null, "The root of the equation is: " + df.format(x2));
    }

    public static void main(String[] args) {
        new SecantMethodCalculator();
    }
}