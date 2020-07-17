package site.teamo.learning.hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.Closeable;
import java.io.IOException;

public class HdfsApp {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        FileSystem fs = null;

        try {
            //创建配置，设置Hdfs的地址信息
            Configuration configuration = new Configuration();
            configuration.set("fs.defaultFS", "hdfs://192.168.56.100:9000");
            //在系统环境变量设置使用的用户名
            System.setProperty("HADOOP_USER_NAME", "root");

            //打开一个文件系统
            fs = FileSystem.get(configuration);

            //创建一个目录
            fs.mkdirs(new Path("/dream-hammer"));

            //判断是否是目录
            fs.isDirectory(new Path("/dream-hammer"));

            //创建一个文件dream1.txt，返回值boolean类型，true：创建成功；false：创建失败
            fs.createNewFile(new Path("/dream-hammer/dream1.txt"));

            //判断文件是否存在，返回值是boolean类型，true：存在；false：不存在
            fs.exists(new Path("/dream-hammer/dream1.txt"));

            //向文件追加内容
            FSDataOutputStream out = null;
            try {
                out = fs.append(new Path("/dream-hammer/dream1.txt"));
                out.writeUTF("dream-hammer");
                out.flush();
                out.close();
            } finally {
                close(out);
            }

            //打开dream1的输入流

            FSDataInputStream in = null;
            try {
                in = fs.open(new Path("/dream-hammer/dream1.txt"));
                System.out.println(in.readUTF());
                in.close();
            } finally {
                close(in);
            }

            //删除目录和文件
            fs.delete(new Path("/dream-hammer"), true);
        } finally {
            close(fs);
        }

    }

    /**
     * 关闭流，释放资源
     * @param closeable
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
