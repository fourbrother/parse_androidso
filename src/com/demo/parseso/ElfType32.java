package com.demo.parseso;

import java.util.ArrayList;

public class ElfType32 {
	
	public elf32_rel rel;
	public elf32_rela rela;
	public ArrayList<Elf32_Sym> symList = new ArrayList<Elf32_Sym>();
	public elf32_hdr hdr;//elf头部信息
	public ArrayList<elf32_phdr> phdrList = new ArrayList<elf32_phdr>();//可能会有多个程序头
	public ArrayList<elf32_shdr> shdrList = new ArrayList<elf32_shdr>();//可能会有多个段头
	public ArrayList<elf32_strtb> strtbList = new ArrayList<elf32_strtb>();//可能会有多个字符串值
	
	public ElfType32() {
		rel = new elf32_rel();
		rela = new elf32_rela();
		hdr = new elf32_hdr();
	}
	
	/**
	 *  typedef struct elf32_rel {
		  Elf32_Addr	r_offset;
		  Elf32_Word	r_info;
		} Elf32_Rel;
	 *
	 */
	public class elf32_rel {
		public byte[] r_offset = new byte[4];
		public byte[] r_info = new byte[4];
		
		@Override
		public String toString(){
			return "r_offset:"+Utils.bytes2HexString(r_offset)+";r_info:"+Utils.bytes2HexString(r_info);
		}
	}
	
	/**
	 *  typedef struct elf32_rela{
		  Elf32_Addr	r_offset;
		  Elf32_Word	r_info;
		  Elf32_Sword	r_addend;
		} Elf32_Rela;
	 */
	public class elf32_rela{
		public byte[] r_offset = new byte[4];
		public byte[] r_info = new byte[4];
		public byte[] r_addend = new byte[4];
		
		@Override
		public String toString(){
			return "r_offset:"+Utils.bytes2HexString(r_offset)+";r_info:"+Utils.bytes2HexString(r_info)+";r_addend:"+Utils.bytes2HexString(r_info);
		}
	}
	
	/**
	 * typedef struct elf32_sym{
		  Elf32_Word	st_name;
		  Elf32_Addr	st_value;
		  Elf32_Word	st_size;
		  unsigned char	st_info;
		  unsigned char	st_other;
		  Elf32_Half	st_shndx;
		} Elf32_Sym;
	 */
	public static class Elf32_Sym{
		public byte[] st_name = new byte[4];
		public byte[] st_value = new byte[4];
		public byte[] st_size = new byte[4];
		public byte st_info;
		public byte st_other;
		public byte[] st_shndx = new byte[2];
		
		@Override
		public String toString(){
			return "st_name:"+Utils.bytes2HexString(st_name)
					+"\nst_value:"+Utils.bytes2HexString(st_value)
					+"\nst_size:"+Utils.bytes2HexString(st_size)
					+"\nst_info:"+(st_info/16)
					+"\nst_other:"+(((short)st_other) & 0xF)
					+"\nst_shndx:"+Utils.bytes2HexString(st_shndx);
		}
	}
	
	public void printSymList(){
		for(int i=0;i<symList.size();i++){
			System.out.println();
			System.out.println("The "+(i+1)+" Symbol Table:");
			System.out.println(symList.get(i).toString());
		}
	}
	
	//Bind字段==》st_info
	public static final int STB_LOCAL = 0;
	public static final int STB_GLOBAL = 1;
	public static final int STB_WEAK = 2;
	//Type字段==》st_other
	public static final int STT_NOTYPE = 0;
	public static final int STT_OBJECT = 1;
	public static final int STT_FUNC = 2;
	public static final int STT_SECTION = 3;
	public static final int STT_FILE = 4;
	/**
	 * 这里需要注意的是还需要做一次转化
	 *  #define ELF_ST_BIND(x)	((x) >> 4)
		#define ELF_ST_TYPE(x)	(((unsigned int) x) & 0xf)
	 */
	
