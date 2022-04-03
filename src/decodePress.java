class decodePress extends Object{
    //解码并执行按键
    public static boolean isplaying = true;
    public decodePress(char[] comChar, char[] subChar) {
        for (int i = 0; i < comChar.length - 1 && isplaying; i++) {
            if (subChar[i] == ' ' && comChar[i] >= 'A' && comChar[i] <= 'Z' && comChar[i + 2] != '.') {
                pressNull press = new pressNull(comChar[i], 'a', comChar[i + 1], subChar[i + 1], false);
                press.run();
            } else if (subChar[i] == ' ' && comChar[i] >= 'A' && comChar[i] <= 'Z' && comChar[i + 2] == '.') {
                //附点
                press press = new press(comChar[i], 'a', comChar[i + 1], subChar[i + 1], false);
                press.run();
            } else if (subChar[i] >= 'A' && subChar[i] <= 'Z' && subChar[i] != '\n' && comChar[i + 2] != '.') {
                pressNull press = new pressNull(comChar[i], subChar[i], comChar[i + 1], subChar[i + 1], true);
                press.run();
            } else if (subChar[i] >= 'A' && subChar[i] <= 'Z' && subChar[i] != '\n' && comChar[i + 2] == '.') {
                //附点
                press press = new press(comChar[i], subChar[i], comChar[i + 1], subChar[i + 1], true);
                press.run();
            }
        }
    }
}

