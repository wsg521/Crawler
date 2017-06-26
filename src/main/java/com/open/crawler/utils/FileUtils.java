package com.open.crawler.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtils {
    private static final String CLASS_NAME = "FileUtils";
    private static boolean flag = false;
    FileUtils(){
    }
    /**
     * 功能:压缩多个文件成一个zip文件    
     * 
     * @param srcfile
     *            ：源文件列表
     * @param zipfile
     *            ：压缩后的文件
     */
    public static void zipFiles(File[] srcfile, File zipfile) {
        byte[] buf = new byte[1024];
        // ZipOutputStream类：完成文件或文件夹的压缩
        try(ZipOutputStream out= new ZipOutputStream(new FileOutputStream(zipfile))) {
            for (File element : srcfile) {
                putOneFileIntoZip(out,element,buf);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    private static void putOneFileIntoZip(ZipOutputStream out, File element, byte[] buf){
        try(FileInputStream in= new FileInputStream(element)){
            out.putNextEntry(new ZipEntry(element.getName()));
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.closeEntry();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // =============================================
    // 打包文件入口类
    // parm:生成文件名,要打包的文件名,或目录
    // =============================================

    public static boolean makeFileToZip(String outfile, String infile) {
        try(ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outfile))){
            File file = new File(infile);
            // 进行打包zip操作,第一次打包不指定文件夹,因为程序接口中指定了一级文件夹
            makeFileZipDo(out, file, "");
            out.close();
            return true;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =============================================
    // 打包文件操作类
    // parm:zip输出流,打包文件,下一级的目录
    // =============================================
    public static void makeFileZipDo(ZipOutputStream out, File file, String dir)
            throws IOException {
        // 如果当前打包的是目录
        if (file.isDirectory()) {
            System.out.println("当前正在打包文件夹:" + file + "...");
            File[] files = file.listFiles();
            // 添加下一个打包目录文件
            out.putNextEntry(new ZipEntry(dir + "/"));
            //
            dir = dir.length() == 0 ? "" : dir + "/";
            for (File file2 : files) {
                makeFileZipDo(out, file2, dir + file2.getName());
            }
        }
        // 如果当前打包文件
        else {
            System.out.println("当前正在打包文件:" + file + "...");
            //
            out.putNextEntry(new ZipEntry(dir + file.getName()));
            FileInputStream in = new FileInputStream(file);
            int i;
            while ((i = in.read()) != -1) {
                out.write(i);
            }
            in.close();
        }
    }

    // =============================================
    // 解压zip文件操作类
    // parm:zip文件,输出文件夹
    // =============================================
    public static boolean openFileZip(String zipfile, String savedir) {
        String methodName="openFileZip";
        try(ZipInputStream in = new ZipInputStream(new FileInputStream(zipfile))) {
            ZipEntry z;
            while ((z = in.getNextEntry()) != null) {
                if (z.isDirectory()) {
                    File tempfile = new File(StringHelper.concat(savedir , File.separator
                            , z.getName().substring(0, z.getName().length() - 1)));
                    tempfile.mkdir();
                } else {
                    File tempfile = new File(StringHelper.concat(savedir , File.separator , z.getName()));
                    if(tempfile.createNewFile()){
                        try(FileOutputStream out = new FileOutputStream(tempfile)){
                            int i;
                            while ((i = in.read()) != -1) {
                                out.write(i);
                            }
                            out.close();
                        }catch(Exception e){
                            e.printStackTrace();
                        }                        
                    }
                }
            }
            in.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     * 
     * @param sPath
     *            要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String sPath) {
        flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) { // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) { // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else { // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     * 
     * @param sPath
     *            被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     * 
     * @param sPath
     *            被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                flag = deleteFile(file.getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else {
                flag = deleteDirectory(file.getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 读取文件内容
     * 
     * @param filePath
     *            输出文件路径
     * @return
     * @throws Exception
     */
    public static String readFile(String filePath) throws Exception {
        return readFile(filePath,null);
    }

    /**
     * 读取文件内容
     * 
     * @param filePath
     *            输出文件路径
     * @return
     * @throws Exception
     */
    public static String readFile(String filePath, String code) throws Exception {
        File file = new File(filePath);
        return readFile(file,code);
    }

    /**
     * 读取文件内容
     * 
     * @param file
     *            输出文件路径
     * @param code
     * @return  null 表示异常
     */
    public static String readFile(File file, String code){
        try(InputStreamReader reader =
                new InputStreamReader(new FileInputStream(file), code != null? code:"UTF-8")){
            BufferedReader br = new BufferedReader(reader);
            StringBuilder fileContent = new StringBuilder();
            String temp;
            while ((temp = br.readLine()) != null) {
                fileContent.append(temp);
            }
            br.close();
            reader.close();
            return fileContent.toString();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 写文件
     * 
     * @param outputPath
     *            输出文件路径
     * @param is
     *            输入流
     * @param isApend
     *            是否追加
     * @throws IOException
     */
    public static void writeFile(InputStream is, String outputPath, boolean isApend)
            throws IOException {
        FileInputStream fis = (FileInputStream) is;
        File file=createDir(outputPath);
        FileOutputStream fos = new FileOutputStream(file, isApend);
        byte[] bs = new byte[1024 * 16];
        int len = -1;
        while ((len = fis.read(bs)) != -1) {
            fos.write(bs, 0, len);
        }
        fos.close();
        fis.close();
    }

    /**
     * copy文件
     * 
     * @param is
     *            输入流
     * @param outputPath
     *            输出文件路径
     * @throws Exception
     */
    public static void writeFile(InputStream is, String outputPath) throws Exception {
        createDir(outputPath);
        InputStream bis = new BufferedInputStream(is);
        OutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath));
        byte[] bs = new byte[1024 * 10];
        int len ;
        while ((len = bis.read(bs)) != -1) {
            bos.write(bs, 0, len);
        }
        bos.flush();
        bis.close();
        bos.close();
    }

    /**
     * 写文件
     * 
     * @param outputPath
     *            输出文件路径
     * @param inPath
     *            输入文件路径
     * @throws IOException
     */
    public static void writeFile(String inPath, String outputPath, boolean isApend)
            throws Exception {
        if (new File(inPath).exists()) {
            FileInputStream fis = new FileInputStream(inPath);
            writeFile(fis, outputPath, isApend);
        } else {
            throw new Exception("文件copy失败，由于源文件不存在!");
        }
    }

    /**
     * 将字符串写到文件内
     * 
     * @param outputPath
     *            输出文件路径
     * @param msg
     *            字符串
     * @param isApend
     *            是否追加
     * @throws IOException
     */
    public static void writeContent(String msg, String outputPath, boolean isApend)
            throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath, isApend));
        bw.write(msg);
        bw.flush();
        bw.close();
    }

    /**
     * 删除文件夹下的所有内容,包括本文件夹
     * 
     * @param path
     *            删除文件路径
     * @throws IOException
     */
    public static void delFileOrDerectory(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File subFile : files) {
                    delFileOrDerectory(subFile.getAbsolutePath());
                }
                file.deleteOnExit();
            } else {
                file.deleteOnExit();
            }
        }
    }

    /**
     * 如果欲写入的文件所在目录不存在，需先创建
     * 
     * @param outputPath
     *            输出文件路径
     */
    public static File createDir(String outputPath) {
        File outputFile = new File(outputPath);
        File outputDir = outputFile.getParentFile();
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        return outputFile;
    }

    
    /**
     * 获取文件 md5 
     * @param file
     *            对象文件
     * @return 如果空字符串 表示异常
     */
    public static String getMd5ByFile(File file) {
        try(FileInputStream in = new FileInputStream(file)){
            MappedByteBuffer byteBuffer = in.getChannel()
                    .map(FileChannel.MapMode.READ_ONLY, 0,file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            return bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return StringUtils.EMPTY;
        }
    }

    public static List<String> readFileByLineWithOutBom(String filename){
        List<String> nameList = new ArrayList<>();
        File file = new File(filename);
        try(InputStream in = new FileInputStream(file)){
            BufferedReader reader = new BufferedReader(new UnicodeReader(in,
                    "utf-8"));
                String tmp = reader.readLine();
                while (tmp != null && tmp.trim().length() > 0) {
                  nameList.add(tmp);
                  tmp = reader.readLine();
                }
                reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return nameList;
      }
    
    /**
     * 将文件内容写入文件
     * 
     * @param content
     *            文件内容
     * @param filePath
     *            输出文件路径
     * @throws IOException
     * @throws Exception
     */
    public static void writeFileWithCode(String content, String filePath, String code) throws IOException {
            createDir(filePath);
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(StringUtils.isNotBlank(code)?
                    new OutputStreamWriter(fos,code):new OutputStreamWriter(fos));
            bw.write(content);
            bw.flush();
            bw.close();
            fos.close();
    }
    /** 
     * 复制单个文件 
     *  
     * @param srcFileName 
     *            待复制的文件名 
     * @param destFileName
     *            目标文件名 
     * @param overlay 
     *            如果目标文件存在，是否覆盖 
     * @return 如果复制成功返回true，否则返回false 
     */  
    public static boolean copyFile(String srcFileName, String destFileName,
             boolean overlay) throws Exception {
        File srcFile = new File(srcFileName);
  
        // 判断源文件是否存在  
        if (!srcFile.exists()) {
            throw new Exception("源文件：" + srcFileName + "不存在！");
        } else if (!srcFile.isFile()) {
            throw new Exception("复制文件失败，源文件：" + srcFileName + "不是一个文件！");
        }  
  
        // 判断目标文件是否存在  
        File destFile = new File(destFileName);
        if (destFile.exists()) {  
            // 如果目标文件存在并允许覆盖  
            if (overlay) {  
                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件  
                new File(destFileName).delete();
            }  
        } else {  
            // 如果目标文件所在目录不存在，则创建目录  
            if (!destFile.getParentFile().exists()) {  
                // 目标文件所在目录不存在  
                if (!destFile.getParentFile().mkdirs()) {  
                    // 复制文件失败：创建目标文件所在目录失败  
                    return false;  
                }  
            }  
        }  
  
        // 复制文件  
        int byteread = 0; // 读取的字节数  
        try (InputStream in = new FileInputStream(srcFile)){
            OutputStream out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];  
  
            while ((byteread = in.read(buffer)) != -1) {  
                out.write(buffer, 0, byteread);  
            }  
            out.close();
            return true;  
        } catch (FileNotFoundException e) {
            return false;  
        } catch (IOException e) {
            return false;  
        } 
    }  
}
