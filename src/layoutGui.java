
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;

//gui
public class layoutGui {
    MenuItem set1time = new MenuItem("设置一拍时长");
    JButton key = new JButton("按键");
    static int[] pai1time = new int[1];
    //编曲new
    ArrayList<Character> arrayList = new ArrayList<>();
    ArrayList<Character> sub = new ArrayList<>();
    ArrayList<String> readList = new ArrayList<>();
    StringBuilder tips = new StringBuilder();
    File file;//jar路径
    File properties;//配置文件
    Properties pro = new Properties();
    boolean i = true;//只打印一次jar路径
    final boolean[] isPlaying = {false};//true 正在/开始弹奏
    final boolean[] isSub = {false};//true 当前音节为和弦
    final boolean[] hadChar = {false};//true 已经有过音节 之后只能定义音节节拍（延迟）
    final boolean[] fuDian = {false};//判断是否为附点
    final boolean[] isBackToStart = {false};//是否从编曲界面返回开始界面
    final boolean[] isSaved = {true};//编曲目是否保存
    final boolean[] isEmpty = {true};//是否清空
    final String[] imagePath = new String[1];//背景图片路径字符串
    final boolean[] isStop = {false};//是否停止/暂停弹奏
    final boolean[] copy = {false};//保存路径是否复制到剪切板
    final boolean[] closeComponent = {true};//是否关闭面板
    final boolean[] componentSizeChanged = {false};
    final int[] count = new int[1];//计数输入音符个数
    Frame component = new Frame("Genshin_Piano                     by 冰焰_Frozen");
    Frame error = new Frame("文 件 非 法");
    JDialog settime = new JDialog(component, "设置一拍时长 100~2000", true);//设置一拍时间
    TextField time = new TextField();//一拍时间文本框
    JDialog exitLog = new JDialog(component, "是否直接退出", true);
    JDialog componentExitLog = new JDialog(component, "检测到未保存编曲是否保存", true);
    FileDialog opening = new FileDialog(component, "选择文件路径", FileDialog.LOAD);
    FileDialog saving = new FileDialog(component, "保存文件", FileDialog.SAVE);
    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension windowSize = kit.getScreenSize();
    JTextField com = new JTextField("谱子：");
    JTextField subCom = new JTextField("和弦：");
    TextField tip = new TextField("提示");
    //文本显示panel
    JPanel text = new JPanel(new GridLayout(4, 1, 10, 0));
    //按钮panel 5*7
    JPanel button = new JPanel(new GridLayout(5, 7, 4, 2));
    //总panel
    JPanel wholeCom = new JPanel(new GridLayout(2, 1, 0, 6));
    JButton A0 = new JButton("1/8 C");
    JButton empty = new JButton("");//填充排版按钮
    JButton empty1 = new JButton("");
    JButton empty2 = new JButton("");
    JButton empty3 = new JButton("");
    JButton empty4 = new JButton("");
    JButton empty5 = new JButton("");
    JButton P = new JButton("0 ");
    JButton A1 = new JButton("1/4 C");
    JButton A2 = new JButton("1/2 C");
    JButton A4 = new JButton("C");
    JButton A8 = new JButton("C -");
    JButton A12 = new JButton("C - - ");
    JButton Ad = new JButton("C .");
    JButton A = new JButton("1 ");
    JButton S = new JButton("2 ");
    JButton D = new JButton("3 ");
    JButton F = new JButton("4 ");
    JButton G = new JButton("5 ");
    JButton H = new JButton("6 ");
    JButton J = new JButton("7 ");
    JButton Q = new JButton("1+");
    JButton W = new JButton("2+");
    JButton E = new JButton("3+");
    JButton R = new JButton("4+");
    JButton T = new JButton("5+");
    JButton Y = new JButton("6+");
    JButton U = new JButton("7+");
    JButton Z = new JButton("1-");
    JButton X = new JButton("2-");
    JButton C = new JButton("3-");
    JButton V = new JButton("4-");
    JButton B = new JButton("5-");
    JButton N = new JButton("6-");
    JButton M = new JButton("7-");
    Button yes = new Button("是");
    Button no = new Button("否");
    Button x = new Button("取消");
    Button yes1 = new Button("是");
    Button no1 = new Button("否");
    Button tuiChu = new Button("5秒后退出程序...");
    JFrame key1 = new JFrame("当前按键");

