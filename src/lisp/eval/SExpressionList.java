package lisp.eval;

import java.util.ArrayList;

/**
 * list (list object)
 *
 * @author tetsuya
 *
 */
public class SExpressionList implements SExpression {

	ArrayList<SExpression> seclist;

	public SExpression[] getValue() {

		return this.seclist.toArray(new SExpression[seclist.size()]);
	}

	private SExpressionList(SExpression sec) {
		seclist = new ArrayList<SExpression>();
		seclist.add(sec);
	}

	public SExpression getLise_ref(int num) {

		return this.seclist.get(this.seclist.size() - num);
	}

	public Integer getLength() {

		return this.seclist.size();
	}

	public SExpressionList addList(SExpression Sec) {
		seclist.add(Sec);

		return this;
	}

	/**
	 * 小数値のインスタンスを得る。
	 *
	 *
	 *
	 */
	public static SExpressionList valueOf(SExpression value) {
		return new SExpressionList(value);
	}

	@Override
	public String toString() {
		String s = "";

		for (SExpression Sec : seclist) {
			s += Sec.toString();
			s += " ";
		}
		return s;
	}

}
