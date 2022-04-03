public class charToString {
    //字符数组转字符串
    private static final StringBuilder text = new StringBuilder();

    charToString(char[] outchar) {
        text.delete(0, text.length());
        int i = 0;
        for (; i < outchar.length;i++){
            if (outchar[i] == 'P'){
                outchar[i] = '*';
            }
            text.append(outchar[i]);
        }
    }

    String tostring2() {
        return text.toString();
    }
}
