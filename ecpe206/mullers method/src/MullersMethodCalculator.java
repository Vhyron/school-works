import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MullersMethodCalculator extends JFrame{
    private JTextField equationField, x0Field, x1Field, x2Field, eaField;
    private JButton calculateButton;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private String[] columns = {"Iteration", "x0", "x1", "x2", "f(x0)", "f(x1)", "f(x2)",
            "h0", "h1", "&0", "&1", "a", "b", "c", "D", "x3", "Ea"};

    public MullersMethodCalculator() {
        setTitle("Muller's Method Calculator");
        setLayout(new GridBagLayout());

        add(new JLabel("Enter Equation: "), 0, 0, 1, 1);
        add(equationField = new JTextField(10), 1, 0, 1, 1);
        add(new JLabel("Enter x0: "), 2, 0, 1, 1);
        add(x0Field = new JTextField(10), 3, 0, 1, 1);
        add(new JLabel("Enter x1: "), 4, 0, 1, 1);
        add(x1Field = new JTextField(10), 5, 0, 1, 1);
        add(new JLabel("Enter x2: "), 6, 0, 1, 1);
        add(x2Field = new JTextField(10), 7, 0, 1, 1);
        add(new JLabel("Enter ea: "), 8, 0, 1, 1);
        add(eaField = new JTextField(10), 9, 0, 1, 1);
        add(calculateButton = new JButton("Calculate"), 10, 0, 1, 1);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String function = equationField.getText();
                double x0 = Double.parseDouble(x0Field.getText());
                double x1 = Double.parseDouble(x1Field.getText());
                double x2 = Double.parseDouble(x2Field.getText());
                double tolerance = Double.parseDouble(eaField.getText());

                performMullersMethod(function, x0, x1, x2, tolerance, tableModel);
            }
        });
        tableModel = new DefaultTableModel();
        for (String column : columns) {
            tableModel.addColumn(column);
        }
        add(scrollPane = new JScrollPane(table = new JTable(tableModel)), 0, 1, 11, 1);
        scrollPane.setPreferredSize(new Dimension(930, 200));

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
                else if (c == 'x' || (c >= '0' && c <= '9') || c == '(') {
                    pos--;
                    x *= parseFactor();
                }
                return x;
            }
        }
        return new Parser().parse();
    }

    public static void performMullersMethod(String function, double a, double b, double c, double e, DefaultTableModel tableModel) {
        int iteration = 1;
        double x3 = 0, error;
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        df.applyPattern("#.####");

        tableModel.setRowCount(0);

        do {
            double xa = f(a, function);
            double xb = f(b, function);
            double xc = f(c, function);
            double h0 = b - a;
            double h1 = c - b;
            double y = (xb - xa) / h0;
            double z = (xc - xb) / h1;
            double q = (z - y) / (h0 + h1);
            double w = q * h1 + z;
            double t = xc;
            double D = Math.sqrt(Math.pow(w, 2) - 4 * q * t);

            double f1 = c + (-2 * t) / (w + D);
            double f2 = c + (-2 * t) / (w - D);

            if (w + D >= 0) {
                x3 = f1;
            } else {
                x3 = f2;
            }

            error = Math.abs((x3 - c) / x3) * 100;

            tableModel.addRow(new Object[]{
                    iteration, df.format(a), df.format(b), df.format(c),
                    df.format(xa), df.format(xb), df.format(xc),
                    df.format(h0), df.format(h1), df.format(y), df.format(z),
                    df.format(q), df.format(w), df.format(t), df.format(D), df.format(x3), df.format(error) + "%"
            });

            a = b;
            b = c;
            c = x3;
            iteration++;

        } while (error > e);
        JOptionPane.showMessageDialog(null, "The root of the equation is: " + df.format(x3));
    }

    public static void main(String[] args) {
        new MullersMethodCalculator();
    }
}