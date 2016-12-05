package com.demo.parseso;

public class ElfType64 {
	
	public elf64_rel rel;
	public elf64_rela rela;
	public elf64_sym sym;
	public elf64_hdr hdr;
	public elf64_phdr phdr;
	public elf64_shdr shdr;
	
	public ElfType64() {
		rel = new elf64_rel();
		rela = new elf64_rela();
		sym = new elf64_sym();
		hdr = new elf64_hdr();
		phdr = new elf64_phdr();
		shdr = new elf64_shdr();
	}
	
	
	/**
	 *  typedef struct elf64_rel {
		  Elf64_Addr r_offset;	// Location at which to apply the action 
		  Elf64_Xword r_info;	// index and type of relocation
		} Elf64_Rel;
	 */
	public class elf64_rel{
		public long r_offset;
		public long r_info;
	}
	
	/**
	 *  typedef struct elf64_rela {
		  Elf64_Addr r_offset;	// Location at which to apply the action
		  Elf64_Xword r_info;	// index and type of relocation
		  Elf64_Sxword r_addend;	// Constant addend used to compute value
		} Elf64_Rela;
	 */
	public class elf64_rela{
		public long r_offset;
		public long r_info;
		public long r_addend;
	}
	
	/**
	 *  typedef struct elf64_sym {
		  Elf64_Word st_name;	// Symbol name, index in string tbl
		  unsigned char	st_info;	// Type and binding attributes
		  unsigned char	st_other;	// No defined meaning, 0
		  Elf64_Half st_shndx;	// Associated section index
		  Elf64_Addr st_value;	// Value of the symbol
		  Elf64_Xword st_size;	// Associated symbol size
		} Elf64_Sym;
	 */
	public class elf64_sym{
		public int st_name;
		public char st_info;
		public char st_other;
		public short st_shndx;
		public long st_value;
		public long st_st_size;
	}
	
	/**
	 * typedef struct elf64_hdr {
		  unsigned char	e_ident[16];	// ELF "magic number"
		  Elf64_Half e_type;
		  Elf64_Half e_machine;
		  Elf64_Word e_version;
		  Elf64_Addr e_entry;	// Entry point virtual address 
		  Elf64_Off e_phoff;	// Program header table file offset 
		  Elf64_Off e_shoff;	// Section header table file offset 
		  Elf64_Word e_flags;
		  Elf64_Half e_ehsize;
		  Elf64_Half e_phentsize;
		  Elf64_Half e_phnum;
		  Elf64_Half e_shentsize;
		  Elf64_Half e_shnum;
		  Elf64_Half e_shstrndx;
		} Elf64_Ehdr;
	 */
	public class elf64_hdr{
		public byte[] e_ident = new byte[16];
		public short e_type;
		public short e_machine;
		public int e_version;
		public long e_entry;
		public long e_phoff;
		public long e_shoff;
		public int e_flags;
		public short e_ehsize;
		public short e_phentsize;
		public short e_phnum;
		public short e_shentsize;
		public short e_shnum;
		public short e_shstrndx;
	}
	
	/**
	 *  typedef struct elf64_phdr {
		  Elf64_Word p_type;
		  Elf64_Word p_flags;
		  Elf64_Off p_offset;	// Segment file offset 
		  Elf64_Addr p_vaddr;	// Segment virtual address 
		  Elf64_Addr p_paddr;	// Segment physical address
		  Elf64_Xword p_filesz;	// Segment size in file 
		  Elf64_Xword p_memsz;	// Segment size in memory
		  Elf64_Xword p_align;	// Segment alignment, file & memory
		} Elf64_Phdr;
	 */
	public class elf64_phdr{
		public int p_type;
		public int p_flags;
		public long p_offset;
		public long p_vaddr;
		public long p_paddr;
		public long p_filesz;
		public long p_memsz;
		public long p_align;
	}
	
	
	/**
	 * typedef struct elf64_shdr {
		  Elf64_Word sh_name;	// Section name, index in string tbl 
		  Elf64_Word sh_type;	// Type of section 
		  Elf64_Xword sh_flags;	// Miscellaneous section attributes 
		  Elf64_Addr sh_addr;	// Section virtual addr at execution 
		  Elf64_Off sh_offset;	// Section file offset 
		  Elf64_Xword sh_size;	// Size of section in bytes 
		  Elf64_Word sh_link;	// Index of another section 
		  Elf64_Word sh_info;	// Additional section information 
		  Elf64_Xword sh_addralign;	// Section alignment 
		  Elf64_Xword sh_entsize;	// Entry size if section holds table 
		} Elf64_Shdr;
	 */
	public class elf64_shdr{
		public int sh_name;
		public int sh_type;
		public long sh_flags;
		public int sh_addr;
		public int sh_offset;
		public long sh_size;
		public int sh_link;
		public int sh_info;
		public long sh_addralign;
		public long sh_entsize;
	}
	

}
