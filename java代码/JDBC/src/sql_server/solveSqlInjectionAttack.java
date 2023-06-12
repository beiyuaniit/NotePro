package sql_server;

/**
 * @author: beiyuan
 * @className: solveSqlInjectionAttack
 * @date: 2022/1/17  9:02
 */

import java.sql.*;

/**
 * PreparedStatement��Statement�Ա�����
 *      �����SQLע��
 *      ֻ����һ�Σ����ж�Σ��ٶȿ�㣨SQL�����򲻻��ٱ��룩
 *      ��java����׶������͵İ�ȫ��飨��setString()����ֻ�����ַ���
 * ����:
 *      �������ַ���ʱ���''
 * ���䣺
 *    ��ɾ�Ĳ�Ҳ�����ô�ֵ�ķ�ʽ
 *    ���鷳����д��������������ӣ��ͷ���Դ��
 */


/*
JDBC������
    �Զ��ύ��ֻҪִ��һ��DML��䣬���ύһ��
    �磺
        ps.executeQuery();//��һ���ύ
        ps.executeQuery();//�ڶ����ύ
���ó��ֶ�
    ���ı����������������仰
    conn.setAutoCommit(false);//�õ����Ӻ�
    conn.commit();//���������ɺ�
    conn.rollback();//��������������쳣��
 */

public class solveSqlInjectionAttack {
    public static void main(String[] args) throws Exception {

        //�������ļ���ȡ��Ϣ
        //�����ļ�·���Ǵ�src��Ŀ¼����


        /**
         * Ԥ���룬Ȼ���ٸ�SQL��䴫ֵ
         * �û��ṩ����Ϣ������SQL���ı������
         */
        PreparedStatement ps=null;//��d
        Connection conn = null;
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            conn = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=beiSql",
                    "sa", "beiyuan3721");


            //׼��SQL���ģ��.һ������ʾռλ����������ֵ���û��������룩
            String SqL="select *from Student where Sno= ? and Sage=?;";

            //DBMS����Ԥ����
            ps=conn.prepareStatement(SqL);//����û��d

            //JDBC���±��1��ʼ
            ps.setString(1, "21003");
            ps.setString(2,"18");

            ResultSet result = ps.executeQuery();

            if(result.next()){
                System.out.println("��¼�ɹ���");
            }else {
                System.out.println("��¼ʧ�ܣ�");
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ps.close();
            conn.close();
        }
    }
}
