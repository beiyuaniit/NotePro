public class 显示当前时间 {
    public static void main(String[] args) {
        //获取1970年1月1日到现在的毫秒数
        long totalMilliseconds=System.currentTimeMillis();

        long second=(totalMilliseconds/1000)%60;

        long minute=(totalMilliseconds/1000/60)%60;

        long hour=(totalMilliseconds/1000/60/60)%12;

        System.out.println(hour+":"+minute+":"+second);
        //小时这里好像出了问题
    }
}