	/**
	 * typedef struct elf32_hdr{
		  unsigned char	e_ident[EI_NIDENT];
		  Elf32_Half	e_type;
		  Elf32_Half	e_machine;
		  Elf32_Word	e_version;
		  Elf32_Addr	e_entry;  // Entry point
		  Elf32_Off	e_phoff;
		  Elf32_Off	e_shoff;
		  Elf32_Word	e_flags;
		  Elf32_Half	e_ehsize;
		  Elf32_Half	e_phentsize;
		  Elf32_Half	e_phnum;
		  Elf32_Half	e_shentsize;
		  Elf32_Half	e_shnum;
		  Elf32_Half	e_shstrndx;
		} Elf32_Ehdr;
	 */
	public class elf32_hdr{
		public byte[] e_ident = new byte[16];
		public byte[] e_type = new byte[2];
		public byte[] e_machine = new byte[2];
		public byte[] e_version = new byte[4];
		public byte[] e_entry = new byte[4];
		public byte[] e_phoff = new byte[4];
		public byte[] e_shoff = new byte[4];
		public byte[] e_flags = new byte[4];
		public byte[] e_ehsize = new byte[2];
		public byte[] e_phentsize = new byte[2];
		public byte[] e_phnum = new byte[2];
		public byte[] e_shentsize = new byte[2];
		public byte[] e_shnum = new byte[2];
		public byte[] e_shstrndx = new byte[2];
		
		@Override
		public String toString(){
			return  "magic:"+ Utils.bytes2HexString(e_ident) 
					+"\ne_type:"+Utils.bytes2HexString(e_type)
					+"\ne_machine:"+Utils.bytes2HexString(e_machine)
					+"\ne_version:"+Utils.bytes2HexString(e_version)
					+"\ne_entry:"+Utils.bytes2HexString(e_entry)
					+"\ne_phoff:"+Utils.bytes2HexString(e_phoff)
					+"\ne_shoff:"+Utils.bytes2HexString(e_shoff)
					+"\ne_flags:"+Utils.bytes2HexString(e_flags)
					+"\ne_ehsize:"+Utils.bytes2HexString(e_ehsize)
					+"\ne_phentsize:"+Utils.bytes2HexString(e_phentsize)
					+"\ne_phnum:"+Utils.bytes2HexString(e_phnum)
					+"\ne_shentsize:"+Utils.bytes2HexString(e_shentsize)
					+"\ne_shnum:"+Utils.bytes2HexString(e_shnum)
					+"\ne_shstrndx:"+Utils.bytes2HexString(e_shstrndx);
		}
	}
	
	/**
	 * typedef struct elf32_phdr{
		  Elf32_Word	p_type;
		  Elf32_Off	p_offset;
		  Elf32_Addr	p_vaddr;
		  Elf32_Addr	p_paddr;
		  Elf32_Word	p_filesz;
		  Elf32_Word	p_memsz;
		  Elf32_Word	p_flags;
		  Elf32_Word	p_align;
		} Elf32_Phdr;
	 */
	public static class elf32_phdr{
		public byte[] p_type = new byte[4];
		public byte[] p_offset = new byte[4];
		public byte[] p_vaddr = new byte[4];
		public byte[] p_paddr = new byte[4];
		public byte[] p_filesz = new byte[4];
		public byte[] p_memsz = new byte[4];
		public byte[] p_flags = new byte[4];
		public byte[] p_align = new byte[4];
		
		@Override
		public String toString(){
			return "p_type:"+ Utils.bytes2HexString(p_type)
					+"\np_offset:"+Utils.bytes2HexString(p_offset)
					+"\np_vaddr:"+Utils.bytes2HexString(p_vaddr)
					+"\np_paddr:"+Utils.bytes2HexString(p_paddr)
					+"\np_filesz:"+Utils.bytes2HexString(p_filesz)
					+"\np_memsz:"+Utils.bytes2HexString(p_memsz)
					+"\np_flags:"+Utils.bytes2HexString(p_flags)
					+"\np_align:"+Utils.bytes2HexString(p_align);
		}
	}
	
