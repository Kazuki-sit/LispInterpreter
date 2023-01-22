package lisp.reader;

import java.io.BufferedReader;
import java.io.IOException;

import lisp.exception.EndOfFileException;
import lisp.exception.LispException;
import lisp.exception.SyntaxErrorException;

/**
 * 字句解析器
 *
 * @author tetsuya
 *
 */
public class Lexer {
	/**
	 * 入力
	 */
	private BufferedReader in;

	/**
	 * 一行分の入力
	 */
	private char[] line = new char[0];
	private int lineIndex = -1;

	/**
	 * 先読み. 初期値は一番最初に読む式の直前にあることになっている文字なので, (isWhiteSpaceCharメソッドが認める)空白文字なら何でも良い.
	 */
	private char nextChar = ' ';

	/**
	 * コンストラクタ
	 *
	 * @param in 入力
	 */
	public Lexer(BufferedReader in) {
		this.in = in;
	}

	/**
	 * 入力の行末に追加する空白文字. 一つの式の入力が完了したら, 直ちに式の評価を行いたい. しかし式が行末で終了すると,
	 * 先読みを更新するためにユーザに一行分の入力を促すことになり, 式の評価が直ちに行われない. そこで入力の行末に必ず空白文字を追加することにし,
	 * 式の最後に行末がセミコロンで終わらないようにする.
	 */
	private static final char WHITESPACE_AT_EOL = ' '; // 空白文字なら何でも良い.

	/**
	 * 先読みを更新する.
	 *
	 * @throws IOException
	 */
	private void updateNextChar() throws IOException, LispException {
		if (this.lineIndex == this.line.length - 1) { // 次の行を読む.
			String newLine = in.readLine();
			if (newLine == null) {
				throw new EndOfFileException();
			}
			this.line = (newLine + WHITESPACE_AT_EOL).toCharArray(); // 行末には必ず空白文字があることにする.
			this.lineIndex = 0;
			this.nextChar = this.line[this.lineIndex];
		} else { // それ以外
			lineIndex++;
			this.nextChar = this.line[this.lineIndex];
		}
	}

	/**
	 * 空白文字？
	 *
	 * @param ch 調べる文字
	 * @return 空白文字ならtrue
	 */
	private boolean isWhiteSpaceChar(char ch) {
		return ch == ' ' || ch == '\r' || ch == '\n' || ch == '\t';
	}

	/**
	 * 数字?
	 *
	 * @param ch 調べる文字
	 * @return 数字ならtrue
	 */
	private boolean isDigit(char ch) {
		return ch >= '0' && ch <= '9';
	}

	/**
	 * アルファベット(a-z, A-Z)?
	 *
	 * @param ch 調べる文字
	 * @return アルファベットならtrue
	 */
	private boolean isAlphabet(char ch) {
		return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
	}

	/**
	 * 英数字以外に記号として使える文字
	 */
	private final char[] sings = { '!', '$', '%', '&', '*', '+', '-', '.', '/', ':', '<', '=', '>', '?', '@', '^', '_',
			'~' };

	/**
	 * 英数字以外に記号として使える文字
	 *
	 * @param ch 調べる文字
	 * @return 英数字以外に記号として使える文字ならtrue
	 */
	private boolean isSign(char ch) {
		for (char s : sings) {
			if (ch == s) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 記号に使える文字?
	 *
	 * @param ch 調べる文字
	 * @return 記号に使える文字ならtrue
	 */
	private boolean isSymbolChar(char ch) {
		return isDigit(ch) || isAlphabet(ch) || isSign(ch);
	}

	/**
	 * 次の字句を返す.
	 *
	 * @return 次の字句
	 * @throws SyntaxErrorException
	 * @throws IOException
	 */
	public Token getNextToken() throws IOException, LispException {
		// 空白文字を読み飛ばす.
		char ch = this.nextChar;
		while (isWhiteSpaceChar(ch)) {
			updateNextChar();
			ch = this.nextChar;
		}

		// '('
		if (ch == '(') {
			updateNextChar();
			return new Token(Token.Kind.LPAREN);
		}
		// ')'
		if (ch == ')') {
			updateNextChar();
			return new Token(Token.Kind.RPAREN);
		}
		// '.'
		if (ch == '.') {
			updateNextChar();
			return new Token(Token.Kind.DOT);
		}
		// '#t' or '#f'
		if (ch == '#') {
			StringBuffer sb = new StringBuffer();
			sb.append(ch);
			updateNextChar();
			ch = this.nextChar;
			while (isSymbolChar(ch)) {
				sb.append(ch);
				updateNextChar();
				ch = this.nextChar;
			}
			String str = sb.toString();
			if ("#t".equals(str)) {
				return new Token(true);
			} else if ("#f".equals(str)) {
				return new Token(false);
			}
			throw new SyntaxErrorException("Invalid # constant");
		}
		// '''
		if (ch == '\'') {
			updateNextChar();
			return new Token(Token.Kind.QUOTE);
		}
		// ;
		if (ch == ';') {
			updateNextChar();
			return new Token(Token.Kind.SEMICOLON);
		}

		// 整数値 or 記号
		if (isSymbolChar(ch)) {
			boolean startsWithDigit = isDigit(ch);
			StringBuffer sb = new StringBuffer();
			while (isSymbolChar(ch)) {
				sb.append(ch);
				updateNextChar();
				ch = this.nextChar;
			}
			String symbolSequence = sb.toString();

			try {
				int intValue = Integer.parseInt(symbolSequence);
				return new Token(intValue);
			} catch (Exception e) {
				// 整数値としては解釈できなかった
			}

			try {
				float floatValue = Float.parseFloat(symbolSequence);
				return new Token(floatValue);
			} catch (Exception e) {
				//小数値としては解釈できなかった
			}

			if (startsWithDigit) {
				throw new SyntaxErrorException("Unexpected word '" + symbolSequence + "'");
			}
			return new Token(symbolSequence);
		}

		// 認識できなかった.
		updateNextChar(); // 認識できなかった字句を読み捨てる。
		throw new SyntaxErrorException("Unknown char '" + ch + "'");
	}

	public void getNextLine() throws IOException, LispException {
		String newLine = in.readLine();
		if (newLine == null) {
			throw new EndOfFileException();
		}
		this.line = (newLine + WHITESPACE_AT_EOL).toCharArray(); // 行末には必ず空白文字があることにする.
		this.lineIndex = 0;
		this.nextChar = this.line[this.lineIndex];
	}

}
