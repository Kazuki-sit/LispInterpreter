package lisp.eval;

/**
 * 小数値
 * 
 * @author syudent
 *
 */
public class Float implements SExpression {
	private java.lang.Float value;

	public java.lang.Float getValue() {
		return value;
	}

	private Float(java.lang.Float value) {
		this.value = value;
	}

	/**
	 * 小数値のインスタンスを得る。
	 * 
	 * 
	 * 
	 */
	public static Float valueOf(float value) {
		return new Float(value);
	}

	@Override
	public String toString() {
		return this.value.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof Float) == false) {
			return false;
		}
		return this.value.equals(((Float) obj).getValue());
	}
}
