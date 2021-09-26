package michapehlivan.mcstatusbot.util;

import java.awt.Color;
import java.awt.Font;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/* 
    Class containing a JFrame used to redirect console output to
*/
public class Console extends JFrame{
    
    private JTextArea textarea;
    private PrintStream printer;

    public Console(String name, int width, int height){
        setSize(width, height);
        setTitle(name);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

        printer = new PrintStream(new OutputStream(){

            @Override
            public void write(int b) throws IOException {
                textarea.append(String.valueOf((char)b));
            }
            
        });

        textarea = new JTextArea();
        textarea.setSize(width, height);
        textarea.setBackground(Color.BLACK);
        textarea.setForeground(Color.WHITE);
        textarea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));

        add(textarea);
        setVisible(true);
    }

    public PrintStream getPrintStream(){
        return printer;
    }
}