    //key按钮设置名字
    void setKeyName(char a, double time) {
        key.setText(String.valueOf(a));
        key1.removeAll();
        key1.add(key);
        //key1.setVisible(true);
//        key1.setAlwaysOnTop(true);
        key1.setResizable(false);
        key1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                decodePress.isplaying = false;
                key1.setVisible(false);
            }
        });

    }

    //图片背景
    void setPic() {
        if (imagePath[0] != null && !imagePath[0].equals("0")) {
            ImageIcon img = new ImageIcon(imagePath[0]);
            JLabel start = new JLabel(img);
            if (componentSizeChanged[0]) {
                img.setImage(img.getImage().getScaledInstance(Integer.parseInt(readProperty("startComponentWide")), Integer.parseInt(readProperty("startComponentHeight")), Image.SCALE_DEFAULT));
                component.add(start);
                component.pack();
                if (readProperty("startComponentWide") != null) {
                    component.setBounds((int) (windowSize.getWidth() / 2 - Integer.parseInt(readProperty("startComponentWide")) / 2), (int) (windowSize.getHeight() / 2 - Integer.parseInt(readProperty("startComponentHeight")) / 2), Integer.parseInt(readProperty("startComponentWide")), Integer.parseInt(readProperty("startComponentHeight")));
                }
                componentSizeChanged[0] = false;
            } else {
                component.add(start);
                component.pack();
                if (readProperty("startComponentWide") != null) {
                    component.setBounds((int) (windowSize.getWidth() / 2 - Integer.parseInt(readProperty("startComponentWide")) / 2), (int) (windowSize.getHeight() / 2 - Integer.parseInt(readProperty("startComponentHeight")) / 2), Integer.parseInt(readProperty("startComponentWide")), Integer.parseInt(readProperty("startComponentHeight")));
                }
            }
        } else {
            component.setBounds((int) windowSize.getWidth() / 2 - 650 / 2, (int) windowSize.getHeight() / 2 - 250 / 2, 650, 250);
        }
    }


    //读取配置文件方法
    String readProperty(String name) {
        try {
            pro.load(new FileInputStream(properties));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取配置文件错误");
        }
        return pro.getProperty(name);
    }

    //个性化打开图片方法
    private void getImagePath() {
        opening.setFile("*.jpg;*.png;*.gif");
        opening.setVisible(true);
        component.setAlwaysOnTop(false);
        opening.setAlwaysOnTop(true);
        if (opening.getFile() != null) {
            imagePath[0] = opening.getDirectory() + opening.getFile();
            writeProperty("imagePath", imagePath[0]);
            System.out.println("图片路径:" + imagePath[0]);
        } else {
            imagePath[0] = "0";
            System.out.println("读取图片失败");
        }
    }

    //写入配置文件方法
    void writeProperty(String name, String value) {
        //配置文件读取写入
        pro.setProperty(name, value);
        try {
            pro.store(new PrintStream(properties), "encoding:utf-8");
            //pro.store(new PrintStream(properties), "修改此配置文件可能出现不可预料的后果");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("配置文件写入错误");
        }
    }

    //开始界面初始化
    void startFrameInitialize() {
        key.setFocusable(false);
        key.setBorder(BorderFactory.createEmptyBorder());//无边框
        key.setContentAreaFilled(false);//透明
        key.setFont(new Font("黑体", Font.BOLD, 18));
        key.setForeground(new Color(250, 180, 180));
        key1.setBounds(0, 0, 100, 80);
        component.removeAll();
        readList.clear();
        arrayList.clear();
        sub.clear();
        closeComponent[0] = false;
        file = new File(getJarPath());//读取jar包路径 方便写入配置文件等
        properties = new File(file + "\\config.properties");//配置文件定位
        if (readProperty("1time") == null) {
            writeProperty("1time", "150");//如果没有设置一拍时间初始值 强行设定
        }
        pai1time[0] = Integer.parseInt(readProperty("1time"));

        //读取配置文件
        if (new File(file + "\\config.properties").exists()) {
            imagePath[0] = readProperty("imagePath");
        } else {
            writeProperty("write_config", "true");
        }
        componentSizeChanged[0] = false;
        setPic();


        //生成readme
        {
            try {
                BufferedWriter bw1 = new BufferedWriter(new FileWriter(file + "\\README.txt"));
                bw1.write("""
                        ##################
                        #    请不要删除此文件    #
                        ##################
                        @author: 冰焰_Frozen
                        https://space.bilibili.com/507499226
                        运行该jar包需要java环境（如果在此计算机上能运行minecraft，请忽略此项）
                        第一次运行必定会直接退出 并生成start.bat和README.txt
                        在此之后的运行都会正常

                        建议双击start.bat运行程序，这样可以以管理员身份运行，未隐藏的cmd窗口可以实时反馈程序运行情况
                        不建议直接运行jar

                        经不完全测试，在未使用管理员身份情况下不能在游戏内进行按键操作（yuanshen.exe以管理员身份运行）

                        特此声明：本程序不含任何恶意代码，经作者测试，未发现bug
                        欢迎提交bug和建议
                        """
                );
                bw1.flush();
                bw1.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("上面一坨的意思是生成README.txt失败，多半是路径问题\n" +
                        "不影响正常使用");
            }

        }

        //生成bat
        {
            if (new File(file + "\\start.bat").exists()) {
                if (i) {
                    System.out.println("当前路径" + file);
                    i = false;
                }
            } else {
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file + "\\start.bat"));
                    //生成管理员身份运行bat
                    bw.write("""
                            @echo off
                            :: BatchGotAdmin (Run as Admin code starts)
                            REM --> Check for permissions
                            >nul 2>&1 "%SYSTEMROOT%\\system32\\cacls.exe" "%SYSTEMROOT%\\system32\\config\\system"
                            REM --> If error flag set, we do not have admin.
                            if '%errorlevel%' NEQ '0' (
                            echo Requesting administrative privileges...
                            goto UACPrompt
                            ) else ( goto gotAdmin )
                            :UACPrompt
                            echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\\getadmin.vbs"
                            echo UAC.ShellExecute "%~s0", "", "", "runas", 1 >> "%temp%\\getadmin.vbs"
                            "%temp%\\getadmin.vbs"
                            exit /B
                            :gotAdmin
                            if exist "%temp%\\getadmin.vbs" ( del "%temp%\\getadmin.vbs" )
                            pushd "%CD%"
                            CD /D "%~dp0"
                            :: BatchGotAdmin (Run as Admin code ends)

                            java -jar genshin_piano.jar
                            """
                    );
                    bw.flush();
                    bw.close();
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("""
                            上面一坨的意思是生成start.bat失败，多半是路径问题
                                                        
                            请手动创建bat 内容为 java -jar genshin_piano.jar
                            右键管理员运行，若不懂请百度“管理员运行jar”""");
                }
            }
        }
        //菜单条
        MenuBar bar = new MenuBar();
        //菜单
        Menu menu = new Menu("菜单");
        Menu menufile = new Menu("文件");
        Menu set = new Menu("个性化");
        Menu color = new Menu("背景");
        Menu font = new Menu("菜单字体");
        Menu play = new Menu("弹奏");
        Menu auth = new Menu("联系作者");

        //菜单项
        MenuItem componentframe = new MenuItem("编曲界面");
        MenuItem top = new MenuItem("置顶窗口");
        MenuItem open = new MenuItem("打开弹奏文件");
        MenuItem image = new MenuItem("本地图片..");
        MenuItem red = new MenuItem("红色");
        MenuItem black = new MenuItem("黑色");
        MenuItem design = new MenuItem("自定义");
        MenuItem startPlay = new MenuItem("开始弹奏");
        MenuItem stopPlay = new MenuItem("停止弹奏", new MenuShortcut(KeyEvent.VK_P, false));
        MenuItem au = new MenuItem("打开网址");

        //设置时长panel
        {
            settime.setLayout(new GridLayout(2, 2, 2, 2));
            settime.setBounds((int) windowSize.getWidth() / 2 - 200 / 2, (int) windowSize.getHeight() / 2 - 80 / 2, 200, 80);
            settime.setResizable(false);
            JButton danWei = new JButton("ms");
            danWei.setEnabled(false);
            danWei.setBorder(BorderFactory.createEmptyBorder());//无边框
            danWei.setContentAreaFilled(false);//透明
            Button timeYes = new Button("确定");
            Button timeNo = new Button("取消");

            settime.add(time);
            settime.add(danWei);
            settime.add(timeYes);
            settime.add(timeNo);


            timeYes.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (Integer.parseInt(time.getText().trim()) >= 100 && Integer.parseInt(time.getText().trim()) <= 2000) {
                        writeProperty("1time", String.valueOf(Integer.parseInt(time.getText().trim())));
                        time.setText("保存成功");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        settime.setVisible(false);
                    } else {
                        time.setText("非法输入");
                        time.setEditable(false);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        time.setText("");
                        time.setEditable(true);
                    }
                }
            });
            timeNo.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    settime.setVisible(false);
                }
            });
            settime.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    settime.setVisible(false);
                }
            });
        }
        menu.add(new MenuItem("-"));
        menu.add(componentframe);
        menu.add(new MenuItem("-"));
        menu.add(top);

        menufile.add(open);


        color.add(red);
        color.add(black);
        color.add(design);
        color.add(new MenuItem("-"));
        color.add(image);

        set.add(new MenuItem("-"));
        set.add(color);
        set.add(font);

        play.add(startPlay);
        play.add(stopPlay);
        play.add(set1time);
        startPlay.setEnabled(false);
        stopPlay.setEnabled(false);

        auth.add(au);

        bar.add(menu);
        bar.add(set);
        bar.add(menufile);
        bar.add(play);
        bar.add(auth);

        component.setMenuBar(bar);
        //菜单监听

        {
            component.addComponentListener(new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent e) {
                    componentSizeChanged[0] = true;
                }

                @Override
                public void componentMoved(ComponentEvent e) {

                }

                @Override
                public void componentShown(ComponentEvent e) {

                }

                @Override
                public void componentHidden(ComponentEvent e) {

                }
            });
            set1time.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    time.setText(readProperty("1time"));
                    settime.setVisible(true);
                }
            });
            au.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        java.net.URI uri = null;
                        try {
                            uri = new java.net.URI("https://space.bilibili.com/507499226");
                        } catch (URISyntaxException ex) {
                            ex.printStackTrace();
                        }

                        java.awt.Desktop.getDesktop().browse(uri);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            image.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getImagePath();
                    setPic();
                }
            });
            stopPlay.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isPlaying[0] = false;
                    decodePress.isplaying = false;
                    set1time.setEnabled(true);
                    startPlay.setEnabled(true);
                }
            });
            startPlay.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    separate(readList, arrayList, sub);
                    isPlaying[0] = true;
                    decodePress.isplaying = true;
                    startPlay.setEnabled(false);
                    set1time.setEnabled(false);
                    System.out.println("五秒后开始弹奏");
                    System.out.println("一拍时长：" + pai1time[0] + "  ms");
                }
            });
            open.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    top.setLabel("窗口置顶");
                    tip.setText("窗口已取消置顶");
                    component.setAlwaysOnTop(false);
                    open();
                    if (readList.size() != 0) {
                        startPlay.setEnabled(true);
                        stopPlay.setEnabled(true);
                    }
                }
            });
            top.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isStop[0] = !isStop[0];
                    if (isStop[0]) {
                        component.setAlwaysOnTop(true);
                        top.setLabel("窗口取消置顶");
                        tip.setText("窗口已置顶");
                    } else {
                        top.setLabel("窗口置顶");
                        tip.setText("窗口已取消置顶");
                        component.setAlwaysOnTop(false);
                    }

                }
            });
            yes1.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            no1.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    exitLog.setVisible(false);
                }
            });
            exitLog.setLayout(new GridLayout(1, 2, 0, 0));
            exitLog.setResizable(false);
            exitLog.add(yes1);
            exitLog.add(no1);
            exitLog.setBounds((int) windowSize.getWidth() / 2 - 200 / 2, (int) windowSize.getHeight() / 2 - 60 / 2, 200, 60);
            component.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    if (closeComponent[0]) {
                        if (isSaved[0]) {
                            System.exit(0);
                        } else {
                            isEmpty[0] = false;
                            isBackToStart[0] = false;
                            componentExitLog.setVisible(true);
                        }
                    } else {
                        exitLog.setVisible(true);
                    }
                }
            });
            componentframe.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!isPlaying[0]) {
                        writeProperty("startComponentWide", String.valueOf(component.getWidth()));
                        writeProperty("startComponentHeight", String.valueOf(component.getHeight()));
                        componentInitialize();
                    } else {
                        System.out.println("正在弹奏请结束弹奏后重试");
                    }
                }
            });
        }
    }

    //编曲界面初始化
    void componentInitialize() {
        //记录之前窗口大小
        writeProperty("startComponentWide", String.valueOf(component.getWidth()));
        writeProperty("startComponentHeight", String.valueOf(component.getHeight()));
        //初始化
        arrayList.clear();
        sub.clear();
        tips.delete(0, tips.length());
        showText();
        com.setText("谱子：");
        subCom.setText("和弦：");
        tip.setText("提示");
        //编曲界面初始化
        component.removeAll();
        closeComponent[0] = true;
        component.setName("编曲界面");
        //初始化编曲界面大小
        if (readProperty("componentWide") == null) {
            component.setBounds((int) windowSize.getWidth() / 2 - 650 / 2, (int) windowSize.getHeight() / 2 - 250 / 2, 650, 250);
        } else {
            component.setBounds((int) windowSize.getWidth() / 2 - Integer.parseInt(readProperty("componentWide")) / 2, (int) windowSize.getHeight() / 2 - Integer.parseInt(readProperty("componentHeight")) / 2, Integer.parseInt(readProperty("componentWide")), Integer.parseInt(readProperty("componentHeight")));
        }
        exitLog.setBounds((int) windowSize.getWidth() / 2 - 300 / 2, (int) windowSize.getHeight() / 2 - 60 / 2, 300, 60);
        com.setFocusable(true);
        com.setEditable(false);
        com.setFont(new Font("黑体", Font.PLAIN, 18));
        subCom.setFocusable(true);
        subCom.setEditable(false);
        subCom.setFont(new Font("黑体", Font.PLAIN, 18));
        tip.setEditable(false);
        tip.setFocusable(false);
        tip.setFont(new Font("黑体", Font.ITALIC, 12));
        componentExitLog.setLayout(new GridLayout(1, 2, 0, 0));
        componentExitLog.add(yes);
        componentExitLog.add(no);
        componentExitLog.add(x);
        componentExitLog.setBounds((int) windowSize.getWidth() / 2 - 250 / 2, (int) windowSize.getHeight() / 2 - 60 / 2, 250, 60);
        componentExitLog.setVisible(false);
        componentExitLog.setResizable(false);

        //文本整合排序
        text.add(com);
        text.add(subCom);
        text.add(tip);
        //文本显示左对齐
        com.setHorizontalAlignment(JTextField.LEFT);
        subCom.setHorizontalAlignment(JTextField.LEFT);
        final int[] jButtonRed = new int[1];
        final int[] jButtonGreen = new int[1];
        final int[] jButtonBlue = new int[1];
        jButtonRed[0] = 240;
        jButtonGreen[0] = 220;
        jButtonBlue[0] = 220;

        //添加按钮组件到button panel中
        button.add(empty);
        empty.setEnabled(false);
        empty.setBorder(BorderFactory.createEmptyBorder());//无边框
        empty.setContentAreaFilled(false);//透明
        button.add(empty1);
        empty1.setEnabled(false);
        empty1.setBorder(BorderFactory.createEmptyBorder());
        empty1.setContentAreaFilled(false);
        button.add(empty2);
        empty2.setEnabled(false);
        empty2.setBorder(BorderFactory.createEmptyBorder());
        empty2.setContentAreaFilled(false);
        button.add(empty3);
        empty3.setEnabled(false);
        empty3.setBorder(BorderFactory.createEmptyBorder());
        empty3.setContentAreaFilled(false);
        button.add(empty4);
        empty4.setEnabled(false);
        empty4.setBorder(BorderFactory.createEmptyBorder());
        empty4.setContentAreaFilled(false);
        button.add(empty5);
        empty5.setEnabled(false);
        empty5.setBorder(BorderFactory.createEmptyBorder());
        empty5.setContentAreaFilled(false);
        button.add(P);
        P.setToolTipText("休止音符");
        P.setBorder(BorderFactory.createEmptyBorder());
        button.add(A0);
        A0.setToolTipText("八分之一拍");
        A0.setBorder(BorderFactory.createEmptyBorder());
        button.add(A1);
        A1.setToolTipText("四分之一拍");
        A1.setBorder(BorderFactory.createEmptyBorder());
        button.add(A2);
        A2.setToolTipText("二分之一拍");
        A2.setBorder(BorderFactory.createEmptyBorder());
        button.add(A4);
        A4.setToolTipText("全拍");
        A4.setBorder(BorderFactory.createEmptyBorder());
        button.add(A8);
        A8.setToolTipText("两拍");
        A8.setBorder(BorderFactory.createEmptyBorder());
        button.add(A12);
        A12.setToolTipText("三拍");
        A12.setBorder(BorderFactory.createEmptyBorder());
        button.add(Ad);
        Ad.setToolTipText("附点");
        Ad.setBorder(BorderFactory.createEmptyBorder());
        button.add(Q);
        Q.setBorder(BorderFactory.createEmptyBorder());
        button.add(W);
        W.setBorder(BorderFactory.createEmptyBorder());
        button.add(E);
        E.setBorder(BorderFactory.createEmptyBorder());
        button.add(R);
        R.setBorder(BorderFactory.createEmptyBorder());
        button.add(T);
        T.setBorder(BorderFactory.createEmptyBorder());
        button.add(Y);
        Y.setBorder(BorderFactory.createEmptyBorder());
        button.add(U);
        U.setBorder(BorderFactory.createEmptyBorder());
        button.add(A);
        A.setBorder(BorderFactory.createEmptyBorder());
        button.add(S);
        S.setBorder(BorderFactory.createEmptyBorder());
        button.add(D);
        D.setBorder(BorderFactory.createEmptyBorder());
        button.add(F);
        F.setBorder(BorderFactory.createEmptyBorder());
        button.add(G);
        G.setBorder(BorderFactory.createEmptyBorder());
        button.add(H);
        H.setBorder(BorderFactory.createEmptyBorder());
        button.add(J);
        J.setBorder(BorderFactory.createEmptyBorder());
        button.add(Z);
        Z.setBorder(BorderFactory.createEmptyBorder());
        button.add(X);
        X.setBorder(BorderFactory.createEmptyBorder());
        button.add(C);
        C.setBorder(BorderFactory.createEmptyBorder());
        button.add(V);
        V.setBorder(BorderFactory.createEmptyBorder());
        button.add(B);
        B.setBorder(BorderFactory.createEmptyBorder());
        button.add(N);
        N.setBorder(BorderFactory.createEmptyBorder());
        button.add(M);
        M.setBorder(BorderFactory.createEmptyBorder());
        //按钮外观初始化
        setJb(A0, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(P, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(A1, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(A2, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(A4, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(A8, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(A12, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(Ad, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(Q, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(W, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(E, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(R, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(T, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(Y, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(U, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(A, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(S, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(D, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(F, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(G, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(H, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(J, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(Z, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(X, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(C, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(V, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(B, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(N, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);
        setJb(M, jButtonRed[0], jButtonGreen[0], jButtonBlue[0]);

        wholeCom.add(text);
        wholeCom.add(button);
        component.add(wholeCom);
        componentMenuInitialize();
//        component.add(text,BorderLayout.NORTH);
//        component.add(button,BorderLayout.SOUTH);

        //菜单监听

        {

            tip.addTextListener(e -> {
                if (arrayList.size() != 0) {
                    isSaved[0] = false;
                }
            });

        }

//按键监听
        count[0] = -1;
        Ad.setEnabled(false);
        {
            yes.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    save();
                    if (isSaved[0] && !isEmpty[0]) {
                        System.exit(0);
                    } else {
                        componentExitLog.setVisible(false);
                    }
                }
            });
            no.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    componentExitLog.setVisible(false);
                    if (isBackToStart[0]) {
                        startFrameInitialize();
                    } else if (!isEmpty[0]) {
                        System.exit(0);
                    } else {
                        arrayList.clear();
                        sub.clear();
                        showText();
                        tip.setText("已清空");
                        count[0] = -1;
                    }
                    isBackToStart[0] = false;
                    isSaved[0] = true;
                }
            });
            x.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    componentExitLog.setVisible(false);
                }
            });
            A0.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(true);
                        count[0] += 2;
                        fuDian[0] = true;
                        hadChar[0] = false;
                        arrayList.add('b');
                        arrayList.add(' ');
                        sub.add(' ');
                        sub.add(' ');
                        tips.append("\t八分之一拍");
                        showText();
                    } else if (hadChar[0]) {
                        sub.set(count[0] - 1, 'b');
                        sub.set(count[0], ' ');
                        fuDian[0] = true;
                        hadChar[0] = false;
                        isSub[0] = false;
                        tips.append("\t八分之一拍");
                        showText();
                    }
                }
            });
            A1.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(true);
                        count[0] += 2;
                        fuDian[0] = true;
                        hadChar[0] = false;
                        arrayList.add('1');
                        arrayList.add(' ');
                        sub.add(' ');
                        sub.add(' ');
                        tips.append("\t四分之一拍");
                        showText();
                    } else if (hadChar[0]) {
                        sub.set(count[0] - 1, '1');
                        sub.set(count[0], ' ');
                        fuDian[0] = true;
                        hadChar[0] = false;
                        isSub[0] = false;
                        tips.append("\t四分之一拍");
                        showText();
                    }
                }
            });
            A2.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(true);
                        count[0] += 2;
                        arrayList.add('2');
                        fuDian[0] = true;
                        hadChar[0] = false;
                        arrayList.add(' ');
                        sub.add(' ');
                        sub.add(' ');
                        tips.append("\t二分之一拍");
                        showText();
                    } else if (hadChar[0]) {
                        sub.set(count[0] - 1, '2');
                        sub.set(count[0], ' ');
                        fuDian[0] = true;
                        hadChar[0] = false;
                        isSub[0] = false;
                        tips.append("\t二分之一拍");
                        showText();
                    }
                }
            });
            A4.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(true);
                        count[0] += 2;
                        arrayList.add('4');
                        fuDian[0] = true;
                        hadChar[0] = false;
                        arrayList.add(' ');
                        sub.add(' ');
                        sub.add(' ');
                        tips.append("\t一拍");
                        showText();
                    } else if (hadChar[0]) {
                        sub.set(count[0] - 1, '4');
                        sub.set(count[0], ' ');
                        fuDian[0] = true;
                        hadChar[0] = false;
                        isSub[0] = false;
                        tips.append("\t一拍");
                        showText();
                    }
                }
            });
            A8.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(true);
                        count[0] += 2;
                        arrayList.add('8');
                        fuDian[0] = true;
                        hadChar[0] = false;
                        arrayList.add(' ');
                        sub.add(' ');
                        sub.add(' ');
                        tips.append("\t两拍");
                        showText();
                    } else if (hadChar[0]) {
                        sub.set(count[0] - 1, '8');
                        sub.set(count[0], ' ');
                        fuDian[0] = true;
                        hadChar[0] = false;
                        isSub[0] = false;
                        tips.append("\t两拍");
                        showText();
                    }
                }
            });
            A12.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(true);
                        count[0] += 2;
                        arrayList.add('9');
                        fuDian[0] = true;
                        hadChar[0] = false;
                        arrayList.add(' ');
                        sub.add(' ');
                        sub.add(' ');
                        tips.append("\t三拍");
                        showText();
                    } else if (hadChar[0]) {
                        sub.set(count[0] - 1, '9');
                        sub.set(count[0], ' ');
                        fuDian[0] = true;
                        hadChar[0] = false;
                        isSub[0] = false;
                        tips.append("\t三拍");
                        showText();
                    }
                }
            });
