package lisp.eval;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import lisp.SetUp;
import lisp.exception.LispException;
import lisp.reader.Reader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestConsCell {
	Reader createReader(String stdin) {
		StringReader sr = new StringReader(stdin);
		BufferedReader br = new BufferedReader(sr);
		return new Reader(br);
	}

	@Test
	public void 省略記法の表示() throws IOException, LispException {
		String stdin = "(quote (1 . (2 . (3 . ()))))";	// (1 2 3)

		Environment env = SetUp.getGlobalEnvironment();
		Reader reader = createReader(stdin);
		SExpression sexp = reader.read();

		SExpression actual = Evaluator.eval(sexp, env);

		System.out.println("lisp> " + stdin);
		System.out.println(actual);

		SExpression expected = ConsCell.getInstance(Int.valueOf(1),
				               ConsCell.getInstance(Int.valueOf(2),
						       ConsCell.getInstance(Int.valueOf(3), EmptyList.getInstance())));
		assertThat(actual, is(expected));
	}

	@Test
	public void 省略記法での入力() throws IOException, LispException {
		String stdin = "(quote (1 (2 3) 4))";

		Environment env = SetUp.getGlobalEnvironment();
		Reader reader = createReader(stdin);
		SExpression sexp = reader.read();

		SExpression actual = Evaluator.eval(sexp, env);

		System.out.println("lisp> " + stdin);
		System.out.println(actual);

		SExpression expected = ConsCell.getInstance(Int.valueOf(1),
				               ConsCell.getInstance(ConsCell.getInstance(Int.valueOf(2), ConsCell.getInstance(Int.valueOf(3), EmptyList.getInstance())),
						       ConsCell.getInstance(Int.valueOf(4), EmptyList.getInstance())));
		assertThat(actual, is(expected));
	}
}
