import javax.swing.*


class Gui {

    private val fJFrame: JFrame = JFrame()
    private val fJPanel: JPanel = JPanel()
    private val fJProgressBar: JProgressBar = JProgressBar()

    private val runnable1 = {
        while (!Thread.currentThread().isInterrupted) {
            fJProgressBar.value = 10
        }
    }

    private val runnable2 = {
        while (!Thread.currentThread().isInterrupted) {
            fJProgressBar.value = 90
        }
    }

    private lateinit var thread1: Thread
    private lateinit var thread2: Thread

    init {
        performJFrameSetup()
        performJPanelSetup()
    }

    private fun performJFrameSetup() {
        fJFrame.isVisible = true
        fJFrame.setSize(200, 200)
        fJFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        fJFrame.add(fJPanel)
    }

    private fun performJPanelSetup() {
        fJPanel.add(fJProgressBar)
        fJPanel.add(createStartButton())
        fJPanel.add(createStopButton())
        performPrioritySelectorsSetup()
    }

    private fun createStartButton(): JButton {
        val startButton = JButton("Start")

        startButton.addActionListener {
            thread1 = Thread(runnable1)
            thread1.start()

            thread2 = Thread(runnable2)
            thread2.start()
        }

        return startButton
    }

    private fun createStopButton(): JButton {
        val stopButton = JButton("Stop")

        stopButton.addActionListener {
            thread1.interrupt()
            thread2.interrupt()
        }

        return stopButton
    }

    private fun performPrioritySelectorsSetup() {
        val items = Severity.values()

        val threadFirstPriority = JComboBox(items)
        threadFirstPriority.addActionListener {
            when (threadFirstPriority.selectedItem) {
                Severity.LOW -> thread1.priority = Thread.MIN_PRIORITY
                Severity.MEDIUM -> thread1.priority = Thread.NORM_PRIORITY
                Severity.HIGH -> thread1.priority = Thread.MAX_PRIORITY
            }
        }

        val threadSecondPriority = JComboBox(items)
        threadSecondPriority.addActionListener {
            when (threadSecondPriority.selectedItem) {
                Severity.LOW -> thread2.priority = Thread.MIN_PRIORITY
                Severity.MEDIUM -> thread2.priority = Thread.NORM_PRIORITY
                Severity.HIGH -> thread2.priority = Thread.MAX_PRIORITY
            }
        }

        fJPanel.add(threadFirstPriority)
        fJPanel.add(threadSecondPriority)
    }

    private enum class Severity {
        LOW, MEDIUM, HIGH
    }
}
