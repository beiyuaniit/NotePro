public class Parent
{
	public static void main(String []args)
	{
		A a=new B();
		a.speak();
		B b=(B)a;
		b.wolk();
	}
}
class A
{
	public void speak()
	{
		
	}
}
class B extends A
{
	public void wolk()
	{
		
	}
	
}