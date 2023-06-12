/**
 * @author: beiyuan
 * @className: SMTPClient
 * @date: 2022/5/22  8:54
 */
/**
 * 简易的邮件客户端程序，后期将逐步完善提供友好UI
 * 测试发现不支持Gmail，Gmail SMTP采用了TSL/SSL协议
 * 已知JavaMail可实现SSL协议。。。
 */

/**
 * @author Daniel Cheng
 *
 */

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

/*

有550错误
去官网登录163邮箱
设置->POP3->开启POP3/SMTP
成功开启POP3/SMTP服务，在第三方客户端登录时，登录密码输入以下授权密码
OILBOSKHHPQZUYBB
 */
public class SMTPClient {

    private boolean debug = true;
    BASE64Encoder encode = new BASE64Encoder();// 自定义BASE64Encoder工具类用于加密字符串

    public static void main(String[] args) throws UnknownHostException,
            IOException {
        MailMessage message = new MailMessage();
        message.setFrom("beiyuanii@163.com");// 发件人
        message.setTo("2834420847@qq.com");// 多个收件人地址间用逗号隔开
        String server = "smtp.163.com";// SMTP邮件服务器
        message.setSubject("Java Mail Test");// 邮件主题
        message.setContent("你好！这里是系统发出的JAVA MAIL测试，请勿回复。");// 邮件内容
        message.setDataFrom("Sender");// 发件人，在邮件的发件人栏目中显示
        message.setDataTo("Receiver");// 收件人，在邮件的收件人栏目中显示
        message.setUser("beiyuanii@163.com");// 登陆的邮箱账号
        message.setPassword("OILBOSKHHPQZUYBB");// 登陆邮箱的密码，建议自己保密好，公开传播会泄密

        SMTPClient smtp = new SMTPClient(server, 25);
        boolean flag;
        flag = smtp.sendMail(message, server);
        if (flag) {
            System.out.println("邮件发送成功！");
        } else {
            System.out.println("邮件发送失败！");
        }
    }

    private Socket socket;

    public SMTPClient(String server, int port) throws UnknownHostException,
            IOException {
        try {
            socket = new Socket(server, 25);
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("已经建立连接!");
        }

    }

    // 注册到邮件服务器
    public void helo(String server, BufferedReader in, BufferedWriter out)
            throws IOException {
        int result;
        result = getResult(in);
        // 连接上邮件服务后,服务器给出220应答
        if (result != 220) {
            throw new IOException("连接服务器失败");
        }
        result = sendServer("HELO " + server, in, out);
        // HELO命令成功后返回250
        if (result != 250) {
            throw new IOException("注册邮件服务器失败！");
        }
    }

    private int sendServer(String str, BufferedReader in, BufferedWriter out)
            throws IOException {
        out.write(str);
        out.newLine();
        out.flush();
        if (debug) {
            System.out.println("已发送命令:" + str);
        }
        return getResult(in);
    }

    public int getResult(BufferedReader in) {
        String line = "";
        try {
            line = in.readLine();
            if (debug) {
                System.out.println("服务器返回状态:" + line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 从服务器返回消息中读出状态码,将其转换成整数返回
        StringTokenizer st = new StringTokenizer(line, " ");
        return Integer.parseInt(st.nextToken());
    }

    public void authLogin(MailMessage message, BufferedReader in,
                          BufferedWriter out) throws IOException {
        int result;
        result = sendServer("AUTH LOGIN", in, out);
        if (result != 334) {
            throw new IOException("用户验证失败！");
        }

        result = sendServer(encode.encode(message.getUser().getBytes()), in,
                out);
        if (result != 334) {
            throw new IOException("错误！");
        }
        result = sendServer(encode.encode(message.getPassword().getBytes()),
                in, out);

        if (result != 235) {
            throw new IOException("验证失败！");
        }
    }

    // 开始发送消息，邮件源地址
    public void mailFrom(String source, BufferedReader in, BufferedWriter out)
            throws IOException {
        int result;
        result = sendServer("MAIL FROM:<" + source + ">", in, out);
        if (result != 250) {
            throw new IOException("指定源地址错误");
        }
    }

    // 设置邮件收件人。多邮件发送用","隔开
    public void rcpt(String touchman, BufferedReader in, BufferedWriter out)
            throws IOException {

        String[] mailList = touchman.split(",");
        if (mailList.length > 1) {
            for (String touch : mailList) {
                connectionServer(touch,in,out);
            }
        }else
            connectionServer(touchman,in,out);

    }

    private void connectionServer(String touch, BufferedReader in, BufferedWriter out)
            throws IOException {
        int result;
        result = sendServer("RCPT TO:<" + touch + ">", in, out);
        if (result != 250) {
            throw new IOException("指定目的地址错误！");
        }
    }

    // 邮件体
    public void data(String from, String to, String subject, String content,
                     BufferedReader in, BufferedWriter out) throws IOException {
        int result;
        result = sendServer("DATA", in, out);
        // 输入DATA回车后,若收到354应答后,继续输入邮件内容
        if (result != 354) {
            throw new IOException("不能发送数据");
        }
        out.write("From: " + from);
        out.newLine();
        out.write("To: " + to);
        out.newLine();
        out.write("Subject: " + subject);
        out.newLine();
        out.newLine();
        out.write(content);
        out.newLine();
        // 句号加回车结束邮件内容输入
        result = sendServer(".", in, out);
        System.out.println(result);
        if (result != 250) {
            throw new IOException("发送数据错误");
        }
    }

    // 退出
    public void quit(BufferedReader in, BufferedWriter out) throws IOException {
        int result;
        result = sendServer("QUIT", in, out);
        if (result != 221) {
            throw new IOException("未能正确退出");
        }
    }

    // 发送邮件主程序
    public boolean sendMail(MailMessage message, String server) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream()));
            helo(server, in, out);// HELO命令
            authLogin(message, in, out);// AUTH LOGIN命令
            mailFrom(message.getFrom(), in, out);// MAIL FROM
            rcpt(message.getTo(), in, out);// RCPT
            data(message.getDataFrom(), message.getDataTo(), message
                    .getSubject(), message.getContent(), in, out);// DATA
            quit(in, out);// QUIT
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
        return true;
    }

}
