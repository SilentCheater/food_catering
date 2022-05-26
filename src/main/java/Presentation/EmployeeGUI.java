package Presentation;

import Model.Order;

import javax.swing.*;
import java.awt.*;

public class EmployeeGUI extends JFrame implements ClientGUI.Observer {

    JButton clear = new JButton("CLEAR");
    JPanel up = new JPanel();

    public EmployeeGUI(){


        ClientGUI.addObserver(this);
        clear.addActionListener(
                e -> {
                    up.removeAll();
                    up.revalidate();
                    up.repaint();
                }
        );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel down = new JPanel();
        down.add(clear);
        add(up, BorderLayout.NORTH);
        add(down, BorderLayout.SOUTH);
        setVisible(true);
        pack();
    }

    @Override
    public void updateEmployee(Order order) {
        up.revalidate();
        up.repaint();
        up.add(new JTextArea(order.toString()), BorderLayout.NORTH);
    }
}
