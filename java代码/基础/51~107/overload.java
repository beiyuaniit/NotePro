public class overload
{
	public static void main(String []args)
	{
		System.out.println(sum(4));
	}
	public static void sum()
	{
		
	}
	public static int  sum(int a)
	{
		if(a<2)
		return 1;
		return a+sum(a-1);
	}
}