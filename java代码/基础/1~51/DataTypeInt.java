public class DataTypeInt
{
	public static void main(String []args)
	{
		int a=10;
		int b=010;
		int c=0x10;
		long x=231;
		long y=2876487326478732L;
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(a+b+c);
		
		long z=1L;
		int d=(int)z; 
		
		int e=23;
		byte o=(byte)e;
	}
}