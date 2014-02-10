package com.lasanthak;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class RandomVisualizer {

    public static void main0(String[] args) {
        QuantumRandom qr = new QuantumRandom();
        final long range = 100, window = 10;
        long max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        long[] hist = new long[(int) Math.ceil(range / window)];

        for (int a = 0; a < 1000; a++) {
            long x = qr.nextLong(range);
            max = x > max ? x : max;
            min = x < min ? x : min;
            hist[(int) ((x * window) / range)]++;
        }

        System.out.println("Max: " + max);
        System.out.println("Min: " + min);
        System.out.println("Histogram:");
        for (int i = 0; i < hist.length; i++) {
            System.out.println(
                    String.format(" %s : %s",
                            StringUtils.leftPad(String.valueOf(i * window), 4),
                            StringUtils.repeat('*', (int) hist[i]))
            );
        }
    }

    public static void main(String[] args) {
        Dimension dim = new Dimension(400, 400);
        JPanel p1 = createPanel(dim);
        JPanel p2 = createPanel(dim);
        createFrame("Random", p1, p2);

        Graphics g1 = p1.getGraphics();
        Graphics g2 = p2.getGraphics();

        Random r = new Random();
        QuantumRandom qr = new QuantumRandom();

        final int DOTS = 5000;
        final int range = dim.width * dim.height;
        final int[] buffer = new int[range];

        Arrays.fill(buffer, 0);
        for (int a = 0; a < DOTS; a++) {
            int randInt = r.nextInt(range);
            buffer[randInt]++;
        }
        drawBuffer(buffer, g1, dim.width, dim.height);


        Arrays.fill(buffer, 0);
        for (int a = 0; a < DOTS; a++) {
            int randInt = (int) qr.nextLong(range);
            if (randInt < 0) {
                System.err.println("Unable to get random int...");
            } else {
                buffer[randInt]++;
            }
        }
        drawBuffer(buffer, g2, dim.width, dim.height);

        System.out.println("Done.");
    }

    private static void drawBuffer(int[] buffer, Graphics g, int width, int height) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        for (int i = 0; i < buffer.length; i++) {
            int val = buffer[i];
            if (val > 0) {
                int x = i % width, y = i / width;
                g.setColor(val > 1 ? Color.BLACK : Color.DARK_GRAY);
                g.drawLine(x - 1, y, x + 1, y);
                g.drawLine(x, y - 1, x, y + 1);

                if (val > 1) {
                    g.setColor(Color.RED);
                    g.drawOval(x - 4, y - 4, 8, 8);
                }
            }
        }
    }

    private static JFrame createFrame(String title, JPanel... panels) {
        FlowLayout fl = new FlowLayout();
        JFrame f = new JFrame(title);
        f.setLayout(fl);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocation(100, 100);
        for (JPanel panel : panels) {
            f.getContentPane().add(panel);
        }
        f.pack();
        f.setVisible(true);

        return f;
    }

    private static JPanel createPanel(Dimension dim) {
        JPanel p = new JPanel(true);
        p.setSize(dim);
        p.setPreferredSize(dim);
        p.setBorder(new LineBorder(Color.GRAY, 1));
        return p;
    }

}
