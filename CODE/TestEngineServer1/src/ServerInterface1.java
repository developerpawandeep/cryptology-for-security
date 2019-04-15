
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface1 extends Remote {

    int checkUser(String userid, String pass) throws RemoteException;

    void save(String a1, String a2, int a3, String a4, int a5, String a6) throws RemoteException;

    public String[] fetchQues(int q,String userid) throws RemoteException;

    public String[] fetchDetails(String name) throws RemoteException;
    boolean[] evaluate(int answers[])throws RemoteException;
}
