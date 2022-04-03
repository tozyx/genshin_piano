import java.awt.*;

//有附点按键 分和弦和非和弦
class press implements Runnable {
    //未使用多线程 用短时间顺序执行伪同时按键
    private double comTime;
    private double subTime;
    private final char comChar;
    private final char subChar;
    boolean isSub;
    layoutGui gui = new layoutGui();

    public press(char comChar, char subChar, char chTime, char subTime, boolean issub) {
        this.comChar = comChar;
        this.subChar = subChar;
        if (chTime == '9') {
            this.comTime = chTime - 48 + 3;
        } else {
            this.comTime = chTime - 48;
        }
        if (subTime == '9') {
            this.subTime = subTime - 48 + 3;
        } else {
            this.subTime = subTime - 48;
        }
        if (chTime == 'b') {
            this.comTime = 0.5;
        }
        if (subTime == 'b') {
            this.subTime = 0.5;
        }
        this.comTime = comTime / 2 + comTime;
        this.isSub = issub;
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

        if (!isSub && comChar >= 'A' && comChar <= 'Z') {
            assert key != null;
            key.keyPress(comChar);
            gui.setKeyName(comChar,comTime);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            key.keyRelease(comChar);
            try {
                Thread.sleep((long) comTime * layoutGui.pai1time[0]);
            } catch (InterruptedException e) {
                System.out.println("press类延迟错误");
                e.printStackTrace();
            }
        } else if (isSub && comChar >= 'A' && comChar <= 'Z') {
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
        }
    }
}
