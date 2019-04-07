import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import java.util.HashMap;

public class App {

    public static void main(String[] args) {
        String[] servers = {"localhost:12345"};
        SockIOPool pool = SockIOPool.getInstance("Test1");
        pool.setServers(servers);
        pool.setFailover(true);
        pool.setInitConn(10);
        pool.setMinConn(5);
        pool.setMaxConn(250);
        pool.setMaintSleep(30);
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setAliveCheck(true);
        pool.initialize();

        MemCachedClient mcc = new MemCachedClient("Test1");
        System.out.println("add status: " + mcc.add("1", "Original"));
        System.out.println("get status: " + mcc.get("1"));
        System.out.println("add status: " + mcc.add("1", "Modified"));
        System.out.println("get status: " + mcc.get("1"));
        System.out.println("set status: " + mcc.set("1", "Modified"));
        System.out.println("Get from Cache after set: "+mcc.get("1"));

        //use delete function to delete key from cache
        System.out.println("remove status: "+mcc.delete("1"));
        System.out.println("Get from Cache after delete: "+mcc.get("1"));

        //Use getMulti function to retrieve multiple keys values in one function
        // Its helpful in reducing network calls to 1
        mcc.set("2", "2");
        mcc.set("3", "3");
        mcc.set("4", "4");
        mcc.set("5", "5");

        String [] keys = {"1", "2","3","INVALID","5"};
        HashMap<String,Object> hm = (HashMap<String, Object>) mcc.getMulti(keys);

        for(String key : hm.keySet()){
            System.out.println("KEY: "+key+" VALUE: "+hm.get(key));
        }
    }
}
