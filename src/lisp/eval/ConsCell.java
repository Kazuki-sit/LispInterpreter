package lisp.eval;

import lisp.eval.subroutine.Sub;
import lisp.exception.LispException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.BooleanSupplier;

/**
 * Cons cell (ドット対)
 *
 * @author tetsuya
 *
 */
public class ConsCell implements SExpression {
	private SExpression car;
	private SExpression cdr;

	public SExpression getCar() {
		return this.car;
	}

	public SExpression getCdr() {
		return this.cdr;
	}

	private ConsCell(SExpression car, SExpression cdr) {
		this.car = car;
		this.cdr = cdr;
	}

	/**
	 * Cons Cell（ドット対）を構築する。
	 *
	 * @param car car部
	 * @param cdr cdr部
	 * @return 指定されたcar部とcdr部で構成されるCons Cell（ドット対）
	 */
	public static ConsCell getInstance(SExpression car, SExpression cdr) {
		return new ConsCell(car, cdr);
	}

	/**
	 * <p>ドット対のリストをArrayListに変換する</p>
	 * <p>空リストのみの場合は要素数0のArrayListが返される</p>
	 *
	 * @param sexp ドット対（リスト形式）
	 * @return ArrayList
	 */
	static public ArrayList<SExpression> toArrayList(SExpression sexp) throws LispException {
		// 格納用のArrayList
		ArrayList<SExpression> sexpList = new ArrayList<>();

		// cdr部が空リストになるまでcar部を取り出す
		while (!(sexp instanceof EmptyList)) {

			// ドット対であれば、car部をリストに追加する
			if (sexp instanceof ConsCell) {
				sexpList.add(((ConsCell) sexp).getCar());
				sexp = ((ConsCell) sexp).getCdr();
				continue;
			}

			// それ以外はエラーを返す
			throw new LispException("渡されたS式がリストではありません");
		}

		return sexpList;
	}

	/**
	 * <p>渡された連想リストをHashMapに変換して返す</p>
	 * <p>リスト末尾の空リストは挿入されない</p>
	 *
	 * @param sexp 連想リスト
	 * @return HashMap
	 * @throws LispException
	 */
	static public HashMap<SExpression, SExpression> toHashMap(SExpression sexp) throws LispException {
		// 格納用のHashMap
		HashMap<SExpression, SExpression> alist = new HashMap<>();
		ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

		if (sexpList.size() == 0) {
			throw new LispException("渡されたリストが連想リストではありません");
		}

		for (SExpression se : sexpList) {

			// 取り出したcar部がドット対であれば、HashMapに追加する
			if (se instanceof ConsCell) {
				SExpression car = ((ConsCell) se).getCar();
				SExpression cdr = ((ConsCell) se).getCdr();

				// すでに同じキーが登録されていたらスキップ
				if (!alist.containsKey(car)) {
					alist.put(car, cdr);
				}

				continue;
			}

			// それ以外はエラーを返す
			throw new LispException("渡されたリストが連想リストではありません");
		}

		return alist;
	}

	/**
	 * 渡されたArrayListをLisp処理系のリストに変換して返す
	 *
	 * @param sexpList 要素がS式かつ末尾が空リストのArrayList
	 * @return 結合されたリスト
	 * @throws LispException
	 */
	public static SExpression toLispList(ArrayList<SExpression> sexpList) {
		Collections.reverse(sexpList);
		SExpression sexp = EmptyList.getInstance();

		for (SExpression se : sexpList) {
			sexp = ConsCell.getInstance(se, sexp);
		}

		return sexp;
	}

	/**
	 * <p>渡されたS式がリストかどうか判定する</p>
	 *
	 * @param sexp S式
	 * @return 真偽値
	 */
	public static boolean isList(SExpression sexp) {

		// cdr部がドット対でなくなるまでcdr部を取り出す
		while(true) {
			if (sexp instanceof ConsCell) {
				sexp = ((ConsCell) sexp).getCdr();
				continue;
			}

			if (sexp instanceof EmptyList) {
				break;
			}

			// ドット対のcdr部が空リストではなければ、リストでないのでfalseを返す
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		String cell = this.car + " . " + this.cdr;

		// 省略できればする
		if (cell.matches(".*\\. \\(.*$")) {

			// ". (" があるなら、その部分を消去する
			int index = cell.indexOf(". (");
			if (this.cdr instanceof EmptyList) {
				cell = cell.substring(0, index - 1) + cell.substring(index + 3);
			} else {
				cell = cell.substring(0, index) + cell.substring(index + 3);
			}

			// 対応する ")" を消す
			cell = cell.substring(0, cell.length() - 1);
		}

		return "(" + cell + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConsCell) {
			return this.car.equals(((ConsCell) obj).getCar()) && this.cdr.equals(((ConsCell) obj).getCdr());
		}
		return false;
	}
}
