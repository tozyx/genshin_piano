
import java.io.*;
import java.util.ArrayList;

public class piano {

    public static void main(String[] args){
        //gui
        layoutGui layoutgui = new layoutGui();
        layoutgui.startFrameInitialize();
        layoutgui.componentShow();

//弹奏
        while (true) {
            if (layoutgui.isPlaying[0]) {
                char[] comChar = layoutgui.getInputChar(layoutgui.arrayList);
                char[] subChar = layoutgui.getInputChar(layoutgui.sub);
                //死循环延迟 不然反应不过来
                delay.sleepTime n = new delay.sleepTime(5);
                n.start();
                try {
                    n.join();//线程阻塞 实现延时
                } catch (InterruptedException ignored) {
                }
                System.out.println("开始弹奏");
                //硬核解码
                if (layoutgui.isPlaying[0]) {
                    new decodePress(comChar, subChar);
                }
                layoutgui.isPlaying[0] = false;
                layoutgui.set1time.setEnabled(true);
                System.out.println("弹奏完成");
                layoutgui.arrayList.clear();
                layoutgui.sub.clear();
                layoutgui.readList.clear();
            }
            //不知道怎么处理的暂停
            while (!layoutgui.isPlaying[0]) {
                delay.sleepTime n2 = new delay.sleepTime(3);
                n2.start();
                try {
                    n2.join();
                } catch (InterruptedException ignored) {
                }
            }
        }

    }//主方法结束

    //读取文件
    public static void read(ArrayList<String> array, String path, String file) throws IOException {
        //字符串缓冲流
        BufferedReader br = new BufferedReader(new FileReader(path + file));
        String line;
        while ((line = br.readLine()) != null) {
            array.add(line);//txt每一行存入array集合
        }
        br.close();
    }

    //写入文件
    public static void write(char[] outcha, String path, String file) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path + "\\" + file));
        for (char a : outcha) {
            StringBuilder out = new StringBuilder();
            switch (a) {
                case 'A' -> out.append(a).append("   \t1");
                case 'S' -> out.append(a).append("   \t2");
                case 'D' -> out.append(a).append("   \t3");
                case 'F' -> out.append(a).append("   \t4");
                case 'G' -> out.append(a).append("   \t5");
                case 'H' -> out.append(a).append("   \t6");
                case 'J' -> out.append(a).append("   \t7");
                case 'Q' -> out.append(a).append("   \t1+");
                case 'W' -> out.append(a).append("   \t2+");
                case 'E' -> out.append(a).append("   \t3+");
                case 'R' -> out.append(a).append("   \t4+");
                case 'T' -> out.append(a).append("   \t5+");
                case 'Y' -> out.append(a).append("   \t6+");
                case 'U' -> out.append(a).append("   \t7+");
                case 'Z' -> out.append(a).append("   \t1-");
                case 'X' -> out.append(a).append("   \t2-");
                case 'C' -> out.append(a).append("   \t3-");
                case 'V' -> out.append(a).append("   \t4-");
                case 'B' -> out.append(a).append("   \t5-");
                case 'N' -> out.append(a).append("   \t6-");
                case 'M' -> out.append(a).append("   \t7-");
                case 'P' -> out.append(a).append("   \t\\0");
                default -> out.append(a);
            }
            bw.write(out.toString());
            if (a == '\0') {
                bw.flush();
                break;
            }
            bw.newLine();
            bw.flush();
        }
        bw.close();
    }
}
