package com.demo.parseso;

/**
 * 添加一个Section：
 * 1、修改elf头部中的section的总数信息
 * 2、在section header中添加一个section header信息
 * 3、修改strtab的长度，放置section header的名称
 * @author i
 *
 */
public class AddSection {
	
	private final static String newSectionName = ".jiangwei";
	private final static int newSectionSize = 1000;
	private final static int newSectionNameLen = 0x10;//new section name的长度不能超过0x10
	
	private final static int sectionSize = 40;//一个Section的大小
	private final static int stringSectionSizeIndex = 20;//String section中的size字段的index
	private final static int programFileSizeIndex = 16;//program header中的file size的index
	private final static int elfHeaderSize = 0x34;//elf header的大小
	private final static int programHeaderSize = 0x20;//Program Header的大小
	private final static int elfHeaderSectionCountIndex = 48;//elf header中的section总数
	
	public static int sectionHeaderOffset;//section header的偏移值
	public static short stringSectionInSectionTableIndex;//string section在section list中的index
	public static int stringSectionOffset;//string section中的偏移值
	public static int firstLoadInPHIndex;//第一个Load类型的Program Header的在Program Header List中的index 从0开始
	public static int lastLoadInPHIndex;
	public static int addSectionStartAddr = 0;//添加Section段的开始地址
	
	/**
	 * 修改elf头部总的section的总数信息
	 */
	public static byte[] changeElfHeaderSectionCount(byte[] src){
		byte[] count = Utils.copyBytes(src, elfHeaderSectionCountIndex, 2);
		short counts = Utils.byte2Short(count);
		counts++;
		count = Utils.short2Byte(counts);
		src = Utils.replaceByteAry(src, elfHeaderSectionCountIndex, count);
		return src;
	}
	
	/**
	 * 添加section header信息
	 * 原理：
	 * 找到String Section的位置，然后获取他偏移值
	 * 将section添加到文件末尾
	 */
	public static byte[] addSectionHeader(byte[] src){
		/**
		 *  public byte[] sh_name = new byte[4];
			public byte[] sh_type = new byte[4];
			public byte[] sh_flags = new byte[4];
			public byte[] sh_addr = new byte[4];
			public byte[] sh_offset = new byte[4];
			public byte[] sh_size = new byte[4];
			public byte[] sh_link = new byte[4];
			public byte[] sh_info = new byte[4];
			public byte[] sh_addralign = new byte[4];
			public byte[] sh_entsize = new byte[4];
		 */
		byte[] newHeader = new byte[sectionSize];
		
		//构建一个New Section Header
		newHeader = Utils.replaceByteAry(newHeader, 0, Utils.int2Byte(addSectionStartAddr - stringSectionOffset));
		newHeader = Utils.replaceByteAry(newHeader, 4, Utils.int2Byte(ElfType32.SHT_PROGBITS));//type=PROGBITS
		newHeader = Utils.replaceByteAry(newHeader, 8, Utils.int2Byte(ElfType32.SHF_ALLOC));
		newHeader = Utils.replaceByteAry(newHeader, 12, Utils.int2Byte(0x5010));
		newHeader = Utils.replaceByteAry(newHeader, 16, Utils.int2Byte(0x5010));
		newHeader = Utils.replaceByteAry(newHeader, 20, Utils.int2Byte(newSectionSize));
		newHeader = Utils.replaceByteAry(newHeader, 24, Utils.int2Byte(0));
		newHeader = Utils.replaceByteAry(newHeader, 28, Utils.int2Byte(0));
		newHeader = Utils.replaceByteAry(newHeader, 32, Utils.int2Byte(4));
		newHeader = Utils.replaceByteAry(newHeader, 36, Utils.int2Byte(0));
		
		//在末尾增加Section
		byte[] newSrc = new byte[src.length + newHeader.length];
		newSrc = Utils.replaceByteAry(newSrc, 0, src);
		newSrc = Utils.replaceByteAry(newSrc, src.length, newHeader);
		
		return newSrc;
	}
	
	/**
	 * 修改.strtab段的长度
	 */
	public static byte[] changeStrtabLen(byte[] src){
		
		//获取到String的size字段的开始位置
		int size_index = sectionHeaderOffset + (stringSectionInSectionTableIndex)*sectionSize + stringSectionSizeIndex;
		
		//多了一个Section Header + 多了一个Section的name的16个字节
		byte[] newLen_ary = Utils.int2Byte(addSectionStartAddr - stringSectionOffset + newSectionNameLen);
		src = Utils.replaceByteAry(src, size_index, newLen_ary);
		return src;
	}
	
	/**
	 * 在文件末尾添加空白段+增加段名String
	 * @param src
	 * @return
	 */
	public static byte[] addNewSectionForFileEnd(byte[] src){
		byte[] stringByte = newSectionName.getBytes();
		byte[] newSection = new byte[newSectionSize + newSectionNameLen];
		newSection = Utils.replaceByteAry(newSection, 0, stringByte);
		//新建一个byte[]
		byte[] newSrc = new byte[0x5000 + newSection.length];
		newSrc = Utils.replaceByteAry(newSrc, 0, src);//复制之前的文件src
		newSrc = Utils.replaceByteAry(newSrc, addSectionStartAddr, newSection);//复制section
		return newSrc;
	}
	
	/**
	 * 修改Program Header中的信息
	 * 把新增的段内容加入到LOAD Segement中
	 * 就是修改第一个LOAD类型的Segement的filesize和memsize为文件的总长度
	 */
	public static byte[] changeProgramHeaderLoadInfo(byte[] src){
		//寻找到LOAD类型的Segement位置
		int offset = elfHeaderSize + programHeaderSize * firstLoadInPHIndex + programFileSizeIndex;
		//file size字段
		byte[] fileSize = Utils.int2Byte(src.length);
		src = Utils.replaceByteAry(src, offset, fileSize);
		//mem size字段
		offset = offset + 4;
		byte[] memSize = Utils.int2Byte(src.length);
		src = Utils.replaceByteAry(src, offset, memSize);
		//flag字段
		offset = offset + 4;
		byte[] flag = Utils.int2Byte(7);
		src = Utils.replaceByteAry(src, offset, flag);
		return src;
	}
	
}
