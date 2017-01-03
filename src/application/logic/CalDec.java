package application.logic;

import java.math.BigDecimal;

public class CalDec {

	public static BigDecimal addition(BigDecimal a, BigDecimal b) {
		
		BigDecimal c = BigDecimal.ZERO;
		
		if(a == null)
			a = BigDecimal.ZERO;
		
		if(b == null)
			b = BigDecimal.ZERO;
		
		c = a.add(b);
		
		return c;
	}
	
}
