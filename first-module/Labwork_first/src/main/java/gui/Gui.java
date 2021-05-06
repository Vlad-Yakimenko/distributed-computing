package gui;

import javax.swing.*;

public class Gui {

    private final Integer monitor = Integer.valueOf("1");

    private final JFrame jFrame = new JFrame();
    private final JPanel jPanel = new JPanel();
    private final JProgressBar jProgressBar = new JProgressBar();

    private JButton firstStartButton;
    private JButton secondStartButton;

    private Thread thread1;
    private Thread thread2;

    private final Runnable runnable1 = () -> {
        synchronized (monitor) {
            while (!Thread.currentThread().isInterrupted()) {
                jProgressBar.setValue(10);
            }
        }
    };

    private final Runnable runnable2 = () -> {
        synchronized (monitor) {
            while (!Thread.currentThread().isInterrupted()) {
                jProgressBar.setValue(90);
            }
        }
    };

    public Gui() {
        performJFrameSetup();
        performJPanelSetup();
    }

    private void performJFrameSetup() {
        jFrame.setVisible(true);
        jFrame.setSize(500, 200);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(jPanel);
    }

    private void performJPanelSetup() {
        jPanel.add(jProgressBar);
        jPanel.add(firstStartButton = createStartFirstThreadButton());
        jPanel.add(secondStartButton = createStartSecondThreadButton());
        jPanel.add(createStopFirstThreadButton());
        jPanel.add(createStopSecondThreadButton());
    }

    private JButton createStartFirstThreadButton() {
        JButton startButton = new JButton("Start 1");

        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            thread1 = new Thread(runnable1);
            thread1.setPriority(Thread.MIN_PRIORITY);
            thread1.start();
        });

        return startButton;
    }

    private JButton createStartSecondThreadButton() {
        JButton startButton = new JButton("Start 2");

        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            thread2 = new Thread(runnable2);
            thread2.setPriority(Thread.MAX_PRIORITY);
            thread2.start();
        });

        return startButton;
    }

    private JButton createStopFirstThreadButton() {
        JButton stopButton = new JButton("Stop 1");

        stopButton.addActionListener(e -> {
            thread1.interrupt();
            firstStartButton.setEnabled(true);
        });

        return stopButton;
    }

    private JButton createStopSecondThreadButton() {
        JButton stopButton = new JButton("Stop 2");

        stopButton.addActionListener(e -> {
            thread2.interrupt();
            secondStartButton.setEnabled(true);
        });

        return stopButton;
    }
}
