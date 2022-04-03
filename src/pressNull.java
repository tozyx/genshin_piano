import java.awt.*;

//无附点按键 分和弦和非和弦
class pressNull implements Runnable {
    private final double comTime;
    private final double subTime;
    private final char comChar;
    private final char subChar;
    boolean isSub;
    layoutGui gui = new layoutGui();

    public pressNull(char comChar, char subChar, char chtime, char subTime, boolean isSub) {
        double subTime1;
        double comTime1;
        this.comChar = comChar;
        this.subChar = subChar;
        if (chtime == '9') {
            comTime1 = chtime - 48 + 3;
        } else {
            comTime1 = chtime - 48;
        }
        if (subTime == '9') {
            subTime1 = subTime - 48 + 3;
        } else {
            subTime1 = subTime - 48;
        }
        if (chtime == 'b') {
           comTime1 = 0.5;
        }
        this.comTime = comTime1;
        if (subTime == 'b') {
            subTime1 = 0.5;
        }
        this.subTime = subTime1;
        this.isSub = isSub;
        System.out.println(comChar);
    }


    @Override
    public void run() {
        Robot key = null;
        try {
            key = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        if (isSub && comChar >= 'A' && comChar <= 'Z') {
            assert key != null;
            key.keyPress(subChar);
            key.keyPress(comChar);
            gui.setKeyName(comChar,comTime);
            delay.sleepTime sleepTime1 = new delay.sleepTime(0.03);
            sleepTime1.start();
            try {
                sleepTime1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            key.keyRelease(subChar);
            key.keyRelease(comChar);
            delay.sleepTime sleepTime3 = new delay.sleepTime(comTime / 1000 * layoutGui.pai1time[0]);
            sleepTime3.start();
            try {
                sleepTime3.join();
            } catch (InterruptedException e) {
                System.out.println("press类延迟错误");
                e.printStackTrace();
            }
        } else if (!isSub && comChar >= 'A' && comChar <= 'Z') {
            assert key != null;
            key.keyPress(comChar);
            gui.setKeyName(comChar,comTime);
            delay.sleepTime sleeptime = new delay.sleepTime(0.03);
            sleeptime.start();
            try {
                sleeptime.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            key.keyRelease(comChar);
            delay.sleepTime sleepTime1 = new delay.sleepTime(comTime / 1000 * layoutGui.pai1time[0]);
            sleepTime1.start();
            try {
                sleepTime1.join();
            } catch (InterruptedException e) {
                System.out.println("press类延迟错误");
                e.printStackTrace();
            }
        }
    }
}
