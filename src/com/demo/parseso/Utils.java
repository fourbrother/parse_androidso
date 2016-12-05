package com.demo.parseso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public final class Utils {
	
	/**
	 * 对其算法
	 * 返回一个addr是align的整数倍的值
	 * @param addr：需要对其的地址
	 * @param align：对其的字节数
	 * @return
	 */
	public static int align(int addr, int align){
		if(align > addr){
			return addr;
		}
		int offset = addr % align;
		return addr + (align-offset);
	}
	
	/**
	 * 输出Hex值，但是需要注意的是，反序输出，只是为了看得清楚点
	 * @param bytes
	 * @return
	 */
	public static String bytes2HexString(byte[] bytes){
		StringBuilder result = new StringBuilder();
		for(int i=bytes.length-1;i>=0;i--){
			String hex = Integer.toHexString(bytes[i]);
			if(hex.length() < 2){
				result.append("0"+hex);
			}else{
				result.append(hex);
			}
			result.append(" ");
		}
		return result.toString();
		
	}
	
	public static boolean saveFile(String fileName, byte[] arys){
		File file = new File(fileName);
		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream(file);
			fos.write(arys);
			fos.flush();
			return true;
		}catch(Exception e){
			System.out.println("save file error:"+e.toString());
		}finally{
			if(fos != null){
				try{
					fos.close();
				}catch(Exception e){
					System.out.println("close file error:"+e.toString());
				}
			}
		}
		return false;
	}
	
	public static byte[] readFile(String fileName){
		File file = new File(fileName);
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try{
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream();
			byte[] temp = new byte[1024];     
	        int size = 0;     
	        while ((size = fis.read(temp)) != -1) {     
	        	bos.write(temp, 0, size);     
	        }
	        return bos.toByteArray();
		}catch(Exception e){
			System.out.println("read file error:"+e.toString());
		}finally{
			if(fis != null){
				try{
					fis.close();
				}catch(Exception e){
					System.out.println("close file error:"+e.toString());
				}
			}
			if(bos != null){
				try{
					bos.close();
				}catch(Exception e){
					System.out.println("close file error:"+e.toString());
				}
			}
		}
		return null;
	}
	
	public static byte[] copyBytes(byte[] res, int start, int count){
		if(res == null){
			return null;
		}
		byte[] result = new byte[count];
		for(int i=0;i<count;i++){
			result[i] = res[start+i];
		}
		return result;
	}
	
	public static int byte2Int(byte[] res) {   
		int targets = (res[0] & 0xff) 
				| ((res[1] << 8) & 0xff00)
				| ((res[2] << 24) >>> 8) 
				| (res[3] << 24);   
		return targets;   
	}   
	
	public static long byte2Long(byte[] b) {
        long s = 0;
        long s0 = b[0] & 0xff;// 最低位
        long s1 = b[1] & 0xff;
        long s2 = b[2] & 0xff;
        long s3 = b[3] & 0xff;
        long s4 = b[4] & 0xff;// 最低位
        long s5 = b[5] & 0xff;
        long s6 = b[6] & 0xff;
        long s7 = b[7] & 0xff;
        // s0不变
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 8 * 4;
        s5 <<= 8 * 5;
        s6 <<= 8 * 6;
        s7 <<= 8 * 7;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;
    }
	
	/** 
     * 注释：字节数组到short的转换！ 
     * 
     * @param b 
     * @return 
     */ 
    public static short byte2Short(byte[] b) { 
        short s = 0; 
        short s0 = (short) (b[0] & 0xff);// 最低位 
        short s1 = (short) (b[1] & 0xff); 
        s1 <<= 8; 
        s = (short) (s0 | s1); 
        return s; 
    }
    
    /**
     * long转化成byte
     * @param number
     * @return
     */
    public static byte[] long2ByteAry(long number) { 
    	long temp = number; 
    	byte[] b = new byte[8]; 
    	for (int i = 0; i < b.length; i++) { 
    		b[i] = new Long(temp & 0xff).byteValue();// 将最低位保存在最低位 
    		temp = temp >> 8; // 向右移8位 
    	} 
    	return b; 
    } 
    
    /**
     * int转化成byte
     * @param number
     * @return
     */
    public static byte[] int2Byte(int number) { 
        int temp = number; 
        byte[] b = new byte[4]; 
        for (int i = 0; i < b.length; i++) { 
            b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位 
            temp = temp >> 8; // 向右移8位 
        } 
        return b;
    } 
    
    /**
     * short转化成byte
     * @param number
     * @return
     */
    public static byte[] short2Byte(short number) { 
        int temp = number; 
        byte[] b = new byte[2]; 
        for (int i = 0; i < b.length; i++) { 
            b[i] = new Integer(temp & 0xff).byteValue();//将最低位保存在最低位 
            temp = temp >> 8; // 向右移8位 
        } 
        return b;
    } 
    
    /**
     * 替换rep_index位置的byte[]
     * @param src
     * @param rep_index
     * @param copyByte
     * @return
     */
    public static byte[] replaceByteAry(byte[] src, int rep_index, byte[] copyByte){
    	for(int i=rep_index;i<rep_index+copyByte.length;i++){
    		src[i] = copyByte[i-rep_index];
    	}
    	return src;
    }
    
    /**
     * 高地位互换
     */
    public static byte[] reverseBytes(byte[] bytes){
    	if(bytes == null || (bytes.length % 2) != 0){
    		return bytes;
    	}
    	int i = 0;
    	int offset = bytes.length/2;
    	while(i < (bytes.length/2)){
    		byte tmp = bytes[i];
    		bytes[i] = bytes[offset+i];
    		bytes[offset+i] = tmp;
    		i++;
    	}
    	return bytes;
    }

}
