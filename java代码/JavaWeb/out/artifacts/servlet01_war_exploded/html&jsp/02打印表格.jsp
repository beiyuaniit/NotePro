<%--
  Created by IntelliJ IDEA.
  User: beilinanju
  Date: 2022/1/23
  Time: 21:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>求值并打印</title>
</head>
<body>
    <%
        out.print("<table border='2' cellspacing cellpadding><tbody>");
        int numStart=1;
        int numEnd=15;
        for (int i=0;i<10;i++){
            out.print("<tr>");

            for(int j=numStart;j<=numEnd;j++) {
                if(i==0){
                    out.print("<th>"+j+"</th>");
                }else {
                    out.print("<td>" + j + "</td>");
                }

            }

            numEnd++;
            numStart++;

            out.print("</tr>");
        }
        out.print("</tbody></table>");
    %>

    <table border="" cellspacing="" cellpadding="">
        <tr>
            <th>Header</th>
            <th>Name</th>
            <th>Age</th>
        </tr>
        <tr>
            <td>Data</td>
            <td>beiyuan</td>
            <td>21</td>
        </tr>
    </table>
</body>
</html>
