
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Login_Register extends JFrame {


    Login_Register() {
        init();
    }

    //登录界面初始化
    public void init() {
        JFrame frame = new JFrame("添加进程");
        frame.setLayout(null);

        //01
        JLabel name = new JLabel("进程名");
        name.setBounds(250, 200, 100, 25);
        frame.add(name);
        //输入框
        JTextField Name = new JTextField();
        Name.setBounds(300, 200, 150, 25);
        frame.add(Name);

        //02
        JLabel pri = new JLabel("优先级");
        pri.setBounds(250, 250, 100, 25);
        frame.add(pri);
        //输入框
        JTextField Pri = new JTextField();
        Pri.setBounds(300, 250, 150, 25);
        frame.add(Pri);

        //03
        JLabel runTime = new JLabel("运行时间");
        runTime.setBounds(250, 300, 100, 25);
        frame.add(runTime);
        //
        JTextField RunTime = new JTextField();
        RunTime.setBounds(300, 300, 150, 25);
        frame.add(RunTime);

        //03
        JLabel Ramsize = new JLabel("所需内存");
        Ramsize.setBounds(250, 350, 100, 25);
        frame.add(Ramsize);
        //
        JTextField RamSize = new JTextField();
        RamSize.setBounds(300, 350, 150, 25);
        frame.add(RamSize);


        //04
        JButton buttonlogin = new JButton("添加");
        buttonlogin.setBounds(375, 400, 70, 25);
        frame.add(buttonlogin);
        //
        frame.setBounds(400, 100, 800, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //为登录按钮添加监听器
        buttonlogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String proName= Name.getText();
                int proPri = Integer.parseInt(Pri.getText());
                int  proRuntime=Integer.parseInt(RunTime.getText());
                int size=Integer.parseInt(RamSize.getText());


                //弹出账号或密码错误的窗口
                JOptionPane.showMessageDialog(null, "添加成功",
                        "", JOptionPane.WARNING_MESSAGE);
                //清除密码框中的信息
                Pri.setText("");
                //清除账号框中的信息
                Name.setText("");


            }
        });

    }

    public static void main(String[] args) {
        //主程序
        //登录窗口
        Login_Register login_register = new Login_Register();
    }
}


