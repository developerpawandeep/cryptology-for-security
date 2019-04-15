import static com.database1.Connections1.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ServerClass1 extends UnicastRemoteObject implements ServerInterface1 {

    ArrayList<String> userids=new ArrayList<String>();
    ArrayList<int[]> ques=new ArrayList<int[]>();
    ServerClass1() throws RemoteException {
        super();
    }
    public int[] getRandomQuestions()
    {
        int n[]=new int[5];
        for(int i=0;i<5;){
            int temp=(int)(17*Math.random());
            if(temp==0)
                temp=temp+1;
            int j=0;
            for(;j<i;j++){
                if(n[j]==temp)
                    break;
            }
            if(j==i)
                n[i++]=temp;
        }
        return n;
    }
    @Override
    public int checkUser(String userid, String passwd) throws RemoteException {
        try {
            PreparedStatement pst = makeConnection("select * from students where username=?");
            pst.setString(1, userid);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String pass = rs.getString(2);
                if (!pass.equals(passwd)) {
                    pst.close();
                    c.close();
                    return 2;
                } else {
                    userids.add(userid);
                    ques.add(getRandomQuestions());
                    pst.close();
                    c.close();
                    return 3;
                }
            } else {
                pst.close();
                c.close();
                return 1;
            }
        } catch (Exception e) {
        }
        return 4;
    }

    @Override
    public void save(String a1, String a2, int a3, String a4, int a5, String a6) {
        try {
            PreparedStatement pst = makeConnection("insert into students(name,passwd,rollno,course,year,gender) values(?,?,?,?,?,?)");
            pst.setString(1, a1);
            pst.setString(2, a2);
            pst.setInt(3, a3);
            pst.setString(4, a4);
            pst.setInt(5, a5);
            pst.setString(6, a6);
            pst.executeUpdate();
            pst.close();
            c.close();
        } catch (Exception e) {

        }
    }

    @Override
    public String[] fetchQues(int q,String userid) throws RemoteException {
        int index=userids.indexOf(userid);
        int qu[]=ques.get(index);
        String z[] = new String[5];
        try {

            PreparedStatement pst = makeConnection("select * from mathematics where qno=?");
            pst.setInt(1, qu[q-1]);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {

                z[0] = rs.getString(2);
                z[1] = rs.getString(3);
                z[2] = rs.getString(4);
                z[3] = rs.getString(5);
                z[4] = rs.getString(6);
            }

            rs.close();
            pst.close();
            c.close();
        } catch (Exception e) {
        }
        return z;
    }

    public static void main(String[] args) {
        try {
            ServerClass1 sc = new ServerClass1();
            Naming.rebind("server1", sc);
            System.out.println("Server Registered!!!");
        } catch (Exception e) {
        }
    }

    public String[] fetchDetails(String name) throws RemoteException {
        String w[] = new String[6];

        try {
            PreparedStatement pst = makeConnection("select * from students where username=?");
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                w[0] = rs.getString(1);
                w[1] = rs.getString(2);
                w[2] = rs.getInt(3) + "";
                w[3] = rs.getString(4);
                w[4] = rs.getString(5);
                w[5] = rs.getString(6);
            }
            rs.close();
            pst.close();
            c.close();
        } catch (Exception e) {
        }
        return w;
    }

    @Override
    public boolean[] evaluate(int[] answers) throws RemoteException 
    {
        boolean b[]=new boolean[16];
        try{
        PreparedStatement st=makeConnection(
                "select ans from mathematics");
        ResultSet rs=st.executeQuery();
        int index=0;
        while(rs.next()){
            int a=rs.getInt(1);
            if(answers[index]==a)
                b[index]=true;
            else
                b[index]=false;
            index++;
        }
        rs.close();
        st.close();
        }catch(Exception e)
        {}
        return b;
    }
    
}
