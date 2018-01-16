import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {

    Main() {
        CalculatorFrame frame = new CalculatorFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(400,250);
    }

    public static void main(String[] args) {
        new Main();
    }
}

class CalculatorFrame extends JFrame {
    CalculatorFrame() {
        setTitle("Calculator");
        CalculatorPanel panel = new CalculatorPanel();
        add(panel);
        pack();
    }
}

class CalculatorPanel extends JPanel {
    private JButton display;
    private JButton clear;
    private JPanel panel;
    private double result;
    private String lastCommand;
    private boolean start;

    public CalculatorPanel() {
        setLayout(new BorderLayout());
        result=0;
        start=true;
        lastCommand = "=";
        display = new JButton("0");
        display.setEnabled(false);
        add(display, BorderLayout.NORTH);

        clear = new JButton("C");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String disp = display.getText();
                if (!disp.equals("0")){
                    if (disp.length()==1) display.setText("0");
                    else display.setText(disp.substring(0, disp.length() - 1));
                }
                else display.setText("0");
            }
        });
        add(clear, BorderLayout.SOUTH);

        ActionListener insert = new InsertAction();
        ActionListener command = new CommandAction();

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));
        addButton("7", insert);
        addButton("8", insert);
        addButton("9", insert);
        addButton("/", command);

        addButton("4", insert);
        addButton("5", insert);
        addButton("6", insert);
        addButton("*", command);

        addButton("1", insert);
        addButton("2", insert);
        addButton("3", insert);
        addButton("-", command);

        addButton("0", insert);
        addButton(".", insert);
        addButton("=", command);
        addButton("+", command);

        add(panel, BorderLayout.CENTER);

    }


    private void addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        panel.add(button);
    }
    private class InsertAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String input = event.getActionCommand();
            if(start) {
                display.setText("");
                start = false;
            }

            if (display.getText()=="0")
                display.setText("");
            //первое, что пришло в голову
            if ((hasPoint(display.getText()))&&(input==".")) input="";
            display.setText(display.getText() + input);
        }
    }
    private class CommandAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();
            if(start) {
                if(command.equals("-")) {
                    display.setText(command);
                    start = false;
                }
                else lastCommand = command;
            }
            else {
                calculate(Double.parseDouble(display.getText()));
                lastCommand = command;
                start=true;
            }
        }
    }
    public void calculate(double x) {
        if(lastCommand.equals("+")) result += x;
        else if(lastCommand.equals("-")) result -= x;
        else if(lastCommand.equals("*")) result *= x;
        else if(lastCommand.equals("/")) result /= x;
        else if(lastCommand.equals("=")) result = x;
        display.setText("" + result);

    }
    public boolean hasPoint(String displayStr) {
        if (display.getText().contains(".")) return true;
        else return false;
    }
}