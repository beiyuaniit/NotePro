package springaop;

/**
 * @author: beiyuan
 * @className: BookServiceImpl
 * @date: 2022/6/29  22:41
 */
public class BookServiceImpl implements BookService{
    @Override
    public boolean bug(String userName, String bookName, double price) {
        System.out.println(userName+" buys "+" the " +bookName+" cost "+price);
        return true;
    }

    @Override
    public void comment(String userName, String comments) {
        System.out.println(userName+"  has made a  comment \""+comments+"\"");
    }
}
