class delay {

    //a秒延迟
    static class sleepTime extends Thread {
        double c;
        public sleepTime(double second) {
            c = second;
        }
        public void run() {
            try {
                sleep((int) (1000 * c));
            } catch (InterruptedException ignored) {
                System.out.println("delay类错误");
            }
        }
    }
}
