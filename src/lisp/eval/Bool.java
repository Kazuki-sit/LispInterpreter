package lisp.eval;

/**
 * 真理値
 * 
 * @author tetsuya
 *
 */
public class Bool implements SExpression {
	private static final Bool TRUE = new Bool(true);
	private static final Bool FALSE = new Bool(false);

	private Boolean value;

	public Boolean getValue() {
		return this.value;
	}

	private Bool(Boolean value) {
		this.value = value;
	}

	/**
	 * 真理値のインスタンスを得る。
	 * 
	 * @param value true or false
	 * @return TRUE or FALSE
	 */
	public static Bool valueOf(boolean value) {
		if (value) {
			return Bool.TRUE;
		}
		return Bool.FALSE;
	}

	@Override
	public String toString() {
		if (this.value) {
			return "#t";
		}
		return "#f";
	}
}
