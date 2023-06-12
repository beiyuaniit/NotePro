package sql_server;

/**
 * @author: beiyuan
 * @className: sql
 * @date: 2021/10/22  23:48
 */


public class sql {

    //一次可以写多条sql语句
    private String sqlcreate=
            //学生表
            "create table Student" +
            "(Sno varchar(9) primary key," +
            "Sname varchar(20) unique," +
            "Ssex varchar(2)," +
            "Sage smallint," +
            "Sdept varchar(20))" +

             //课程表
            "create table Course" +
            "(Cno varchar(4) primary key," +
            "Cname varchar(40) not null," +
            "Cpno varchar(4)," +
            "Ccredit smallint," +
            "foreign key (Cpno) references Course(Cno))" +

             //选课表
            "create table Sc" +
            "(Sno varchar(9)," +
            "Cno varchar(4)," +
            "Grade smallint," +
            "primary key(Sno,Cno)," +
            "foreign key (Sno) references Student(Sno)," +
            "foreign key (Cno) references Course(Cno))";


    private String sqlinsert=
                    "insert into Student(Sno,Sname,Ssex,Sage,Sdept)" +
                    "values"+
                    "('21001','李勇','男',20,'CS'),"+
                    "('21002','刘晨','女',19,'IS'),"+
                    "('21003','王敏','女',18,'MA'),"+
                    "('21004','张立','男',19,'IS')"+

                    "insert into Course(Cno,Cname,Cpno,Ccredit)"+
                    "values"+
                    "('1','数据库','5',4),"+
                    "('2','数学',null,2),"+
                    "('3','信息系统','1',4),"+
                    "('4','操作系统','6',3),"+
                    "('5','数据结构','7',4),"+
                    "('6','数据处理',null,2),"+
                    "('7','C语言','6',4)"+

                    "insert into Sc(Sno,Cno,Grade)"+
                    "values"+
                    "('21001','1',92),"+
                    "('21001','2',85),"+
                    "('21001','3',88),"+
                    "('21002','2',90),"+
                    "('21002','3',80)";

    private String sqldelete="drop table Sc,Course,Student;";

    private String sqlquery="select * from Student;";

    public String getSqlCreate() {
        return sqlcreate;
    }

    public String getSqlQuery(){
        return sqlquery;
    }

    public String getSqldelete(){
        return sqldelete;
    }
    public String getSqlinsert(){
        return sqlinsert;
    }
}