	public void printPhdrList(){
		for(int i=0;i<phdrList.size();i++){
			System.out.println();
			System.out.println("The "+(i+1)+" Program Header:");
			System.out.println(phdrList.get(i).toString());
		}
	}
	
	/**
	 * typedef struct elf32_shdr {
		  Elf32_Word	sh_name;
		  Elf32_Word	sh_type;
		  Elf32_Word	sh_flags;
		  Elf32_Addr	sh_addr;
		  Elf32_Off	sh_offset;
		  Elf32_Word	sh_size;
		  Elf32_Word	sh_link;
		  Elf32_Word	sh_info;
		  Elf32_Word	sh_addralign;
		  Elf32_Word	sh_entsize;
		} Elf32_Shdr;
	 */
	public static class elf32_shdr{
		public byte[] sh_name = new byte[4];
		public byte[] sh_type = new byte[4];
		public byte[] sh_flags = new byte[4];
		public byte[] sh_addr = new byte[4];
		public byte[] sh_offset = new byte[4];
		public byte[] sh_size = new byte[4];
		public byte[] sh_link = new byte[4];
		public byte[] sh_info = new byte[4];
		public byte[] sh_addralign = new byte[4];
		public byte[] sh_entsize = new byte[4];
		
		@Override
		public String toString(){
			return "sh_name:"+Utils.bytes2HexString(sh_name)/*Utils.byte2Int(sh_name)*/
					+"\nsh_type:"+Utils.bytes2HexString(sh_type)
					+"\nsh_flags:"+Utils.bytes2HexString(sh_flags)
					+"\nsh_add:"+Utils.bytes2HexString(sh_addr)
					+"\nsh_offset:"+Utils.bytes2HexString(sh_offset)
					+"\nsh_size:"+Utils.bytes2HexString(sh_size)
					+"\nsh_link:"+Utils.bytes2HexString(sh_link)
					+"\nsh_info:"+Utils.bytes2HexString(sh_info)
					+"\nsh_addralign:"+Utils.bytes2HexString(sh_addralign)
					+"\nsh_entsize:"+ Utils.bytes2HexString(sh_entsize);
		}
	}
	
	/****************sh_type********************/
	public static final int SHT_NULL = 0;
	public static final int SHT_PROGBITS = 1;
	public static final int SHT_SYMTAB = 2;
	public static final int SHT_STRTAB = 3;
	public static final int SHT_RELA = 4;
	public static final int SHT_HASH = 5;
	public static final int SHT_DYNAMIC = 6;
	public static final int SHT_NOTE = 7;
	public static final int SHT_NOBITS = 8;
	public static final int SHT_REL = 9;
	public static final int SHT_SHLIB = 10;
	public static final int SHT_DYNSYM = 11;
	public static final int SHT_NUM = 12;
	public static final int SHT_LOPROC = 0x70000000;
	public static final int SHT_HIPROC = 0x7fffffff;
	public static final int SHT_LOUSER = 0x80000000;
	public static final int SHT_HIUSER = 0xffffffff;
	public static final int SHT_MIPS_LIST = 0x70000000;
	public static final int SHT_MIPS_CONFLICT = 0x70000002;
	public static final int SHT_MIPS_GPTAB = 0x70000003;
	public static final int SHT_MIPS_UCODE = 0x70000004;
	
	/*****************sh_flag***********************/
	public static final int SHF_WRITE = 0x1;
	public static final int SHF_ALLOC = 0x2;
	public static final int SHF_EXECINSTR = 0x4;
	public static final int SHF_MASKPROC = 0xf0000000;
	public static final int SHF_MIPS_GPREL = 0x10000000;
	
	public void printShdrList(){
		for(int i=0;i<shdrList.size();i++){
			System.out.println();
			System.out.println("The "+(i+1)+" Section Header:");
			System.out.println(shdrList.get(i));
		}
	}
	
	
	public static class elf32_strtb{
		public byte[] str_name;
		public int len;
		
		@Override
		public String toString(){
			return "str_name:"+str_name
					+"len:"+len;
		}
	}
}
