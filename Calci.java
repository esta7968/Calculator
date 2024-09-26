package calculator;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Calci extends JFrame {

    private static final long serialVersionUID = 1L;
    JTextField textField;
    boolean decimalMode = false;
    int decimalPlaces = 2;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Calci window = new Calci();
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Calci() {
        initialize();
    }

    private void initialize() {
        setBounds(100, 100, 409, 458);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        textField = new JTextField();
        textField.setBounds(30, 30, 344, 62);
        getContentPane().add(textField);
        textField.setColumns(10);  
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);

        addFunctionButtons();
        addNumberButtons();
    }

    private void addFunctionButtons() {
        JButton btnBinToDec = new JButton("BIN-->DEC");
        btnBinToDec.addActionListener(e -> convertBinToDec());
        btnBinToDec.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnBinToDec.setBounds(30, 116, 125, 21);
        getContentPane().add(btnBinToDec);
        btnBinToDec.setBackground(Color.BLACK);
        btnBinToDec.setForeground(Color.WHITE);

        JButton btnDecToBin = new JButton("DEC-->BIN");
        btnDecToBin.addActionListener(e -> convertDecToBin());
        btnDecToBin.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnDecToBin.setBounds(176, 116, 125, 21);
        getContentPane().add(btnDecToBin); 
        btnDecToBin.setBackground(Color.BLACK);
        btnDecToBin.setForeground(Color.WHITE);

        JButton btnOnc = new JButton("ON/C");
        btnOnc.addActionListener(e -> textField.setText(""));
        btnOnc.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnOnc.setBounds(311, 116, 63, 21);
        getContentPane().add(btnOnc);
        btnOnc.setBackground(Color.BLACK);
        btnOnc.setForeground(Color.WHITE);

        JButton btnS = new JButton("S");
        btnS.addActionListener(e -> displaySignBit());
        btnS.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnS.setBounds(176, 359, 76, 48);
        getContentPane().add(btnS);
        btnS.setBackground(Color.WHITE);
        btnS.setForeground(Color.BLACK);

        JButton btnDecimalPlaces = new JButton("Decimal Places");
        btnDecimalPlaces.addActionListener(e -> setDecimalPlaces());
        btnDecimalPlaces.setFont(new Font("Tahoma", Font.BOLD, 8));
        btnDecimalPlaces.setBounds(262, 147, 96, 33);
        getContentPane().add(btnDecimalPlaces);
        btnDecimalPlaces.setBackground(Color.BLACK);
        btnDecimalPlaces.setForeground(Color.WHITE);

        JButton btnLeftArrow = new JButton("<");
        btnLeftArrow.addActionListener(e -> moveCursorLeft());
        btnLeftArrow.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnLeftArrow.setBounds(262, 202, 44, 33);
        getContentPane().add(btnLeftArrow);
        btnLeftArrow.setBackground(Color.BLACK);
        btnLeftArrow.setForeground(Color.WHITE);

        JButton btnRightArrow = new JButton(">");
        btnRightArrow.addActionListener(e -> moveCursorRight());
        btnRightArrow.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnRightArrow.setBounds(316, 202, 44, 33);
        getContentPane().add(btnRightArrow);
        btnRightArrow.setBackground(Color.BLACK);
        btnRightArrow.setForeground(Color.WHITE);

        JButton btnEnter = new JButton("<--|");
        btnEnter.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnEnter.setBounds(262, 265, 96, 142);
        getContentPane().add(btnEnter);  
        btnEnter.setBackground(Color.BLACK);
        btnEnter.setForeground(Color.WHITE);
    }

    private void addNumberButtons() {
        JButton[] numButtons = new JButton[10];
        Font buttonFont = new Font("Tahoma", Font.PLAIN, 18);
        ActionListener numListener = e -> textField.setText(textField.getText() + ((JButton) e.getSource()).getText());

        int[][] positions = {
            {30, 147}, {103, 147}, {176, 147},
            {30, 216}, {103, 216}, {176, 216},
            {30, 288}, {103, 288}, {176, 288},
            {30, 359}
        };

        for (int i = 0; i < 10; i++) {
            numButtons[i] = new JButton(String.valueOf(i));
            numButtons[i].addActionListener(numListener);
            numButtons[i].setFont(buttonFont);
            numButtons[i].setBounds(positions[i][0], positions[i][1], 76, 48);
            getContentPane().add(numButtons[i]);  
            numButtons[i].setBackground(Color.WHITE);
            numButtons[i].setForeground(Color.BLACK);
        }

        JButton btnDot = new JButton(".");
        btnDot.addActionListener(e -> textField.setText(textField.getText() + "."));
        btnDot.setFont(buttonFont);
        btnDot.setBounds(103, 359, 76, 48);
        getContentPane().add(btnDot);
        btnDot.setBackground(Color.WHITE);
        btnDot.setForeground(Color.BLACK);
    }

    void convertBinToDec() {
        String binaryInput = textField.getText();
        try {
            int decimal = Integer.parseInt(binaryInput, 2);
            if (decimalMode) {
                float decimalFloat = Float.intBitsToFloat(decimal);
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(decimalPlaces);
                textField.setText(Float.toString(decimalFloat));
                decimalMode = false;
            } else {
                textField.setText(Integer.toString(decimal));
            }
        } catch (NumberFormatException nfe) {
            textField.setText("Invalid Binary");
        }
    }

     public void convertDecToBin() {
        try {
            float decimalInput = Float.parseFloat(textField.getText());
            int intBits = Float.floatToIntBits(decimalInput);
            String binary = Integer.toBinaryString(intBits);
            textField.setText(binary);
        } catch (NumberFormatException nfe) {
            textField.setText("Invalid Number");
        }
    }

    public void displaySignBit() {
        try {
            float input = Float.parseFloat(textField.getText());
            int intBits = Float.floatToIntBits(input);
            int signBit = (intBits >>> 31) & 1;
            if (signBit == 0) {
                textField.setText("0 (+ve)");
            } else {
                textField.setText("1 (-ve)");
            }
        } catch (NumberFormatException nfe) {
            textField.setText("Invalid Number");
        }
    }

    public void moveCursorLeft() {
        textField.requestFocusInWindow();
        int caretPosition = textField.getCaretPosition();
        if (caretPosition > 0) {
            textField.setCaretPosition(caretPosition - 1);
        }
    }

    public void moveCursorRight() {
        textField.requestFocusInWindow();
        int caretPosition = textField.getCaretPosition();
        if (caretPosition < textField.getText().length()) {
            textField.setCaretPosition(caretPosition + 1);
        }
    }
    
    public void setDecimalPlaces() {
        String input = JOptionPane.showInputDialog(this, "Enter number of decimal places:");
        try {
            decimalPlaces = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid number. Please enter an integer.");
        }
    }
} 