/**
 * Created by xiaokun on 2015/3/21.
 */
package us.thosedays;

import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;

import java.io.IOException;


public class BasicDemo1 {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException{
        // create one connection to ZK
        ZooKeeper zk=new ZooKeeper("10.197.38.157:2182",60000,new Watcher(){
            // listen all events
            public void process(WatchedEvent event){
                System.out.println("AAAAEVENT:" +event.getType());
            }
        });

        // check root node
        System.out.println("ls / =>"+ zk.getChildren("/", true));

        // create directory node
        if(zk.exists("/node", true)==null){
            zk.create("/node", "xihou".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("create /node xihou");

            //check node data
            System.out.println("get /node=> " +new String(zk.getData("/node", false, null)));
            //check root node
            System.out.println("ls / =>"+zk.getChildren("/", true));

        }
        if(zk.exists("/node/sub1", true)==null){
            zk.create("/node/sub1", "sub1".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("create /node/sub1 sub1");

            System.out.println("ls /node =>"+zk.getChildren("/node", true));
        }

        // change node data
        if (zk.exists("/node", true) != null) {
            zk.setData("/node", "changed".getBytes(), -1);
            // check node data
            System.out.println("get /node => " + new String(zk.getData("/node", false, null)));
        }

        // delete node
        if (zk.exists("/node/sub1", true) != null) {
            zk.delete("/node/sub1", -1);
            zk.delete("/node", -1);
            // check root node
            System.out.println("ls / => " + zk.getChildren("/", true));
        }
        zk.close();
    }
}