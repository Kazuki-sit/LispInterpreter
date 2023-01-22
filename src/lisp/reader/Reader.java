package lisp.reader;

import java.io.BufferedReader;
import java.io.IOException;

import lisp.eval.Bool;
import lisp.eval.ConsCell;
import lisp.eval.EmptyList;
import lisp.eval.Int;
import lisp.eval.Float;
import lisp.eval.SExpression;
import lisp.eval.Symbol;
import lisp.exception.LispException;
import lisp.exception.SyntaxErrorException;

/**
 * 構文解析器
 *
 * @author tetsuya
 *
 */
public class Reader {
	/**
	 * 字句解析器
	 */
	private Lexer lexer;

	/**
	 * 先読みの字句
	 */
	private Token token = null;

	/**
	 * 括弧の入れ子レベル。式を読み終わった時にnestingLevelが0ならば、そこまでの式を評価する。
	 */
	private int nestingLevel = 0;

	/**
	 * コンストラクタ
	 *
	 * @param in 入力
	 */
	public Reader(BufferedReader in) {
		this.lexer = new Lexer(in);
	}

	/**
	 * <pre>
	 * {@literal <S式>} ::= {@literal <整数値>} | {@literal <記号>} | {@literal <真理値>} | '(' ({@literal <S式>} '.' {@literal <S式>})? ')'
	 * </pre>
	 *
	 * @return S式
	 * @throws LispException
	 * @throws IOException
	 */
	SExpression sExpression() throws IOException, LispException {
		// 整数値
		if (this.token.getKind() == Token.Kind.INT) {
			int value = this.token.getIntValue();
			if (this.nestingLevel != 0) { // 式が未完成
				this.token = this.lexer.getNextToken();
			}
			return Int.valueOf(value);
		}
		// 浮動小数点数
		if (this.token.getKind() == Token.Kind.FLOAT) {
			float value = this.token.getFloatValue();
			if (this.nestingLevel != 0) { // 式が未完成
				this.token = this.lexer.getNextToken();
			}
			return Float.valueOf(value);
		}
		// 記号
		if (this.token.getKind() == Token.Kind.SYMBOL) {
			String value = this.token.getSymbolValue();
			if (this.nestingLevel != 0) { // 式が未完成
				this.token = this.lexer.getNextToken();
			}
			return Symbol.getInstance(value);
		}
		// 真理値
		if (this.token.getKind() == Token.Kind.BOOLEAN) {
			boolean value = this.token.getBooleanValue();
			if (this.nestingLevel != 0) { // 式が未完成
				this.token = this.lexer.getNextToken();
			}
			return Bool.valueOf(value);
		}
		// クオート
		if (this.token.getKind() == Token.Kind.QUOTE) {
			this.token = this.lexer.getNextToken();
			return ConsCell.getInstance(Symbol.getInstance("quote"), ConsCell.getInstance(sExpression(), EmptyList.getInstance()));
		}
		// セミコロン
		if (this.token.getKind() == Token.Kind.SEMICOLON) {
			this.lexer.getNextLine();
			this.token = this.lexer.getNextToken();
			return sExpression();
		}
		// '(' ')' or '(' <S式> . <S式> ')'
		if (this.token.getKind() == Token.Kind.LPAREN) {
			SExpression car;
			SExpression cdr;
			this.nestingLevel++;
			this.token = this.lexer.getNextToken();
			// 空リスト
			if (this.token.getKind() == Token.Kind.RPAREN) {
				this.nestingLevel--;
				if (this.nestingLevel != 0) { // 式が未完成
					this.token = this.lexer.getNextToken();
				}
				return EmptyList.getInstance();
			}
			// car
			car = sExpression();

			if (this.token.getKind() == Token.Kind.DOT) {
				this.token = this.lexer.getNextToken();
				// '.'あり cdr
				cdr = sExpression();
			}
			else {
				// '.'なし cdr
				cdr = searchCdr();
			}
			// 閉じかっこがある場合
			if (this.token.getKind() == Token.Kind.RPAREN) {
				this.nestingLevel--;
			}
			// 式が未完成
			if (this.nestingLevel != 0) {
				this.token = this.lexer.getNextToken();
			}
			return ConsCell.getInstance(car, cdr);
		}

		throw new SyntaxErrorException("Invalid expression:" + this.token.getKind());
	}

	public SExpression read() throws IOException, LispException {
		this.nestingLevel = 0;
		this.token = this.lexer.getNextToken();
		return sExpression();
	}

	// 省略記法でのCdr部の探索
	private SExpression searchCdr() throws IOException, LispException {
		if (this.token.getKind() == Token.Kind.RPAREN) {
			// 右括弧がでたら、空リストを挿入してリストを閉じる
			return EmptyList.getInstance();
		}
		if (this.token.getKind() == Token.Kind.DOT) {
			// ドットであれば、それ以上のCdr部がないのでそのまま返す
			this.token = this.lexer.getNextToken();
			return sExpression();
		}

		// Cdr部の探索を続ける
		return ConsCell.getInstance(sExpression(), searchCdr());
	}
}