//附点注意
//
            Ad.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (arrayList.size() != 0 && !hadChar[0] && fuDian[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        arrayList.remove(count[0]);
                        arrayList.add('.');
                        tips.append("附点");
                        showText();
                    } else if (!hadChar[0] && isSub[0]) {
                        sub.set(count[0], '.');
                        isSub[0] = false;
                        tips.append("附点");
                        showText();
                    }

                    fuDian[0] = false;
                }
            });
//
            P.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('P');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("0");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'P');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("0");
                        showText();
                    }
                }
            });

            Q.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('Q');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("1+");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'Q');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("1+");
                        showText();
                    }
                }
            });
            W.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('W');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("2+");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'W');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("2+");
                        showText();
                    }
                }
            });
            E.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('E');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("3+");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'E');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("3+");
                        showText();
                    }
                }
            });
            R.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('R');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("4+");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'R');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("4+");
                        showText();
                    }
                }
            });//冰焰_Frozen
            T.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('T');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("5+");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'T');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("5+");
                        showText();
                    }
                }
            });
            Y.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('Y');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("6+");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'Y');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("6+");
                        showText();
                    }
                }
            });
            U.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('U');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("7+");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'U');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("7+");
                        showText();
                    }
                }
            });
            A.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('A');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("1");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'A');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("1");
                        showText();
                    }
                }
            });
            S.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('S');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("2");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'S');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("2");
                        showText();
                    }
                }
            });
            D.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('D');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("3");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'D');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("3");
                        showText();
                    }
                }
            });
            F.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('F');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("4");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'F');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("4");
                        showText();
                    }
                }
            });
            G.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('G');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("5");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'G');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("5");
                        showText();
                    }
                }
            });
            H.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('H');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("6");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'H');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("6");
                        showText();
                    }
                }
            });
            J.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('J');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("7");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'J');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("7");
                        showText();
                    }
                }
            });
            Z.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('Z');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("1-");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'Z');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("1-");
                        showText();
                    }
                }
            });
            X.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('X');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("2-");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'X');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("2-");
                        showText();
                    }
                }
            });
            C.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('C');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("3-");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'C');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("3-");
                        showText();
                    }
                }
            });
            V.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('V');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("4-");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'V');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("4-");
                        showText();
                    }
                }
            });
            B.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('B');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("5-");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'B');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("5-");
                        showText();
                    }
                }
            });
            N.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('N');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("6-");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'N');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("6-");
                        showText();
                    }
                }
            });
            M.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!hadChar[0] && !isSub[0]) {
                        Ad.setEnabled(false);
                        count[0] += 1;
                        arrayList.add('M');
                        fuDian[0] = false;
                        hadChar[0] = true;
                        sub.add(' ');
                        tips.delete(0, tips.length());
                        tips.append("7-");
                        showText();
                    } else if (!hadChar[0]) {
                        sub.set(count[0] - 2, 'M');
                        hadChar[0] = true;
                        fuDian[0] = false;
                        tips.delete(0, tips.length());
                        tips.append("7-");
                        showText();
                    }
                }
            });

        }
    }

    //显示面板文字
    void showText() {
        com.setText(new charToString(getInputChar(arrayList)).tostring2());
        subCom.setText(new charToString(getInputChar(sub)).tostring2());
        tip.setText(tips.toString());
    }

    //集合到数组
    public char[] getInputChar(ArrayList<Character> arrayList) {
        final char[] inputchar = new char[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            inputchar[i] = arrayList.get(i);
        }
        return inputchar;
    }

    //个性化jb
    void setJb(JButton a, int red, int green, int blue) {
        a.setBackground(new Color(red, green, blue));
    }

    //重写
    void setJb(JButton a, String b, int d) {
        a.setFont(new Font(b, Font.PLAIN, d));
    }

    //编曲面板可视化
    void componentShow() {
        component.setVisible(true);
    }

    //编曲菜单栏初始化
    void componentMenuInitialize() {

        //菜单条
        MenuBar bar = new MenuBar();
        //菜单
        Menu menu = new Menu("菜单");
        Menu menufile = new Menu("文件");
        Menu edit = new Menu("编辑");
        Menu set = new Menu("个性化");
//        PopupMenu popupMenucom = new PopupMenu();//右击菜单
//        PopupMenu popupMenusub = new PopupMenu();
//        PopupMenu popupMenutip = new PopupMenu();
        //菜单项
        MenuItem startframe = new MenuItem("开始界面");
        MenuItem top1 = new MenuItem();
        MenuItem open = new MenuItem("打开文件...");
        MenuItem save = new MenuItem("另存为", new MenuShortcut(KeyEvent.VK_S, false));
        MenuItem copyitem = new MenuItem("开启/关闭复制保存路径到剪切板");
        MenuItem back = new MenuItem("退格", new MenuShortcut(KeyEvent.VK_Z, false));
        MenuItem skip = new MenuItem("在此分节 (分节只为了便于编曲观察，无实际用途)", new MenuShortcut(KeyEvent.VK_Z, true));
        MenuItem empty = new MenuItem("清空");
        MenuItem subcom = new MenuItem("插入此节拍和弦", new MenuShortcut(KeyEvent.VK_X, true));
        final String[] hidestring = {"隐藏"};
        MenuItem hide = new MenuItem(hidestring[0] + "提示栏");

        MenuItem comset = new MenuItem("主谱..");
        MenuItem subset = new MenuItem("和弦..");
        MenuItem tipset = new MenuItem("提示..");
        //组装菜单栏
        menufile.add(open);
        menufile.add(save);
        menufile.add(copyitem);

        edit.add(back);
        edit.add(subcom);
        edit.add(skip);
        edit.add(empty);
        edit.add(hide);
        edit.add(new MenuItem("-"));

        set.add(comset);
        set.add(subset);
        set.add(tipset);
        edit.add(set);


        //菜单

        menu.add(startframe);
        menu.add(new MenuItem("-"));
        menu.add(top1);
        //文件

        //右键菜单
//        PopupMenu mainframe = new PopupMenu();
//        //mainframe.add(back2);
//        mainframe.add(empty);
//        component.add(mainframe);
//        wholecom.add(mainframe);
//
//        text.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                super.mouseReleased(e);
//                boolean flag = e.isPopupTrigger();
//                if (flag) {
//                    //显示右键菜单
//                    mainframe.show(wholecom, e.getX(), e.getY());
//                }
//            }
//        });


        bar.add(menu);
        bar.add(menufile);
        bar.add(edit);
        component.setMenuBar(bar);

        if (isStop[0]) {
            component.setAlwaysOnTop(true);
            top1.setLabel("窗口取消置顶");
            tip.setText("窗口已置顶");
        } else {
            top1.setLabel("窗口置顶");
            tip.setText("窗口已取消置顶");
            component.setAlwaysOnTop(false);
        }
        //菜单监听器
        {
            top1.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isStop[0] = !isStop[0];
                    if (isStop[0]) {
                        component.setAlwaysOnTop(true);
                        top1.setLabel("窗口取消置顶");
                        tip.setText("窗口已置顶");
                    } else {
                        top1.setLabel("窗口置顶");
                        tip.setText("窗口已取消置顶");
                        component.setAlwaysOnTop(false);
                    }
                }
            });
            open.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (arrayList.size() == 0) {
                        readList.clear();
                        top1.setLabel("窗口置顶");
                        tip.setText("窗口已取消置顶");
                        component.setAlwaysOnTop(false);
                        open();
                        if (readList.size() != 0) {
                            separate(readList, arrayList, sub);
                            showText();
                            tip.setText("打开成功");
                            count[0] = arrayList.size() - 1;
                        } else {
                            tip.setText("未读取到文件");
                        }
                    } else {
                        tip.setText("请清空后重试");
                    }
                }
            });
            empty.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isSaved[0]) {
                        arrayList.clear();
                        sub.clear();
                        showText();
                        tip.setText("已清空");
                        tips.delete(0, tips.length());
                        count[0] = -1;
                        hadChar[0] = false;
                    } else {
                        isBackToStart[0] = false;
                        componentExitLog.setVisible(true);
                        hadChar[0] = false;
                    }

                }
            });
            skip.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!isSub[0]) {
                        if (arrayList.size() == 0) {
                            arrayList.add('|');
                            sub.add('|');
                            showText();
                            count[0] += 1;
                            tip.setText("插入成功");
                        } else if (!hadChar[0] && arrayList.get(count[0]) == ' ') {
                            arrayList.remove(count[0]);
                            arrayList.add('|');
                            sub.remove(count[0]);
                            sub.add('|');
                            showText();
                            System.out.println(count[0] + "" + sub.get(count[0]));
                            tip.setText("插入成功");
                            Ad.setEnabled(false);
                        } else if (!hadChar[0] && arrayList.get(count[0]) == '.') {
                            tip.setText("由于排版问题 暂不支持附点后插入分节 分节不影响弹奏");
                        } else {
                            tip.setText("插入失败 请输入完整音节");
                        }
                    } else {
                        System.out.println("请先插入和弦后再分节");
                    }
                }
            });
            copyitem.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    copy[0] = !copy[0];
                    tip.setText("功能：复制保存路径到剪切板：" + copy[0]);
                }
            });
            back.addActionListener(new

                                           AbstractAction() {
                                               @Override
                                               public void actionPerformed(ActionEvent e) {
                                                   if (arrayList.size() != 0) {
                                                       if (count[0] == 0) {
                                                           arrayList.clear();
                                                           sub.clear();
                                                           showText();
                                                           Ad.setEnabled(false);
                                                           count[0] = -1;
                                                       } else if (arrayList.get(count[0]) == '|' || arrayList.get(count[0]) == '.') {
                                                           arrayList.remove(count[0]);
                                                           arrayList.add(' ');
                                                           fuDian[0] = true;
                                                           sub.remove(count[0]);
                                                           sub.add(' ');
                                                           Ad.setEnabled(true);
                                                           showText();
                                                       } else if (arrayList.get(count[0]) == ' ') {
                                                           Ad.setEnabled(false);
                                                           arrayList.remove(count[0]);
                                                           arrayList.remove(count[0] - 1);
                                                           sub.remove(count[0]);
                                                           sub.remove(count[0] - 1);
                                                           count[0] -= 2;
                                                           showText();
                                                       } else {
                                                           Ad.setEnabled(false);
                                                           arrayList.remove(count[0]);
                                                           sub.remove(count[0]);
                                                           count[0] -= 1;
                                                           showText();
                                                       }
                                                       tip.setText("退格");
                                                       tips.delete(0, tips.length());
                                                       if (arrayList.size() != 0 && count[0] > -1) {
                                                           hadChar[0] = arrayList.get(count[0]) >= 'A' && arrayList.get(count[0]) <= 'Z';
                                                       } else {
                                                           hadChar[0] = false;
                                                           Ad.setEnabled(false);
                                                       }
                                                   } else {
                                                       isSaved[0] = true;
                                                   }
                                                   if (!hadChar[0] && count[0] > 1 && arrayList.get(count[0]) != '|') {
                                                       Ad.setEnabled(true);
                                                   }
                                               }
                                           });
            //到启动面板
            startframe.addActionListener(new

                                                 AbstractAction() {
                                                     @Override
                                                     public void actionPerformed(ActionEvent e) {
                                                         if (isSaved[0]) {
                                                             //记录编曲界面大小
                                                             writeProperty("componentWide", String.valueOf(component.getWidth()));
                                                             writeProperty("componentHeight", String.valueOf(component.getHeight()));
                                                             startFrameInitialize();
                                                             componentShow();
                                                         } else {
                                                             isBackToStart[0] = true;
                                                             componentExitLog.setVisible(true);
                                                         }
                                                     }
                                                 });
            //隐藏提示栏
            hide.addActionListener(new

                                           AbstractAction() {
                                               @Override
                                               public void actionPerformed(ActionEvent e) {
                                                   if (hidestring[0].equals("隐藏")) {
                                                       hidestring[0] = "显示";
                                                       tip.setVisible(false);

                                                   } else {
                                                       hidestring[0] = "隐藏";
                                                       tip.setVisible(true);
                                                   }
                                                   hide.setLabel(hidestring[0] + "提示栏");
                                               }
                                           });
            //保存文件
            save.addActionListener(new

                                           AbstractAction() {
                                               @Override
                                               public void actionPerformed(ActionEvent e) {
                                                   save();
                                               }
                                           });
            subcom.addActionListener(new

                                             AbstractAction() {
                                                 @Override
                                                 public void actionPerformed(ActionEvent e) {
                                                     if (arrayList.size() >= 1) {
                                                         if (!hadChar[0] && arrayList.get(count[0]) == ' ') {
                                                             Ad.setEnabled(false);
                                                             isSub[0] = true;
                                                             tip.setText("请插入此节拍和弦");
                                                         } else if (!hadChar[0] && arrayList.get(count[0]) == '.') {
                                                             Ad.setEnabled(false);
                                                             isSub[0] = true;
                                                             tip.setText("请插入此节拍和弦 暂不支持附点输入");
                                                             // sub.set(count[0], '.');
                                                         } else if (!hadChar[0] && arrayList.get(count[0]) == '|') {
                                                             Ad.setEnabled(false);
                                                             tip.setText("插入失败 请在分节以前插入和弦!!!");
                                                         } else if (hadChar[0]) {
                                                             tip.setText("不能在此插入和弦 请输入完整音节后插入!!!");
                                                         }
                                                     }
                                                 }
                                             });
        }
    }


    //打开文件方法
    void open() {
        String[] opendi = new String[1];
        opendi[0] = null;
        String[] openfile = new String[1];
        openfile[0] = null;
        if (isSaved[0]) {
            opening.setFile("*.GenshinPiano");
            opening.setVisible(true);
            opening.setAlwaysOnTop(true);
            if (opening.getFile() != null) {
                opendi[0] = opening.getDirectory();
                openfile[0] = opening.getFile();
                System.out.println("打开文件路径：\n" + opendi[0] + openfile[0]);
                try {
                    piano.read(readList, opendi[0], openfile[0]);
                    tip.setText("打开文件成功");
                    opening.setFile(null);
                    opening.setVisible(false);
                    System.out.println("\n打开文件成功");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            tip.setText("文件打开失败");
        }
    }

    //获取jar路径
    public String getJarPath() {
        String path = null;
        try {
            path = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (System.getProperty("os.name").contains("dows")) {
            assert path != null;
            path = path.substring(1, path.length());
        }
        assert path != null;
        if (path.contains("jar")) {
            path = path.substring(0, path.lastIndexOf("."));
            return path.substring(0, path.lastIndexOf("/"));
        }
        return path.replace("target/classes/", "");
    }


    //保存方法
    void save() {
        String[] savingdi = new String[1];
        String[] savingfile = new String[1];
        Dialog shuldnot = new Dialog(component, "erro:音节不完整，不能保存");
        shuldnot.setBounds((int) (windowSize.getWidth() / 2 - 150), (int) (windowSize.getHeight() / 2 - 20), 300, 20);
        shuldnot.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                shuldnot.setVisible(false);
            }
        });
        if (!hadChar[0]) {
            saving.setFile("*.GenshinPiano");
            tip.setText("窗口已取消置顶");
            component.setAlwaysOnTop(false);
            saving.setAlwaysOnTop(true);
            saving.setVisible(true);
            componentExitLog.setVisible(false);
            if (saving.getFile() != null) {
                isSaved[0] = true;
                savingdi[0] = saving.getDirectory();
                savingfile[0] = saving.getFile();
                System.out.println("存储路径:\n" + savingdi[0] + savingfile[0]);
                try {
                    piano.write(getInputChar(outputNewList(arrayList, sub)), savingdi[0], savingfile[0]);
                    if (copy[0]) {
                        tip.setText("保存成功  保存路径已复制到剪切板");
                        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
                        Transferable tText = new StringSelection(savingdi[0]);
                        clip.setContents(tText, null);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            shuldnot.setResizable(false);
            shuldnot.setVisible(true);
            tip.setText("保存失败！");

        }
    }

    //输出整合编码 （保存文件整合编码）两个集合到一个集合
    ArrayList<Character> outputNewList(ArrayList<Character> arrayList, ArrayList<Character> sub) {
        ArrayList<Character> out = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            if (sub.get(i) == ' ') {
                out.add(arrayList.get(i));
            } else if (sub.get(i) == '|') {
                out.add('|');
            } else if (sub.get(i) != ' ' && sub.get(i) != null && sub.get(i) != '\0') {
                out.add(arrayList.get(i));
                out.add(arrayList.get(i + 1));
                if (arrayList.get(i + 2) == '.') {
                    out.add(arrayList.get(i + 2));
                    out.add('和');
                    out.add(sub.get(i));
                    out.add(sub.get(i + 1));
                    out.add(' ');
                    i += 2;
                } else if (arrayList.get(i + 2) == ' ' || arrayList.get(i + 2) == '|') {
                    //out.remove(i + 2);
                    out.add('和');
                    out.add(sub.get(i));
                    out.add(sub.get(i + 1));
                    i += 1;
                }
            }
        }
        return out;
    }

    //输入编码分离（打开文件解码）一个集合到两个集合
    void separate(ArrayList<String> readList, ArrayList<Character> arrayList, ArrayList<Character> sub) {
        int count = 0;
        for (int i = 0; i < readList.size(); i++) {
            if (readList.get(i).equals("和")) {
                if (!readList.get(i - 1).equals(".")) {
                    sub.remove(count - 1);
                    sub.remove(count - 2);
                    sub.add(readList.get(i + 1).charAt(0));
                    sub.add(readList.get(i + 2).charAt(0));
                    i += 2;
                } else {
                    sub.remove(count - 1);
                    sub.remove(count - 2);
                    sub.remove(count - 3);
                    sub.add(readList.get(i + 1).charAt(0));
                    sub.add(readList.get(i + 2).charAt(0));
                    sub.add(' ');
                    i += 2;
                }
            } else if (readList.get(i).equals("|")) {
                arrayList.add('|');
                count++;
                sub.add('|');
            } else if ((readList.get(i).charAt(0) >= 'A' && readList.get(i).charAt(0) <= 'Z') ||
                    (readList.get(i).charAt(0) >= '0' && readList.get(i).charAt(0) <= '9') ||
                    (readList.get(i).charAt(0) == ' ') || readList.get(i).charAt(0) == '.' || readList.get(i).charAt(0) == '\n') {
                arrayList.add(readList.get(i).charAt(0));
                sub.add(' ');
                count++;
            } else {
                error.add(tuiChu);
                component.setAlwaysOnTop(false);
                error.setAlwaysOnTop(true);
                error.setBounds((int) windowSize.getWidth() / 2 - 250 / 2, (int) windowSize.getHeight() / 2 - 64 / 2, 250, 64);
                error.setVisible(true);
                tuiChu.setFocusable(false);
                tuiChu.setForeground(Color.red);
                tuiChu.setFont(new Font("黑体", Font.BOLD | Font.ITALIC, 16));

                for (int daoshu = 5; daoshu > 0; daoshu--) {
                    tuiChu.setLabel(daoshu + "秒后退出程序...");
                    delay.sleepTime time = new delay.sleepTime(1);
                    time.start();
                    try {
                        time.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (daoshu <= 3) {
                        tuiChu.setForeground(Color.blue);
                    }
                }
                System.exit(0);
                error.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });

            }
        }
    }
}
