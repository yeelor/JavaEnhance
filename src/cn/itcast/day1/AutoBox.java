package cn.itcast.day1;

public class AutoBox {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer iObj = 3;
		System.out.println(iObj + 12);
		
		
		
		String s1 = new String("abc");
		String s2 = new String("abc");	
		System.out.println("s1 == s2:"+(s1 == s2));
		
		
		s1 = "abc";
		s2 = "abc";
		System.out.println("s1 == s2:"+(s1 == s2));
		
		Integer i1 = 137;
		Integer i2 = 137;
		//-128~+127 范围内的小整数会用享元模式flyweight
		System.out.println("i1 == i2:"+(i1 == i2));
		
		 i1 = 127;
		 i2 = 127;
		//-128~+127 范围内的小整数会用享元模式flyweight
		System.out.println("i1 == i2:"+(i1 == i2));
		
		Integer i3 = Integer.valueOf(3);
		Integer i4 = Integer.valueOf(3);
		System.out.println("i3 == i4:"+(i3 == i4));
		
	}

}
