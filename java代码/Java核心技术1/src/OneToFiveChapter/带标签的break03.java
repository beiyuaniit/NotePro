package OneToFiveChapter;

/**
 * @author: beiyuan
 * @className: 带标签的break
 * @date: 2022/3/3  22:50
 */
public class 带标签的break03 {
    public static void main(String[] args) {
        int i=2;
        toContinue:
        while (i>0){
            for(int j=1;j<10;j++){
                System.out.println(j);
                break toContinue;
            }
            i--;
        }
    }
}
