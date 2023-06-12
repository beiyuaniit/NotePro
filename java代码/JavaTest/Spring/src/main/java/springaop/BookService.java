package springaop;

/**
 * @author: beiyuan
 * @className: BookService
 * @date: 2022/6/29  22:38
 */
public interface BookService {
    //买书
    boolean bug(String userName,String bookName,double price);
    //评论
    void comment(String userName,String comments);
}
