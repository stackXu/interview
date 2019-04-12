import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Created by 东东 on 2019/4/12.
 */
public class InterviewDemo {

    // 监控目录,可以写到配置文件中
    public static final String rootDir = "E:\\test\\A\\";

    public static void main(String[] args) {

//        questionOne(3);

        questionTwo();

    }

    //  1.  java求一个整数的平方根,不保留小数
    public static void questionOne(int sc) {
        //保留几位小数
        DecimalFormat df = new DecimalFormat("0");
        System.out.print(sc + "的算术平方根是：");
        System.out.println(df.format(SQR(sc)));
    }

    public static double SQR(int a) {
        double x1 = 1, x2;
        //牛顿迭代公式
        x2 = x1 / 2.0 + a / (2 * x1);
        while (Math.abs(x2 - x1) > 1e-4) {
            x1 = x2;
            x2 = x1 / 2.0 + a / (2 * x1);
        }
        return x2;
    }

    // 2.具体方法是onFileCreate
    public static void questionTwo() {

        // 轮询间隔 3 秒
        long interval = TimeUnit.SECONDS.toMillis(3);

        //遍历根目录下所有文件
        Collection<File> listFiles = FileUtils.listFiles(new File(rootDir),
                FileFilterUtils.suffixFileFilter(".jpg"), DirectoryFileFilter.DIRECTORY);
        for (File file : listFiles) {
            System.out.println(file.getName());
            //根据情况去判断要不要在文件夹B里面创建
        }

        //不使用过滤器,就会监听目录下所有文件,目录(包括子文件里面的改动)
        FileAlterationObserver observer = new FileAlterationObserver(
                new File(rootDir));

        observer.addListener(new MyFileListener());
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        // 开始监控
        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
