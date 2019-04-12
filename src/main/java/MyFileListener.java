import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.*;

/**
 * 自定义文件监听器
 */
public class MyFileListener extends FileAlterationListenerAdaptor {

    public static final String toRoot = "E:\\test\\B\\";

    @Override
    public void onFileCreate(File file) {
        System.out.println("[新建]:" + file.getAbsolutePath());
        String absolutePath = file.getParent();
        String[] split = absolutePath.split("\\\\");
        int length = split.length;
        try {
            File file1 = new File(toRoot + split[length - 3] + "_" + split[length - 2] + "_" + split[length - 1] + "_" + file.getName().substring(file.getName().lastIndexOf('.')));
            FileUtils.copyFile(file, file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFileChange(File file) {
        System.out.println("[修改]:" + file.getAbsolutePath());
    }

    @Override
    public void onFileDelete(File file) {
        System.out.println("[删除]:" + file.getAbsolutePath());
    }

    @Override
    public void onDirectoryCreate(File directory) {
        System.out.println("[目录创建]:" + directory.getAbsolutePath());
        super.onDirectoryCreate(directory);
    }

    @Override
    public void onStart(FileAlterationObserver observer) {
        System.out.println("[开始监听]");
        super.onStart(observer);
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        System.out.println("[停止监听]");
        super.onStop(observer);
    }

    @Override
    public void onDirectoryDelete(File directory) {
        System.out.println("[目录删除]:" + directory.getAbsolutePath());
        super.onDirectoryDelete(directory);
    }

    @Override
    public void onDirectoryChange(File directory) {
        System.out.println("[目录修改]:" + directory.getAbsolutePath());
        super.onDirectoryChange(directory);
    }

    // 复制文件
    public static void copyFile(File sourceFile, File targetFile)
            throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {

            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }

            outBuff.flush();
        } finally {

            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

}  